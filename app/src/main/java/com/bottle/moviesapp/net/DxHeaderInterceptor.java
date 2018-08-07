package com.bottle.moviesapp.net;

import android.support.annotation.NonNull;

import java.io.IOException;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by admin on 2018/5/11.
 * Header拦截器
 */

public abstract class DxHeaderInterceptor implements Interceptor {

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();

        Request.Builder builder  = request.newBuilder();
        Map<String,String> header = getHeaderMap(request.url());
        if (header != null) {
            for (String key : header.keySet()) {
                builder.addHeader(key, header.get(key));
            }
        }
        return chain.proceed(builder.build());
    }

    /**
     *
     * @return 配置网络请求Header的Map
     */
    public abstract Map<String, String> getHeaderMap(HttpUrl url);

}
