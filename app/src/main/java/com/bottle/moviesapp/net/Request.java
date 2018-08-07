package com.bottle.moviesapp.net;

import android.support.annotation.NonNull;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by mengbaobao on 2018/8/4.
 */

public class Request {
    private static Request mInstant;
    private CompositeDisposable mCompositeDisposable;


    private Request(){

    }

    public static Request getInstant(){
        if (mInstant==null){
            mInstant=new Request();
        }
        return mInstant;
    }

    /**
     * 将subscriber注册到flowable，同时将
     * {@link Disposable} 添加到 {@link CompositeDisposable}
     */
    public  <T> void doRequest(Flowable<T> flowable, ResourceSubscriber<T> subscriber) {

        Disposable disposable =
                flowable.compose(RxUtil.<T>getFlowableAndroidTransform())
                        .subscribeWith(subscriber);
        addDisposable(disposable);
    }

    /**
     * 将observer注册到observable，同时将
     * {@link Disposable} 添加到 {@link CompositeDisposable}
     * @param observable
     * @param observer
     */
    public <T> void doRequest(Observable<T> observable, DisposableObserver<T> observer) {

        Disposable disposable =
                observable.compose(RxUtil.<T>getAndroidTransform())
                        .subscribeWith(observer);
        addDisposable(disposable);
    }

    /**
     * 添加Disposable到CompositeDisposable中，
     * 会销毁所有任务的
     */
    protected void addDisposable(@NonNull Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    /**
     * 销毁特定Disposable
     */
    protected void deleteDisposable(@NonNull Disposable disposable) {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.delete(disposable);
        }
    }
    /**
     * 停止集合中正在执行的 RxJava 任务
     */
    private void clearDisposable() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }
}
