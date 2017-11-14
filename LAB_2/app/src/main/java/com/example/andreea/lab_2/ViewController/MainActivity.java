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
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.example.andreea.lab_2.R;
import com.example.andreea.lab_2.ViewController.adapter.TabsAdapter;

import java.util.Date;

public class MainActivity extends AppCompatActivity implements android.support.v7.app.ActionBar.TabListener{
    private ViewPager tabsViewPager;
    private TabsAdapter mTabsAdapter;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tabsViewPager = findViewById(R.id.tabsPager);

        mTabsAdapter = new TabsAdapter(getSupportFragmentManager());

        tabsViewPager.setAdapter(mTabsAdapter);

        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(tabsViewPager);

        getSupportActionBar().setTitle("Home");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();

        String todayString = new SimpleDateFormat("EEE, MMM d").format(today);
        String tomorrowString = new SimpleDateFormat("EEE, MMM d").format(tomorrow);

        ActionBar.Tab historyTab = getSupportActionBar().newTab().setTabListener((ActionBar.TabListener) this);
        ActionBar.Tab todayTab = getSupportActionBar().newTab().setTabListener((ActionBar.TabListener) this);
        ActionBar.Tab tomorrowTab = getSupportActionBar().newTab().setTabListener((ActionBar.TabListener) this);

        TextView tvHistory = new TextView(this);
        tvHistory.setText(Html.fromHtml("<b>HISTORY</b>"));
        tvHistory.setTextColor(Color.WHITE);
        tvHistory.setGravity(Gravity.CENTER);
        tvHistory.setHeight(200);
        historyTab.setCustomView(tvHistory);

        TextView tvToday = new TextView(this);
        tvHistory.setText(Html.fromHtml("<b>TODAY</b><br><small>" + todayString + "</small>"));
        tvHistory.setTextColor(Color.WHITE);
        tvHistory.setGravity(Gravity.CENTER);
        tvHistory.setHeight(200);
        historyTab.setCustomView(tvToday);

        TextView tvTomorrow = new TextView(this);
        tvHistory.setText(Html.fromHtml("<b>TOMORROW</b><br><small>" + tomorrowString + "</small>"));
        tvHistory.setTextColor(Color.WHITE);
        tvHistory.setGravity(Gravity.CENTER);
        tvHistory.setHeight(200);
        historyTab.setCustomView(tvTomorrow);

        getSupportActionBar().addTab(historyTab);
        getSupportActionBar().addTab(todayTab);
        getSupportActionBar().addTab(tomorrowTab);

        getSupportActionBar().setSelectedNavigationItem(1);

        tabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageScrolled(int arg1, float arg2, int arg3) {

            }

            @Override
            public void onPageSelected(int position) {
                getSupportActionBar().setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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

    @Override
    public void onTabSelected(ActionBar.Tab selectedTab, FragmentTransaction arg1){
        tabsViewPager.setCurrentItem(selectedTab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

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
}
