package com.example.andreea.lab_2.ViewController;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.andreea.lab_2.Model.History;
import com.example.andreea.lab_2.Model.Pill;
import com.example.andreea.lab_2.Model.PillBox;
import com.example.andreea.lab_2.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AlertActivity extends FragmentActivity {

    private AlarmManager alarmManager;
    private PendingIntent operation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AlertAlarm alert = new AlertAlarm();

        alert.show(getSupportFragmentManager(), "AertAlarm");
    }

    public void doNeutralClick(String pillName){
        final int _id = (int) System.currentTimeMillis();
        final long minute = 60000;
        long snoozeLength = 10;
        long currentTime = System.currentTimeMillis();
        long min = currentTime + minute * snoozeLength;

        Intent intent = new Intent(getBaseContext(), AlertActivity.class);
        intent.putExtra("pill_name", pillName);

        operation = PendingIntent.getActivity(getBaseContext(), _id, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager = (AlarmManager) getBaseContext().getSystemService(ALARM_SERVICE);

        alarmManager.set(AlarmManager.RTC_WAKEUP, min, operation);
        Toast.makeText(getBaseContext(), "Alarm for " + pillName + " was snoozed for 10 minutes.", Toast.LENGTH_LONG).show();

        finish();
    }

    public void doPositiveClick(String pillName){
        PillBox pillBox = new PillBox();
        Pill pill = pillBox.getPillByName(this, pillName);
        History history = new History();

        Calendar takeTime = Calendar.getInstance();
        Date date = takeTime.getTime();
        String dateString = new SimpleDateFormat("MMM d, yyy").format(date);

        int hour = takeTime.get(Calendar.HOUR_OF_DAY);
        int minute = takeTime.get(Calendar.MINUTE);
        String am_pm = (hour < 12) ? "am" : "pm";

        history.setHourTaken(hour);
        history.setMinuteTaken(minute);
        history.setDateString(dateString);
        history.setPillName(pillName);

        pillBox.addToHistory(this, history);

        String stringMinute;
        if (minute < 10)
            stringMinute = "0" + minute;
        else
            stringMinute = "" + minute;

        int nonMilitaryName = hour % 12;
        if (nonMilitaryName == 0)
            nonMilitaryName = 12;

        Toast.makeText(getBaseContext(), pillName + " was taken at " + nonMilitaryName + ":" + minute + " " + am_pm + ".", Toast.LENGTH_LONG).show();

        Intent returnHome = new Intent(getBaseContext(), MainActivity.class);
        startActivity(returnHome);
        finish();
    }

    public void doNegativeClick(){finish();}
}
