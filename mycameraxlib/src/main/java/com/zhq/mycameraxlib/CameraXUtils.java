package com.zhq.mycameraxlib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.util.Size;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.camera.core.AspectRatio;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraControl;
import androidx.camera.core.CameraInfo;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageInfo;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.video.ActiveRecording;
import androidx.camera.video.FileOutputOptions;
import androidx.camera.video.QualitySelector;
import androidx.camera.video.Recorder;
import androidx.camera.video.VideoCapture;
import androidx.camera.video.VideoRecordEvent;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.core.util.Consumer;
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
    private VideoCapture<Recorder> videoCapture;
    private ImageCapture imageCapture;
    private boolean isBackCamera = true;
    private CameraSelector cameraSelector;
    private CameraControl cameraControl;
    private CameraInfo cameraInfo;
    private LifecycleOwner lifecycleOwner;
    private int flashMode = ImageCapture.FLASH_MODE_AUTO;
    private int jpegQuality = 100;
    private int recordQualitySelector = QualitySelector.QUALITY_FHD;
    private int captureMode = ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY;
    private int targetAspectRatio = AspectRatio.RATIO_16_9;

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

    /**
     * 设置默认的闪光灯类型 默认自动 ImageCapture.FLASH_MODE_AUTO 设置在setPreviewType之前
     *
     * @param flashMode
     * @return
     */
    public CameraXUtils setFlashModeDefault(int flashMode) {
        this.flashMode = flashMode;
        return this;
    }

    /**
     * 设置拍照图片质量
     *
     * @param jpegQuality 默认100  设置在setPreviewType之前
     * @return
     */
    public CameraXUtils setJpegQuality(int jpegQuality) {
        this.jpegQuality = jpegQuality;
        return this;
    }

    /**
     * 设置录制的质量
     *
     * @param recordQualitySelector 默认 QualitySelector.QUALITY_FHD 1080p 设置在setPreviewType之前
     * @return
     */
    public CameraXUtils setRecordQualitySelector(int recordQualitySelector) {
        this.recordQualitySelector = recordQualitySelector;
        return this;
    }

    /**
     * 拍摄模式
     *
     * @param captureMode ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY 注重捕获速度而不注重图像质量
     *                    ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY 注重捕获图像质量而不注重延迟
     * @return
     */
    public CameraXUtils setCaptureMode(int captureMode) {
        this.captureMode = captureMode;
        return this;
    }

    /**
     * 设置目标外观比例
     *
     * @param targetAspectRatio AspectRatio.RATIO_16_9
     *                          AspectRatio.RATIO_4_3
     * @return
     */
    public CameraXUtils setTargetAspectRatio(int targetAspectRatio) {
        this.targetAspectRatio = targetAspectRatio;
        return this;
    }

    private int previewType = PREVIEW_TYPE_IMAGE;

    //3.设置预览类型
    public CameraXUtils setPreviewType(int previewType) {
        this.previewType = previewType;
        if (previewType == PREVIEW_TYPE_IMAGE) {
            //创建ImageCapture
            imageCapture = new ImageCapture.Builder()
                    .setCaptureMode(captureMode)
                    .setJpegQuality(jpegQuality)
                    .setFlashMode(flashMode)
                    .setTargetAspectRatio(targetAspectRatio)
                    .build();
        } else if (previewType == PREVIEW_TYPE_VIDEO) {
            //创建Record
            Recorder recorder = new Recorder.Builder()
                    .setQualitySelector(QualitySelector.of(recordQualitySelector))
                    .build();
            //创建VideoCapture
            videoCapture = VideoCapture.withOutput(recorder);
        } else {
            //创建Record
            Recorder recorder = new Recorder.Builder()
                    .setQualitySelector(QualitySelector.of(recordQualitySelector))
                    .build();
            //创建VideoCapture
            videoCapture = VideoCapture.withOutput(recorder);
            //创建ImageCapture
            imageCapture = new ImageCapture.Builder()
                    .setCaptureMode(captureMode)
                    .setJpegQuality(jpegQuality)
                    .setFlashMode(flashMode)
                    .setTargetAspectRatio(targetAspectRatio)
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
                .setTargetAspectRatio(targetAspectRatio)
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

    private ImageAnalysis imageAnalysis;

    public CameraXUtils initImageAnalysis(Executor executor) {
        imageAnalysis = new ImageAnalysis.Builder()
                // enable the following line if RGBA output is needed.
                //.setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888)
                .setTargetResolution(new Size(1280, 720))
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build();
        imageAnalysis.setAnalyzer(executor, new ImageAnalysis.Analyzer() {
            @Override
            public void analyze(@NonNull ImageProxy image) {
                ImageInfo imageInfo = image.getImageInfo();
                Log.d(TAG, "===analyze: " + imageInfo.getRotationDegrees());
                image.close();
            }
        });
        return this;
    }


    public void bindPreview(ProcessCameraProvider cameraProvider, int previewType) {
        if (lifecycleOwner == null) {
            throw new NullPointerException("lifecycle is null,Please setLifecycle first");
        }
        Camera camera = null;
        try {
            cameraProvider.unbindAll();
            //前后置摄像头切换
            cameraSelector = isBackCamera ? CameraSelector.DEFAULT_BACK_CAMERA : CameraSelector.DEFAULT_FRONT_CAMERA;
            if (previewType == PREVIEW_TYPE_IMAGE) {
                if (imageAnalysis != null) {
                    camera = cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, imageCapture,imageAnalysis, preview);
                } else {
                    camera = cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, imageCapture, preview);
                }

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

    public void switchCamera() {
        isBackCamera = !isBackCamera;
        if (cameraProvider != null) {
            bindPreview(cameraProvider, previewType);
        }
    }

    public int switchFlashMode() {
        if (imageCapture != null) {
            switch (imageCapture.getFlashMode()) {
                case ImageCapture.FLASH_MODE_AUTO:
                    imageCapture.setFlashMode(ImageCapture.FLASH_MODE_ON);
                    flashMode = ImageCapture.FLASH_MODE_ON;
                    break;
                case ImageCapture.FLASH_MODE_ON:
                    imageCapture.setFlashMode(ImageCapture.FLASH_MODE_OFF);
                    flashMode = ImageCapture.FLASH_MODE_OFF;
                    break;
                case ImageCapture.FLASH_MODE_OFF:
                    imageCapture.setFlashMode(ImageCapture.FLASH_MODE_AUTO);
                    flashMode = ImageCapture.FLASH_MODE_AUTO;
                    break;
            }
        }
        return flashMode;
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

    private ActiveRecording activeRecording;
    private File tempVideoSaveFile;

    @SuppressLint("MissingPermission")
    public void takeVideo(File saveVideoFile, @NonNull Executor mainExecutor) {
        if (videoCapture == null) {
            throw new NullPointerException("videoCapture is null,Please setPreviewType first");
        }
        tempVideoSaveFile = saveVideoFile;
        FileOutputOptions outputOptions = new FileOutputOptions.Builder(saveVideoFile).build();
        activeRecording = videoCapture.getOutput().prepareRecording(context, outputOptions)
                .withAudioEnabled()
                .withEventListener(mainExecutor, videoRecordEventListener)
                .start();
    }

    private Consumer<VideoRecordEvent> videoRecordEventListener = new Consumer<VideoRecordEvent>() {
        @Override
        public void accept(VideoRecordEvent videoRecordEvent) {
            if (onCameraXUtilsListener != null) {
                if (videoRecordEvent instanceof VideoRecordEvent.Start) {
                    onCameraXUtilsListener.onVideoRecording(videoRecordEvent);
                } else if (videoRecordEvent instanceof VideoRecordEvent.Finalize) {
                    onCameraXUtilsListener.onVideoRecordFinish(videoRecordEvent, tempVideoSaveFile);
                }
            }
        }
    };

    public void stopVideoRecording() {
        if (activeRecording != null) {
            activeRecording.stop();
            activeRecording = null;
        }
    }


    public void releaseCameraX() {
        if (cameraProvider != null) {
            cameraProvider.unbindAll();
            cameraProvider = null;
        }
        if (cameraProviderFuture != null) {
            cameraProviderFuture.cancel(true);
        }
    }

    public interface OnCameraXUtilsListener {
        default void onPictureSavedSuccess(ImageCapture.OutputFileResults outputFileResults, Uri saveUri, String picturePath) {
        }

        default void onPictureSavedError(ImageCaptureException exception) {
        }

        default void onVideoRecording(VideoRecordEvent videoRecordEvent) {
        }

        default void onVideoRecordFinish(VideoRecordEvent videoRecordEvent, File videoFile) {
        }
    }

    private OnCameraXUtilsListener onCameraXUtilsListener;

    public CameraXUtils setCameraXUtilsCallback(OnCameraXUtilsListener onCameraXUtilsListener) {
        this.onCameraXUtilsListener = onCameraXUtilsListener;
        return this;
    }
}
