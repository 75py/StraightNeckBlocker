package com.nagopy.android.straightneckblocker.model.impl;

import android.content.SharedPreferences;

import com.nagopy.android.straightneckblocker.model.UserSettings;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UserSettingsImpl implements UserSettings {

    @Inject
    SharedPreferences sp;

    @Inject
    UserSettingsImpl() {
    }

    private static final String KEY_ENABLED = "enabled";

    @Override
    public boolean isEnabled() {
        return sp.getBoolean(KEY_ENABLED, false);
    }

    @Override
    public void setEnabled(boolean enabled) {
        sp.edit().putBoolean(KEY_ENABLED, enabled).apply();
    }
}
