package com.android.acadgild.expensemanager;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.acadgild.expensemanager.adapter.MyAdapter;
import com.android.acadgild.expensemanager.bean.ItemData;
import com.android.acadgild.expensemanager.database.DBHelper;
import com.android.acadgild.expensemanager.utils.CommonUtilities;
import com.android.acadgild.expensemanager.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jal on 08-09-2017.
 */

public class RecyclerClass extends AppCompatActivity {

    DBHelper dbHelper;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_category);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // 1. get a reference to recyclerView
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        Button btnAddNewCategory = (Button) findViewById(R.id.btnAddNewCategory);


        btnAddNewCategory.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                AlertDialog.Builder mBuilder =  new AlertDialog.Builder(RecyclerClass.this);
                final View mView = getLayoutInflater().inflate(R.layout.dialog_add_new_category,null);

                Button mSave = (Button) mView.findViewById(R.id.btnSaveNewCategoty);

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                mSave.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {

                        final EditText mNewCategoryName = (EditText) mView.findViewById(R.id.etNewCategoryName);
                        RadioGroup radioCategoryGroup = (RadioGroup) mView.findViewById(R.id.radioCategory);
                        RadioButton radioCategoryButton;
                        int selectedId = radioCategoryGroup.getCheckedRadioButtonId();

                        // find the radiobutton by returned id
                        radioCategoryButton = (RadioButton) mView.findViewById(selectedId);
                       // updateToDoRecord(mView,mTitle,mDesc,mDueDate,position );
                        Log.e("radioCategoryButton", "= " + radioCategoryButton);
                        if(!mNewCategoryName.getText().toString().isEmpty() && !radioCategoryButton.getText().toString().isEmpty())
                        {
                        ContentValues vals = new ContentValues();
                            String ctype=null;
                            if(radioCategoryButton.getText().toString().equalsIgnoreCase("Expense"))
                            {ctype = "E";}
                            if(radioCategoryButton.getText().toString().equalsIgnoreCase("Income"))
                            {ctype = "I";}
                        vals.put(Constants.CATEGORY_NAME, mNewCategoryName.getText().toString());
                        vals.put(Constants.CATEGORY_TYPE, ctype);
                        long insId = dbHelper.insertContentVals(Constants.CATEGORY_RECORD, vals);
                        Log.d("Insert","insId: " + insId);
                        if(insId != -1){
                            dbHelper= CommonUtilities.getDBObject(RecyclerClass.this);
                            ArrayList<ItemData> itemsData = dbHelper.getAllCategoryData();
                            // To Load all Categories
                            // 2. set layoutManger
                            recyclerView.setLayoutManager(new LinearLayoutManager(RecyclerClass.this));
                            // 3. create an adapter
                            MyAdapter mAdapter = new MyAdapter(itemsData);
                            // 4. set adapter
                            recyclerView.setAdapter(mAdapter);
                            // 5. set item animator to DefaultAnimator
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            Toast.makeText(RecyclerClass.this, "New row added, row id: " + insId, Toast.LENGTH_SHORT).show();

                            //Toast.makeText(RecyclerClass.this, "radioCategoryButton.getText().toString(): " + radioCategoryButton.getText().toString(), Toast.LENGTH_LONG).show();

                        }

                        else
                            Toast.makeText(RecyclerClass.this, "Something wrong", Toast.LENGTH_SHORT).show();
                    }
        else
                    {
                        Toast.makeText(RecyclerClass.this, "Kindly enter all details properly.", Toast.LENGTH_SHORT).show();
                    }

                        dialog.cancel();
                    }
                });
            }
        });


        // this is data fro recycler view
        dbHelper= CommonUtilities.getDBObject(this);
        ArrayList<ItemData> itemsData = dbHelper.getAllCategoryData();

        // 2. set layoutManger
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // 3. create an adapter
        MyAdapter mAdapter = new MyAdapter(itemsData);
        // 4. set adapter
        recyclerView.setAdapter(mAdapter);
        // 5. set item animator to DefaultAnimator
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }
}
