package com.nagopy.android.straightneckblocker.app;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;

import com.nagopy.android.straightneckblocker.App;
import com.nagopy.android.straightneckblocker.model.PkgMonitor;

import javax.inject.Inject;

import timber.log.Timber;

public class MonitorService extends AccessibilityService {

    @Inject
    PkgMonitor pkgMonitor;

    @Override
    public void onCreate() {
        super.onCreate();

        ((App) getApplicationContext())
                .getApplicationComponent()
                .inject(this);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        CharSequence pkg = event.getPackageName();
        pkgMonitor.update(pkg);
    }

    @Override
    public void onInterrupt() {
    }

}
