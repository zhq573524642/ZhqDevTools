package com.zhq.toolslib.screen;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.MediaStore;
import android.util.Log;

import com.zhq.toolslib.ThreadUtil;

/**
 * @Author ZhangHuiQiang
 * @Date 2023/3/1 14:50
 * Description
 */
public class ScreenshotListenManager {

    private static final String TAG = "ScreenshotListenManager";

    public static ScreenshotListenManager getInstance() {
        return ScreenshotListenManagerHolder.instance;
    }

    private static final class ScreenshotListenManagerHolder {
        private static ScreenshotListenManager instance = new ScreenshotListenManager();
    }

    private static final String[] KEYWORDS = {
            "screenshot", "screen_shot", "screen-shot", "screen shot",
            "screencapture", "screen_capture", "screen-capture", "screen capture",
            "screencap", "screen_cap", "screen-cap", "screen cap", "Screenshots"
    };

    private static final String[] imageType = {"image/png", "image/jpeg"};
    /**
     * 读取媒体数据库时需要读取的列
     */
    private static final String[] MEDIA_PROJECTIONS = {
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,  //图片的显示名称  aaa.jpg
            MediaStore.Images.Media.DATA,  //图片的真实路径  /storage/emulated/0/pp/downloader/wallpaper/aaa.jpg
            MediaStore.Images.Media.SIZE,  //图片的大小，long型  132492
            MediaStore.Images.Media.WIDTH,  //图片的宽度，int型  1s920
            MediaStore.Images.Media.HEIGHT,  //图片的高度，int型  1080
            MediaStore.Images.Media.MIME_TYPE,  //图片的类型     image/jpeg
            MediaStore.Images.Media.DATE_ADDED //图片被添加的时间，long型  1450518608
    };

    private Context mContext;
    private String handlerThreadTag = "Screenshots_Observer";
    /**
     * 内部存储器内容观察者
     */
    private ContentObserver mInternalObserver;

    /**
     * 外部存储器内容观察者
     */
    private ContentObserver mExternalObserver;

    private HandlerThread mHandlerThread;
    private Handler mHandler;
    private ContentResolver contentResolver;

    public void init(Context context, OnScreenshotListenCallback onScreenshotListenCallback) {
        mContext = context;
        this.onScreenshotListenCallback = onScreenshotListenCallback;
        initHandlerAndRegister();
    }

    public void init(Context context, String handleThreadTagName, OnScreenshotListenCallback onScreenshotListenCallback) {
        mContext = context;
        handlerThreadTag = handleThreadTagName;
        this.onScreenshotListenCallback = onScreenshotListenCallback;
        initHandlerAndRegister();
    }

    private OnScreenshotListenCallback onScreenshotListenCallback;

    public interface OnScreenshotListenCallback {
        void onCaptureScreenshotSuccess(String path);

        void onCaptureScreenshotThrowable(String error);
    }

    public ContentResolver getContentResolver() {
        return contentResolver;
    }

    public ContentObserver getInternalObserver() {
        return mInternalObserver;
    }

    public ContentObserver getExternalObserver() {
        return mExternalObserver;
    }

    private void initHandlerAndRegister() {
        if (mContext == null) return;
        contentResolver = mContext.getContentResolver();
        mHandlerThread = new HandlerThread("Screenshots_Observer");
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper());
        // 初始化
        mInternalObserver = new MediaContentObserver(MediaStore.Images.Media.INTERNAL_CONTENT_URI, mHandler);
        mExternalObserver = new MediaContentObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, mHandler);
    }

    public void startListen() {
        if (contentResolver != null) {
            // 添加监听
            contentResolver.registerContentObserver(
                    MediaStore.Images.Media.INTERNAL_CONTENT_URI,
                    true,
                    mInternalObserver
            );
            contentResolver.registerContentObserver(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    true,
                    mExternalObserver
            );
        }
    }

    public void releaseListen() {
        // 注销监听
        if (mHandlerThread != null) {
            mHandlerThread.quitSafely();
        }
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
        if (contentResolver != null) {
            contentResolver.unregisterContentObserver(mInternalObserver);
            contentResolver.unregisterContentObserver(mExternalObserver);
        }
    }

    private void handleMediaContentChange(Uri contentUri) {
        Cursor cursor = null;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                String selection = (MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                        + MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE) + " AND "
                        + MediaStore.Images.Media.MIME_TYPE + "=?"
                        + " or " + MediaStore.Images.Media.MIME_TYPE + "=?";
                String sortOrder = MediaStore.Files.FileColumns._ID + " DESC";
                Bundle queryArgs = new Bundle();
//                queryArgs.putString(ContentResolver.QUERY_ARG_SQL_SELECTION, selection);
//                queryArgs.putStringArray(ContentResolver.QUERY_ARG_SQL_SELECTION_ARGS, imageType);
                queryArgs.putString(ContentResolver.QUERY_ARG_SQL_SORT_ORDER, sortOrder);
                queryArgs.putString(ContentResolver.QUERY_ARG_SQL_LIMIT, "1 offset 0");
                cursor = contentResolver.query(
                        contentUri,
                        MEDIA_PROJECTIONS,
                        queryArgs, null);
            } else {
                // 数据改变时查询数据库中最后加入的一条数据
                String selection = (MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                        + MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE) + " AND "
                        + MediaStore.Images.Media.MIME_TYPE + "=?"
                        + " or " + MediaStore.Images.Media.MIME_TYPE + "=?";
                String sortOrder = MediaStore.Images.Media._ID + " DESC limit 1 ";
                cursor = contentResolver.query(
                        contentUri,
                        MEDIA_PROJECTIONS,
                        selection,
                        imageType,
                        sortOrder);
            }


            if (cursor == null) {
                if (onScreenshotListenCallback != null) {
                    onScreenshotListenCallback.onCaptureScreenshotThrowable("cursor cannot null");
                }
                return;
            }
            if (!cursor.moveToFirst()) {
                if (onScreenshotListenCallback != null) {
                    onScreenshotListenCallback.onCaptureScreenshotThrowable("cursor cannot moveToFirst");
                }
                return;
            }

            // 获取各列的索引
            int dataIndex = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
//            int dateTakenIndex = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATE_TAKEN);
            int dateAddedIndex = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATE_ADDED);

            // 获取行数据
            String data = cursor.getString(dataIndex);
//            long dateTaken = cursor.getLong(dateTakenIndex);
            long dateAdded = cursor.getLong(dateAddedIndex);
            // 处理获取到的第一行数据
            handleMediaRowData(data, dateAdded);

        } catch (Exception e) {
            e.printStackTrace();
            if (onScreenshotListenCallback != null) {
                onScreenshotListenCallback.onCaptureScreenshotThrowable(e.getMessage());
            }
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
    }

    /**
     * 判断时间是否合格，图片插入时间小于1秒才有效
     */
    private boolean isTimeValid(long dateAdded) {
        return Math.abs(System.currentTimeMillis() / 1000 - dateAdded) <= 1;
    }

    /**
     * 处理监听到的资源
     */
    private void handleMediaRowData(String data, long dateTaken) {
        long duration = 0;
        long step = 100;
        // 发现个别手机会自己修改截图文件夹的文件，截屏功能会误以为是用户在截屏操作，进行捕获，所以加了一个时间判断
        if (!isTimeValid(dateTaken)) {
            Log.d(TAG, "===图片插入时间大于1秒，不是截屏");
            return;
        }
        while (!checkScreenShot(data) && duration <= 500) {
            try {
                duration += step;
                Thread.sleep(step);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (checkScreenShot(data)) {
            ThreadUtil.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (onScreenshotListenCallback != null) {
                        onScreenshotListenCallback.onCaptureScreenshotSuccess(data);
                    }
                }
            });
        } else {
            if (onScreenshotListenCallback != null) {
                onScreenshotListenCallback.onCaptureScreenshotThrowable("未检测到截屏操作");
            }
        }
    }

    /**
     * 判断是否是截屏
     */
    private boolean checkScreenShot(String data) {
        if (data == null) return false;
        data = data.toLowerCase();
        // 判断图片路径是否含有指定的关键字之一, 如果有, 则认为当前截屏了
        for (String keyWork : KEYWORDS) {
            if (data.contains(keyWork)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 媒体内容观察者(观察媒体数据库的改变)
     */
    private class MediaContentObserver extends ContentObserver {

        private Uri mContentUri;

        public MediaContentObserver(Uri contentUri, Handler handler) {
            super(handler);
            mContentUri = contentUri;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            handleMediaContentChange(mContentUri);
        }
    }
}
