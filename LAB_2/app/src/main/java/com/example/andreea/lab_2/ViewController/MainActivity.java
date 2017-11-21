package com.example.andreea.lab_2.ViewController;

import android.content.Intent;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.example.andreea.lab_2.R;
import com.example.andreea.lab_2.ViewController.adapter.SectionsPagerAdapter;
import com.example.andreea.lab_2.ViewController.adapter.TabsAdapter;

import java.util.Date;

public class MainActivity extends AppCompatActivity{

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        mViewPager.setCurrentItem(1, false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            goToSettings();
            return true;
        }
        if (id == R.id.action_add) {
            goToAdd();
            return true;
        }
        if (id == R.id.action_edit) {
            goToEdit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void goToAdd(){
        Intent intent = new Intent(this, AddPillActivity.class);
        startActivity(intent);
        finish();
    }

    public void goToSettings(){
        Intent intent = new Intent(this, ScheduleActivity.class);
        startActivity(intent);
        finish();
    }

    public void goToEdit(){
        Intent intent = new Intent(this, PillBoxActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        return;
    }


    private void setupViewPager(ViewPager viewPager){
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HistoryFragment(), "HISTORY");
        adapter.addFragment(new TodayFragment(), "TODAY");
        adapter.addFragment(new TomorrowFragment(), "TOMORROW");

        viewPager.setAdapter(adapter);
    }
}
