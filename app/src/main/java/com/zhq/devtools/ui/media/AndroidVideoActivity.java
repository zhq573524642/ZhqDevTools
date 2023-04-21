package com.zhq.devtools.ui.media;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import com.zhq.devtools.databinding.ActivityAndroidVideoBinding;
import com.zhq.toolslib.toast.ToastUtils;

public class AndroidVideoActivity extends AppCompatActivity {

    private com.zhq.devtools.databinding.ActivityAndroidVideoBinding binding;
    private static final int REQUEST_PERMISSION_CODE = 1001;
    private static final int REQUEST_VIDEO_CAPTURE = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAndroidVideoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
    }

    private void initView() {
        binding.btnSimpleVideo.setOnClickListener(v -> {
            PackageManager packageManager = getPackageManager();
            if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
                handleSimpleCameraVideo();
            } else {
                ToastUtils.getInstance().showShortToast(this, "没有相机");
            }
        });
    }

    private void handleSimpleCameraVideo() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_VIDEO_CAPTURE) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    Uri uri = data.getData();
                    binding.videoView.setVisibility(View.VISIBLE);
                    binding.videoView.setVideoURI(uri);
                    binding.videoView.start();
                }
            }
        }
    }
}