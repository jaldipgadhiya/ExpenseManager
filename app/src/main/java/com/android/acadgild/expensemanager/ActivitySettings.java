package com.android.acadgild.expensemanager;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by Jal on 12-09-2017.
 * Settings shared preference activity class
 * AppCompatDelegate objects and methods are used to support actionbar and mene kind of oprations on Preference activity
 */

public class ActivitySettings extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static int prefs=R.xml.preferences;
    private AppCompatDelegate mDelegate;

    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getDelegate().installViewFactory();
        getDelegate().onCreate(savedInstanceState);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        try {
            getClass().getMethod("getFragmentManager");
            AddResourceApi11AndGreater();

        } catch (NoSuchMethodException e) { //Api < 11
            AddResourceApiLessThan11();
        }

    }
    public ActionBar getSupportActionBar() {
        return getDelegate().getSupportActionBar();
    }

    public void setSupportActionBar(@Nullable Toolbar toolbar) {
        getDelegate().setSupportActionBar(toolbar);
    }

    @Override
    protected void onStop() {
        super.onStop();
        getDelegate().onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getDelegate().onDestroy();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getDelegate().onPostCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        getDelegate().setContentView(layoutResID);
    }

    @Override
    public void setContentView(View view) {
        getDelegate().setContentView(view);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        getDelegate().setContentView(view, params);
    }

    @Override
    public void addContentView(View view, ViewGroup.LayoutParams params) {
        getDelegate().addContentView(view, params);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        getDelegate().onPostResume();
    }

    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
        getDelegate().setTitle(title);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        getDelegate().onConfigurationChanged(newConfig);
    }

    public void invalidateOptionsMenu() {
        getDelegate().invalidateOptionsMenu();
    }

    private AppCompatDelegate getDelegate() {
        if (mDelegate == null) {
            mDelegate = AppCompatDelegate.create(this, null);
        }
        return mDelegate;
    }

    @SuppressWarnings("deprecation")
    protected void AddResourceApiLessThan11()
    {

        addPreferencesFromResource(prefs);
    }

    @TargetApi(11)
    protected void AddResourceApi11AndGreater()
    {
        getFragmentManager().beginTransaction().replace(android.R.id.content,
                new PF()).commit();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

    }

    @TargetApi(11)
    public static class PF extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener
    {
        @Override
        public void onCreate(final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(ActivitySettings.prefs); //outer class
            // private members seem to be visible for inner class, and
            // making it static made things so much easier

        }
        @Override
        public void onResume() {
            super.onResume();
            getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

        }
        @Override
        public void onPause() {
            getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
            super.onPause();
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            Log.e("key= " , key);
            Toast.makeText(getActivity(), key+"  is changed. ", Toast.LENGTH_SHORT).show();
            //to display summary of all List preferences
            Preference pref = findPreference(key);
            if (pref instanceof ListPreference) {
                ListPreference etp = (ListPreference) pref;
                pref.setSummary(etp.getEntry());
            }
        }
    }
}
