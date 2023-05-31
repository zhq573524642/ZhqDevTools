package com.zhq.devtools.ui.media;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.zhq.devtools.R;
import com.zhq.devtools.databinding.ActivityGlidePictureLoadBinding;
import com.zhq.toolslib.glide.GlideUtils;
import com.zhq.toolslib.thread.ObserverUtil;
import com.zhq.toolslib.toast.ToastUtils;

public class GlidePictureLoadActivity extends AppCompatActivity {

    private com.zhq.devtools.databinding.ActivityGlidePictureLoadBinding binding;
    private String testImage = "https://img1.baidu.com/it/u=413643897,2296924942&fm=253&fmt=auto&app=138&f=JPEG?w=800&h=500";
    private static final String TAG = "GlidePictureLoadActivit";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGlidePictureLoadBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
    }

    private String imageUrl;
    private int imageResource;
    private Bitmap imageBitmap;
    private int cacheType;
    private int cropImageType;

    private void initView() {
        binding.rgImageType.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_image1:
                    imageUrl = testImage;
                    imageResource = 0;
                    imageBitmap = null;
                    break;
                case R.id.rb_image2:
                    ToastUtils.getInstance().showShortToast(this, "暂不支持");
                    imageUrl = "";
                    imageResource = 0;
                    break;
                case R.id.rb_image3:
                    imageResource = R.drawable.ic_test_img;
                    imageUrl = "";
                    imageBitmap = null;
                    break;
            }
        });

        binding.rgCache.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_cache1:
                    cacheType = GlideUtils.DiskCacheStrategy_ALL;
                    break;
                case R.id.rb_cache2:
                    cacheType = GlideUtils.DiskCacheStrategy_NONE;
                    break;
                case R.id.rb_cache3:
                    cacheType = GlideUtils.DiskCacheStrategy_AUTOMATIC;
                    break;
                case R.id.rb_cache4:
                    cacheType = GlideUtils.DiskCacheStrategy_DATA;
                    break;
                case R.id.rb_cache5:
                    cacheType = GlideUtils.DiskCacheStrategy_RESURCE;
                    break;
            }
        });
        binding.rgCropType.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_crop_type1:
                    cropImageType = GlideUtils.SHOW_CENTER_CROP;
                    break;
                case R.id.rb_crop_type2:
                    cropImageType = GlideUtils.SHOW_CENTER_INSIDE;
                    break;
                case R.id.rb_crop_type3:
                    cropImageType = GlideUtils.SHOW_FIT_CENTER;
                    break;
                case R.id.rb_crop_type4:
                    cropImageType = GlideUtils.SHOW_CIRCLE_CROP;
                    break;
            }
        });
        binding.btnLoadImage.setOnClickListener(v -> {
            binding.imageView.setVisibility(View.VISIBLE);
            loadNormalImage();
        });
        binding.sbCorner.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                binding.imageView.setVisibility(View.VISIBLE);
                GlideUtils.getInstance()
                        .init(getApplicationContext())
                        .loadCornerImageRes(R.drawable.ic_test_img, binding.imageView, progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        binding.btnLoadCornerImage.setOnClickListener(v -> {
            binding.imageView.setVisibility(View.VISIBLE);
            GlideUtils.getInstance()
                    .init(getApplicationContext())
                    .loadCornerImageRes(R.drawable.ic_test_img, binding.imageView, 18);
        });
        binding.btnLoadBlurImage.setOnClickListener(v -> {
            binding.imageView.setVisibility(View.VISIBLE);
            GlideUtils.getInstance()
                    .init(getApplicationContext())
                    .loadBlurBitmap(R.drawable.ic_test_img, binding.imageView);
        });

        binding.btnLoadOriginalImage.setOnClickListener(v -> {
           new  ObserverUtil<Bitmap>().setObserverTask(new ObserverUtil.OnObserverHandleListener<Bitmap>() {
               @Override
               public Bitmap onHandleFunction() {
                   return GlideUtils.getInstance().getBitmapByUrl(getApplicationContext(), testImage);
               }

               @Override
               public void onNext(Bitmap value) {
                   Log.d(TAG, "===bitmap大小: "+value.getByteCount());
                   binding.imageViewLoad.setImageBitmap(value);
               }
           });
        });
        binding.btnLoadSizeImage.setOnClickListener(v -> {
                 GlideUtils.getInstance().loadImageUrlWithListener(getApplicationContext(),
                         testImage, binding.imageViewLoad, new SimpleTarget<Bitmap>() {
                             @Override
                             public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                 Log.d(TAG, "===onResourceReady: "+resource.getByteCount());
                                 binding.imageViewLoad.setImageBitmap(resource);
                             }
                         });
        });
    }


    private void loadNormalImage() {
        if (TextUtils.isEmpty(imageUrl) && imageBitmap == null && imageResource == 0) {
            ToastUtils.getInstance().showShortToast(this, "请选择加载图片类型");
            return;
        }
        if (cacheType == 0) {
            ToastUtils.getInstance().showShortToast(this, "请选择缓存策略");
            return;
        }
        if (cropImageType == 0) {
            ToastUtils.getInstance().showShortToast(this, "请选择图片剪切类型");
            return;
        }
        GlideUtils.getInstance()
                .init(getApplicationContext())
                .loadImageData(imageUrl)
                .loadImageData(imageResource)
                .loadImageData(imageBitmap)
                .setDiskCacheStrategy(cacheType)
                .setCropType(cropImageType)
                .configRequestOptions()
                .loadImage(binding.imageView);
    }
}