package com.task.ui.task.interactors;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.task.R;

public class TaskInteractorImpl implements ExitAlertInteractor {

    private static final String TAG = TaskInteractorImpl.class.getSimpleName();

    @Override
    public void performExitAlert(AppCompatActivity appCompatActivity, OnExitAlertListener exitAlertListener) {
        ViewGroup viewGroup = appCompatActivity.findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(appCompatActivity).inflate(R.layout.exit_alert_dialog, viewGroup, false);

        AlertDialog.Builder builder = new AlertDialog.Builder(appCompatActivity);
        builder.setView(dialogView);
        builder.setCancelable(false);
        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        alertDialog.show();

        TextView no = dialogView.findViewById(R.id.no_text_view_button);
        TextView yes =dialogView.findViewById(R.id.yes_text_view_button);

        no.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                alertDialog.dismiss();
            }
        });

        yes.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                alertDialog.dismiss();
                appCompatActivity.finishAffinity();
            }
        });
    }
}