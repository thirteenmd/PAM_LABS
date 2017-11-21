package com.example.andreea.lab_2.ViewController;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.andreea.lab_2.Model.Alarm;
import com.example.andreea.lab_2.Model.Pill;
import com.example.andreea.lab_2.Model.PillBox;
import com.example.andreea.lab_2.R;

import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class AddPillActivity extends AppCompatActivity {
    private AlarmManager alarmMnager;
    private PendingIntent operation;
    private boolean dayOfWeekList[] = new boolean[7];

    int hour, minute;
    TextView timeLabel;
    PillBox pillBox = new PillBox();


    TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minuteOfHour) {
            hour = hourOfDay;
            minute = minuteOfHour;
            timeLabel.setText(setTime(hour, minute));
        }
    };

    private String setTime(int hour, int minute) {
        String am_pm = (hour < 12) ? "am" : "pm";
        int nonMilitaryHour = hour % 12;
        if (nonMilitaryHour == 0)
            nonMilitaryHour = 12;
        String minuteWithZero;
        if (minute < 10)
            minuteWithZero = "0" + minute;
        else
            minuteWithZero = "" + minute;
        return nonMilitaryHour + ":" + minuteWithZero + am_pm;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pill);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add an alarm");

        timeLabel = findViewById(R.id.reminder_time);

        final Calendar now = Calendar.getInstance();
        hour = now.get(Calendar.HOUR_OF_DAY);
        minute = now.get(Calendar.MINUTE);
        timeLabel.setText(setTime(hour, minute));

        timeLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerDialog( AddPillActivity.this, timeSetListener, hour, minute, false).show();
            }
        });
        timeLabel.setText(setTime(hour, minute));

        View.OnClickListener setClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int checkBoxCounter = 0;

                EditText editText = findViewById(R.id.pill_name);
                String pill_name = editText.getText().toString();

                Alarm alarm = new Alarm();

                if (!pillBox.pillExist(getApplicationContext(), pill_name)){
                    Pill pill = new Pill();
                    pill.setPillName(pill_name);
                    alarm.setHour(hour);
                    alarm.setMinute(minute);
                    alarm.setPillName(pill_name);
                    alarm.setDayOfWeek(dayOfWeekList);
                    pill.addAlarm(alarm);
                    long pillId = pillBox.addPill(getApplicationContext(), pill);
                    pill.setPillId(pillId);
                    pillBox.addAlarm(getApplicationContext(), alarm, pill);
                } else {
                    Pill pill = pillBox.getPillByName(getApplicationContext(), pill_name);
                    alarm.setHour(hour);
                    alarm.setMinute(minute);
                    alarm.setPillName(pill_name);
                    alarm.setDayOfWeek(dayOfWeekList);
                    pill.addAlarm(alarm);
                    pillBox.addAlarm(getApplicationContext(), alarm, pill);
                }

                List<Long> ids = new LinkedList<Long>();
                try {
                    List<Alarm> alarms = pillBox.getAlarmByPill(getApplicationContext(), pill_name);
                    for (Alarm tempAlarm: alarms){
                        if (tempAlarm.getHour() == hour && tempAlarm.getMinute() == minute){
                            ids = tempAlarm.getIds();
                            break;
                        }
                    }
                }catch (URISyntaxException e){
                    e.printStackTrace();
                }

                for (int i = 0; i < 7; i++){
                    if (dayOfWeekList[i] && pill_name.length() != 0){
                        int dayOfWeek = i + 1;
                        long _id = ids.get(checkBoxCounter);
                        int id = (int) _id;
                        checkBoxCounter++;

                        Intent intent = new Intent(getBaseContext(), AlertActivity.class);
                        intent.putExtra("pill_name", pill_name);

                        operation = PendingIntent.getActivity(getBaseContext(), id, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                        alarmMnager = (AlarmManager) getBaseContext().getSystemService(ALARM_SERVICE);

                        Calendar calendar = Calendar.getInstance();

                        calendar.set(Calendar.HOUR_OF_DAY, hour);
                        calendar.set(Calendar.MINUTE, minute);
                        calendar.set(Calendar.SECOND, 0);
                        calendar.set(Calendar.MILLISECOND, 0);
                        calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);

                        long alarm_time = calendar.getTimeInMillis();

                        if (calendar.before(Calendar.getInstance()))
                            alarm_time += AlarmManager.INTERVAL_DAY * 7;

                        alarmMnager.setRepeating(AlarmManager.RTC_WAKEUP, alarm_time, alarmMnager.INTERVAL_DAY * 7, operation);
                    }
                }
                if (checkBoxCounter == 0 || pill_name.length() == 0){
                    Toast.makeText(getBaseContext(), "Please input a pill name or check at least one day!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getBaseContext(), "Alarm for " + pill_name + "set.", Toast.LENGTH_LONG).show();
                    Intent returnHome = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(returnHome);
                    finish();
                }
            }
        };

        View.OnClickListener cancelClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnHome = new Intent(getBaseContext(), MainActivity.class);
                startActivity(returnHome);
                finish();
            }
        };

        Button btnSetAlarm = findViewById(R.id.btn_set_alarm);
        btnSetAlarm.setOnClickListener(setClickListener);

        Button btnQuitAlarm = findViewById(R.id.btn_cancel_alarm);
        btnQuitAlarm.setOnClickListener(cancelClickListener);
    }
    public void onCheckboxClicked(View view){
        boolean checked = ((CheckBox) view).isChecked();

        switch (view.getId()){
            case R.id.checkbox_monday:
                if (checked)
                    dayOfWeekList[1] = true;
                else
                    dayOfWeekList[1] = false;
                break;
            case R.id.checkbox_tuesday:
                if (checked)
                    dayOfWeekList[2] = true;
                else
                    dayOfWeekList[2] = false;
                break;
            case R.id.checkbox_wednesday:
                if (checked)
                    dayOfWeekList[3] = true;
                else
                    dayOfWeekList[3] = false;
                break;
            case R.id.checkbox_thursday:
                if (checked)
                    dayOfWeekList[4] = true;
                else
                    dayOfWeekList[4] = false;
                break;
            case R.id.checkbox_friday:
                if (checked)
                    dayOfWeekList[5] = true;
                else
                    dayOfWeekList[5] = false;
                break;
            case R.id.checkbox_saturday:
                if (checked)
                    dayOfWeekList[6] = true;
                else
                    dayOfWeekList[6] = false;
                break;
            case R.id.checkbox_sunday:
                if (checked)
                    dayOfWeekList[0] = true;
                else
                    dayOfWeekList[0] = false;
                break;
            case R.id.every_day:
                LinearLayout linearLayout = findViewById(R.id.checkbox_layout);
                for (int i = 0; i < linearLayout.getChildCount(); i++){
                    View v = linearLayout.getChildAt(i);
                    ((CheckBox) v).setChecked(checked);
                    onCheckboxClicked(v);
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent returnHome = new Intent(getBaseContext(), MainActivity.class);
        startActivity(returnHome);
        finish();
    }
}
