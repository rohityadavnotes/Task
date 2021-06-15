package com.task.ui.task.presenters;

import androidx.appcompat.app.AppCompatActivity;
import com.task.ui.androidmvp.MvpPresenter;
import com.task.ui.common.views.BaseActivityView;

public interface TaskPresenter<V extends BaseActivityView> extends MvpPresenter<V> {
    void exitAlert(AppCompatActivity appCompatActivity);
}