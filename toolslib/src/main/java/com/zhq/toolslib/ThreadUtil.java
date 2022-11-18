package com.zhq.toolslib;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class ThreadUtil {

    private static Executor sExecutor = Executors.newSingleThreadExecutor();
    private static Handler sHandler = new Handler(Looper.getMainLooper());

    public static  void runOnSubThread(Runnable runnable){
        sExecutor.execute(runnable);
    }

    public static  void runOnUiThread(Runnable runnable){
        sHandler.post(runnable);
    }

    public static boolean isMainThread(){
        return Looper.myLooper() == Looper.getMainLooper();
    }
}
