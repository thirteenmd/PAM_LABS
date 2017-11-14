package com.example.andreea.lab_2.Model;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Andreea on 11/12/2017.
 */

public class Alarm implements Comparable<Alarm> {
    private long id;
    private int hour;
    private int minute;
    private String pillName;
    private List<Long> ids = new LinkedList<Long>();
    private boolean dayOfWeek[] = new boolean[7];


    public long getId(){return id;}

    public void setId(long id){this.id = id;}

    public List<Long> getIds(){return Collections.unmodifiableList(ids);}

    public boolean[] getDayOfWeek(){return dayOfWeek;}

    public void setDayOfWeek(boolean[] dayOfWeek){this.dayOfWeek = dayOfWeek;}

    public void addId(long id){ids.add(id);}

    public int getHour(){return hour;}

    public void setHour(int hour){this.hour = hour;}

    public int getMinute(){return minute;}

    public void setMinute(int minute){this.minute = minute;}

    public String getAm_pm(){return (hour < 12) ? "am" : "pm";}

    public String getPillName(){return pillName;}

    public void setPillName(String pillName){this.pillName = pillName;}


    @Override
    public int compareTo(@NonNull Alarm anotherAlarm) {
        if (hour < anotherAlarm.getHour())
            return -1;
        else if (hour > anotherAlarm.getHour())
            return 1;
        else {
            if (minute < anotherAlarm.getMinute())
                return -1;
            else if (minute > anotherAlarm.getMinute())
                return 1;
            else
                return 0;
        }
    }


    public String getStringTime(){
        int nonMilitaryHour = hour % 12;
        if (nonMilitaryHour == 0)
            nonMilitaryHour = 12;

        String min = Integer.toString(minute);
        if (minute < 10)
            min = "0" + minute;

        String time = nonMilitaryHour + ":" + min + ":" + getAm_pm();
        return time;
    }


}
