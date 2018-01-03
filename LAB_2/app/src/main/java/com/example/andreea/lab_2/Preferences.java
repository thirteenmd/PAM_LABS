package com.example.andreea.lab_2;

/**
 * Created by Andreea on 1/3/2018.
 */

import android.os.Bundle;
import android.preference.PreferenceActivity;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class Preferences extends PreferenceActivity
{
    @Override
    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        addPreferencesFromResource(R.xml.preferences);
    }
}
