package com.zhq.devtools.entity;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

/**
 * @Author ZhangHuiQiang
 * @Date 2023/3/31 17:03
 * Description
 */
@Database(entities = {Student.class}, version = 1)
public abstract class MyDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "my_db";
    private static MyDatabase myDatabaseInstance;

    public static synchronized MyDatabase getInstance(Context context) {
        if (myDatabaseInstance == null) {
            myDatabaseInstance = Room.databaseBuilder(context.getApplicationContext(),
                    MyDatabase.class, DATABASE_NAME)
                    /*.addMigrations(MIGRATION_1_TO_2)*/.build();
        }
        return myDatabaseInstance;
    }

    static final Migration MIGRATION_1_TO_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

        }
    };

    public abstract StudentDao studentDao();
}
