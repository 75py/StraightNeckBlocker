package com.nagopy.android.straightneckblocker.model.impl;

import com.nagopy.android.straightneckblocker.model.PkgMonitor;

import javax.inject.Inject;

import rx.Subscription;
import rx.functions.Action1;
import rx.subjects.BehaviorSubject;

public class PkgMonitorImpl implements PkgMonitor {

    CharSequence current;

    BehaviorSubject<CharSequence> behaviorSubject = BehaviorSubject.create();

    Action1<CharSequence> onNext;
    Action1<Throwable> onError;
    Subscription subscription;

    @Inject
    PkgMonitorImpl() {
    }

    @Override
    public void update(CharSequence pkg) {
        this.current = pkg;
        behaviorSubject.onNext(pkg);
    }

    @Override
    public CharSequence current() {
        return current;
    }

    @Override
    public PkgMonitor setOnNext(Action1<CharSequence> onNext) {
        this.onNext = onNext;
        return this;
    }

    @Override
    public PkgMonitor setOnError(Action1<Throwable> onError) {
        this.onError = onError;
        return this;
    }

    @Override
    public void subscribe() {
        if (onNext == null) {
            throw new IllegalStateException("onNext未設定");
        }
        if (onError == null) {
            throw new IllegalStateException("onError未設定");
        }

        subscription = behaviorSubject.subscribe(onNext, onError);
    }

    @Override
    public void unsubscribe() {
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }
}
