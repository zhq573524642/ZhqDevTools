package com.zhuiq.fileslib.cache;

import com.danikula.videocache.file.FileNameGenerator;

/**
 * @Author ZhangHuiQiang
 * @Date 2023/4/10 15:11
 * Description
 */
public class CacheConfigOption {

    public int videoCacheMaxCount;
    public long videoCacheMaxSize;
    public String videoCachePath;
    public FileNameGenerator videoNameGenerator;

    public int soundCacheMaxCount;
    public long soundCacheMaxSize;
    public String soundCachePath;
    public FileNameGenerator soundNameGenerator;

    public int filesCacheMaxCount;
    public long filesCacheMaxSize;
    public String filesCachePath;
    public FileNameGenerator filesNameGenerator;
}
