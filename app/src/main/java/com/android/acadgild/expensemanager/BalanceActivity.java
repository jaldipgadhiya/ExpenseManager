package com.android.acadgild.expensemanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.acadgild.expensemanager.adapter.CustomAdapterMainList;
import com.android.acadgild.expensemanager.bean.ExpIncData;
import com.android.acadgild.expensemanager.database.DBHelper;
import com.android.acadgild.expensemanager.utils.CommonUtilities;

import java.util.ArrayList;

/**
 * Created by Jal on 11-09-2017.
 * This activity class if for showing balance activity screen and details.
 * All Expense and Income details will be displayed in separate list views and dedit side total, credit side total will
 * be displayed on top of both listviews and balance amount will be displayed on top of all.
 */

public class BalanceActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    ArrayList<ExpIncData> expList= new ArrayList<>();
    ArrayList<ExpIncData> incList= new ArrayList<>();
    DBHelper dbHelper;
    CustomAdapterMainList mainAdapter;
    Float exptotal=0.0f;
    Float inctotal=0.0f;
    Float balance=0.0f;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.balance_screen_activity);

        dbHelper= CommonUtilities.getDBObject(this);

        ListView listExp= (ListView)findViewById(R.id.expenseList);
        ListView listInc= (ListView)findViewById(R.id.incomeList);
        TextView tvBalance = (TextView) findViewById(R.id.txtBalance);
        TextView tvExpTot = (TextView) findViewById(R.id.txtDebitBalance);
        TextView tvIncTot = (TextView) findViewById(R.id.txtCreditBalance);

        expList = dbHelper.getAllExpData();
        for(int i=0; i<expList.size(); i++)
        {
            exptotal = exptotal + expList.get(i).getEntryAmount();
        }
        incList = dbHelper.getAllIncData();
        for(int i=0; i<incList.size(); i++)
        {
            inctotal = inctotal + incList.get(i).getEntryAmount();
        }
        balance = inctotal - exptotal;



        tvBalance.setText("Balance - " + balance.toString());
        tvExpTot.setText("Debit " +exptotal.toString());
        tvIncTot.setText("Credit " +inctotal.toString());

        mainAdapter = new CustomAdapterMainList(this, expList);
        listExp.setAdapter(mainAdapter);
        listExp.setOnItemClickListener(this);


        mainAdapter = new CustomAdapterMainList(this, incList);
        listInc.setAdapter(mainAdapter);
        listInc.setOnItemClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater M = getMenuInflater();
        M.inflate(R.menu.main, menu);



        //  getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.menuOpenBalanceSheet) {
            startActivity(new Intent(BalanceActivity.this, MainActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

}
