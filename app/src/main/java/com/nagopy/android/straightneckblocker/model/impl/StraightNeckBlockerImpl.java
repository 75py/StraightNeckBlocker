package com.nagopy.android.straightneckblocker.model.impl;

import com.nagopy.android.straightneckblocker.model.StraightNeckBlocker;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class StraightNeckBlockerImpl implements StraightNeckBlocker {

    DataList<Double> data = new DataList<>();

    @Inject
    public StraightNeckBlockerImpl() {
    }

    public void updatePatch(double patch) {
        data.update(patch);
    }

    public Status judge() {
        if (!data.ready()) {
            return Status.NONE;
        }

        boolean changed1 = Math.abs(data.current - data.prev1) < 1;
        boolean changed2 = Math.abs(data.prev2 - data.prev2) < 1;
        if (changed1 && changed2) {
            return Status.STAY;
        } else {
            if (data.current < 35) {
                return Status.NICE;
            } else {
                return Status.BAD;
            }
        }
    }

}
