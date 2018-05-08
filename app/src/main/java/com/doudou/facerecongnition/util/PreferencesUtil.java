package com.doudou.facerecongnition.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;



public final class PreferencesUtil {

    private PreferencesUtil() {}

    public static void put(Context ctx, String key, int value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static void put(Context ctx, String key, long value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public static void put(Context ctx, String key, String value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void put(Context ctx, String key, boolean value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static void put(Context ctx, String key, float value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public static int getInt(Context ctx, String key, int defValue) {
        return PreferenceManager.getDefaultSharedPreferences(ctx).getInt(key, defValue);
    }

    public static float getFloat(Context ctx, String key, float defValue) {
        return PreferenceManager.getDefaultSharedPreferences(ctx).getFloat(key, defValue);
    }

    public static long getLong(Context ctx, String key, long defValue) {
        return PreferenceManager.getDefaultSharedPreferences(ctx).getLong(key, defValue);
    }

    public static String getString(Context ctx, String key, String defValue) {
        return PreferenceManager.getDefaultSharedPreferences(ctx).getString(key, defValue);
    }

    public static boolean getBoolean(Context ctx, String key, boolean defValue) {
        return PreferenceManager.getDefaultSharedPreferences(ctx).getBoolean(key, defValue);
    }

    /**
     * @return 如果该“键”下有值则返回true，否则返回false
     */
    public static boolean contains(Context ctx, String key) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        return sharedPreferences.contains(key);
    }

    /**
     * 清空所有数据
     */
    public static void clear(Context ctx) {
        SharedPreferences.Editor editor = PreferenceManager.
                getDefaultSharedPreferences(ctx).edit();
        editor.clear();
        editor.apply();
    }

    /**
     * 根据键值删除数据
     */
    public static void remove(Context ctx, String... keys) {
        if (keys != null) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            for (String key : keys) {
                editor.remove(key);
            }
            editor.apply();
        }
    }

}


