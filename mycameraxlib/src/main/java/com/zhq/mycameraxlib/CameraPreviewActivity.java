package com.zhq.mycameraxlib;

import android.Manifest;
import android.net.Uri;
import android.os.Environment;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.view.PreviewView;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;

import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.ExplainReasonCallbackWithBeforeParam;
import com.permissionx.guolindev.callback.RequestCallback;
import com.permissionx.guolindev.request.ExplainScope;
import com.zhq.mycameraxlib.databinding.ActivityCameraPreviewBinding;
import com.zhq.toolslib.ToastUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CameraPreviewActivity extends FragmentActivity {
    private static final String TAG = "CameraPreviewActivity";
    private com.zhq.mycameraxlib.databinding.ActivityCameraPreviewBinding binding;
    private ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCameraPreviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        executorService = Executors.newSingleThreadExecutor();
        initCameraXUtils();
        binding.btnTakePicture.setOnClickListener(v -> {
            takePicture();
        });
    }

    private void takePicture() {
        File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.US).format(System.currentTimeMillis()) + ".jpg");
        CameraXUtils.getInstance()
                .takePicture(file, executorService);
    }

    private void initCameraXUtils() {
        CameraXUtils.getInstance()
                .setContext(CameraPreviewActivity.this)
                .setLifecycleOwner((LifecycleOwner) this)
                .setPreviewView(binding.previewView)
                .setPreviewType(CameraXUtils.PREVIEW_TYPE_IMAGE)
                .setCameraSelectorDefault(true)
                .createPreview()
                .setCameraXUtilsCallback(new CameraXUtils.OnCameraXUtilsListener() {
                    @Override
                    public void onPictureSavedSuccess(ImageCapture.OutputFileResults outputFileResults, Uri saveUri, String picturePath) {
                      ToastUtils.getInstance().showShortToast(CameraPreviewActivity.this,"????????????");
                    }

                    @Override
                    public void onPictureSavedError(ImageCaptureException exception) {
                    }
                })
                .initProcessCameraProvider();
        //????????????????????????
        binding.previewView.setImplementationMode(PreviewView.ImplementationMode.PERFORMANCE);
        //??????????????????
        binding.previewView.setScaleType(PreviewView.ScaleType.FILL_CENTER);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CameraXUtils.getInstance().releaseCameraX();
    }
}