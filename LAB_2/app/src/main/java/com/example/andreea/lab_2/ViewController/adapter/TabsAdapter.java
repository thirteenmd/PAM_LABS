package com.example.andreea.lab_2.ViewController.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.andreea.lab_2.ViewController.HistoryFragment;
import com.example.andreea.lab_2.ViewController.TodayFragment;
import com.example.andreea.lab_2.ViewController.TomorrowFragment;

/**
 * Created by Andreea on 10/30/2017.
 */

public class TabsAdapter extends FragmentPagerAdapter{
    private int TOTAL_TABS = 3;

    public TabsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new HistoryFragment();
            case 1:
                return new TodayFragment();
            case 2:
                return new TomorrowFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return TOTAL_TABS;
    }
}
