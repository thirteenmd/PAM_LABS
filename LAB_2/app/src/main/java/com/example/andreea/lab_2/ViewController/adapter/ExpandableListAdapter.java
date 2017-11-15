package com.example.andreea.lab_2.ViewController.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.andreea.lab_2.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Andreea on 11/13/2017.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context _context;
    private List<String> _listDataHeader;
    private HashMap<String, List<String>> _listDataChild;

    public ExpandableListAdapter(Context context, List<String> listDataHeader, HashMap<String, List<String>> listChildData){
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_group, null);
        }

        TextView listHeader = convertView.findViewById(R.id.lblListHeader);
        listHeader.setTypeface(null, Typeface.BOLD);
        listHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, null);
        }

        String[] parts = childText.split("#");
        String time = parts[0];
        String daysOfWeek = parts[1];

        TextView timeListChild = convertView.findViewById(R.id.pill_box_time);
        timeListChild.setText(time);

        TextView monday = convertView.findViewById(R.id.pill_box_monday);
        TextView tuesday = convertView.findViewById(R.id.pill_box_tuesday);
        TextView wednesday = convertView.findViewById(R.id.pill_box_wednesday);
        TextView thursday = convertView.findViewById(R.id.pill_box_thursday);
        TextView friday = convertView.findViewById(R.id.pill_box_friday);
        TextView saturday = convertView.findViewById(R.id.pill_box_saturday);
        TextView sunday = convertView.findViewById(R.id.pill_box_sunday);

        int colorSelected = _context.getResources().getColor(R.color.blue600);
        int colorNotSelected = Color.parseColor("#f4f4f4");

        for (int i = 0; i < 7; i++){
            if (i == 0){
                if (daysOfWeek.substring(i, i + 1).equals("1")){
                    sunday.setTextColor(colorSelected);
                }else {
                    sunday.setTextColor(colorNotSelected);
                }
            } else if (i == 1){
                if (daysOfWeek.substring(i, i + 1).equals("1")){
                    monday.setTextColor(colorSelected);
                }else {
                    monday.setTextColor(colorNotSelected);
                }
            } else if (i == 2){
                if (daysOfWeek.substring(i, i + 1).equals("1")){
                    tuesday.setTextColor(colorSelected);
                }else {
                    tuesday.setTextColor(colorNotSelected);
                }
            } else if (i == 3){
                if (daysOfWeek.substring(i, i + 1).equals("1")){
                    wednesday.setTextColor(colorSelected);
                }else {
                    wednesday.setTextColor(colorNotSelected);
                }
            } else if (i == 4){
                if (daysOfWeek.substring(i, i + 1).equals("1")){
                    thursday.setTextColor(colorSelected);
                }else {
                    thursday.setTextColor(colorNotSelected);
                }
            } else if (i == 5){
                if (daysOfWeek.substring(i, i + 1).equals("1")){
                    friday.setTextColor(colorSelected);
                }else {
                    friday.setTextColor(colorNotSelected);
                }
            } else if (i == 6){
                if (daysOfWeek.substring(i, i + 1).equals("1")){
                    saturday.setTextColor(colorSelected);
                }else {
                    saturday.setTextColor(colorNotSelected);
                }
            }
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int goupPosition, int childPosition) {
        return true;
    }
}
