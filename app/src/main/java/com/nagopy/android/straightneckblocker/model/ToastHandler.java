package com.nagopy.android.straightneckblocker.model;

import android.view.View;

public interface ToastHandler {

    ToastHandler setView(View view);

    void show();

    void cancel();
}
