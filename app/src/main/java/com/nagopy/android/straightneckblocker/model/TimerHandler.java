package com.nagopy.android.straightneckblocker.model;

import java.util.concurrent.TimeUnit;

import rx.functions.Action1;

public interface TimerHandler {

    TimerHandler setInterval(long interval, TimeUnit unit);

    TimerHandler setOnNext(Action1<Long> onNext);

    TimerHandler setOnError(Action1<Throwable> onError);

    void start();

    void stop();

}
