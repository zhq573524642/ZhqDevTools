package com.zhq.toolslib.sp;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by Huiqiang Zhang
 * on 2020/7/28
 */
public class SpUtils {

    private static final String FILE_NAME = "config_new";
    private static final Context context = null /*APP.getAppContext()*/;

    /**
     * 保存用户名
     */
    public static void putUserName(String token) {
        context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit().putString("UserName", token).apply();
    }

    /**
     * 取出用用户名
     */
    public static String getUserName() {
        return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).getString("UserName", "");
    }


    /**
     * 保存第一次安裝
     */
    public static void putFirstInstall(boolean b) {
        context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit().putBoolean("FirstInstall", b).apply();
    }

    /**
     * 取出第一次安裝
     */
    public static boolean getFirstInstall() {
        return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).getBoolean("FirstInstall", true);
    }


    public static String getString(String key, String defaultValue) {
        return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).getString(key, defaultValue);
    }

    public static void putString(String key, String value) {

        context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit().putString(key, value).apply();
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defaultValue);
    }

    public static void putBoolean(String key, boolean value) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static int getInt(String key, int defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getInt(key, defaultValue);
    }

    public static void putInt(String key, int value) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static float getFloat(String key, float defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getFloat(key, defaultValue);
    }

    public static void putFloat(String key, float value) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public static void putLong(String key, long value) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public static Long getLong(String key, long defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getLong(key, defaultValue);
    }
}
