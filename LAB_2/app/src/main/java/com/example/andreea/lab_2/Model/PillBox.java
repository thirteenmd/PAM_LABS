package com.example.andreea.lab_2.Model;

import android.content.Context;

import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;

/**
 * Created by Andreea on 11/12/2017.
 */

public class PillBox {
    private DbHelper db;
    private static List<Long> tempIds;
    private static String tempName;

    public List<Long> getTempIds(){return Collections.unmodifiableList(tempIds);}

    public void setTempIds(List<Long> tempIds){this.tempIds = tempIds;}

    public String getTempName(){return tempName;}

    public void setTempName(String tempName){this.tempName = tempName;}

    public List<Pill> getPills(Context c){
        db = new DbHelper(c);
        List<Pill> allPills = db.getAllPills();
        db.close();
        return allPills;
    }

    public long allPill(Context c, Pill pill){
        db = new DbHelper(c);
        long pillId = db.createPill(pill);
        pill.setPillId(pillId);
        db.close();
        return pillId;
    }

    public Pill getPillByName(Context c, String pillName){
        db = new DbHelper(c);
        Pill wantedPill = db.getPillByName(pillName);
        db.close();
        return wantedPill;
    }

    public void allAlarm(Context c, Alarm alarm, Pill pill){
        db = new DbHelper(c);
        db.createAlarm(alarm, pill.getPillId());
        db.close();
    }

    public List<Alarm> getAlarms(Context c, int dayOfWeek) throws URISyntaxException{
        db = new DbHelper(c);
        List<Alarm> daysAlarms = db.getAlarmsByDay(dayOfWeek);
        db.close();
        Collections.sort(daysAlarms);
        return daysAlarms;
    }

    public List<Alarm> getAlarmByPill(Context c, String pillName) throws URISyntaxException{
        db = new DbHelper(c);
        List<Alarm> pillsAlarms = db.getAllAlarmsByPill(pillName);
        db.close();
        return pillsAlarms;
    }

    public boolean pillExists(Context c, String pillName) {
        db = new DbHelper(c);
        for (Pill pill: this.getPills(c)){
            if (pill.getPillName().equals(pillName))
                return true;
        }
        return false;
    }

    public void deletePill(Context c, String pillName) throws URISyntaxException{
        db = new DbHelper(c);
        db.deletePill(pillName);
        db.close();
    }

    public void deleteAlarm(Context c, long alarmId){
        db = new DbHelper(c);
        db.deleteAlarm(alarmId);
        db.close();
    }

    public  void addToHistory(Context c, History h){
        db = new DbHelper(c);
        db.createHistory(h);
        db.close();
    }

    public List<History> getHistory(Context c){
        db = new DbHelper(c);
        List<History> history = db.getHistory();
        db.close();
        return history;
    }

    public Alarm getAlarmById(Context c, long alarm_id) throws URISyntaxException{
        db = new DbHelper(c);
        Alarm alarm = db.getAlarmById(alarm_id);
        db.close();
        return alarm;
    }

    public int getDayOfWeek(Context c, long alarm_id) throws URISyntaxException{
        db = new DbHelper(c);
        int dayOfWeek = db.getDayOfWeek(alarm_id);
        db.close();
        return dayOfWeek;
    }



}