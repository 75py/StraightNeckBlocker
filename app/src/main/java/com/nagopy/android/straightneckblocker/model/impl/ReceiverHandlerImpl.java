package com.nagopy.android.straightneckblocker.model.impl;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;

import com.nagopy.android.straightneckblocker.model.ReceiverHandler;

import javax.inject.Inject;

public class ReceiverHandlerImpl implements ReceiverHandler {

    @Inject
    Context context;

    IntentFilter filter;
    BroadcastReceiver receiver;

    @Inject
    public ReceiverHandlerImpl() {
    }

    @Override
    public ReceiverHandlerImpl setActions(String... actions) {
        if (actions == null || actions.length == 0) {
            throw new IllegalArgumentException("actions are required");
        }

        filter = new IntentFilter();
        for (String action : actions) {
            filter.addAction(action);
        }
        return this;
    }

    @Override
    public ReceiverHandlerImpl setReceiver(BroadcastReceiver receiver) {
        this.receiver = receiver;
        return this;
    }

    @Override
    public void register() {
        context.registerReceiver(receiver, filter);
    }

    @Override
    public void unregister() {
        context.unregisterReceiver(receiver);
    }
}
