package com.bottle.moviesapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.bottle.moviesapp.base.BaseApplication;


/**
 * sharoPreferences	工具类
 */
public class PreferenceUtil {

    public static String PREFERENCE_NAME = "PreferenceUtil";

    public static boolean putString(String key, String value) {
        return getEditor().putString(key, value).commit();
    }

    public static String getString(String key, String defaultValue) {
        return getSetting().getString(key, defaultValue);
    }

    public static boolean putInt(String key, int value) {
        return getEditor().putInt(key, value).commit();
    }

    public static int getInt(String key, int defaultValue) {
        return getSetting().getInt(key, defaultValue);
    }

    public static boolean putLong(String key, long value) {
        return getEditor().putLong(key, value).commit();
    }

    public static long getLong(String key, long defaultValue) {
        return getSetting().getLong(key, defaultValue);
    }

    public static boolean putFloat(String key, float value) {
        return getEditor().putFloat(key, value).commit();
    }

    public static float getFloat(String key, float defaultValue) {
        return getSetting().getFloat(key, defaultValue);
    }

    public static boolean putBoolean(String key, boolean value) {
        return getEditor().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return getSetting().getBoolean(key, defaultValue);
    }

    public static boolean removeKey(String key) {
        return getEditor().remove(key).commit();
    }

    private static SharedPreferences.Editor getEditor() {
        return getSetting().edit();
    }

    private static SharedPreferences getSetting() {
        return BaseApplication.getContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);

    }


}
