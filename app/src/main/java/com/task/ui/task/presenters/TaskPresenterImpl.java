package com.task.ui.task.presenters;

import androidx.appcompat.app.AppCompatActivity;
import com.task.ui.common.presenters.BasePresenter;
import com.task.ui.task.interactors.ExitAlertInteractor;
import com.task.ui.task.interactors.TaskInteractorImpl;
import com.task.ui.task.views.TaskView;

public class TaskPresenterImpl <V extends TaskView> extends BasePresenter<V> implements TaskPresenter<V> ,
        ExitAlertInteractor.OnExitAlertListener {

    private ExitAlertInteractor exitAlertInteractor;

    public TaskPresenterImpl() {
        this.exitAlertInteractor        = new TaskInteractorImpl();
    }

    @Override
    public void exitAlert(AppCompatActivity appCompatActivity) {
        if (getMvpView() != null) {
            exitAlertInteractor.performExitAlert(appCompatActivity, this);
        }
    }

    @Override
    public void onYesExit() {
        if (getMvpView() != null) {
            getMvpView().onYesExit();
        }
    }

    @Override
    public void onCancel() {
        if (getMvpView() != null) {
            getMvpView().onCancel();
        }
    }
}
