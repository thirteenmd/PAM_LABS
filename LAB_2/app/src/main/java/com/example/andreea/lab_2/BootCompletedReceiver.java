package com.example.andreea.lab_2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Andreea on 1/3/2018.
 */

public class BootCompletedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent)
    {
        // just create AlarmListAdapter and it will load alarms and start them
        new AlarmListAdapter(context);
    }
}
