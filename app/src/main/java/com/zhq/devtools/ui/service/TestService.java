package com.zhq.devtools.ui.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

/**
 * @Author ZhangHuiQiang
 * @Date 2023/12/4 11:09
 * Description
 */
public class TestService extends Service {

    private static final String TAG = "TestService";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "====onCreate: ");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String param = intent.getStringExtra("service_param");
        Log.d(TAG, "====onStartCommand: 参数：" + param + "==flags：" + flags + "==startId：" + startId);
        return super.onStartCommand(intent, flags, startId);

    }

    private class MyBinder extends Binder {
        public TestService getService() {
            return TestService.this;
        }
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        String param = intent.getStringExtra("service_param");
        Log.d(TAG, "====onStart: 参数：" + param + "==startId：" + startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        String param = intent.getStringExtra("service_param");
        Log.d(TAG, "====onBind: 参数：" + param);
        MyBinder binder = new MyBinder();
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        String param = intent.getStringExtra("service_param");
        Log.d(TAG, "====onUnbind: 参数："+param);
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "====onDestroy: ");
    }


    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.d(TAG, "====onRebind: ");
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Log.d(TAG, "====onTaskRemoved: ");
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Log.d(TAG, "====onTrimMemory: level："+level);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.d(TAG, "====onLowMemory: ");
    }
}
