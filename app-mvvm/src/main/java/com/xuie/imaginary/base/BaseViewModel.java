package com.xuie.imaginary.base;


import androidx.lifecycle.ViewModel;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 封装了网络请求
 *
 * @author bakumon https://bakumon.me
 * @date 2018/2/2
 */

public abstract class BaseViewModel extends ViewModel {
    /**
     * RxJava 的订阅者集合
     */
    protected CompositeDisposable mSubscriptions = new CompositeDisposable();

    /**
     * 封装网络请求
     *
     * @param observable 被观察者，url
     * @param observer   回调
     * @param <T>        解析后的实体
     */
    protected <T> void getHttpData(Observable<T> observable, Consumer<T> observer) {
        Disposable disposable = observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        mSubscriptions.add(disposable);
    }

    @Override
    protected void onCleared() {
        mSubscriptions.dispose();
    }
}
