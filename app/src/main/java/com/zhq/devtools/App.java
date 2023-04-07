package com.zhq.devtools;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.lifecycle.ProcessLifecycleOwner;

import com.zhq.devtools.database.MySQLiteOpenHelper;
import com.zhq.devtools.ui.jetpack.mvvm.api.ApiService;
import com.zhq.devtools.ui.jetpack.mvvm.api.RetrofitClient;
import com.zhq.devtools.ui.jetpack.mvvm.db.AppDatabase;

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
