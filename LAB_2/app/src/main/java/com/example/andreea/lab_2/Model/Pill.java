package com.example.andreea.lab_2.Model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Andreea on 11/12/2017.
 */

public class Pill {
    private String pillName;
    private long pillId;
    private List<Alarm> alarms = new LinkedList<Alarm>();

    public String getPillName(){return pillName;}

    public void setPillName(String pillName){this.pillName = pillName;}

    public void addAlarm(Alarm alarm){
        alarms.add(alarm);
        Collections.sort(alarms);
    }

    public long getPillId(){return pillId;}

    public void setPillId(long pillId){this.pillId = pillId;}

}
