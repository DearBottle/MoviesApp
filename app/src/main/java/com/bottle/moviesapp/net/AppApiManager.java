package com.bottle.moviesapp.net;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;


import java.util.Map;

import okhttp3.HttpUrl;

/**
 * Created by admin on 2018/4/19.
 */

public class AppApiManager extends DxApiManager<AppApi> {
    private static AppApiManager sInstance;

    public synchronized static AppApiManager getInstance() {
        if (sInstance == null) {
            sInstance = new AppApiManager();
        }
        return sInstance;
    }

    @NonNull
    @Override
    protected String getBaseUrl() {
        return Config.url;
    }

    @Nullable
    @Override
    protected DxHeaderInterceptor getHeaderInterceptor() {
        return new DxHeaderInterceptor() {
            @Override
            public Map<String, String> getHeaderMap(HttpUrl url) {

                return null;
            }
        };
    }

    @Override
    protected boolean enableLog() {
        return true;
    }
}
