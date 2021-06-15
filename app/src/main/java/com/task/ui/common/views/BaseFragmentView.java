package com.task.ui.common.views;

import androidx.annotation.StringRes;
import com.task.ui.androidmvp.MvpView;

public interface BaseFragmentView extends MvpView {
    void showProgressBar();

    void hideProgressBar();

    void showToast(String message);

    void showToast(@StringRes int messageResId);
}