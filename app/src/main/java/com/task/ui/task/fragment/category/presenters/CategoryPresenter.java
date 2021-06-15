package com.task.ui.task.fragment.category.presenters;

import androidx.appcompat.app.AppCompatActivity;

import com.task.data.remote.NetworkService;
import com.task.ui.androidmvp.MvpPresenter;
import com.task.ui.common.views.BaseActivityView;
import com.task.ui.common.views.BaseFragmentView;

public interface CategoryPresenter<V extends BaseFragmentView> extends MvpPresenter<V> {
    void getCategory(NetworkService networkService);
}