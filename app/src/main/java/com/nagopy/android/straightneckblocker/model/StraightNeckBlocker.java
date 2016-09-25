package com.nagopy.android.straightneckblocker.model;

import com.nagopy.android.straightneckblocker.model.impl.Status;

public interface StraightNeckBlocker {

    void updatePatch(double patch);

    Status judge();
}
