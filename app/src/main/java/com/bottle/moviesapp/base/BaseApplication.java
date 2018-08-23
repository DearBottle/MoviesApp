package com.bottle.moviesapp.base;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.bottle.moviesapp.utils.PreferenceUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by mengbaobao on 2018/7/28.
 */

public class BaseApplication extends MultiDexApplication {

    private static Set<BaseActivity> mActivitySet;
    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mActivitySet = new HashSet<>();
        context = this;
    }

    public static void addActivity(BaseActivity activity) {
        mActivitySet.add(activity);
    }

    public static void removeActivity(BaseActivity activity) {
        if (mActivitySet.contains(activity)) {
            mActivitySet.remove(activity);
        }
    }

    public static void logout() {
        for (BaseActivity activity : mActivitySet) {
            activity.finish();
        }
        PreferenceUtil.putString("UserName", "");
        PreferenceUtil.putString("PassWord", "");
    }


}
