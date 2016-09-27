package com.nagopy.android.straightneckblocker.view;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;

import com.nagopy.android.straightneckblocker.databinding.ViewPopupToastBinding;
import com.nagopy.android.straightneckblocker.model.ToastHandler;
import com.nagopy.android.straightneckblocker.viewmodel.PopupViewModel;

import javax.inject.Inject;

public class PopupToastView implements PopupView {

    @Inject
    Context context;

    @Inject
    PopupViewModel popupViewModel;

    @Inject
    ToastHandler toastHandler;

    ViewPopupToastBinding binding;

    @Inject
    PopupToastView() {
    }

    @Override
    public PopupView init() {
        binding = ViewPopupToastBinding.inflate(LayoutInflater.from(context));
        binding.setVm(popupViewModel);
        binding.getRoot().setAlpha(0.77f);
        toastHandler.setView(binding.getRoot());
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
        toastHandler.show();
    }

    @Override
    public void cancel() {
        toastHandler.cancel();
    }

}
