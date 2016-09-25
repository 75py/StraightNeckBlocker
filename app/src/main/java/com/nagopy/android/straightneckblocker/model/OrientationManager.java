package com.nagopy.android.straightneckblocker.model;

public interface OrientationManager {

    void init();

    void resume();

    void pause();

    void destroy();

    /**
     * @return 傾きを取得する用意ができているかどうか。できていればtrue
     */
    boolean ready();

    double getPatch();

}
