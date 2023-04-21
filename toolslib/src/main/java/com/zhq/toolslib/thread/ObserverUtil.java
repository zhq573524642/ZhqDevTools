package com.zhq.toolslib.thread;

import android.graphics.Bitmap;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @Author ZhangHuiQiang
 * @Date 2023/4/19 15:26
 * Description
 */
public class ObserverUtil<T> {

    private OnObserverHandleListener<T> onObserverHandleListener;

    public void setObserverTask(OnObserverHandleListener<T> onObserverHandleListener) {
        this.onObserverHandleListener = onObserverHandleListener;
        Observable<T> observable = Observable.create(emitter -> {
            if (onObserverHandleListener != null) {
                T t = onObserverHandleListener.onHandleFunction();
                emitter.onNext(t);
                //发送完成
                emitter.onComplete();
            }
        });
        Observer<T> observer = new Observer<T>() {
            @Override
            public void onSubscribe(Disposable d) {
                if (onObserverHandleListener != null) {
                    onObserverHandleListener.onSubscribe(d);
                }
            }

            @Override
            public void onNext(T value) {
                if (onObserverHandleListener != null) {
                    onObserverHandleListener.onNext(value);
                }
            }

            @Override
            public void onError(Throwable e) {
                if (onObserverHandleListener != null) {
                    onObserverHandleListener.onError(e);
                }
            }

            @Override
            public void onComplete() {
                if (onObserverHandleListener != null) {
                    onObserverHandleListener.onComplete();
                }
            }
        };
        observable.observeOn(AndroidSchedulers.mainThread())//回调在主线程
                .subscribeOn(Schedulers.io())//执行在io线程subscribe(observer);
                .subscribe(observer);
    }

    public interface OnObserverHandleListener<T> {
        T onHandleFunction();

        default void onSubscribe(Disposable d) {
        }

        void onNext(T value);


        default void onError(Throwable e) {
        }

        default void onComplete() {
        }


    }
}
