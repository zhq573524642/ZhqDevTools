package com.zhq.toolslib;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.ViewTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;
import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;


import jp.wasabeef.glide.transformations.BlurTransformation;


/**
 * Created by Huiqiang Zhang
 * on 2020/7/29
 */
public class GlideUtils {

    private static final String TAG = "GlideUtils";
    private RequestOptions options;

    public static GlideUtils getInstance() {
        return GlideUtilsHolder.instance;
    }

    private static final class GlideUtilsHolder {
        private static GlideUtils instance = new GlideUtils();
    }

    public static final int DiskCacheStrategy_ALL = 1;
    public static final int DiskCacheStrategy_NONE = 2;
    public static final int DiskCacheStrategy_AUTOMATIC = 3;
    public static final int DiskCacheStrategy_DATA = 4;
    public static final int DiskCacheStrategy_RESURCE = 5;

    //options.centerCrop();
    //        options.centerInside();
    //        options.fitCenter();
    public static final int SHOW_CENTER_CROP = 10;
    public static final int SHOW_CENTER_INSIDE = 11;
    public static final int SHOW_FIT_CENTER = 12;
    public static final int SHOW_CIRCLE_CROP = 13;

    private Context context;
    private String imageUrl;
    private int imageResId;
    private Bitmap imageBitmap;
    private Drawable imageDrawable;
    private Uri imageUri;
    private File imageFile;
    private byte[] imageByte;

    public GlideUtils init(Context context) {
        this.context = context;
        return this;
    }

    /***********************加载不同类型图片*************************/
    public GlideUtils loadImageData(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public GlideUtils loadImageData(int imageResId) {
        this.imageResId = imageResId;
        return this;
    }

    public GlideUtils loadImageData(Bitmap bitmap) {
        this.imageBitmap = bitmap;
        return this;
    }

    public GlideUtils loadImageData(Drawable drawable) {
        this.imageDrawable = drawable;
        return this;
    }

    public GlideUtils loadImageData(Uri uri) {
        this.imageUri = uri;
        return this;
    }

    public GlideUtils loadImageData(File file) {
        this.imageFile = file;
        return this;
    }

    public GlideUtils loadImageData(byte[] mode) {
        this.imageByte = mode;
        return this;
    }

    /*******************设置不同类型 placeholder和error*********************/
    private int placeholderRes;
    private Drawable placeholderDrawable;
    private int errorRes;
    public Drawable errorDrawable;

    public GlideUtils setPlaceholder(int placeholderRes) {
        this.placeholderRes = placeholderRes;
        return this;
    }

    public GlideUtils setPlaceholder(Drawable placeholderDrawable) {
        this.placeholderDrawable = placeholderDrawable;
        return this;
    }

    public GlideUtils setErrorImage(int errorRes) {
        this.errorRes = errorRes;
        return this;
    }

    public GlideUtils setErrorImage(Drawable errorDrawable) {
        this.errorDrawable = errorDrawable;
        return this;
    }

    /**********************缓存相关设置**************************/
    private boolean skipMemoryCache = false;

    public GlideUtils skipMemoryCache(boolean skipMemoryCache) {
        this.skipMemoryCache = skipMemoryCache;
        return this;
    }

    private boolean dontAnimate;

    public GlideUtils dontAnimate(boolean dontAnimate) {
        this.dontAnimate = dontAnimate;
        return this;
    }

    private int diskCacheStrategy = DiskCacheStrategy_AUTOMATIC;

    public GlideUtils setDiskCacheStrategy(int diskCacheStrategy) {
        this.diskCacheStrategy = diskCacheStrategy;
        return this;
    }

    /************************图片缩放类型*******************************/
    private int cropType=SHOW_FIT_CENTER;
    public GlideUtils setCropType(int cropType){
        this.cropType=cropType;
        return this;
    }

    /************************初始化RequestOptions**********************/
    public GlideUtils configRequestOptions() {
        options = new RequestOptions();
        if (dontAnimate) {
            options.dontAnimate();
        }
        options.skipMemoryCache(skipMemoryCache);
        switch (diskCacheStrategy) {
            case DiskCacheStrategy_ALL:
                options.diskCacheStrategy(DiskCacheStrategy.ALL);
                break;
            case DiskCacheStrategy_NONE:
                options.diskCacheStrategy(DiskCacheStrategy.NONE);
                break;
            case DiskCacheStrategy_AUTOMATIC:
                options.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
                break;
            case DiskCacheStrategy_DATA:
                options.diskCacheStrategy(DiskCacheStrategy.DATA);
                break;
            case DiskCacheStrategy_RESURCE:
                options.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
                break;
            default:
                break;
        }
        if (placeholderRes != 0) {
            options.placeholder(placeholderRes);
        }
        if (placeholderDrawable != null) {
            options.placeholder(placeholderDrawable);
        }
        if (errorRes != 0) {
            options.error(errorRes);
        }
        if (errorDrawable != null) {
            options.error(errorDrawable);
        }
        switch (cropType){
            case SHOW_CENTER_CROP:
                options.centerCrop();
                break;
            case SHOW_CENTER_INSIDE:
                options.centerInside();
                break;
            case SHOW_FIT_CENTER:
                options.fitCenter();
                break;
            case SHOW_CIRCLE_CROP:
                options.circleCrop();
                break;
        }
        return this;
    }


    /**
     * 加载头像 不使用缓存 保证修改头像及时更新
     *
     * @param imageView
     */
    public GlideUtils loadImageUrl(ImageView imageView) {
        if (options != null) {
            Glide.with(context)
                    .load(imageUrl)
                    .apply(options)
                    .into(imageView);
        } else {
            throw new NullPointerException("RequestOptions cannot null");
        }
        return this;
    }

    public static Bitmap getBitmapByUrl(Context context, String imageUrl) {
        Bitmap bitmap = null;

        try {
            bitmap = Glide.with(context)
                    .asBitmap()
                    .load(R.mipmap.ic_test_icon)
                    .submit()
                    .get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return bitmap;
    }

    public static void loadBlurBitmap(Context context, int resId, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .bitmapTransform(new BlurTransformation(10, 35));
        Glide.with(context)
                .load(resId)
                .apply(options)
                .into(new ViewTarget<ImageView, Drawable>(imageView) {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable>
                            transition) {
                        Drawable drawable = resource.getCurrent();
                        drawable.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
                        imageView.setImageDrawable(drawable);
                    }
                });
    }


    /**
     * 加载圆角图片资源
     *
     * @param imageRes
     * @param imageView
     */
    public static void loadCornerImageRes(Context context, int imageRes, ImageView imageView) {
        Glide.with(context)
                .load(imageRes)
                .transform(new MultiTransformation<Bitmap>(new CenterCrop(), new RoundedCorners(18)))//相当于两种变换的交集（圆角时前面的变换可以根据情况设定）
//                        .dontTransform()//不使用任何变换
                .into(imageView);
    }

    public static void loadImageUrlWithListener(Context context, String imageUrl, ImageView imageView, RequestListener<Drawable> requestListener) {
        RequestOptions options = new RequestOptions()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                /*.placeholder(R.mipmap.ic_default_avatar)
                .error(R.mipmap.ic_default_avatar)*/;
        Glide.with(context)
                .load(imageUrl)
                .apply(options)
                .listener(requestListener)
                .into(imageView);
//        RequestListener<Drawable> requestListener = new RequestListener<Drawable>() {
//            @Override
//            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                //加载失败
//                if () {
//
//                }
//                return false;
//            }
//
//            @Override
//            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                //加载完成
//
////                        target.getSize(new SizeReadyCallback() {
////                            @Override
////                            public void onSizeReady(int width, int height) {
////
////                            }
////                        });
//
//                return false;
//            }
//        };
    }

    public static void loadCornerImageUrlWithListener(Context context, String imageUrl, ImageView imageView, RequestListener<Drawable> requestListener) {
        CornerTransform cornerTransform = new CornerTransform(context, DensityUtil.dp2px(context, 8));
        cornerTransform.setExceptCorner(false, false, true, true);
        RequestOptions options = new RequestOptions()
                .skipMemoryCache(true)
                .transform(cornerTransform)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
               /* .placeholder(R.mipmap.ic_default_avatar)
                .error(R.mipmap.ic_default_avatar)*/;
        Glide.with(context)
                .load(imageUrl)
                .apply(options)
                .listener(requestListener)
                .into(imageView);
//        RequestListener<Drawable> requestListener = new RequestListener<Drawable>() {
//            @Override
//            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                //加载失败
//                if () {
//
//                }
//                return false;
//            }
//
//            @Override
//            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                //加载完成
//
////                        target.getSize(new SizeReadyCallback() {
////                            @Override
////                            public void onSizeReady(int width, int height) {
////
////                            }
////                        });
//
//                return false;
//            }
//        };
    }

    public static void showCornerImage(Context context, String imageUrl, ImageView imageView, int radius, boolean leftTop, boolean rightTop, boolean leftBootom, boolean rightBootom) {
        CornerTransform cornerTransform = new CornerTransform(context, DensityUtil.dp2px(context, radius));
        cornerTransform.setExceptCorner(leftTop, rightTop, leftBootom, rightBootom);
        RequestOptions options = new RequestOptions();
        RequestOptions requestOptions = options
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(cornerTransform)
                .dontAnimate();
        Glide.with(context)
                .asBitmap()
                .load(imageUrl)
                .apply(requestOptions)
                .into(imageView);
    }

    public static void loadImageResWithListener(Context context, int imgRes, ImageView imageView, RequestListener<Drawable> requestListener) {
        RequestOptions options = new RequestOptions()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                /*.placeholder(R.mipmap.ic_default_avatar)
                .error(R.mipmap.ic_default_avatar)*/;
        Glide.with(context)
                .load(imgRes)
                .apply(options)
                .listener(requestListener)
                .into(imageView);
//        RequestListener<Drawable> requestListener = new RequestListener<Drawable>() {
//            @Override
//            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                //加载失败
//                if () {
//
//                }
//                return false;
//            }
//
//            @Override
//            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                //加载完成
//
////                        target.getSize(new SizeReadyCallback() {
////                            @Override
////                            public void onSizeReady(int width, int height) {
////
////                            }
////                        });
//
//                return false;
//            }
//        };
    }


//    public interface OnImageLoadStatusListener{
//        void onImageLoadSuccess(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource);
//        void onImageLoadedSize(int width, int height);
//        void onImageLoadFailed();
//    }
//
//    private OnImageLoadStatusListener onImageLoadStatusListener;
//
//    public void setOnImageLoadStatusListener(OnImageLoadStatusListener onImageLoadStatusListener) {
//        this.onImageLoadStatusListener = onImageLoadStatusListener;
//    }


    public static void getGrayBitmap(final Context context, final String url, final ImageView imageView) {
        ThreadUtil.runOnSubThread(new Runnable() {
            @Override
            public void run() {
                try {
                    final Bitmap bitmap = Glide.with(context).asBitmap().load(url).submit().get();
                    ThreadUtil.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (bitmap == null) {
                                return;
                            }
                            int width = bitmap.getWidth();
                            int height = bitmap.getHeight();
                            Bitmap grayImg = null;
                            try {

                                grayImg = Bitmap.createBitmap(width, height,
                                        Bitmap.Config.ARGB_8888);
                                Canvas canvas = new Canvas(grayImg);
                                Paint paint = new Paint();
                                ColorMatrix colorMatrix = new ColorMatrix();//仰仗这玩意了
                                colorMatrix.setSaturation(0);
                                ColorMatrixColorFilter colorMatrixFilter = new ColorMatrixColorFilter(
                                        colorMatrix);
                                paint.setColorFilter(colorMatrixFilter);
                                canvas.drawBitmap(bitmap, 0, 0, paint);
                                imageView.setImageBitmap(grayImg);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * 青醋图片磁盘缓存
     */
    public static void clearImageDiskCache(Context context) {
        try {
            if (ThreadUtil.isMainThread()) {
                ThreadUtil.runOnSubThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(context).clearDiskCache();
                    }
                });
            } else {
                Glide.get(context).clearDiskCache();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 清除图片内存缓存
     */
    public static void clearImageMemoryCache(Context context) {
        try {
            if (ThreadUtil.isMainThread()) { //只能在主线程执行
                Glide.get(context).clearMemory();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取Glide造成的缓存大小
     *
     * @return CacheSize
     */
    public static String getCacheSize(Context context) {
        try {
            return getFormatSize(getFolderSize(new File(context.getCacheDir() + "/" + InternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取指定文件夹内所有文件大小的和
     *
     * @param file file
     * @return size
     * @throws Exception
     */
    private static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (File aFileList : fileList) {
                if (aFileList.isDirectory()) {
                    size = size + getFolderSize(aFileList);
                } else {
                    size = size + aFileList.length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 删除指定目录下的文件，这里用于缓存的删除
     *
     * @param filePath       filePath
     * @param deleteThisPath deleteThisPath
     */
    private void deleteFolderFile(String filePath, boolean deleteThisPath) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {
                    File files[] = file.listFiles();
                    for (File file1 : files) {
                        deleteFolderFile(file1.getAbsolutePath(), true);
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory()) {
                        file.delete();
                    } else {
                        if (file.listFiles().length == 0) {
                            file.delete();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 格式化单位
     *
     * @param size size
     * @return size
     */
    private static String getFormatSize(double size) {

        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "Byte";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);

        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }

}
