package com.android.acadgild.expensemanager;


import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.acadgild.expensemanager.adapter.CustomAdapterMainList;
import com.android.acadgild.expensemanager.adapter.SettingsAdapter;
import com.android.acadgild.expensemanager.bean.ExpIncData;

import java.util.ArrayList;

/**
 * Created by Jal on 11-09-2017.
 */

public class SettingsActivity extends AppCompatActivity implements  AdapterView.OnItemClickListener{

    ArrayList model1= new ArrayList();
    SettingsAdapter mainAdapter;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ListView list= (ListView)findViewById(R.id.listSettings);

        model1.add("Show balance view");
        model1.add("Default View"+"\n"+"Single-Column");
        model1.add("Currency format"+"\n"+"Lakh");
        model1.add("Manage Categories");
        model1.add("About");

        //model1 = dbHelper.getAllExpIncData();
        mainAdapter = new SettingsAdapter(this, model1);
        list.setAdapter(mainAdapter);
        list.setOnItemClickListener(this);
    }



}
