package com.zhuiq.fileslib.cache;

import com.danikula.videocache.file.FileNameGenerator;

public class MyFilesNameGenerator implements FileNameGenerator {
    private static final String TAG = "MyFileNameGenerator";

    @Override
    public String generate(String url) {
        int i = url.lastIndexOf("/");
        String substring = url.substring(i+1);
        return "files_"+substring;
    }
}
