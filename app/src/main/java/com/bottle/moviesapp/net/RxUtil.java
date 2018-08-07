package com.bottle.moviesapp.net;


import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by admin on 2018/4/19.
 */

public class RxUtil {
    /**
     * @return 后台操作常用的Transform
     */
    public static <T> FlowableTransformer<T, T> getFlowableAndroidTransform() {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return upstream
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };

    }

    /**
     * @return 后台操作常用的Transform
     */
    public static <T> ObservableTransformer<T, T> getAndroidTransform() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 检查服务器接口返回的数据，如果出错则抛出{@link DxServerException}
     */

    public static <T, K extends DxResponse<T>> ObservableTransformer<K, T> getResponseTransformer() {
        return new ObservableTransformer<K, T>() {
            @Override
            public ObservableSource<T> apply(Observable<K> upstream) {
                return upstream
                        .map(new Function<K, T>() {
                            @Override
                            public T apply(K response) throws Exception {
                                if (!response.isSucc()) {
                                    throw new DxServerException(response.getCode(), response.getMessage());
                                }
                                return response.getData();
                            }
                        });
            }
        };
    }

    /**
     * 检查服务器接口返回的数据，如果出错则抛出{@link DxServerException}
     */
    public static <T, K extends DxResponse<T>> FlowableTransformer<K, T> getResponseFlowableTransformer() {
        return new FlowableTransformer<K, T>() {

            @Override
            public Publisher<T> apply(Flowable<K> upstream) {
                return upstream.map(new Function<K, T>() {
                    @Override
                    public T apply(K response) throws Exception {
                        if (!response.isSucc()) {
                            throw new DxServerException(response.getCode(), response.getMessage());
                        }
                        return response.getData();
                    }
                });
            }
        };
    }

}

