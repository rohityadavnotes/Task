package com.task.progressdialog;

import android.app.Dialog;
import android.content.Context;

import com.task.R;

public class DotProgressDialog {

    private static DotProgressDialog dotProgressDialog = null;
    private static DottedProgressBar dottedProgressBar;
    private Dialog dialog;

    public static DotProgressDialog getInstance()
    {
        if (dotProgressDialog == null)
        {
           dotProgressDialog = new DotProgressDialog();
        }
        return dotProgressDialog;
    }

    public void showProgress(Context context, boolean cancelable)
    {
        dialog = new Dialog(context, R.style.TransparentProgressDialog);

        dialog.setContentView(R.layout.dot_progress_dialog);
        dialog.setCancelable(cancelable);
        dialog.setCanceledOnTouchOutside(cancelable);

        dottedProgressBar = dialog.findViewById(R.id.dotted_progress_bar);
        startProgress();

        dialog.show();
    }

    public void hideProgress()
    {
        if (dialog != null)
        {
            stopProgress();
            dialog.dismiss();
            dialog = null;
        }
    }

    private void startProgress() {
        dottedProgressBar.startProgress();
    }

    private void stopProgress() {
        dottedProgressBar.stopProgress();
    }
}
