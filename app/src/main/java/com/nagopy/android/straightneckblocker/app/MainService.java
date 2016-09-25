package com.nagopy.android.straightneckblocker.app;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.nagopy.android.straightneckblocker.App;
import com.nagopy.android.straightneckblocker.BuildConfig;
import com.nagopy.android.straightneckblocker.model.NotificationHandler;
import com.nagopy.android.straightneckblocker.model.OrientationManager;
import com.nagopy.android.straightneckblocker.model.PkgMonitor;
import com.nagopy.android.straightneckblocker.model.ReceiverHandler;
import com.nagopy.android.straightneckblocker.model.TimerHandler;
import com.nagopy.android.straightneckblocker.model.impl.Status;
import com.nagopy.android.straightneckblocker.model.impl.StraightNeckBlockerImpl;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.functions.Action1;
import timber.log.Timber;

public class MainService extends Service {

    private static final String ACTION_PAUSE = BuildConfig.APPLICATION_ID + ".ACTION_PAUSE";

    @Inject
    OrientationManager orientationManager;
    @Inject
    TimerHandler timerHandler;
    @Inject
    NotificationHandler notificationHandler;

    @Inject
    ReceiverHandler screenOnBroadcastReceiverHandler;
    @Inject
    ReceiverHandler screenOffBroadcastReceiverHandler;

    @Inject
    PkgMonitor pkgMonitor;

    @Inject
    StraightNeckBlockerImpl blocker;

    @Override
    public void onCreate() {
        super.onCreate();

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
                        Timber.i("傾き＝%s", patch);

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
        pkgMonitor.setOnNext(new Action1<CharSequence>() {
            @Override
            public void call(CharSequence pkg) {
                Timber.d("pkg=%s", pkg);
            }
        }).setOnError(new Action1<Throwable>() {
            @Override
            public void call(Throwable e) {
                Timber.e(e, "何かに失敗");
            }
        });

        orientationManager.resume();
        timerHandler.start();
        screenOnBroadcastReceiverHandler.setActions(Intent.ACTION_SCREEN_ON)
                .setReceiver(screenOnReceiver)
                .register();
        screenOffBroadcastReceiverHandler.setActions(Intent.ACTION_SCREEN_OFF)
                .setReceiver(screenOffReceiver)
                .register();
        pkgMonitor.subscribe();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String action = intent.getAction();
            if (ACTION_PAUSE.equals(action)) {
                pause();
                Toast.makeText(this, "Pause", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timerHandler.stop();
        orientationManager.destroy();
        notificationHandler.destroy();
        screenOnBroadcastReceiverHandler.unregister();
        screenOffBroadcastReceiverHandler.unregister();
        pkgMonitor.unsubscribe();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void pause() {
        orientationManager.pause();
        timerHandler.stop();
    }

    public static void start(Context context) {
        context.startService(new Intent(context, MainService.class));
    }

    public static void stop(Context context) {
        context.stopService(new Intent(context, MainService.class));
    }

    private BroadcastReceiver screenOnReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            orientationManager.resume();
            timerHandler.start();
        }
    };

    private BroadcastReceiver screenOffReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            pause();
        }
    };

    public static Intent createPauseIntent(Context context) {
        return new Intent(context, MainService.class).setAction(ACTION_PAUSE);
    }

}
