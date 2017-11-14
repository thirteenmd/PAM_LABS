package com.example.andreea.lab_2.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import javax.xml.transform.URIResolver;

/**
 * Created by Andreea on 11/12/2017.
 */

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "pill_model_database";

    private static final int DATABASE_VERSION = 3;

    private static final String PILL_TABLE = "pills";
    private static final String ALARM_TABLE = "alarms";
    private static final String PILL_ALARM_LINKS = "pill_alarm";
    private static final String HISTORIES_TABLE = "histories";

    public static final String KEY_ROWID = "id";

    public static final String KEY_PILLNAME = "pillName";

    private static final String KEY_INTENT = "intent";
    private static final String KEY_HOUR = "hour";
    private static final String KEY_MINUTE = "minute";
    private static final String KEY_DAY_WEEK = "day_of_week";
    private static final String KEY_ALARMS_PILL_NAME = "pillName";

    private static final String KEY_PILLTABLE_ID = "pill_id";
    private static final String KEY_ALARMTABLE_ID = "alarm_id";

    private static final String KEY_DATE_STRING = "date";

    private static final String CREATE_PILL_TABLE =
            "create table " + PILL_TABLE + "(" + KEY_ROWID + " integer primary key not null, "
                    + KEY_PILLNAME + " text not null" + ")";

    private static final String CREATE_ALARM_TABLE =
            "create table " + ALARM_TABLE + "(" + KEY_ROWID + " integer primary key, "
            + KEY_INTENT + " text, "
            + KEY_HOUR + " integer, "
            + KEY_MINUTE + " integer, "
            + KEY_ALARMS_PILL_NAME + " text not null, "
            + KEY_DAY_WEEK + " integer " + ")";

    private static final String CREATE_PILL_ALARM_LINKS_TABLE =
            "create table " + PILL_ALARM_LINKS + "("
                    + KEY_ROWID + " integer primary key not null, "
                    + KEY_PILLTABLE_ID + "integer not null, "
                    + KEY_ALARMTABLE_ID + " integer not null " + ")";

    private static final String CREATE_HISTORIES_TABLE =
            "create table " + HISTORIES_TABLE + "("
            + KEY_ROWID + " integer primary key, "
            + KEY_PILLNAME + " text not null, "
            + KEY_DATE_STRING + " text, "
            + KEY_HOUR + " integer, "
            + KEY_MINUTE + " integer " + ")";

    public DbHelper (Context context){super(context, DATABASE_NAME, null, DATABASE_VERSION);}


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PILL_TABLE);
        db.execSQL(CREATE_ALARM_TABLE);
        db.execSQL(CREATE_PILL_ALARM_LINKS_TABLE);
        db.execSQL(CREATE_HISTORIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + PILL_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ALARM_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + PILL_ALARM_LINKS);
        db.execSQL("DROP TABLE IF EXISTS " + HISTORIES_TABLE);
        onCreate(db);
    }

    public long createPill(Pill pill){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PILLNAME, pill.getPillName());

        long pill_id = db.insert(PILL_TABLE, null, values);

        return pill_id;
    }

    public long[] createAlarm(Alarm alarm, long pill_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long[] alarm_ids = new long[7];

        int arrayPos = 0;
        for (boolean day : alarm.getDayOfWeek()){
            if (day){
                ContentValues values = new ContentValues();
                values.put(KEY_HOUR, alarm.getHour());
                values.put(KEY_MINUTE, alarm.getMinute());
                values.put(KEY_DAY_WEEK, arrayPos + 1);
                values.put(KEY_ALARMS_PILL_NAME, alarm.getPillName());

                long alarm_id = db.insert(ALARM_TABLE, null, values);
                alarm_ids[arrayPos] = alarm_id;

                createPillAlarmLink(pill_id, alarm_id);
            }
            arrayPos++;
        }
        return alarm_ids;
    }

    private long createPillAlarmLink(long pill_id, long alarm_id){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PILLTABLE_ID, pill_id);
        values.put(KEY_ALARMTABLE_ID, alarm_id);

        long pillAlarmLink_id = db.insert(PILL_ALARM_LINKS, null, values);

        return pillAlarmLink_id;
    }

    public void createHistory(History history){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PILLNAME, history.getPillName());
        values.put(KEY_DATE_STRING, history.getDateString());
        values.put(KEY_HOUR, history.getHourTaken());
        values.put(KEY_MINUTE, history.getMinuteTaken());

        db.insert(HISTORIES_TABLE, null, values);
    }

    public Pill getPillByName(String pillName){
        SQLiteDatabase db = this.getReadableDatabase();

        String dbPill = "select * from "
                + PILL_TABLE + " where "
                + KEY_PILLNAME + " = "
                + "'" + pillName + "'";

        Cursor c = db.rawQuery(dbPill, null);

        Pill pill = new Pill();

        if (c.moveToFirst() && c.getCount() >= 1){
            pill.setPillName(c.getString(c.getColumnIndex(KEY_PILLNAME)));
            pill.setPillId(c.getLong(c.getColumnIndex(KEY_ROWID)));
        }
        return pill;
    }

    public List<Pill> getAllPills(){
        List<Pill> pills = new ArrayList<>();
        String dbPills = "SELECT * FROM " + PILL_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(dbPills, null);

        if (c.moveToFirst()){
            do{
                Pill p = new Pill();
                p.setPillName(c.getString(c.getColumnIndex(KEY_PILLNAME)));
                p.setPillId(c.getLong(c.getColumnIndex(KEY_ROWID)));
            }while (c.moveToNext());
        }
        c.close();
        return pills;
    }

    public List<Alarm> getAllAlarmsByPill(String pillName) throws URISyntaxException{
        List<Alarm> alarmsByPill = new ArrayList<Alarm>();

        String selectQuery = "SELECT * FROM " +
                ALARM_TABLE + " alarm, " +
                PILL_TABLE + " pill, " +
                PILL_ALARM_LINKS + " pillAlarm WHERE " +
                "pill." + KEY_PILLNAME + " = '" + pillName + "'" +
                " AND pill." + KEY_ROWID + " = " +
                "pillAlarm." + KEY_PILLTABLE_ID +
                " AND alar." + KEY_ROWID + " = " +
                "pillAlarm." + KEY_ALARMTABLE_ID;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()){
            do {
                Alarm alarm = new Alarm();
                alarm.setId(c.getInt(c.getColumnIndex(KEY_ROWID)));
                alarm.setHour(c.getInt(c.getColumnIndex(KEY_HOUR)));
                alarm.setMinute(c.getInt(c.getColumnIndex(KEY_HOUR)));
                alarm.setPillName(c.getString(c.getColumnIndex(KEY_ALARMS_PILL_NAME)));

                alarmsByPill.add(alarm);
            }while (c.moveToNext());
        }
        c.close();
        return combineAlarms(alarmsByPill);
    }

    public List<Alarm> getAlarmsByDay(int day){
        List<Alarm> daysAlarms = new ArrayList<Alarm>();

        String selectQuery = "SELECT * FROM " +
                ALARM_TABLE + " alarm WHERE " +
                "alarm." + KEY_DAY_WEEK +
                " = '" + day + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()){
            do {
                Alarm alarm = new Alarm();
                alarm.setId(c.getInt(c.getColumnIndex(KEY_ROWID)));
                alarm.setHour(c.getInt(c.getColumnIndex(KEY_HOUR)));
                alarm.setMinute(c.getInt(c.getColumnIndex(KEY_MINUTE)));
                alarm.setPillName(c.getString(c.getColumnIndex(KEY_ALARMS_PILL_NAME)));

                daysAlarms.add(alarm);
            }while (c.moveToNext());
        }
        c.close();
        return daysAlarms;
    }

    public Alarm getAlarmById(long alarm_id) throws URISyntaxException{
        String dbAlarm = "SELECT * FROM " +
                ALARM_TABLE + " WHERE " +
                KEY_ROWID + " = " + alarm_id;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(dbAlarm, null);

        if (c != null)
            c.moveToFirst();

        Alarm alarm = new Alarm();
        alarm.setId(c.getInt(c.getColumnIndex(KEY_ROWID)));
        alarm.setHour(c.getInt(c.getColumnIndex(KEY_HOUR)));
        alarm.setMinute(c.getInt(c.getColumnIndex(KEY_MINUTE)));
        alarm.setPillName(c.getString(c.getColumnIndex(KEY_ALARMS_PILL_NAME)));

        c.close();
        return alarm;
    }

    private List<Alarm> combineAlarms(List<Alarm> dbAlarms) throws URISyntaxException{
        List<String> timesOfDay = new ArrayList<>();
        List<Alarm> combinedAlarms = new ArrayList<>();

        for (Alarm alarm : dbAlarms){
            if (timesOfDay.contains(alarm.getStringTime())){
                for (Alarm ala : dbAlarms){
                    if (ala.getStringTime().equals(alarm.getStringTime())){
                        int day = getDayOfWeek(alarm.getId());
                        boolean[] days = ala.getDayOfWeek();
                        days[day - 1] = true;
                        ala.setDayOfWeek(days);
                        ala.addId(alarm.getId());
                    }
                }
            }else {
                Alarm newAlarm = new Alarm();
                boolean[] days = new boolean[7];

                newAlarm.setPillName(alarm.getPillName());
                newAlarm.setId(alarm.getId());
                newAlarm.setMinute(alarm.getMinute());
                newAlarm.setHour(alarm.getHour());

                int day = getDayOfWeek(alarm.getId());
                days[day - 1] = true;
                newAlarm.setDayOfWeek(days);

                timesOfDay.add(alarm.getStringTime());
                combinedAlarms.add(newAlarm);
            }
        }
        Collections.sort(combinedAlarms);
        return combinedAlarms;
    }

    public int getDayOfWeek(long alarm_id) throws URISyntaxException{
        SQLiteDatabase db = this.getReadableDatabase();

        String dbAlarm = "SELECT * FROM " +
                ALARM_TABLE + " WHERE " +
                KEY_ROWID + " = " + alarm_id;

        Cursor c = db.rawQuery(dbAlarm, null);

        if (c != null)
            c.moveToFirst();

        int dayOfWeek = c.getInt(c.getColumnIndex(KEY_DAY_WEEK));
        c.close();

        return dayOfWeek;
    }

    public List<History> getHistory(){
        List<History> allHistory = new ArrayList<>();
        String dbHistory = "SELECT * FROM " + HISTORIES_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(dbHistory, null);

        if (c.moveToFirst()){
            do {
                History history = new History();
                history.setPillName(c.getString(c.getColumnIndex(KEY_PILLNAME)));
                history.setDateString(c.getString(c.getColumnIndex(KEY_DATE_STRING)));
                history.setHourTaken(c.getInt(c.getColumnIndex(KEY_HOUR)));
                history.setMinuteTaken(c.getInt(c.getColumnIndex(KEY_MINUTE)));
                allHistory.add(history);
            }while (c.moveToNext());
        }
        c.close();
        return allHistory;
    }

    private void deletePillAlarmLinks(long alarmId){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(PILL_ALARM_LINKS, KEY_ALARMTABLE_ID + " = ?", new String[]{String.valueOf(alarmId)});
    }

    public void deleteAlarm(long alarmId){
        SQLiteDatabase db = this.getReadableDatabase();
        deletePillAlarmLinks(alarmId);

        db.delete(ALARM_TABLE, KEY_ROWID + " = ?", new String[]{String.valueOf(alarmId)});
    }

    public void deletePill(String pillName) throws URISyntaxException{
        SQLiteDatabase db = this.getReadableDatabase();
        List<Alarm> pillsAlarm;

        pillsAlarm = getAllAlarmsByPill(pillName);
        for (Alarm alarm: pillsAlarm){
            long id = alarm.getId();
            deleteAlarm(id);
        }
        db.delete(PILL_TABLE, KEY_PILLNAME + " = ?", new String[]{pillName});
    }




}
