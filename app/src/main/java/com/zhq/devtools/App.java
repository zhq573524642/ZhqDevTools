package com.zhq.devtools;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.zhq.devtools.database.MySQLiteOpenHelper;

import org.litepal.LitePal;

/**
 * @Author ZhangHuiQiang
 * @Date 2023/2/6 18:07
 * Description
 */
public class App extends Application {


    private static SQLiteDatabase sqLiteOpenHelperWritableDatabase;
    private static Context applicationContext;


    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this.getApplicationContext();
        LitePal.initialize(this);
        MySQLiteOpenHelper mySQLiteOpenHelper = new MySQLiteOpenHelper(this, "mypersondata1.db", null, 3);
        sqLiteOpenHelperWritableDatabase = mySQLiteOpenHelper.getWritableDatabase();
    }

    public static SQLiteDatabase getSqLiteOpenHelperWritableDatabase() {
        return sqLiteOpenHelperWritableDatabase;
    }

    public static Context getAppContext() {
        return applicationContext;
    }
}
