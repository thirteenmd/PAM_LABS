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

import com.example.andreea.lab_2.Model.History;
import com.example.andreea.lab_2.Model.PillBox;
import com.example.andreea.lab_2.R;

/**
 * Created by Andreea on 10/30/2017.
 */

public class HistoryFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_history, container, false);
        TableLayout stk = rootView.findViewById(R.id.table_history);
        TableRow tbrow0 = new TableRow(container.getContext());

        TextView tt1 = new TextView(container.getContext());
        tt1.setText("Pill Name");
        tt1.setGravity(Gravity.CENTER);
        tt1.setTypeface(null, Typeface.BOLD);
        tbrow0.addView(tt1);

        TextView tt2 = new TextView(container.getContext());
        tt1.setText("Date Taken");
        tt1.setGravity(Gravity.CENTER);
        tt1.setTypeface(null, Typeface.BOLD);
        tbrow0.addView(tt2);

        TextView tt3 = new TextView(container.getContext());
        tt1.setText("Time Taken");
        tt1.setGravity(Gravity.CENTER);
        tt1.setTypeface(null, Typeface.BOLD);
        tbrow0.addView(tt3);

        stk.addView(tbrow0);

        PillBox pillBox = new PillBox();

        for (History history: pillBox.getHistory(container.getContext())){
            TableRow tbrow = new TableRow(container.getContext());

            TextView tv1 = new TextView(container.getContext());
            tv1.setText(history.getPillName());
            tv1.setGravity(Gravity.CENTER);
            tv1.setMaxEms(4);
            tbrow.addView(tv1);

            TextView tv2 = new TextView(container.getContext());
            tv2.setText(history.getDateString());
            tv2.setGravity(Gravity.CENTER);
            tv2.setMaxEms(4);
            tbrow.addView(tv2);

            TextView tv3 = new TextView(container.getContext());
            int nonMillitaryHour = history.getHourTaken() % 12;
            if (nonMillitaryHour == 0)
                nonMillitaryHour = 12;

            String minute;
            if (history.getMinuteTaken() < 10)
                minute = "0" + history.getMinuteTaken();
            else
                minute = "" + history.getMinuteTaken();

            String time = nonMillitaryHour + ":" + minute + " " + history.getAm_pmTaken();
            tv3.setText(time);
            tv3.setGravity(Gravity.CENTER);
            tv3.setMaxEms(4);
            tbrow.addView(tv3);

            stk.addView(tbrow);
        }

        return rootView;
    }
}
