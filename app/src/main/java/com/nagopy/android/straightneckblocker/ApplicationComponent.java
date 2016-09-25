package com.nagopy.android.straightneckblocker;

import com.nagopy.android.straightneckblocker.app.MainActivity;
import com.nagopy.android.straightneckblocker.app.MainService;
import com.nagopy.android.straightneckblocker.app.MonitorService;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(MainService mainService);

    void inject(MonitorService monitorService);

    void inject(MainActivity mainActivity);
}
