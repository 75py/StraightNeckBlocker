package com.nagopy.android.straightneckblocker.model;

import android.content.BroadcastReceiver;

public interface ReceiverHandler {

    ReceiverHandler setActions(String... actions);

    ReceiverHandler setReceiver(BroadcastReceiver receiver);

    void register();

    void unregister();
}
