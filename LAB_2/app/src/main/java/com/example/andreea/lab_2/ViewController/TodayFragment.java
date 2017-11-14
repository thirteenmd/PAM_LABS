package com.example.andreea.lab_2.ViewController;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.andreea.lab_2.Model.Alarm;
import com.example.andreea.lab_2.Model.PillBox;
import com.example.andreea.lab_2.R;

import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Andreea on 10/30/2017.
 */

public class TodayFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_history, container, false);
        TableLayout stk = rootView.findViewById(R.id.table_history);
        PillBox pillBox = new PillBox();

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        List<Alarm> alarms = Collections.emptyList();

        try {
            alarms = pillBox.getAlarms(container.getContext(), day);
        }catch (URISyntaxException e){
            e.printStackTrace();
        }

        if (alarms.size() != 0){
            for (Alarm alarm: alarms){
                TableRow tbrow = new TableRow(container.getContext());

                TextView tv1 = new TextView(container.getContext());
                tv1.setText(alarm.getPillName());
                tv1.setGravity(Gravity.CENTER);
                tv1.setPadding(30, 30,30,30);
                tv1.setTextSize(25);
                tv1.setMaxEms(6);

                tbrow.addView(tv1);

                TextView tv2 = new TextView(container.getContext());
                String time = alarm.getStringTime();
                tv2.setText(time);
                tv2.setGravity(Gravity.CENTER);
                tv2.setPadding(30, 30,30,30);
                tv2.setTextSize(25);
                tv2.setMaxEms(6);

                tbrow.addView(tv2);

                stk.addView(tbrow);
            }
        }else {
            TableRow tbrow = new TableRow(container.getContext());

            TextView tv1 = new TextView(container.getContext());
            tv1.setText("You have no pills to take today!");
            tv1.setGravity(Gravity.CENTER);
            tv1.setPadding(30, 30,30,30);
            tv1.setTextSize(25);
            tv1.setMaxEms(10);
            tbrow.addView(tv1);

            stk.addView(tbrow);
        }
        return rootView;
    }
}
