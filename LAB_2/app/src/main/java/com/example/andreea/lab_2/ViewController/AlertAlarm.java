package com.example.andreea.lab_2.ViewController;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.WindowManager;

/**
 * Created by Andreea on 11/15/2017.
 */

public class AlertAlarm extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        setCancelable(false);

        final String pill_name = getActivity().getIntent().getStringExtra("pill_name");

        builder.setMessage("Did you take your " + pill_name + "?");

        builder.setPositiveButton("I took it", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AlertActivity act = (AlertActivity)getActivity();
                act.doPositiveClick(pill_name);
                getActivity().finish();
            }
        });

        builder.setNeutralButton("Snooze", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AlertActivity act = (AlertActivity)getActivity();
                act.doNeutralClick(pill_name);
                getActivity().finish();
            }
        });

        builder.setNegativeButton("I won't take it.", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AlertActivity act = (AlertActivity)getActivity();
                act.doNegativeClick();
                getActivity().finish();
            }
        });

        return builder.create();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().finish();
    }
}
