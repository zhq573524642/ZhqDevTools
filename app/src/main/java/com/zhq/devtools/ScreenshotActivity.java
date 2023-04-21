package com.zhq.devtools;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;

import com.zhq.devtools.databinding.ActivityScreenshotBinding;
import com.zhq.toolslib.thread.ThreadUtil;
import com.zhq.toolslib.toast.ToastUtils;
import com.zhq.toolslib.screen.ScreenshotListenManager;

public class ScreenshotActivity extends AppCompatActivity implements ScreenshotListenManager.OnScreenshotListenCallback {
    private static final String TAG = "ScreenshotActivity";
    private com.zhq.devtools.databinding.ActivityScreenshotBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScreenshotBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ScreenshotListenManager.getInstance().init(ScreenshotActivity.this, this);
        ScreenshotListenManager.getInstance().startListen();
    }

    protected void onDestroy() {
        super.onDestroy();
        ScreenshotListenManager.getInstance().releaseListen();
    }

    @Override
    public void onCaptureScreenshotSuccess(String path) {
        if (!TextUtils.isEmpty(path)) {
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            if (bitmap != null) {
                binding.imageView.setImageBitmap(bitmap);
            }
        }
    }

    @Override
    public void onCaptureScreenshotThrowable(String error) {
        ThreadUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtils.getInstance().showShortToast(ScreenshotActivity.this,error);
            }
        });

    }
}