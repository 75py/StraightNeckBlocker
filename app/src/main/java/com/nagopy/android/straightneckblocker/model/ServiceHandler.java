package com.nagopy.android.straightneckblocker.model;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.ActivityManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ServiceHandler {

    @Inject
    ActivityManager activityManager;

    @Inject
    AccessibilityManager accessibilityManager;

    @Inject
    ServiceHandler() {
    }

    public boolean isServiceRunning(Class<?> cls) {
        String serviceName = cls.getName();
        for (ActivityManager.RunningServiceInfo service : activityManager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceName.equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


    public boolean isAccessibilityEnabled(String id) {
        List<AccessibilityServiceInfo> runningServices
                = accessibilityManager.getEnabledAccessibilityServiceList(AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED);
        for (AccessibilityServiceInfo service : runningServices) {
            if (id.equals(service.getId())) {
                return true;
            }
        }
        return false;
    }

}
