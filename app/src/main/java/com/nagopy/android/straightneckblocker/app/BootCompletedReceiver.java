package com.nagopy.android.straightneckblocker.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.nagopy.android.straightneckblocker.App;
import com.nagopy.android.straightneckblocker.model.UserSettings;

import javax.inject.Inject;

public class BootCompletedReceiver extends BroadcastReceiver {

    @Inject
    UserSettings userSettings;

    @Override
    public void onReceive(Context context, Intent intent) {
        ((App) context.getApplicationContext()).getApplicationComponent().inject(this);
        if (userSettings.isEnabled()) {
            MainService.start(context);
        }
    }
}
