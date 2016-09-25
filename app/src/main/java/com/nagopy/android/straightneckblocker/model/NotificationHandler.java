package com.nagopy.android.straightneckblocker.model;

import com.nagopy.android.straightneckblocker.model.impl.Status;

public interface NotificationHandler {

    void init();

    void update(double patch, Status status);

    void destroy();

}
