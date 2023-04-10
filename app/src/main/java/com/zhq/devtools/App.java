package com.zhq.devtools;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import androidx.lifecycle.ProcessLifecycleOwner;

import com.zhq.devtools.database.MySQLiteOpenHelper;
import com.zhq.devtools.ui.jetpack.mvvm.api.ApiService;
import com.zhq.devtools.ui.jetpack.mvvm.api.RetrofitClient;
import com.zhq.devtools.ui.jetpack.mvvm.db.AppDatabase;
import com.zhuiq.fileslib.FilesSaveManager;
import com.zhuiq.fileslib.cache.CacheConfigOption;
import com.zhuiq.fileslib.cache.MyFilesNameGenerator;
import com.zhuiq.fileslib.cache.MyMp3FileNameGenerator;
import com.zhuiq.fileslib.cache.MyMp4FileNameGenerator;

import org.litepal.LitePal;

/**
 * @Author ZhangHuiQiang
 * @Date 2023/2/6 18:07
 * Description
 */
public class App extends Application {


    private static SQLiteDatabase sqLiteOpenHelperWritableDatabase;
    private static Context applicationContext;
    private static AppDatabase appDatabase;
    private static ApiService apiService;


    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this.getApplicationContext();
        ProcessLifecycleOwner.get().getLifecycle().addObserver(new ApplicationLifecycleObserver());
        LitePal.initialize(this);
        MySQLiteOpenHelper mySQLiteOpenHelper = new MySQLiteOpenHelper(this, "mypersondata1.db", null, 3);
        sqLiteOpenHelperWritableDatabase = mySQLiteOpenHelper.getWritableDatabase();

        appDatabase = AppDatabase.getInstance(this);
        apiService = RetrofitClient.getInstance().getApiService();

        CacheConfigOption option = new CacheConfigOption();
        option.soundCacheMaxCount = 100;
        option.soundCacheMaxSize = 2L * 1024 * 1024 * 1024;
        option.soundNameGenerator = new MyMp3FileNameGenerator();
        option.soundCachePath = this.getExternalFilesDir(Environment.DIRECTORY_MUSIC).getPath();
        option.videoCacheMaxCount = 50;
        option.videoCacheMaxSize = 2L * 1024 * 1024 * 1024;
        option.videoNameGenerator = new MyMp4FileNameGenerator();
        option.videoCachePath = this.getExternalFilesDir(Environment.DIRECTORY_MOVIES).getPath();
        option.filesCacheMaxCount = 50;
        option.filesCacheMaxSize = 1024 * 1024 * 1024;
        option.filesNameGenerator = new MyFilesNameGenerator();
        option.filesCachePath = this.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getPath();
        FilesSaveManager.getInstance().initConfig(this, option);
    }

    public static AppDatabase getAppDatabase() {
        return appDatabase;
    }

    public static ApiService getApiService() {
        return apiService;
    }

    public static SQLiteDatabase getSqLiteOpenHelperWritableDatabase() {
        return sqLiteOpenHelperWritableDatabase;
    }

    public static Context getAppContext() {
        return applicationContext;
    }
}
