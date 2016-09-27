package com.nagopy.android.straightneckblocker;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crash.FirebaseCrash;

import timber.log.Timber;

public class App extends Application {

    public static Context context;

    ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        App.context = this;

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new Timber.Tree() {
                @Override
                protected void log(int priority, String tag, String message, Throwable t) {
                    switch (priority) {
                        case Log.INFO:
                            Bundle b = new Bundle();
                            b.putString("message", message);
                            FirebaseAnalytics.getInstance(App.this).logEvent("INFO", b);
                            break;
                        case Log.WARN:
                            FirebaseCrash.log(message);
                            break;
                        case Log.ERROR:
                            FirebaseCrash.report(t);
                            break;
                        default:
                            break;
                    }
                }
            });
        }

        MobileAds.initialize(this, BuildConfig.AD_APP_ID);
        Timber.d("onCreate");
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
