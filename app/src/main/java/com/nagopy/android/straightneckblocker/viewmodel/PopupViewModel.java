package com.nagopy.android.straightneckblocker.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import com.nagopy.android.straightneckblocker.R;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PopupViewModel {

    public ObservableField<String> title = new ObservableField<>();
    public ObservableField<String> message = new ObservableField<>();
    public ObservableInt icon = new ObservableInt(R.mipmap.ic_good);

    @Inject
    Context context;

    @Inject
    PopupViewModel() {
    }

    public void setTitle(@StringRes int resId) {
        title.set(context.getString(resId));
    }

    public void setMessage(@StringRes int resId, Object... formatArgs) {
        String text = context.getString(resId, formatArgs);
        message.set(text);
    }

    public void setIcon(@DrawableRes int resId) {
        icon.set(resId);
    }

}
