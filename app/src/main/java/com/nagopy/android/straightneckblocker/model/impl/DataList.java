package com.nagopy.android.straightneckblocker.model.impl;

import android.support.annotation.NonNull;

class DataList<T> {

    T prev2;
    T prev1;
    T current;

    void update(@NonNull T newValue) {
        prev2 = prev1;
        prev1 = current;
        current = newValue;
    }

    boolean ready() {
        return prev2 != null;
    }

    void clear() {
        prev2 = null;
        prev1 = null;
        current = null;
    }

}
