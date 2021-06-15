package com.task.ui.common.views;

import androidx.annotation.StringRes;
import com.task.ui.androidmvp.MvpView;

public interface BaseActivityView extends MvpView {

    void showToast(String message);

    void showToast(@StringRes int messageResId);
}
