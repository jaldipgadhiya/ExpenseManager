package com.android.acadgild.expensemanager;

/*
This is main activity class
to show Main List of Expense Income data details
Menu configuration
Navigation view configuration
Floating Action button configuration to add new Expense or Income details
onItemClick of main list configuration
onItemClick of Navigation view list configuration
 */
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.acadgild.expensemanager.adapter.CustomAdapter;
import com.android.acadgild.expensemanager.adapter.CustomAdapterMainList;
import com.android.acadgild.expensemanager.bean.CustomHandler;
import com.android.acadgild.expensemanager.bean.ExpIncData;
import com.android.acadgild.expensemanager.bean.ExpenseIncomeData;
import com.android.acadgild.expensemanager.database.DBHelper;
import com.android.acadgild.expensemanager.utils.CommonUtilities;
import com.android.acadgild.expensemanager.utils.Constants;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener  {

    String[] names= new String[]{"All Transactions", "Settings", "Share", "Feedback"};
    ArrayList<CustomHandler> model= new ArrayList<>();
    ArrayList<ExpIncData> model1= new ArrayList<>();
    DBHelper dbHelper;
    CustomAdapterMainList mainAdapter;
    CustomAdapter adapter;



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ArrayList<ExpIncData> data= new ArrayList<>();
        // Condition to check if expenseIncomeList item is clicked or not
        if(parent.getId()==R.id.expenseIncomeList) {
            ExpIncData item=   (ExpIncData) mainAdapter.getItem(position);

            int entryid=item.getEntryId();
            String amount=item.getEntryAmount().toString();
            String category = item.getEntryCategory();
            String source = item.getEntrySource();
            String desc = item.getEntryDescription();
            String date = item.getEntryDate();
            String type = item.getEntryType();

            // To store details in budle and to redirect to EditExpenseIncomeDetail activity
            Intent intent = new Intent(MainActivity.this, EditExpenseIncomeDetail.class);
            Bundle dataBundle = new Bundle();
            dataBundle.putInt("entryid", entryid);
            dataBundle.putString("amount", amount);
            dataBundle.putString("category", category);
            dataBundle.putString("source", source);
            dataBundle.putString("desc", desc);
            dataBundle.putString("date", date);
            dataBundle.putString("type", type);
            intent.putExtras(dataBundle);
            startActivity(intent);
        }
        // Condition to check if Navigation view list item is clicked or not
        if(parent.getId()==R.id.list) {
            CustomHandler item=   (CustomHandler) adapter.getItem(position);
            if(item.getTitle().equalsIgnoreCase("Settings"))
            {
                Intent intent = new Intent(MainActivity.this, ActivitySettings.class);
                startActivity(intent);
            }

            if(item.getTitle().equalsIgnoreCase("All Transactions"))
            {
                Snackbar.make(view, "Clicked on All Transactions.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }



        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper= CommonUtilities.getDBObject(this);
        //To Insert record of Categories and Source into DB - One time activity
        //insertCategoryRecord();
        //insertSourceRecord();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


               startActivity(new Intent(MainActivity.this, ExpenseIncomeTabs.class));
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        ListView list= (ListView)findViewById(R.id.list);


        // To keep Navigation View List items inside HashMap
        HashMap<Integer,String> hmap = new HashMap<Integer,String>();


        hmap.put(R.drawable.dfeedback,"Feedback");
        hmap.put(R.drawable.cnotifications,"Share");
        hmap.put(R.drawable.bsettings,"Settings");
        hmap.put(R.drawable.atrans,"All Transactions");


        //Iterate Key- Value pair of Image and List Items
        for(Map.Entry m:hmap.entrySet()){
            CustomHandler handler= new CustomHandler();
            Log.d("-"+m.getValue().toString(),""+m.getKey().toString());

            handler.setTitle(m.getValue().toString());
            handler.setImage(Integer.parseInt(m.getKey().toString()));

            // Add details inside arraylist
            model.add(handler);

        }

        // to sort Navigation view items list in ascending order
        Collections.sort(model,new ImageComparator());
        for(Object o:model)
        {
            CustomHandler c =(CustomHandler)o;
        }

        //Creating adapter with list of items inside model
        adapter= new CustomAdapter(this, model);
        //Set Adapter
        list.setAdapter(adapter);
        list.setOnItemClickListener(this);

        ListView listMain= (ListView)findViewById(R.id.expenseIncomeList);
        listMain.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, EditExpenseIncomeDetail.class);
                startActivity(intent);
                return true;
            }
        });

        dbHelper= CommonUtilities.getDBObject(this);
        // to get all the Expense and Income details
        model1 = dbHelper.getAllExpIncData();
        //set adapter to list
        mainAdapter = new CustomAdapterMainList(this, model1);
        listMain.setAdapter(mainAdapter);
        listMain.setOnItemClickListener(this);
    }


    // to Inflate Menu
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
            startActivity(new Intent(MainActivity.this, BalanceActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    // To insert category records for the first time when app runs(One time activity)
    private void insertCategoryRecord(){


        //Inserting values to Employee table.
        ContentValues vals = new ContentValues();
        vals.put(Constants.CATEGORY_NAME, "-");
        vals.put(Constants.CATEGORY_TYPE, "E");
        dbHelper.insertContentVals(Constants.CATEGORY_RECORD, vals);
        vals.put(Constants.CATEGORY_NAME, "Add New Category");
        vals.put(Constants.CATEGORY_TYPE, "E");
        dbHelper.insertContentVals(Constants.CATEGORY_RECORD, vals);
        vals.put(Constants.CATEGORY_NAME, "Shopping");
        vals.put(Constants.CATEGORY_TYPE, "E");
        dbHelper.insertContentVals(Constants.CATEGORY_RECORD, vals);
        vals.put(Constants.CATEGORY_NAME, "Household");
        vals.put(Constants.CATEGORY_TYPE, "E");
        dbHelper.insertContentVals(Constants.CATEGORY_RECORD, vals);
        vals.put(Constants.CATEGORY_NAME, "Transport");
        vals.put(Constants.CATEGORY_TYPE, "E");
        dbHelper.insertContentVals(Constants.CATEGORY_RECORD, vals);
        vals.put(Constants.CATEGORY_NAME, "Entertainment");
        vals.put(Constants.CATEGORY_TYPE, "E");
        dbHelper.insertContentVals(Constants.CATEGORY_RECORD, vals);
        vals.put(Constants.CATEGORY_NAME, "Gift");
        vals.put(Constants.CATEGORY_TYPE, "E");
        dbHelper.insertContentVals(Constants.CATEGORY_RECORD, vals);
        vals.put(Constants.CATEGORY_NAME, "-");
        vals.put(Constants.CATEGORY_TYPE, "I");
        dbHelper.insertContentVals(Constants.CATEGORY_RECORD, vals);
        vals.put(Constants.CATEGORY_NAME, "Add New Category");
        vals.put(Constants.CATEGORY_TYPE, "I");
        dbHelper.insertContentVals(Constants.CATEGORY_RECORD, vals);
        vals.put(Constants.CATEGORY_NAME, "Salary");
        vals.put(Constants.CATEGORY_TYPE, "I");
        dbHelper.insertContentVals(Constants.CATEGORY_RECORD, vals);
        vals.put(Constants.CATEGORY_NAME, "Loan");
        vals.put(Constants.CATEGORY_TYPE, "I");
        dbHelper.insertContentVals(Constants.CATEGORY_RECORD, vals);
        vals.put(Constants.CATEGORY_NAME, "Sales");
        vals.put(Constants.CATEGORY_TYPE, "I");
        dbHelper.insertContentVals(Constants.CATEGORY_RECORD, vals);





    }
    // To insert Source records for the first time when app runs(One time activity)

    private void insertSourceRecord() {
        ContentValues vals = new ContentValues();
        vals.put(Constants.SOURCE_NAME, "-");
        dbHelper.insertContentVals(Constants.SOURCE_RECORD, vals);
        vals.put(Constants.SOURCE_NAME, "Cash");
        dbHelper.insertContentVals(Constants.SOURCE_RECORD, vals);
        vals.put(Constants.SOURCE_NAME, "Card");
        dbHelper.insertContentVals(Constants.SOURCE_RECORD, vals);
        vals.put(Constants.SOURCE_NAME, "Cheque");
        dbHelper.insertContentVals(Constants.SOURCE_RECORD, vals);

    }
}
