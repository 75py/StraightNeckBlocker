package com.nagopy.android.straightneckblocker.model.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.util.SparseArray;

import com.nagopy.android.straightneckblocker.R;

public enum Status {

    NONE(R.string.notification_status_none, R.mipmap.ic_normal, R.drawable.ic_stat_normal),
    STAY(R.string.notification_status_stay, R.mipmap.ic_normal, R.drawable.ic_stat_normal),
    NICE(R.string.notification_status_nice, R.mipmap.ic_good, R.drawable.ic_stat_good),
    BAD(R.string.notification_status_bad, R.mipmap.ic_bad, R.drawable.ic_stat_bad),
    LIE(R.string.notification_status_lie, R.mipmap.ic_normal, R.drawable.ic_stat_normal),
    ;

    @StringRes
    final int notificationMessage;

    @DrawableRes
    final int icon;

    @DrawableRes
    final int statIcon;

    Status(int resId, @DrawableRes int iconId, @DrawableRes int statIconId) {
        notificationMessage = resId;
        icon = iconId;
        statIcon = statIconId;
    }

    static final SparseArray<Bitmap> RES_ICON_CACHE = new SparseArray<>();

    public synchronized Bitmap getLargeIcon(Context context) {
        Bitmap bitmap = RES_ICON_CACHE.get(icon);
        if (bitmap == null) {
            bitmap = BitmapFactory.decodeResource(context.getResources(), icon);
            RES_ICON_CACHE.put(icon, bitmap);
        }
        return bitmap;
    }

}
