package com.nagopy.android.straightneckblocker;

import com.nagopy.android.straightneckblocker.app.MainActivity;
import com.nagopy.android.straightneckblocker.app.MainService;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(MainService mainService);

    void inject(MainActivity mainActivity);
}
