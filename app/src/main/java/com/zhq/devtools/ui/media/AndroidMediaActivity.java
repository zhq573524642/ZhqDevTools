package com.zhq.devtools.ui.media;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.print.PrintHelper;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.RequestCallback;
import com.zhq.devtools.R;
import com.zhq.devtools.ScreenshotActivity;
import com.zhq.devtools.ScreenshotFileObserverActivity;
import com.zhq.devtools.WebViewActivity;
import com.zhq.devtools.adapter.MainMenuListAdapter;
import com.zhq.toolslib.ToastUtils;
import com.zhq.toolslib.media.AudioFocusManagerUtils;
import com.zhq.toolslib.media.NoisyAudioStreamReceiver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class AndroidMediaActivity extends AppCompatActivity {
    private static final String TAG = "MediaTestActivity";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_PERMISSION_CODE = 1001;
    private static final int REQUEST_VIDEO_CAPTURE = 2;

    private com.zhq.devtools.databinding.ActivityAndroidMediaBinding binding;
    private String[] itemNameArray = {"拍照与图片", "音频", "视频","打印机"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = com.zhq.devtools.databinding.ActivityAndroidMediaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
    }


    private void initView() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MainMenuListAdapter mainMenuListAdapter = new MainMenuListAdapter(Arrays.asList(itemNameArray));
        binding.recyclerView.setAdapter(mainMenuListAdapter);
        mainMenuListAdapter.setOnRecyclerViewItemClickListener(new MainMenuListAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(int position) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(AndroidMediaActivity.this, AndroidPictureActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(AndroidMediaActivity.this, AndroidAudioActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(AndroidMediaActivity.this, AndroidVideoActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(AndroidMediaActivity.this,AndroidPrintActivity.class));
                        break;
                }
            }
        });
    }


}