package com.task.ui.task.fragment.item.presenters;

import com.task.data.remote.NetworkService;
import com.task.ui.androidmvp.MvpPresenter;
import com.task.ui.common.views.BaseActivityView;
import com.task.ui.common.views.BaseFragmentView;

public interface ItemPresenter<V extends BaseFragmentView> extends MvpPresenter<V> {
    void getItem(String categoryId, NetworkService networkService);
}