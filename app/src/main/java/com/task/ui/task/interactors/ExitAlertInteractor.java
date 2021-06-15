package com.task.ui.task.interactors;

import androidx.appcompat.app.AppCompatActivity;

public interface ExitAlertInteractor {

    void performExitAlert(AppCompatActivity appCompatActivity, OnExitAlertListener exitAlertListener);

    interface OnExitAlertListener {
        void onYesExit();
        void onCancel();
    }
}
