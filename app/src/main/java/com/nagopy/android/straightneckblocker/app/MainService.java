package com.nagopy.android.straightneckblocker.app;

import android.app.KeyguardManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.nagopy.android.straightneckblocker.App;
import com.nagopy.android.straightneckblocker.BuildConfig;
import com.nagopy.android.straightneckblocker.R;
import com.nagopy.android.straightneckblocker.model.NotificationHandler;
import com.nagopy.android.straightneckblocker.model.OrientationManager;
import com.nagopy.android.straightneckblocker.model.ReceiverHandler;
import com.nagopy.android.straightneckblocker.model.TimerHandler;
import com.nagopy.android.straightneckblocker.model.impl.Status;
import com.nagopy.android.straightneckblocker.model.impl.StraightNeckBlockerImpl;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Named;

import rx.functions.Action1;
import timber.log.Timber;

public class MainService extends Service {

    private static final String ACTION_PAUSE = BuildConfig.APPLICATION_ID + ".ACTION_PAUSE";
    private static final String ACTION_STOP = BuildConfig.APPLICATION_ID + ".ACTION_STOP";

    @Inject
    OrientationManager orientationManager;
    @Inject
    TimerHandler timerHandler;
    @Inject
    NotificationHandler notificationHandler;

    @Inject
    @Named("screenOnBroadcastReceiverHandler")
    ReceiverHandler screenOnBroadcastReceiverHandler;
    @Inject
    @Named("screenOffBroadcastReceiverHandler")
    ReceiverHandler screenOffBroadcastReceiverHandler;

    @Inject
    StraightNeckBlockerImpl blocker;

    @Inject
    KeyguardManager keyguardManager;

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.d("onCreate");

        ((App) getApplicationContext()).getApplicationComponent().inject(this);

        orientationManager.init();
        timerHandler.setInterval(3, TimeUnit.SECONDS)
                .setOnNext(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        Timber.d("%s", aLong);
                        if (!orientationManager.ready()) {
                            return;
                        }
                        final double patch = orientationManager.getPatch();
                        Timber.d("傾き＝%s", patch);

                        blocker.updatePatch(patch);
                        Status status = blocker.judge();
                        notificationHandler.update(patch, status);
                    }
                }).setOnError(new Action1<Throwable>() {
            @Override
            public void call(Throwable e) {
                Timber.e(e, "何かに失敗");
            }
        });
        notificationHandler.init();

        orientationManager.resume();
        timerHandler.start();
        screenOnBroadcastReceiverHandler.setActions(
                Intent.ACTION_SCREEN_ON, Intent.ACTION_USER_PRESENT)
                .setReceiver(unlockReceiver)
                .register();
        screenOffBroadcastReceiverHandler.setActions(Intent.ACTION_SCREEN_OFF)
                .setReceiver(screenOffReceiver)
                .register();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Timber.d("onStartCommand");
        if (intent != null) {
            String action = intent.getAction();
            Timber.d("action = %s", action);
            if (ACTION_PAUSE.equals(action)) {
                pause();
                Toast.makeText(this, R.string.msg_pause, Toast.LENGTH_LONG).show();
            } else if (ACTION_STOP.equals(action)) {
                stopSelf();
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Timber.d("onDestroy");

        timerHandler.stop();
        orientationManager.destroy();
        notificationHandler.destroy();
        screenOnBroadcastReceiverHandler.unregister();
        screenOffBroadcastReceiverHandler.unregister();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void resume() {
        orientationManager.resume();
        timerHandler.start();
    }

    private void pause() {
        orientationManager.pause();
        timerHandler.stop();
        blocker.invalidateCache();
        notificationHandler.hide();
    }

    public static void start(Context context) {
        context.startService(new Intent(context, MainService.class));
    }

    public static void stop(Context context) {
        context.startService(new Intent(context, MainService.class).setAction(ACTION_STOP));
    }

    private BroadcastReceiver unlockReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_USER_PRESENT.equals(intent.getAction())) {
                Timber.d("SCREEN ON + UNLOCK");
                resume();
            } else {
                Timber.d("SCREEN ON");
                if (!keyguardManager.inKeyguardRestrictedInputMode()) {
                    Timber.d("NO LOCKED");
                    resume();
                }
            }
        }
    };

    private BroadcastReceiver screenOffReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Timber.d("SCREEN OFF");
            pause();
        }
    };

    public static Intent createPauseIntent(Context context) {
        return new Intent(context, MainService.class).setAction(ACTION_PAUSE);
    }

}
