package com.nagopy.android.straightneckblocker.view;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.nagopy.android.straightneckblocker.databinding.ViewPopupToastBinding;
import com.nagopy.android.straightneckblocker.viewmodel.PopupViewModel;

import javax.inject.Inject;

public class PopupToastView implements PopupView {

    @Inject
    Context context;

    @Inject
    PopupViewModel popupViewModel;

    ViewPopupToastBinding binding;
    Toast toast;

    @Inject
    PopupToastView() {
    }

    @Override
    public PopupView init() {
        binding = ViewPopupToastBinding.inflate(LayoutInflater.from(context));
        binding.setVm(popupViewModel);

        toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(binding.getRoot());

        return this;
    }

    @Override
    public PopupView setTitle(@StringRes int resId) {
        popupViewModel.setTitle(resId);
        return this;
    }

    @Override
    public PopupView setMessage(@StringRes int resId, Object... formatArgs) {
        popupViewModel.setMessage(resId, formatArgs);
        return this;
    }

    @Override
    public PopupView setIcon(@DrawableRes int iconResId) {
        popupViewModel.setIcon(iconResId);
        return this;
    }

    @Override
    public void show() {
        toast.show();
    }

    @Override
    public void cancel() {
        toast.cancel();
    }

}
