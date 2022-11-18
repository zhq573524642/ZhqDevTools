package com.zhq.devtools;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.RequestCallback;
import com.zhq.devtools.databinding.ActivityTestCameraXactivityBinding;
import com.zhq.mycameraxlib.CameraPreviewActivity;

import java.util.List;

public class TestCameraXActivity extends AppCompatActivity {

    private com.zhq.devtools.databinding.ActivityTestCameraXactivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTestCameraXactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnTakePicture.setOnClickListener(v -> {
            PermissionX.init(this)
                    .permissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .request(new RequestCallback() {
                        @Override
                        public void onResult(boolean allGranted, @NonNull List<String> grantedList, @NonNull List<String> deniedList) {
                            if (allGranted) {
                                startActivity(new Intent(TestCameraXActivity.this,CameraPreviewActivity.class));
                            } else {
                                Toast.makeText(TestCameraXActivity.this, "为获取权限" + deniedList.get(0), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        });
    }
}