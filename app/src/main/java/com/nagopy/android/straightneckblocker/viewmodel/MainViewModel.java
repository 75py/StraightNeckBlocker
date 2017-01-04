package com.nagopy.android.straightneckblocker.viewmodel;

import android.app.Activity;
import android.databinding.ObservableBoolean;
import android.view.View;
import android.widget.ToggleButton;

import com.nagopy.android.straightneckblocker.app.MainService;
import com.nagopy.android.straightneckblocker.model.UserSettings;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MainViewModel {

    @Inject
    UserSettings userSettings;

    Activity activity;

    public ObservableBoolean isServiceEnabled = new ObservableBoolean(false);

    @Inject
    MainViewModel() {
    }

    public void onCreate(Activity activity) {
        this.activity = activity;
        isServiceEnabled.set(userSettings.isEnabled());
        updateServiceEnabled(userSettings.isEnabled());
    }

    public void onDestroy() {
        this.activity = null;
    }

    public void onClickToggleBtn(View v) {
        ToggleButton tb = (ToggleButton) v;
        updateServiceEnabled(tb.isChecked());
    }

    void updateServiceEnabled(boolean enabled) {
        userSettings.setEnabled(enabled);
        if (enabled) {
            MainService.start(activity.getApplicationContext());
        } else {
            MainService.stop(activity.getApplicationContext());
        }
    }
}
