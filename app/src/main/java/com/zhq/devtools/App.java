package com.zhq.devtools;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import androidx.lifecycle.ProcessLifecycleOwner;

import com.amap.api.maps.MapsInitializer;
import com.zhq.devtools.database.MySQLiteOpenHelper;
import com.zhq.devtools.ui.jetpack.api.ApiService;
import com.zhq.devtools.ui.jetpack.api.RetrofitClient;
import com.zhq.devtools.ui.jetpack.databinding.ToastUtils;
import com.zhq.devtools.ui.jetpack.db.AppDatabase;

import org.litepal.LitePal;

import java.io.File;

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
        ToastUtils.INSTANCE.init(this);
        ProcessLifecycleOwner.get().getLifecycle().addObserver(new ApplicationLifecycleObserver());
        LitePal.initialize(this);
        MySQLiteOpenHelper mySQLiteOpenHelper = new MySQLiteOpenHelper(this, "mypersondata1.db", null, 3);
        sqLiteOpenHelperWritableDatabase = mySQLiteOpenHelper.getWritableDatabase();

        appDatabase = AppDatabase.getInstance(this);
        apiService = RetrofitClient.getInstance().getApiService();

//        CacheConfigOption option = new CacheConfigOption();
//        option.soundCacheMaxCount = 100;
//        option.soundCacheMaxSize = 2L * 1024 * 1024 * 1024;
//        option.soundNameGenerator = new MyMp3FileNameGenerator();
//        option.soundCachePath = this.getExternalFilesDir(Environment.DIRECTORY_MUSIC).getPath();
//        option.videoCacheMaxCount = 50;
//        option.videoCacheMaxSize = 2L * 1024 * 1024 * 1024;
//        option.videoNameGenerator = new MyMp4FileNameGenerator();
//        option.videoCachePath = this.getExternalFilesDir(Environment.DIRECTORY_MOVIES).getPath();
//        option.filesCacheMaxCount = 50;
//        option.filesCacheMaxSize = 1024 * 1024 * 1024;
//        option.filesNameGenerator = new MyFilesNameGenerator();
//        option.filesCachePath = this.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getPath();
//        FilesSaveManager.getInstance().initConfig(this, option);

        MapsInitializer.updatePrivacyShow(this,true,true);
        MapsInitializer.updatePrivacyAgree(this,true);
        // 设置应用单独的地图存储目录
        MapsInitializer.sdcardDir = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)+ File.separator+"gaode_map";
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
