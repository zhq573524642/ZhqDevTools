package com.zhq.devtools.widget;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.text.util.Linkify;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.zhq.devtools.R;
import com.zhq.devtools.base.BaseActivity;
import com.zhq.devtools.databinding.ActivityGeneralViewBinding;
import com.zhq.toolslib.toast.ToastUtils;

/**
 * @Author ZhangHuiQiang
 * @Date 2023/11/13 15:02
 * Description
 */
public class GeneralViewActivity extends BaseActivity<ActivityGeneralViewBinding> {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_general_view;
    }

    @Override
    protected void initView() {
        //1.普通TextView的样式
        //2.TextView自动识别Link
        mBinding.textView2.setText("百度一下：www.baidu.com");
        mBinding.textView2.setAutoLinkMask(Linkify.ALL);
        mBinding.textView2.setMovementMethod(LinkMovementMethod.getInstance());
        //3.TextView通过Html样式
        String s="<font color='yellow'><b><i><big>百度一下</big></i></b></font><br>";
        String s1=s+"<a href='http://www.baidu.com'>百度</a>";
        mBinding.textView3.setText(Html.fromHtml(s1));
        mBinding.textView3.setMovementMethod(LinkMovementMethod.getInstance());
        //4.SpannableString
        SpannableString span = new SpannableString("红色打电话斜体删除线绿色下划线图片:.");
        //1.设置背景色和前景色（字体颜色）,setSpan时需要指定的flag,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE(前后都不包括)
        span.setSpan(new BackgroundColorSpan(Color.YELLOW), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        span.setSpan(new ForegroundColorSpan(Color.RED), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //2.用超链接标记文本
        span.setSpan(new URLSpan("tel:17610676602"), 2, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //3.用样式标记文本（粗体+斜体）
        span.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), 5, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //4.用删除线标记文本
        span.setSpan(new StrikethroughSpan(), 7, 10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //5.用下划线标记文本
        span.setSpan(new UnderlineSpan(), 10, 16, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //6.用颜色标记
        span.setSpan(new ForegroundColorSpan(Color.GREEN), 10, 13,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //7.获取Drawable资源
        Drawable d = getResources().getDrawable(R.drawable.ic_seek_bar_thumb,getTheme());
        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
        //8.创建ImageSpan,然后用ImageSpan来替换文本
        ImageSpan imgspan = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
        span.setSpan(imgspan, 18, 19, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        mBinding.textView4.setText(span);
        //通过SpannableString实现朋友圈点赞样式
        clickLikesStyle();
        //EditText
        mBinding.btnEditText.setOnClickListener(v -> {
            startActivity(new Intent(mContext,EditTextActivity.class));
        });
    }

    private void clickLikesStyle() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 30; i++) {
            sb.append("好友"+i+",");
        }
        String likeUsers = sb.substring(0, sb.lastIndexOf(",")).toString();
        mBinding.textView5.setMovementMethod(LinkMovementMethod.getInstance());
        mBinding.textView5.setText(addTextClick(likeUsers), TextView.BufferType.SPANNABLE);
    }

    private SpannableStringBuilder addTextClick(String likeUsers) {
        ImageSpan imageSpan = new ImageSpan(mContext, R.drawable.ic_rating_bar_thumb);
        SpannableString spannableStr = new SpannableString("a.");
        spannableStr.setSpan(imageSpan,0,1,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        SpannableStringBuilder ssb = new SpannableStringBuilder(spannableStr);
        ssb.append(likeUsers);
        String[] users = likeUsers.split(",");
        if (users.length>0){
            for (int i = 0; i < users.length; i++) {
                String name = users[i];
                int start = likeUsers.indexOf(name) + spannableStr.length();
                ssb.setSpan(new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        ToastUtils.getInstance().showShortToast(mContext,name);
                    }

                    @Override
                    public void updateDrawState(@NonNull TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setColor(Color.BLUE);
                        ds.setUnderlineText(false);
                    }
                },start,start+name.length(),0);
            }
        }
        return ssb.append("等"+users.length+"个人觉得很赞");
    }
}
