package com.zhuiq.fileslib.cache;

import com.danikula.videocache.headers.HeaderInjector;

import java.util.HashMap;
import java.util.Map;

public class UserAgentHeadersInjector implements HeaderInjector {
    @Override
    public Map<String, String> addHeaders(String url) {
        Map<String, String> map = new HashMap<>();
        map.put("User-Agent", "xxx");
        return map;
    }
}
