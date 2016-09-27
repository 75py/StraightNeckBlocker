package com.nagopy.android.straightneckblocker.model.impl;


import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.nagopy.android.straightneckblocker.model.ToastHandler;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ToastHandlerImpl implements ToastHandler {

    @Inject
    WindowManager windowManager;

    WindowManager.LayoutParams params;
    View view;
    boolean show = false;
    Handler handler = new Handler(Looper.getMainLooper());

    @Inject
    ToastHandlerImpl() {
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
        params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
    }

    @Override
    public ToastHandler setView(View view) {
        this.view = view;
        return this;
    }

    @Override
    public synchronized void show() {
        if (show) {
            windowManager.updateViewLayout(view, params);
        } else {
            windowManager.addView(view, params);
        }
        show = true;

        // hideDelayed(5000);
    }

    @Override
    public synchronized void cancel() {
        if (show) {
            hideDelayed(2000);
        }
    }

    void hideDelayed(long delayMillis) {
        handler.postDelayed(runnableHide, delayMillis);
    }

    void hide() {
        if (show) {
            windowManager.removeView(view);
        }
        show = false;
    }

    private Runnable runnableHide = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
}
