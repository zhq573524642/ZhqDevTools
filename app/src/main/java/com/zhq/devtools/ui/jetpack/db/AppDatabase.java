package com.zhq.devtools.ui.jetpack.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.zhq.devtools.ui.jetpack.entity.Menu;
import com.zhq.devtools.ui.jetpack.entity.TangPoem;
import com.zhq.devtools.ui.jetpack.entity.User;

/**
 * @Author ZhangHuiQiang
 * @Date 2023/4/6 10:44
 * Description
 */
@Database(entities = {User.class, TangPoem.class, Menu.class},exportSchema = false, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "app_database11";

    private static AppDatabase instanceDatabase;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instanceDatabase == null) {
            instanceDatabase = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DATABASE_NAME)
                    .createFromAsset("databases/quantangshi.db")
                    .build();
        }
        return instanceDatabase;
    }

    public abstract UserDao userDao();

    public abstract TangPoemDao tangPoemDao();

    public abstract MenuDao menuDao();




}
