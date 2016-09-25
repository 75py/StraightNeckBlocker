package com.nagopy.android.straightneckblocker.model.impl;

import com.nagopy.android.straightneckblocker.model.TimerHandler;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class TimerHandlerImpl implements TimerHandler {

    private Action1<Long> onNext;
    private Action1<Throwable> onError;

    @Inject
    TimerHandlerImpl() {
    }

    long interval;
    TimeUnit unit;
    Subscription intervalSubscription;

    @Override
    public TimerHandler setInterval(long interval, TimeUnit unit) {
        this.interval = interval;
        this.unit = unit;
        return this;
    }

    @Override
    public TimerHandler setOnNext(Action1<Long> onNext) {
        this.onNext = onNext;
        return this;
    }

    @Override
    public TimerHandler setOnError(Action1<Throwable> onError) {
        this.onError = onError;
        return this;
    }

    @Override
    public void start() {
        if (unit == null) {
            throw new IllegalStateException("interval未設定");
        }
        if (onNext == null) {
            throw new IllegalStateException("onNext未設定");
        }
        if (onError == null) {
            throw new IllegalStateException("onError未設定");
        }

        if (intervalSubscription != null && !intervalSubscription.isUnsubscribed()) {
            Timber.d("既にstart済みのため、前のものはunsubscribeする");
            intervalSubscription.unsubscribe();
        }

        Timber.d("start");
        intervalSubscription = Observable.interval(interval, unit, Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
    }

    @Override
    public void stop() {
        if (intervalSubscription != null) {
            Timber.d("stop");
            intervalSubscription.unsubscribe();
        }
    }
}
