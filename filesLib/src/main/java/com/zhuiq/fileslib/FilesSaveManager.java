package com.zhuiq.fileslib;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.danikula.videocache.HttpProxyCacheServer;
import com.zhuiq.fileslib.cache.CacheConfigOption;
import com.zhuiq.fileslib.cache.MyFilesNameGenerator;
import com.zhuiq.fileslib.cache.MyMp3FileNameGenerator;
import com.zhuiq.fileslib.cache.MyMp4FileNameGenerator;

import java.io.File;

/**
 * @Author ZhangHuiQiang
 * @Date 2023/4/10 15:05
 * Description
 */
public class FilesSaveManager {

    public static FilesSaveManager getInstance() {
        return FileSaveManagerHolder.instance;
    }

    private static final class FileSaveManagerHolder {
        private static FilesSaveManager instance = new FilesSaveManager();
    }

    /**
     * 在项目Application中初始化
     *
     * @param context
     */
    public void initConfig(Context context) {
        newSoundProxy(context);
        newVideoProxy(context);
        newFileProxy(context);
    }

    /**
     * CacheConfigOption option = new CacheConfigOption();
     *         option.soundCacheMaxCount = 100;
     *         option.soundCacheMaxSize = 2L * 1024 * 1024 * 1024;
     *         option.soundNameGenerator = new MyMp3FileNameGenerator();
     *         option.soundCachePath = this.getExternalFilesDir(Environment.DIRECTORY_MUSIC).getPath();
     *         option.videoCacheMaxCount = 50;
     *         option.videoCacheMaxSize = 2L * 1024 * 1024 * 1024;
     *         option.videoNameGenerator = new MyMp4FileNameGenerator();
     *         option.videoCachePath = this.getExternalFilesDir(Environment.DIRECTORY_MOVIES).getPath();
     *         option.filesCacheMaxCount = 50;
     *         option.filesCacheMaxSize = 1024 * 1024 * 1024;
     *         option.filesNameGenerator = new MyFilesNameGenerator();
     *         option.filesCachePath = this.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getPath();
     *         FilesSaveManager.getInstance().initConfig(this, option);
     * @param context
     * @param option
     */
    public void initConfig(Context context, @NonNull CacheConfigOption option) {
        if (option != null) {
            newSoundProxyWithOptions(context, option);
            newVideoProxyWithOptions(context, option);
            newFileProxyWithOptions(context, option);
        }

    }

    private HttpProxyCacheServer videoProxy;
    private HttpProxyCacheServer soundProxy;
    private HttpProxyCacheServer fileProxy;

    //视频缓存路径
    public String setCacheVideoPath(Context context,String path) {
        if (!TextUtils.isEmpty(path)){
            return path;
        }
        return context.getExternalFilesDir(Environment.DIRECTORY_MOVIES).getPath();
    }

    //语音缓存路径
    public String setCacheSoundPath(Context context,String path) {
        if (!TextUtils.isEmpty(path)){
            return path;
        }
        return context.getExternalFilesDir(Environment.DIRECTORY_MUSIC).getPath();
    }

    //文件缓存路径
    public String setCachePdfPath(Context context,String path) {
        if (!TextUtils.isEmpty(path)){
            return path;
        }
        return context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getPath();
    }

    /**
     * 获取音频缓存代理
     *
     * @param context
     * @return
     */
    public HttpProxyCacheServer getSoundProxy(Context context) {
        return soundProxy == null ? (soundProxy = newSoundProxy(context)) : soundProxy;
    }

    //配置音频缓存
    private HttpProxyCacheServer newSoundProxy(Context context) {
        return new HttpProxyCacheServer.Builder(context)
                .fileNameGenerator(new MyMp3FileNameGenerator())
                .maxCacheFilesCount(100)
                .maxCacheSize(2L * 1024 * 1024 * 1024)
                .cacheDirectory(new File(setCacheSoundPath(context,"")))
                .build();
    }

    //配置音频缓存
    private HttpProxyCacheServer newSoundProxyWithOptions(Context context, CacheConfigOption option) {
        return new HttpProxyCacheServer.Builder(context)
                .fileNameGenerator(option.soundNameGenerator)
                .maxCacheFilesCount(option.soundCacheMaxCount)
                .maxCacheSize(option.soundCacheMaxSize)
                .cacheDirectory(new File(option.soundCachePath))
                .build();
    }


    /**
     * 获取视频缓存代理
     *
     * @param context
     * @return
     */
    public HttpProxyCacheServer getVideoProxy(Context context) {
        return videoProxy == null ? (videoProxy = newVideoProxy(context)) : videoProxy;
    }

    //视频缓存配置
    private HttpProxyCacheServer newVideoProxy(Context context) {
        return new HttpProxyCacheServer.Builder(context)
                .fileNameGenerator(new MyMp4FileNameGenerator())
                .maxCacheFilesCount(100)
                .maxCacheSize(2L * 1024 * 1024 * 1024)
                .cacheDirectory(new File(setCacheVideoPath(context,"")))
                .build();
    }

    private HttpProxyCacheServer newVideoProxyWithOptions(Context context, CacheConfigOption option) {
        return new HttpProxyCacheServer.Builder(context)
                .fileNameGenerator(option.videoNameGenerator)
                .maxCacheFilesCount(option.videoCacheMaxCount)
                .maxCacheSize(option.videoCacheMaxSize)
                .cacheDirectory(new File(option.videoCachePath))
                .build();
    }

    public HttpProxyCacheServer getFileProxy(Context context) {
        return fileProxy == null ? (fileProxy = newFileProxy(context)) : fileProxy;
    }

    //配置音频缓存
    private HttpProxyCacheServer newFileProxy(Context context) {
        return new HttpProxyCacheServer.Builder(context)
                .fileNameGenerator(new MyFilesNameGenerator())
                .maxCacheFilesCount(50)
                .maxCacheSize(1024 * 1024 * 1024)
                .cacheDirectory(new File(setCachePdfPath(context,"")))
                .build();
    }

    private HttpProxyCacheServer newFileProxyWithOptions(Context context, CacheConfigOption option) {
        return new HttpProxyCacheServer.Builder(context)
                .fileNameGenerator(option.filesNameGenerator)
                .maxCacheFilesCount(option.filesCacheMaxCount)
                .maxCacheSize(option.filesCacheMaxSize)
                .cacheDirectory(new File(option.filesCachePath))
                .build();
    }
}
