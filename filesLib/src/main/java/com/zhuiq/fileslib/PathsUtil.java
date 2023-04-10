package com.zhuiq.fileslib;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * @Author ZhangHuiQiang
 * @Date 2023/4/10 14:06
 * Description
 */
public class PathsUtil {
    private static final String TAG = "PathsUtil";

    /**
     * Environment.DIRECTORY_MUSIC,
     * Environment.DIRECTORY_PODCASTS,
     * Environment.DIRECTORY_RINGTONES,
     * Environment.DIRECTORY_ALARMS,
     * Environment.DIRECTORY_NOTIFICATIONS,
     * Environment.DIRECTORY_PICTURES,
     * or Environment.DIRECTORY_MOVIES
     *
     * @param context
     */
    public static void getExternalPrivateStorage(Context context) {

        //外部存储：私有目录（其他应用可访问，应用卸载数据消失）
        File externalFilesDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (externalFilesDir != null) {
            //TODO /storage/emulated/0/Android/data/com.zhq.devtools/files/Pictures
            Log.d(TAG, "===context.getExternalFilesDir(Environment.DIRECTORY_PICTURES): " + externalFilesDir.getPath());
        }
        File[] externalFilesDirs = context.getExternalFilesDirs(Environment.DIRECTORY_PICTURES);
        if (externalFilesDirs != null) {
            for (int i = 0; i < externalFilesDirs.length; i++) {
                //TODO 0=/storage/emulated/0/Android/data/com.zhq.devtools/files/Pictures
                Log.d(TAG, "===externalFilesDirs: " + i + "=" + externalFilesDirs[i].getPath());
            }
        }
        File externalCacheDir = context.getExternalCacheDir();
        if (externalCacheDir != null) {
            //TODO /storage/emulated/0/Android/data/com.zhq.devtools/cache
            Log.d(TAG, "===context.getExternalCacheDir(): " + externalCacheDir.getPath());
        }
        File[] externalCacheDirs = context.getExternalCacheDirs();
        if (externalCacheDirs != null) {
            for (int i = 0; i < externalCacheDirs.length; i++) {
                //TODO 0 = /storage/emulated/0/Android/data/com.zhq.devtools/cache
                Log.d(TAG, "===externalCacheDirs: " + i + "=" + externalCacheDirs[i].getPath());
            }
        }

        File[] externalMediaDirs = context.getExternalMediaDirs();
        if (externalMediaDirs != null) {
            for (int i = 0; i < externalMediaDirs.length; i++) {
                //TODO 0  =   /storage/emulated/0/Android/media/com.zhq.devtools
                Log.d(TAG, "===externalMediaDirs: " + i + "=" + externalMediaDirs[i].getPath());
            }
        }

    }

    public static void getExternalPublicStorage(){
        //外部存储：公共存储（自由访问，应用卸载数据不消失）
        String externalStorageState = Environment.getExternalStorageState();
        //TODO mounted
        Log.d(TAG, "===externalStorageState: " + externalStorageState);
        if (Environment.MEDIA_MOUNTED.equals(externalStorageState)) {
            File externalStorageDirectory = Environment.getExternalStorageDirectory();
            if (externalStorageDirectory != null) {
                //TODO /storage/emulated/0
                Log.d(TAG, "===externalStorageDirectory: " + externalStorageDirectory.getPath());
            }

            File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            if (externalStoragePublicDirectory != null) {
                //TODO /storage/emulated/0/Pictures
                Log.d(TAG, "===externalStoragePublicDirectory: " + externalStoragePublicDirectory.getPath());
            }
        }
    }

    public static void getInternalStorageByEnvironment() {
        File dataDirectory = Environment.getDataDirectory();
        if (dataDirectory != null) {
            //TODO /data
            Log.d(TAG, "===dataDirectory: " + dataDirectory.getPath());
        }
        File rootDirectory = Environment.getRootDirectory();
        if (rootDirectory != null) {
            //TODO /system
            Log.d(TAG, "===rootDirectory: " + rootDirectory.getPath());
        }

        File downloadCacheDirectory = Environment.getDownloadCacheDirectory();
        if (downloadCacheDirectory != null) {
            //TODO /data/cache
            Log.d(TAG, "===downloadCacheDirectory: " + downloadCacheDirectory.getPath());
        }
    }

    public static void getInternalStorageByContext(Context context) {
        //内部存储：用户无法查看 root后可查看
        File filesDir = context.getFilesDir();
        if (filesDir != null) {
            //TODO /data/user/0/com.zhq.devtools/files
            Log.d(TAG, "===context.getFilesDir: " + filesDir.getPath());
        }
        File cacheDir = context.getCacheDir();
        if (cacheDir != null) {
            //TODO /data/user/0/com.zhq.devtools/cache
            Log.d(TAG, "===context.getCacheDir: " + cacheDir.getPath());
        }

        //>=Build.VERSION_CODES.LOLLIPOP
        File dir = context.getDir("啥玩意", Context.MODE_PRIVATE);
        if (dir != null) {
            //TODO /data/user/0/com.zhq.devtools/app_啥玩意
            Log.d(TAG, "===context.getDir: " + dir.getPath());
        }

        File noBackupFilesDir = context.getNoBackupFilesDir();
        if (noBackupFilesDir != null) {
            //TODO /data/user/0/com.zhq.devtools/no_backup
            Log.d(TAG, "===context.getNoBackupFilesDir(): " + noBackupFilesDir.getPath());
        }

        File codeCacheDir = context.getCodeCacheDir();
        if (codeCacheDir != null) {
            //TODO /data/user/0/com.zhq.devtools/code_cache
            Log.d(TAG, "===context.getCodeCacheDir(): " + codeCacheDir.getPath());
        }
        //>=Build.VERSION_CODES.N
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            File dataDir = context.getDataDir();
            if (dataDir != null) {
                //TODO /data/user/0/com.zhq.devtools
                Log.d(TAG, "===context.getDataDir(): "+dataDir.getPath());
            }
        }

    }


}
