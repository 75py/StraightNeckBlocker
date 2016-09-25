/*
package com.nagopy.android.straightneckblocker.view;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.nagopy.android.straightneckblocker.R;

import javax.inject.Inject;

public class PopupViewWMImpl implements PopupView {

    @Inject
    Context context;

    @Inject
    WindowManager windowManager;

    Handler handler = new Handler(Looper.getMainLooper());

    // late init
    View parentView;
    TextView titleView;
    TextView messageView;
    ImageView iconView;

    // late init
    WindowManager.LayoutParams params;

    boolean show = false;


    @Inject
    PopupViewWMImpl() {
    }

    @Override
    public PopupView init() {
        parentView = View.inflate(context, R.layout.view_popup, null);
        titleView = (TextView) parentView.findViewById(android.R.id.title);
        messageView = (TextView) parentView.findViewById(android.R.id.message);
        iconView = (ImageView) parentView.findViewById(android.R.id.icon);
        params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_TOAST,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_FULLSCREEN
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT);
        params.y = 100;
        params.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
        return this;
    }

    @Override
    public PopupView setTitle(@StringRes int resId) {
        titleView.setText(resId);
        return this;
    }

    @Override
    public PopupView setMessage(@StringRes int resId, Object... formatArgs) {
        String message = context.getString(resId, formatArgs);
        messageView.setText(message);
        return this;
    }

    @Override
    public PopupView setIcon(@DrawableRes int iconResId) {
        iconView.setImageResource(iconResId);
        return this;
    }

    @Override
    public synchronized void show() {
        if (show) {
            windowManager.updateViewLayout(parentView, params);
        } else {
            windowManager.addView(parentView, params);
        }
        show = true;

        hideDelayed(5000);

    }

    public synchronized void hideDelayed(long delayMillis) {
        handler.postDelayed(runnableHide, delayMillis);
    }

    @Override
    public synchronized void cancel() {
        if (show) {
            windowManager.removeView(parentView);
        }
        show = false;
    }

    private Runnable runnableHide = new Runnable() {
        @Override
        public void run() {
            cancel();
        }
    };

}
*/