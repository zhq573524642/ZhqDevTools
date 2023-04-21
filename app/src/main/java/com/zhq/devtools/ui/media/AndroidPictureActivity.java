package com.zhq.devtools.ui.media;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.RequestCallback;
import com.zhq.devtools.ScreenshotActivity;
import com.zhq.devtools.ScreenshotFileObserverActivity;
import com.zhq.devtools.TestCameraXActivity;
import com.zhq.devtools.databinding.ActivityAndroidPictureBinding;
import com.zhq.toolslib.thread.ThreadUtil;
import com.zhq.toolslib.toast.ToastUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AndroidPictureActivity extends AppCompatActivity {
    private static final String TAG = "AndroidPictureActivity";
    private com.zhq.devtools.databinding.ActivityAndroidPictureBinding binding;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_PERMISSION_CODE = 1001;
    private boolean isSimplePhoto = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAndroidPictureBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
    }

    private void initView() {
        binding.btnSimplePhoto.setOnClickListener(v -> {
            isSimplePhoto = true;
            PackageManager packageManager = getPackageManager();
            if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
                handleSimpleCameraPhoto();
            } else {
                ToastUtils.getInstance().showShortToast(this, "没有相机");
            }
        });
        binding.btnSavePhoto.setOnClickListener(v -> {
            isSimplePhoto = false;
            PackageManager packageManager = getPackageManager();
            if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
                handleSimpleCameraPhoto();
            } else {
                ToastUtils.getInstance().showShortToast(this, "没有相机");
            }
        });


        binding.btnScreentshotFileobserver.setOnClickListener(v -> {
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
                                        startActivity(new Intent(AndroidPictureActivity.this, ScreenshotFileObserverActivity.class));
                                    } else {
                                        if (deniedList != null) {
                                            for (int i = 0; i < deniedList.size(); i++) {
                                                ToastUtils.getInstance().showShortToast(AndroidPictureActivity.this, deniedList.get(i) + "权限未获取");
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
                                    startActivity(new Intent(AndroidPictureActivity.this, ScreenshotFileObserverActivity.class));
                                } else {
                                    if (deniedList != null) {
                                        for (int i = 0; i < deniedList.size(); i++) {
                                            ToastUtils.getInstance().showShortToast(AndroidPictureActivity.this, deniedList.get(i) + "权限未获取");
                                        }
                                    }
                                }
                            }
                        });
            } else {
                startActivity(new Intent(AndroidPictureActivity.this, ScreenshotFileObserverActivity.class));
            }

        });
        binding.btnScreentshot.setOnClickListener(v -> {
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
                                        startActivity(new Intent(AndroidPictureActivity.this, ScreenshotActivity.class));
                                    } else {
                                        if (deniedList != null) {
                                            for (int i = 0; i < deniedList.size(); i++) {
                                                ToastUtils.getInstance().showShortToast(AndroidPictureActivity.this, deniedList.get(i) + "权限未获取");
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
                                    startActivity(new Intent(AndroidPictureActivity.this, ScreenshotActivity.class));
                                } else {
                                    if (deniedList != null) {
                                        for (int i = 0; i < deniedList.size(); i++) {
                                            ToastUtils.getInstance().showShortToast(AndroidPictureActivity.this, deniedList.get(i) + "权限未获取");
                                        }
                                    }
                                }
                            }
                        });
            } else {
                startActivity(new Intent(AndroidPictureActivity.this, ScreenshotActivity.class));
            }

        });

        binding.btnCameraxTest.setOnClickListener(v -> {
            startActivity(new Intent(this, TestCameraXActivity.class));
        });
        binding.btnGlideLoad.setOnClickListener(v -> {
            startActivity(new Intent(this,GlidePictureLoadActivity.class));
        });
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
                                            ToastUtils.getInstance().showShortToast(AndroidPictureActivity.this, deniedList.get(i) + "权限未获取");
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
                                        ToastUtils.getInstance().showShortToast(AndroidPictureActivity.this, deniedList.get(i) + "权限未获取");
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
        binding.imageView.setVisibility(View.VISIBLE);
//        int targetW = binding.imageView.getWidth();
//        int targetH = binding.imageView.getHeight();
//        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
//        bmOptions.inJustDecodeBounds = true;
        Bitmap bitmap1 = BitmapFactory.decodeFile(mCurrentPhotoPath);
//        int photoW = bmOptions.outWidth;
//        int photoH = bmOptions.outHeight;
//        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);
//        bmOptions.inJustDecodeBounds = false;
//        bmOptions.inSampleSize = scaleFactor;
//        bmOptions.inPurgeable = true;
//        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);

        binding.imageView.setImageBitmap(bitmap1);
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
        ThreadUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                binding.tvPhotoSavePath.setText("保存路径：" + mCurrentPhotoPath);
                setImage();
            }
        });
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
                        binding.imageView.setVisibility(View.VISIBLE);
                        binding.imageView.setImageBitmap(bitmap);
                    }
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
                                                ToastUtils.getInstance().showShortToast(AndroidPictureActivity.this, deniedList.get(i) + "权限未获取");
                                            }
                                        }
                                    }
                                }
                            });
                }
            }
        }
    }


}