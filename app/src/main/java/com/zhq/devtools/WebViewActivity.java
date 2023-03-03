package com.zhq.devtools;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.just.agentweb.AgentWeb;
import com.just.agentweb.AgentWebConfig;
import com.just.agentweb.DefaultWebClient;
import com.just.agentweb.PermissionInterceptor;
import com.just.agentweb.WebChromeClient;
import com.just.agentweb.WebViewClient;
import com.zhq.devtools.databinding.ActivityWebViewBinding;

import java.util.ArrayList;
import java.util.List;

public class WebViewActivity extends AppCompatActivity {

    private com.zhq.devtools.databinding.ActivityWebViewBinding binding;
    private AgentWeb mAgentWeb;
    private String webUrl;

    public static void start(Context context, String url) {
        if (context == null) return;
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("web_url", url);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWebViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        if (intent == null) return;
        webUrl = intent.getStringExtra("web_url");
        //WebView载入该url地址的页面并显示。
        mAgentWeb = AgentWeb.with(this)//
                .setAgentWebParent((LinearLayout) binding.llParentLayout, -1, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))//传入AgentWeb的父控件。
                .useDefaultIndicator(Color.RED, 3)//设置进度条颜色与高度，-1为默认值，高度为2，单位为dp。
                .setWebViewClient(mWebViewClient)//WebViewClient ， 与 WebView 使用一致 ，但是请勿获取WebView调用setWebViewClient(xx)方法了,会覆盖AgentWeb DefaultWebClient,同时相应的中间件也会失效。
                .setWebChromeClient(mWebChromeClient) //WebChromeClient
                .setPermissionInterceptor(mPermissionInterceptor) //权限拦截 2.0.0 加入。
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK) //严格模式 Android 4.2.2 以下会放弃注入对象 ，使用AgentWebView没影响。
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//打开其他页面时，弹窗质询用户前往其他应用 AgentWeb 3.0.0 加入。
                .interceptUnkownUrl() //拦截找不到相关页面的Url AgentWeb 3.0.0 加入。
                .createAgentWeb()//创建AgentWeb。
                .ready()//设置 WebSettings。
                .go(webUrl);
        WebJavascriptInterface mJavascriptInterface = new WebJavascriptInterface();
        mAgentWeb.getJsInterfaceHolder().addJavaObject("webCall", mJavascriptInterface);
        mAgentWeb.clearWebCache();
        AgentWebConfig.clearDiskCache(this);
        mAgentWeb.getWebCreator().getWebView().setOverScrollMode(WebView.OVER_SCROLL_NEVER);
    }


    private class WebJavascriptInterface {

        @JavascriptInterface
        public void finishPage() {
            WebViewActivity.this.finish();
        }
    }

    private WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            //do you  work
        }


        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            createWebPrintJob(view);
        }
    };

    protected WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }
    };

    protected PermissionInterceptor mPermissionInterceptor = new PermissionInterceptor() {

        /**
         * PermissionInterceptor 能达到 url1 允许授权， url2 拒绝授权的效果。
         * @param url
         * @param permissions
         * @param action
         * @return true 该Url对应页面请求权限进行拦截 ，false 表示不拦截。
         */
        @Override
        public boolean intercept(String url, String[] permissions, String action) {
            return false;
        }
    };


    /***********************其他功能******************************/
    private List<PrintJob> mPrintJobs = new ArrayList<>();

    private void createWebPrintJob(WebView webView) {
// Get a PrintManager instance
        PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);
// Get a print adapter instance
        PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter();
// Create a print job with name and adapter instance
        String jobName = getString(R.string.app_name) + " Document";
        PrintJob printJob = printManager.print(jobName, printAdapter,
                new PrintAttributes.Builder().build());
// Save the job object for later status checking
        mPrintJobs.add(printJob);
    }
}