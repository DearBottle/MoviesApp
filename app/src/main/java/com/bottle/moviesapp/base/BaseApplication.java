package com.bottle.moviesapp.base;

import android.app.Application;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by mengbaobao on 2018/7/28.
 */

public class BaseApplication extends Application {

    private static Set<BaseActivity> mActivitySet;

    @Override
    public void onCreate() {
        super.onCreate();
        mActivitySet = new HashSet<>();
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
       for (BaseActivity activity:mActivitySet){
            activity.finish();
        }
    }


}
