package com.zhq.devtools;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.print.PrintHelper;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.media.AudioDeviceInfo;
import android.media.AudioFocusRequest;
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
import android.widget.RadioGroup;

import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.RequestCallback;
import com.zhq.devtools.databinding.ActivityMediaTestBinding;
import com.zhq.toolslib.ToastUtils;
import com.zhq.toolslib.media.AudioFocusManagerUtils;
import com.zhq.toolslib.media.NoisyAudioStreamReceiver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MediaTestActivity extends AppCompatActivity implements NoisyAudioStreamReceiver.OnNoisyAudioStreamReceiverListener {
    private static final String TAG = "MediaTestActivity";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_PERMISSION_CODE = 1001;
    private static final int REQUEST_VIDEO_CAPTURE = 2;
    private NoisyAudioStreamReceiver noisyAudioStreamReceiver;
    private IntentFilter intentFilter;
    private com.zhq.devtools.databinding.ActivityMediaTestBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMediaTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        requestAudioFocus();
        initView();
    }

    private boolean isSimplePhoto = true;

    private void initView() {
        binding.rgSimplePhoto.setOnCheckedChangeListener((group, checkedId) -> {
            isSimplePhoto = checkedId == 1;
        });
        binding.btnSimplePhoto.setOnClickListener(v -> {
            PackageManager packageManager = getPackageManager();
            if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
                handleSimpleCameraPhoto();
            } else {
                ToastUtils.getInstance().showShortToast(this, "没有相机");
            }
        });
        binding.btnShowPhoto.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(mCurrentPhotoPath)) {
                setImage();
            } else {
                ToastUtils.getInstance().showShortToast(this, "请先拍照");
            }
        });

        binding.btnSimpleVideo.setOnClickListener(v -> {
            PackageManager packageManager = getPackageManager();
            if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
                handleSimpleCameraVideo();
            } else {
                ToastUtils.getInstance().showShortToast(this, "没有相机");
            }
        });

        binding.btnPrintImage.setOnClickListener(v -> {
            PrintHelper photoPrinter = new PrintHelper(this);
            photoPrinter.setScaleMode(PrintHelper.SCALE_MODE_FIT);
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                    R.drawable.ic_test_img);
            photoPrinter.printBitmap("droids.jpg - test print", bitmap);
        });
        binding.btnPrintHtml.setOnClickListener(v -> {
            WebViewActivity.start(this,"https://beta-front.yiboshi.com/resident-h5/#/");
        });
        binding.btnScreentshotFileobserver.setOnClickListener(v -> {
            PermissionX.init(this)
                    .permissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.MANAGE_EXTERNAL_STORAGE)
                    .request(new RequestCallback() {
                        @Override
                        public void onResult(boolean allGranted, @NonNull List<String> grantedList, @NonNull List<String> deniedList) {
                            if (allGranted) {
                                startActivity(new Intent(MediaTestActivity.this, ScreenshotFileObserverActivity.class));
                            }
                        }
                    });

        });
        binding.btnScreentshot.setOnClickListener(v -> {
            PermissionX.init(this)
                    .permissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.MANAGE_EXTERNAL_STORAGE)
                    .request(new RequestCallback() {
                        @Override
                        public void onResult(boolean allGranted, @NonNull List<String> grantedList, @NonNull List<String> deniedList) {
                            if (allGranted) {
                                startActivity(new Intent(MediaTestActivity.this, ScreenshotActivity.class));
                            }
                        }
                    });

        });

    }

    private void handleSimpleCameraVideo() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }

    private void handleSimpleCameraPhoto() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
                PermissionX.init(this)
                        .permissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                                , Manifest.permission.MANAGE_EXTERNAL_STORAGE)
                        .request(new RequestCallback() {
                            @Override
                            public void onResult(boolean allGranted, @NonNull List<String> grantedList, @NonNull List<String> deniedList) {
                                if (allGranted) {
                                    goCameraPhoto();
                                } else {
                                    if (deniedList != null) {
                                        for (int i = 0; i < deniedList.size(); i++) {
                                            ToastUtils.getInstance().showShortToast(MediaTestActivity.this, deniedList.get(i) + "权限未获取");
                                        }
                                    }
                                }
                            }
                        });
            } else {
                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, REQUEST_PERMISSION_CODE);
                }
            }

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionX.init(this)
                    .permissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .request(new RequestCallback() {
                        @Override
                        public void onResult(boolean allGranted, @NonNull List<String> grantedList, @NonNull List<String> deniedList) {
                            if (allGranted) {
                                goCameraPhoto();
                            } else {
                                if (deniedList != null) {
                                    for (int i = 0; i < deniedList.size(); i++) {
                                        ToastUtils.getInstance().showShortToast(MediaTestActivity.this, deniedList.get(i) + "权限未获取");
                                    }
                                }
                            }
                        }
                    });
        } else {
            goCameraPhoto();
        }
    }

    private void goCameraPhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            if (!isSimplePhoto) {
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    Log.d(TAG, "===IOException: " + ex.getMessage());
                }
                if (photoFile != null) {
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
//                        Uri.fromFile(photoFile));
                    //报 android.os.FileUriExposedException: file:///storage/emulated/0/Pictures/JPEG_20230228_161143_8762253550302050912.jpg
                    // exposed beyond app through ClipData.Item.getUri()

                    Uri photoUri = FileProvider.getUriForFile(
                            this,
                            getPackageName() + ".fileprovider",
                            photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                            photoUri);
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

                }
            } else {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private void setImage() {
        int targetW = binding.imageView.getWidth();
        int targetH = binding.imageView.getHeight();
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;
        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        binding.imageView.setVisibility(View.VISIBLE);
        binding.imageView.setImageBitmap(bitmap);

    }

    private String mCurrentPhotoPath;

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
//        Uri contentUri = Uri.fromFile(f);
        Uri contentUri = FileProvider.getUriForFile(
                this,
                getPackageName() + ".fileprovider",
                f);
        //报 IllegalArgumentException: Failed to find configured root that contains
        //在  file_provider_paths.xml中添加
        //<root-path
        //        name="root_path"
        //        path="." />
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    private File createImageFile() throws IOException {
// Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName, /* prefix */
                ".jpg", /* suffix */
                storageDir /* directory */
        );
// Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (resultCode == RESULT_OK) {
                if (!isSimplePhoto) {
                    galleryAddPic();
                } else {
                    if (data != null) {
                        Bundle extras = data.getExtras();
                        Bitmap bitmap = (Bitmap) extras.get("data");
                        binding.imageView.setImageBitmap(bitmap);
                    }
                }
            }
        } else if (requestCode == REQUEST_VIDEO_CAPTURE) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    Uri uri = data.getData();
                    binding.videoView.setVisibility(View.VISIBLE);
                    binding.videoView.setVideoURI(uri);
                    binding.videoView.start();
                }
            }
        } else if (requestCode == REQUEST_PERMISSION_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                    PermissionX.init(this)
                            .permissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.MANAGE_EXTERNAL_STORAGE)
                            .request(new RequestCallback() {
                                @Override
                                public void onResult(boolean allGranted, @NonNull List<String> grantedList, @NonNull List<String> deniedList) {
                                    if (allGranted) {
                                        goCameraPhoto();
                                    } else {
                                        if (deniedList != null) {
                                            for (int i = 0; i < deniedList.size(); i++) {
                                                ToastUtils.getInstance().showShortToast(MediaTestActivity.this, deniedList.get(i) + "权限未获取");
                                            }
                                        }
                                    }
                                }
                            });
                }
            }
        }
    }

    private void requestAudioFocus() {
        AudioFocusManagerUtils.getInstance().setOnRequestAudioFocusListener(new AudioFocusManagerUtils.OnRequestAudioFocusListener() {
            @Override
            public void onRequestAudioFocusState(int requestAudioFocus) {

            }

            @Override
            public void onAudioFocusChange(int focusChange) {

            }
        });
        AudioFocusManagerUtils.getInstance().requestAudioFocus(this, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK);
        //判断当前音频输出设备
        AudioManager audioManager = AudioFocusManagerUtils.getInstance().setAudioManager();
        if (audioManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                for (AudioDeviceInfo device : audioManager.getDevices(AudioManager.GET_DEVICES_OUTPUTS)) {

                }

            } else {
                if (audioManager.isBluetoothA2dpOn()) {
                    //蓝牙耳机
                } else if (audioManager.isSpeakerphoneOn()) {
                    //扬声器
                } else if (audioManager.isWiredHeadsetOn()) {
                    //这并不是一个有效的指示，音频播放实际上是通过有线耳机，因为音频路由取决于其他条件。
                    //连线耳机
                } else {

                }
            }

        }

        //当输出设备断开连接，比如耳机断开连接，需要停止当前播放的音频，以免扬声器播放太大声
        noisyAudioStreamReceiver = new NoisyAudioStreamReceiver(this);
        intentFilter = new IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY);
        registerNoisyAudioReceiver();
    }

    private void registerNoisyAudioReceiver() {
        registerReceiver(noisyAudioStreamReceiver, intentFilter);
    }

    private void unregisterNoisyAudioReceiver() {
        if (noisyAudioStreamReceiver != null) {
            unregisterReceiver(noisyAudioStreamReceiver);
            noisyAudioStreamReceiver = null;
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (AudioManager.ACTION_AUDIO_BECOMING_NOISY.equals(intent.getAction())) {
            // Pause the playback
            //暂停当前音频播放
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterNoisyAudioReceiver();
    }
}