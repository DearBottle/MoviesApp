package com.bottle.moviesapp.net;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bottle.moviesapp.BuildConfig;
import com.google.gson.GsonBuilder;

import java.lang.reflect.ParameterizedType;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by admin on 2018/4/18.
 * 网络请求基类
 */

public abstract class DxApiManager<T> {
    private static final int CONNECT_TIMEOUT_DEFAULT = 20;
    private static final int READ_TIMEOUT_DEFAULT = 20;

    private T mWebApi ;

    protected String mBaseUrl;

    @NonNull
    public T getApi() {
        if (mWebApi == null) {
            mWebApi = createWebApis();
        }
        return mWebApi;
    }

    /**
     * 设置BaseUrl
     * 重新创建Api实例
     */
    public void setBaseUrl(String baseUrl) {
        mBaseUrl = baseUrl;
        mWebApi = createWebApis();
    }
    /**
     * @return 服务器地址
     */
    @NonNull
    protected abstract String getBaseUrl();

    /**
     * @return 默认Header拦截器
     */
    @Nullable
    protected abstract DxHeaderInterceptor getHeaderInterceptor();

    /**
     * 重载此方法配置OkHttpClient
     */
    protected void onOkHttpClientCreate(OkHttpClient.Builder builder){}

    /**
     * 重载此方法配置Retrofit
     */
    protected void onRetrofitBuilderCreate(Retrofit.Builder retrofitBuilder) {}

    /**
     *
     */
    protected boolean enableLog() {
        return BuildConfig.DEBUG;
    }

    @NonNull
    private T createWebApis(){
        if (mBaseUrl == null) {
            mBaseUrl = getBaseUrl();
        }
        return getDefaultRetrofit(mBaseUrl).create(getTClass());
    }


    /**
     * 返回默认的 RetrofitBuilder
     * @param url web Api 地址
     */
    private Retrofit getDefaultRetrofit(@NonNull String url) {
        DxHeaderInterceptor headerInterceptor = getHeaderInterceptor();
        OkHttpClient.Builder builder = getDefaultClientBuilder(headerInterceptor);
        onOkHttpClientCreate(builder);
        if (enableLog()) { //最后再添加Log拦截器
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }

        Retrofit.Builder retrofitBuilder = getDefaultRetrofitBuilder(url,builder.build());
        onRetrofitBuilderCreate(retrofitBuilder);
        return retrofitBuilder.build();
    }


    /**
     * 返回默认的 RetrofitBuilder
     * @param client OkHttpClient
     * @param url web Api 地址
     */
    private Retrofit.Builder getDefaultRetrofitBuilder(@NonNull String url, OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client);
    }

    /**
     * 返回默认的OkHttpClient Builder
     * @param interceptor Header拦截器
     */
    private OkHttpClient.Builder getDefaultClientBuilder(@Nullable DxHeaderInterceptor interceptor) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(READ_TIMEOUT_DEFAULT, TimeUnit.SECONDS);
        builder.readTimeout(CONNECT_TIMEOUT_DEFAULT, TimeUnit.SECONDS);

        if (interceptor != null) {
            builder.networkInterceptors().add(interceptor);
        }
        return builder;
    }

    @SuppressWarnings("unchecked")
    private Class<T> getTClass()
    {
        return (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
}
