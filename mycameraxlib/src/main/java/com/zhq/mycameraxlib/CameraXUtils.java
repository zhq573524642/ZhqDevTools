package com.zhq.mycameraxlib;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.camera.core.AspectRatio;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraControl;
import androidx.camera.core.CameraInfo;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.video.QualitySelector;
import androidx.camera.video.Recorder;
import androidx.camera.video.VideoCapture;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.google.common.util.concurrent.ListenableFuture;
import com.zhq.toolslib.ThreadUtil;

import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CameraXUtils {
    private static final String TAG = "CameraXUtils";

    private Preview preview;

    public static CameraXUtils getInstance() {
        return CameraXUtilsHolder.instance;
    }

    private static final class CameraXUtilsHolder {
        public static final CameraXUtils instance = new CameraXUtils();
    }

    public static final int PREVIEW_TYPE_IMAGE = 0;
    public static final int PREVIEW_TYPE_VIDEO = 1;
    public static final int PREVIEW_TYPE_IMAGE_AND_VIDEO = 2;
    private Context context;
    public ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    public ProcessCameraProvider cameraProvider;
    private PreviewView previewView;
    private static final int DEFAULT_ASPECT_RATIO = AspectRatio.RATIO_16_9;
    private VideoCapture<Recorder> videoCapture;
    private ImageCapture imageCapture;
    private boolean isBackCamera = true;
    private CameraSelector cameraSelector;
    private CameraControl cameraControl;
    private CameraInfo cameraInfo;
    private LifecycleOwner lifecycleOwner;

    //1.传入上下文
    public CameraXUtils setContext(Context context) {
        this.context = context;
        return this;
    }

    public CameraXUtils setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner;
        return this;
    }

    //2.设置显示的previewView
    public CameraXUtils setPreviewView(PreviewView previewView) {
        this.previewView = previewView;
        return this;
    }

    private int previewType = PREVIEW_TYPE_IMAGE;

    //3.设置预览类型
    public CameraXUtils setPreviewType(int previewType) {
        this.previewType = previewType;
        if (previewType == PREVIEW_TYPE_IMAGE) {
            //创建ImageCapture
            imageCapture = new ImageCapture.Builder()
                    .setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY)
                    .setJpegQuality(100)
                    .setFlashMode(ImageCapture.FLASH_MODE_AUTO)
                    .setTargetAspectRatio(DEFAULT_ASPECT_RATIO)
                    .build();
        } else if (previewType == PREVIEW_TYPE_VIDEO) {
            //创建Record
            Recorder recorder = new Recorder.Builder()
                    .setQualitySelector(QualitySelector.of(QualitySelector.QUALITY_FHD))
                    .build();
            //创建VideoCapture
            videoCapture = VideoCapture.withOutput(recorder);
        } else {
            //创建Record
            Recorder recorder = new Recorder.Builder()
                    .setQualitySelector(QualitySelector.of(QualitySelector.QUALITY_FHD))
                    .build();
            //创建VideoCapture
            videoCapture = VideoCapture.withOutput(recorder);
            //创建ImageCapture
            imageCapture = new ImageCapture.Builder()
                    .setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY)
                    .setJpegQuality(100)
                    .setFlashMode(ImageCapture.FLASH_MODE_AUTO)
                    .setTargetAspectRatio(DEFAULT_ASPECT_RATIO)
                    .build();
        }
        return this;
    }

    //4.设置默认的摄像头显示方向
    public CameraXUtils setCameraSelectorDefault(boolean isBackCamera) {
        this.isBackCamera = isBackCamera;
        //前后置摄像头切换
        cameraSelector = isBackCamera ? CameraSelector.DEFAULT_BACK_CAMERA : CameraSelector.DEFAULT_FRONT_CAMERA;
        return this;
    }

    //5.创建preview
    public CameraXUtils createPreview() {
        //创建preview
        preview = new Preview.Builder()
                .setTargetAspectRatio(DEFAULT_ASPECT_RATIO)
                .build();
        if (previewView != null) {
            preview.setSurfaceProvider(previewView.getSurfaceProvider());
        } else {
            throw new NullPointerException("previewView is null,Please setPreviewView first");
        }
        return this;
    }

    //4.初始化ProcessCameraProvider
    public CameraXUtils initProcessCameraProvider() {
        if (context == null) {
            throw new NullPointerException("context is null,Please setContext first");
        }
        cameraProviderFuture = ProcessCameraProvider.getInstance(context);
        cameraProviderFuture.addListener(() -> {
            try {
                cameraProvider = cameraProviderFuture.get();
                if (previewView != null) {
                    bindPreview(cameraProvider, previewType);
                } else {
                    throw new NullPointerException("previewView is null,Please setPreviewView first");
                }
            } catch (ExecutionException | InterruptedException e) {
                // No errors need to be handled for this Future.
                // This should never be reached.
            }
        }, ContextCompat.getMainExecutor(context));
        return this;
    }


    public void bindPreview(ProcessCameraProvider cameraProvider, int previewType) {
        if (lifecycleOwner == null) {
            throw new NullPointerException("lifecycle is null,Please setLifecycle first");
        }
        Camera camera = null;
        try {
            cameraProvider.unbindAll();
            if (previewType == PREVIEW_TYPE_IMAGE) {
                camera = cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, imageCapture, preview);

            } else if (previewType == PREVIEW_TYPE_VIDEO) {
                camera = cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, videoCapture, preview);

            } else {
                camera = cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, videoCapture, imageCapture, preview);

            }
            // 获取CameraControl
            cameraControl = camera.getCameraControl();
            //获取CameraInfo
            cameraInfo = camera.getCameraInfo();
        } catch (Exception e) {
            //重置
        }
    }


    public void takePicture(File savePictureFile, @NonNull Executor executorService) {
        if (imageCapture == null) {
            throw new NullPointerException("imageCapture is null,Please setPreviewType first");
        }
        //文件存储位置
        ImageCapture.Metadata metadata = new ImageCapture.Metadata();
        metadata.setReversedHorizontal(!isBackCamera);
        ImageCapture.OutputFileOptions outputFileOptions =
                new ImageCapture.OutputFileOptions.Builder(savePictureFile)
                        .setMetadata(metadata)
                        .build();
        imageCapture.takePicture(outputFileOptions, executorService, new ImageCapture.OnImageSavedCallback() {
            @Override
            public void onImageSaved(@androidx.annotation.NonNull ImageCapture.OutputFileResults outputFileResults) {
                Uri savedUri = outputFileResults.getSavedUri();
                if (savedUri != null) {
                    ThreadUtil.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (onCameraXUtilsListener != null) {
                                onCameraXUtilsListener.onPictureSavedSuccess(outputFileResults, savedUri, savedUri.getPath());
                            }
                        }
                    });

                }
            }

            @Override
            public void onError(@androidx.annotation.NonNull ImageCaptureException exception) {
                if (onCameraXUtilsListener != null) {
                    onCameraXUtilsListener.onPictureSavedError(exception);
                }
            }
        });


    }


    public void releaseCameraX(){
        if (cameraProvider != null) {
            cameraProvider.unbindAll();
            cameraProvider = null;
        }
        if (cameraProviderFuture != null) {
            cameraProviderFuture.cancel(true);
        }
    }

    public interface OnCameraXUtilsListener {
        void onPictureSavedSuccess(ImageCapture.OutputFileResults outputFileResults, Uri saveUri, String picturePath);

        void onPictureSavedError(ImageCaptureException exception);
    }

    private OnCameraXUtilsListener onCameraXUtilsListener;

    public CameraXUtils setCameraXUtilsCallback(OnCameraXUtilsListener onCameraXUtilsListener) {
        this.onCameraXUtilsListener = onCameraXUtilsListener;
        return this;
    }
}
