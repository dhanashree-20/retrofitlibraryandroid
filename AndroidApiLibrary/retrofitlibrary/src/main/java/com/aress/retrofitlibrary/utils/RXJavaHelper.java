package com.aress.retrofitlibrary.utils;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class RXJavaHelper<T> {

    private static RXJavaHelper instance;
    CompositeDisposable disposable = new CompositeDisposable();

    private RXJavaHelper() {

    }

    public static RXJavaHelper getInstance() {
        if (instance == null) {
            instance = new RXJavaHelper();
        }
        return instance;
    }

    public void dispose(Single<T> single, DisposableSingleObserver<T> disposableSingleObserver) {
        disposable.add((Disposable) single.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(disposableSingleObserver));
    }

    public void complete(Action action, CompletableObserver completableObserver) {
        Completable.fromAction(action)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(completableObserver);

    }
}
