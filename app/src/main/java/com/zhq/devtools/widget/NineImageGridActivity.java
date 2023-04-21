package com.zhq.devtools.widget;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.zhq.devtools.R;
import com.zhq.devtools.databinding.ActivityNineImageGridBinding;
import com.zhq.devtools.widget.nine_grid.NineGridView;
import com.zhq.devtools.widget.nine_grid.NineImageAdapter;

import java.util.ArrayList;
import java.util.List;

import byc.imagewatcher.CustomDotIndexProvider;
import byc.imagewatcher.GlideSimpleLoader;
import byc.imagewatcher.ImageWatcher;
import byc.imagewatcher.ImageWatcherHelper;
import byc.imagewatcher.ProgressView;

public class NineImageGridActivity extends AppCompatActivity {

    private com.zhq.devtools.databinding.ActivityNineImageGridBinding binding;
    private List<String> imageUrlList = new ArrayList<>();
    private List<Uri> imageUriList = new ArrayList<>();
    private NineImageAdapter nineImageAdapter;
    private RequestOptions requestOptions;
    private ImageWatcherHelper imageWatcherHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNineImageGridBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
    }

    private int imageShowCount = 9;

    private void initView() {
        initImageWatcher();
        binding.btnImageCount.setText("图片数量：" + imageShowCount);
        binding.btnImageCount.setOnClickListener(v -> {
            imageShowCount++;
            if (imageShowCount > 9) {
                imageShowCount = 1;
            }
            binding.btnImageCount.setText("图片数量：" + imageShowCount);
            makeImages(imageShowCount);
            initAdapter();
            binding.nineGridView.setAdapter(nineImageAdapter);
        });
        makeImages(imageShowCount);
        requestOptions = new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        initAdapter();

    }

    private void initAdapter() {
        nineImageAdapter = new NineImageAdapter(this, requestOptions, DrawableTransitionOptions.withCrossFade(), imageUrlList);
        binding.nineGridView.setAdapter(nineImageAdapter);
        binding.nineGridView.setOnImageClickListener(new NineGridView.OnImageClickListener() {
            @Override
            public void onImageClick(int position, View view) {
                parseStringToUri();
                imageWatcherHelper.show((ImageView) view, binding.nineGridView.getImageViews(), imageUriList);
//                binding.imageWatcher.show((ImageView) view, binding.nineGridView.getImageViews(), imageUriList);
            }
        });
    }

    private void parseStringToUri() {
        imageUriList.clear();
        for (String s : imageUrlList) {
            Uri uri = Uri.parse(s);
            imageUriList.add(uri);
        }
    }


    private void makeImages(int imageCount) {
        imageUrlList.clear();
        for (int i = 0; i < imageCount; i++) {
            imageUrlList.add(DataConstants.IMAGE_URL_9[i]);
        }
    }

    private static final String TAG = "NineImageGridActivity";

    private void initImageWatcher() {
        // 自定义LoadingUI，并不一定要调用这个API
        imageWatcherHelper = ImageWatcherHelper.with(NineImageGridActivity.this, new GlideSimpleLoader()) // 一般来讲， ImageWatcher 需要占据全屏的位置
                .setTranslucentStatus(0) // 如果不是透明状态栏，你需要给ImageWatcher标记 一个偏移值，以修正点击ImageView查看的启动动画的Y轴起点的不正确
                .setErrorImageRes(R.mipmap.error_picture) // 配置error图标 如果不介意使用lib自带的图标，并不一定要调用这个API
                .setOnPictureLongPressListener(new ImageWatcher.OnPictureLongPressListener() {
                    @Override
                    public void onPictureLongPress(ImageView v, Uri uri, int pos) {

                    }
                })//长安监听，并不一定要调用这个API
                .setOnStateChangedListener(new ImageWatcher.OnStateChangedListener() {
                    @Override
                    public void onStateChangeUpdate(ImageWatcher imageWatcher, ImageView clicked, int position, Uri uri, float animatedValue, int actionTag) {
                        Log.e("IW", "onStateChangeUpdate [" + position + "][" + uri + "][" + animatedValue + "][" + actionTag + "]");
                    }

                    @Override
                    public void onStateChanged(ImageWatcher imageWatcher, int position, Uri uri, int actionTag) {
                        if (actionTag == ImageWatcher.STATE_ENTER_DISPLAYING) {
                            Log.d(TAG, "===点击了图片 [" + position + "]" + uri + "");
                        } else if (actionTag == ImageWatcher.STATE_EXIT_HIDING) {
                            Log.d(TAG, "===退出了查看大图: ");
                        }
                    }
                })
                .setIndexProvider(new CustomDotIndexProvider())//自定义页码指示器（默认数字），并不一定要调用这个API
                .setLoadingUIProvider(new LoadingUI());

        // 如果不是透明状态栏，你需要给ImageWatcher标记 一个偏移值，以修正点击ImageView查看的启动动画的Y轴起点的不正确
//        binding.imageWatcher.setTranslucentStatus(StatusBarUtils.getStatusBarHeight(this));
//        // 配置error图标 如果不介意使用lib自带的图标，并不一定要调用这个API
//        binding.imageWatcher.setErrorImageRes(R.mipmap.error_picture);
//        // 长按图片的回调，你可以显示一个框继续提供一些复制，发送等功能
//        binding.imageWatcher.setOnPictureLongPressListener(new ImageWatcher.OnPictureLongPressListener() {
//            @Override
//            public void onPictureLongPress(ImageView v, Uri uri, int pos) {
//
//            }
//        });
//        binding.imageWatcher.setLoader(new GlideSimpleLoader());
//        binding.imageWatcher.setOnStateChangedListener(new ImageWatcher.OnStateChangedListener() {
//            @Override
//            public void onStateChangeUpdate(ImageWatcher imageWatcher, ImageView clicked, int position, Uri uri, float animatedValue, int actionTag) {
//                Log.e("IW", "onStateChangeUpdate [" + position + "][" + uri + "][" + animatedValue + "][" + actionTag + "]");
//            }
//
//            @Override
//            public void onStateChanged(ImageWatcher imageWatcher, int position, Uri uri, int actionTag) {
//                if (actionTag == ImageWatcher.STATE_ENTER_DISPLAYING) {
//                    Toast.makeText(getApplicationContext(), "点击了图片 [" + position + "]" + uri + "", Toast.LENGTH_SHORT).show();
//                } else if (actionTag == ImageWatcher.STATE_EXIT_HIDING) {
//                    Toast.makeText(getApplicationContext(), "退出了查看大图", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }

    public class LoadingUI implements ImageWatcher.LoadingUIProvider {

        final FrameLayout.LayoutParams lpCenterInParent = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);

        @Override
        public View initialView(Context context) {
            lpCenterInParent.gravity = Gravity.CENTER;
            ProgressView progressView = new ProgressView(context);
            progressView.setLayoutParams(lpCenterInParent);
            return progressView;
        }

        @Override
        public void start(View loadView) {
            loadView.setVisibility(View.VISIBLE);
            ((ProgressView) loadView).start();
        }

        @Override
        public void stop(View loadView) {
            ((ProgressView) loadView).stop();
            loadView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        if (!imageWatcherHelper.handleBackPressed()) {
            super.onBackPressed();
        }
//        if (!binding.imageWatcher.handleBackPressed()){
//            super.onBackPressed();
//        }
    }
}