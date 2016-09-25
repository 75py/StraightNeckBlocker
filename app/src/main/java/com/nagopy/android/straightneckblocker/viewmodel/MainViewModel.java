package com.nagopy.android.straightneckblocker.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableBoolean;
import android.provider.Settings;
import android.view.View;

import com.nagopy.android.straightneckblocker.app.MainService;
import com.nagopy.android.straightneckblocker.model.ServiceHandler;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MainViewModel {

    public final ObservableBoolean isServiceEnabled = new ObservableBoolean();

    public final ObservableBoolean isAccessibilityServiceEnabled = new ObservableBoolean();

    @Inject
    Context context;

    @Inject
    ServiceHandler serviceHandler;

    Activity activity;

    @Inject
    MainViewModel() {
    }

    public void onCreate(Activity activity) {
        this.activity = activity;

        isServiceEnabled.set(
                serviceHandler.isServiceRunning(MainService.class)
        );
        isAccessibilityServiceEnabled.set(
                serviceHandler.isAccessibilityEnabled("com.nagopy.android.straightneckblocker/.app.MonitorService")
        );
    }

    public void onDestroy() {
        this.activity = null;
    }

    public void onClickServiceToggle(View view) {
        if (serviceHandler.isServiceRunning(MainService.class)) {
            MainService.stop(context);
            isServiceEnabled.set(false);
        } else {
            MainService.start(context);
            isServiceEnabled.set(true);
        }
    }

    public void onClickAccessibilitySettingsButton(View view) {
        activity.startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
    }


}
