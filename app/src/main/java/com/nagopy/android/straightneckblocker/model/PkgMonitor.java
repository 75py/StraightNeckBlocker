package com.nagopy.android.straightneckblocker.model;

import rx.functions.Action1;

public interface PkgMonitor {

    void update(CharSequence pkg);

    CharSequence current();

    PkgMonitor setOnNext(Action1<CharSequence> onNext);

    PkgMonitor setOnError(Action1<Throwable> onError);

    void subscribe();

    void unsubscribe();
}
