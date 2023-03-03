package com.zhq.devtools;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileObserver;
import android.text.TextUtils;
import android.util.Log;

import com.zhq.devtools.databinding.ActivityScreenshotFileObserverBinding;

import java.io.File;

public class ScreenshotFileObserverActivity extends AppCompatActivity {

    private com.zhq.devtools.databinding.ActivityScreenshotFileObserverBinding binding;
    private static final String TAG = "ScreenshotFileProviderA";
    private FileObserver fileObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScreenshotFileObserverBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
    }

    private String lastShotPath;
    private static final int MAX_TRY_COUNT = 10;

    private void initView() {
        String shotPath = Environment.getExternalStorageDirectory()
                + File.separator + Environment.DIRECTORY_DCIM
                + File.separator + "Screenshots" + File.separator;
        fileObserver = new FileObserver(shotPath, FileObserver.CREATE) {

            @Override
            public void onEvent(int event, @Nullable String path) {
                Log.d(TAG, "===FileObserver-OnEvent: " + event + "==" + path);
                if (null != path && !TextUtils.isEmpty(path) && event == FileObserver.CREATE && !path.equals(lastShotPath)) {
                    int i = path.lastIndexOf("-");
                    String realPath = path.substring(i + 1);
                    lastShotPath = realPath;
                    String filePath = shotPath + realPath;
                    int tryTimes = 0;
                    while (true) {
                        try {
                            Thread.sleep(600);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        try {
                            Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                            if (bitmap != null) {
                                Log.d(TAG, "===检测到的截图: ");
                                binding.imageView.setImageBitmap(bitmap);
                                binding.imageView.setBackgroundColor(Color.RED);
                                break;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            tryTimes++;
                            if (tryTimes >= MAX_TRY_COUNT) {
                                Log.d(TAG, "===没有检测到合适的: ");
                                return;
                            }
                        }
                    }
                }
            }
        };
        fileObserver.startWatching();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (fileObserver != null) {
            fileObserver.stopWatching();
        }
    }
}