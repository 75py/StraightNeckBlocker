package com.nagopy.android.straightneckblocker.model.impl;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.nagopy.android.straightneckblocker.R;
import com.nagopy.android.straightneckblocker.app.MainActivity;
import com.nagopy.android.straightneckblocker.app.MainService;
import com.nagopy.android.straightneckblocker.model.NotificationHandler;
import com.nagopy.android.straightneckblocker.view.PopupView;

import javax.inject.Inject;

import timber.log.Timber;

public class NotificationHandlerImpl implements NotificationHandler {

    @Inject
    Context context;

    @Inject
    NotificationManagerCompat notificationManagerCompat;

    @Inject
    Vibrator vibrator;

    @Inject
    PopupView popupView;

    NotificationCompat.Builder builder;

    int badCount = 0;

    @Inject
    NotificationHandlerImpl() {
    }

    @Override
    public void init() {
        builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setContentIntent(
                        PendingIntent.getActivity(
                                context,
                                0,
                                MainActivity.createIntent(context),
                                PendingIntent.FLAG_UPDATE_CURRENT
                        )
                ).addAction(R.drawable.ic_pause, "Pause",
                        PendingIntent.getService(
                                context,
                                0,
                                MainService.createPauseIntent(context),
                                PendingIntent.FLAG_UPDATE_CURRENT
                        )
                );

        popupView.init();
    }

    @Override
    public void update(double patch, Status status) {
        Timber.d("iconRes=%d", status.statIcon);
        builder.setContentTitle(context.getString(status.notificationMessage))
                .setContentText("傾き " + patch)
                .setWhen(System.currentTimeMillis())
                .setLargeIcon(status.getLargeIcon(context))
                .setSmallIcon(status.statIcon);

        popupView.setTitle(status.notificationMessage)
                .setMessage(R.string.notification_msg_patch, patch)
                .setIcon(status.icon);

        if (status == Status.BAD) {
            badCount++;
            if (badCount == 3) {
                vibrator.vibrate(200);
                popupView.show();
            }
        } else {
            badCount = 0;
            popupView.cancel();
        }

        notificationManagerCompat.notify(R.string.app_name, builder.build());
    }

    @Override
    public void destroy() {
        notificationManagerCompat.cancel(R.string.app_name);
    }
}
