package com.zhq.devtools.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @Author ZhangHuiQiang
 * @Date 2023/2/7 14:59
 * Description
 */
public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    public static final String CREATE_PERSON = "create table person(" +
            "id integer primary key autoincrement," +
            "avatar text," +
            "name text," +
            "description text," +
            "age integer" +
            ")";
    public static final String CREATE_BOOK = "create table book(id integer primary key autoincrement," +
            "name text," +
            "description text," +
            "book_type integer," +
            "date integer)";

    public MySQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PERSON);
        db.execSQL(CREATE_BOOK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:
                db.execSQL(CREATE_BOOK);
                break;
            case 2://版本3的时候在book表中增加了publishdate字段
                db.execSQL("alter table book add column publishdate integer");
                break;
            default:
        }
    }
}
