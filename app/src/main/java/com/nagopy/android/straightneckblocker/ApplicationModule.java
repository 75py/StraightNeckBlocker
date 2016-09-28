package com.nagopy.android.straightneckblocker;

import android.app.ActivityManager;
import android.app.Application;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.SensorManager;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationManagerCompat;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;

import com.nagopy.android.straightneckblocker.model.NotificationHandler;
import com.nagopy.android.straightneckblocker.model.OrientationManager;
import com.nagopy.android.straightneckblocker.model.ReceiverHandler;
import com.nagopy.android.straightneckblocker.model.StraightNeckBlocker;
import com.nagopy.android.straightneckblocker.model.TimerHandler;
import com.nagopy.android.straightneckblocker.model.ToastHandler;
import com.nagopy.android.straightneckblocker.model.impl.NotificationHandlerImpl;
import com.nagopy.android.straightneckblocker.model.impl.OrientationManagerImpl;
import com.nagopy.android.straightneckblocker.model.impl.ReceiverHandlerImpl;
import com.nagopy.android.straightneckblocker.model.impl.StraightNeckBlockerImpl;
import com.nagopy.android.straightneckblocker.model.impl.TimerHandlerImpl;
import com.nagopy.android.straightneckblocker.model.impl.ToastHandlerImpl;
import com.nagopy.android.straightneckblocker.view.PopupToastView;
import com.nagopy.android.straightneckblocker.view.PopupView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Singleton
    @Provides
    public Context provideContext() {
        return application;
    }

    @Singleton
    @Provides
    public SharedPreferences provideSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    @Singleton
    @Provides
    public SensorManager provideSensorManager() {
        return (SensorManager) application.getSystemService(Context.SENSOR_SERVICE);
    }

    @Singleton
    @Provides
    public NotificationManagerCompat provideNotificationManagerCompat() {
        return NotificationManagerCompat.from(application);
    }

    @Singleton
    @Provides
    public Vibrator provideVibrator() {
        return (Vibrator) application.getSystemService(Context.VIBRATOR_SERVICE);
    }

    @Singleton
    @Provides
    public WindowManager provideWindowManager() {
        return (WindowManager) application.getSystemService(Context.WINDOW_SERVICE);
    }

    @Singleton
    @Provides
    ActivityManager provideActivityManager() {
        return (ActivityManager) application.getSystemService(Context.ACTIVITY_SERVICE);
    }

    @Singleton
    @Provides
    AccessibilityManager provideAccessibilityManager() {
        return (AccessibilityManager) application.getSystemService(Context.ACCESSIBILITY_SERVICE);
    }

    @Singleton
    @Provides
    KeyguardManager provideKeyguardManager() {
        return (KeyguardManager) application.getSystemService(Context.KEYGUARD_SERVICE);
    }

    @Singleton
    @Provides
    public OrientationManager provideOrientationManager(OrientationManagerImpl orientationManager) {
        return orientationManager;
    }

    @Singleton
    @Provides
    public TimerHandler provideTimerHandler(TimerHandlerImpl timerHandler) {
        return timerHandler;
    }

    @Singleton
    @Provides
    public NotificationHandler provideNotificationHandler(NotificationHandlerImpl notificationHandler) {
        return notificationHandler;
    }

    @Singleton
    @Provides
    public PopupView providePopUpView(PopupToastView popUpView) {
        return popUpView;
    }

    @Singleton
    @Provides
    public ReceiverHandler provideReceiverHandler(ReceiverHandlerImpl receiverHandler) {
        return receiverHandler;
    }

    @Singleton
    @Provides
    StraightNeckBlocker provideStraightNeckBlocker(StraightNeckBlockerImpl straightNeckBlocker) {
        return straightNeckBlocker;
    }

    @Singleton
    @Provides
    ToastHandler provideToastHandler(ToastHandlerImpl toastHandler) {
        return toastHandler;
    }
}
