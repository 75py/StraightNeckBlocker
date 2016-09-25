package com.nagopy.android.straightneckblocker.view;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

public interface PopupView {

    PopupView init();

    PopupView setTitle(@StringRes int resId);

    PopupView setMessage(@StringRes int resId, Object... formatArgs);

    PopupView setIcon(@DrawableRes int iconResId);

    void show();

    void cancel();
}
