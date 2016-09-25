package com.nagopy.android.straightneckblocker.viewmodel;

import android.app.Activity;
import android.content.Context;

import com.nagopy.android.straightneckblocker.app.MainService;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MainViewModel {

    @Inject
    Context context;

    Activity activity;

    @Inject
    MainViewModel() {
    }

    public void onCreate(Activity activity) {
        this.activity = activity;
        MainService.start(context);
    }

    public void onDestroy() {
        this.activity = null;
    }

}
