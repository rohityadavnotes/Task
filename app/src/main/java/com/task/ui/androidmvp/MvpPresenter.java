package com.task.ui.androidmvp;

import androidx.annotation.NonNull;
import com.task.ui.common.presenters.BasePresenter;

public interface MvpPresenter<V extends MvpView> {

    void attachView(@NonNull V mvpView);

    void detachView();

    V getMvpView();

    boolean isViewAttached();

    void checkViewAttached() throws BasePresenter.MvpViewNotAttachedException;
}
