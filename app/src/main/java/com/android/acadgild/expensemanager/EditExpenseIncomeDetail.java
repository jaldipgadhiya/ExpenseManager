package com.android.acadgild.expensemanager;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.acadgild.expensemanager.database.DBHelper;
import com.android.acadgild.expensemanager.utils.CommonUtilities;
import com.android.acadgild.expensemanager.utils.Constants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Jal on 08-09-2017.
 * This class if for editing details of Expense or Income. On click of list items of list Income Expense details Edit details
 * screen will open from where user can edit details of list item and can also delete list item.
 */

public class EditExpenseIncomeDetail extends AppCompatActivity {

    DBHelper dbHelper;
    ArrayAdapter<String> sourceDataAdapter;
    ArrayAdapter<String> dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_exp_income_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intentObject = getIntent();
        //Extract bundle object
        String amount=  intentObject.getExtras().getString("amount");
        String category=  intentObject.getExtras().getString("category");
        String source=  intentObject.getExtras().getString("source");
        String desc=  intentObject.getExtras().getString("desc");
        String date=  intentObject.getExtras().getString("date");
        String type=  intentObject.getExtras().getString("type");
        final int id = intentObject.getExtras().getInt("entryid");
        loadSpinnerData(type);



        final EditText einputName = (EditText) findViewById(R.id.einput_name);
        final Spinner einputCategory = (Spinner) findViewById(R.id.einput_category);
        final String mExpCategory = einputCategory.getSelectedItem().toString();
        final Spinner einputSource = (Spinner) findViewById(R.id.einput_source);
        final String mExpSource = einputSource.getSelectedItem().toString();
        final EditText einputDesc = (EditText) findViewById(R.id.einput_desc);
        final ImageView eimgSelectDate = (ImageView) findViewById(R.id.eimgSelectDate);
        final TextView etxtExpDateSelected =(TextView) findViewById(R.id.etxtExpDateSelected);
        final Button ebtnDelete = (Button) findViewById(R.id.ebtnDelete);
        final Button ebtnEdit = (Button) findViewById(R.id.ebtnEditExp);
        final Button ebtnSave = (Button) findViewById(R.id.ebtnSaveExp);

        //Set values to particular view object on screen
        einputName.setText(amount);
        for(int i=0; i < dataAdapter.getCount(); i++) {
            Log.e("category", "= " + category);
            Log.e("getItem(i)", "= " + dataAdapter.getItem(i).toString());
            if(category.trim().equals(dataAdapter.getItem(i).toString())){
                einputCategory.setSelection(i);
                break;
            }}
        for(int i=0; i < sourceDataAdapter.getCount(); i++) {
            if(source.trim().equals(sourceDataAdapter.getItem(i).toString())){
                einputSource.setSelection(i);
                break;
            }}
        einputDesc.setText(desc);
        etxtExpDateSelected.setText(date);
        einputCategory.setEnabled(false);
        einputSource.setEnabled(false);

        // to make all the view objects read only
       eimgSelectDate.setFocusable(false); eimgSelectDate.setClickable(false);
        einputName.setEnabled(false);
        einputCategory.setEnabled(false);
        einputSource.setEnabled(false);
        einputDesc.setEnabled(false);
        eimgSelectDate.setEnabled(false);

        //Edit button click

        ebtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // On click of edit button make all view objects enabled for editing purpose
                einputName.setEnabled(true);
                einputCategory.setEnabled(true);
                einputSource.setEnabled(true);
                einputDesc.setEnabled(true);
                eimgSelectDate.setFocusable(true); eimgSelectDate.setClickable(true);
                eimgSelectDate.setEnabled(true);


                // hide edit button and show save button to save details in DB
                ebtnSave.setVisibility(view.VISIBLE);
                ebtnEdit.setVisibility(view.INVISIBLE);
            }
        });

        //To get Calendar instance and update textview to selected date from Calendar

        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener edate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(etxtExpDateSelected,myCalendar);
            }

        };

        // On click of image show calander
        eimgSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(EditExpenseIncomeDetail.this, edate, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        //To save all the details inside DB
        ebtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    dbHelper= CommonUtilities.getDBObject(EditExpenseIncomeDetail.this);
                // Get all the entered values while editing details
                String sExpCategory = einputCategory.getSelectedItem().toString();
                String sExpSource = einputSource.getSelectedItem().toString();
                EditText sinputName = (EditText) findViewById(R.id.einput_name);
                TextView stxtExpDateSelected =(TextView) findViewById(R.id.etxtExpDateSelected);
                EditText sinputDesc = (EditText) findViewById(R.id.einput_desc);


                    // Check if values are not null
                    if(!sinputName.getText().toString().isEmpty() && (!sExpCategory.isEmpty() && !sExpCategory.equalsIgnoreCase("-"))
                        && (!sExpSource.isEmpty() && !sExpSource.equalsIgnoreCase("-")) && !stxtExpDateSelected.getText().toString().isEmpty()
                        ) {
                        ContentValues vals = new ContentValues();

                        vals.put(Constants.ENTRY_AMOUNT, Float.parseFloat(sinputName.getText().toString()));
                        vals.put(Constants.ENTRY_CATEGORY, sExpCategory);
                        vals.put(Constants.ENTRY_SOURCE, sExpSource);
                        vals.put(Constants.ENTRY_DESCRIPTION, sinputDesc.getText().toString());
                        vals.put(Constants.ENTRY_DATE, stxtExpDateSelected.getText().toString());

                        // Update values inside DB
                        long insId = dbHelper.updateRecords(Constants.EXP_INC_ENTRY_RECORD, vals, "entry_id = " + id, null);

                        if (insId != -1) {
                            Toast.makeText(EditExpenseIncomeDetail.this, "New row updated, row id: " + insId, Toast.LENGTH_LONG).show();
                        } else
                            Toast.makeText(EditExpenseIncomeDetail.this, "Something wrong", Toast.LENGTH_LONG).show();
                    }
                        else{
                        Toast.makeText(EditExpenseIncomeDetail.this, "Something wrong edit save.", Toast.LENGTH_LONG).show();
                    }
                //}

            }
        });

        // On Delete button click
        ebtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Delete records from DB.
                dbHelper.deleteRecords(Constants.EXP_INC_ENTRY_RECORD, "entry_id = "+id,null);
                Toast.makeText(EditExpenseIncomeDetail.this, "Row Deleted: ", Toast.LENGTH_LONG).show();

                startActivity(new Intent(EditExpenseIncomeDetail.this, MainActivity.class));
            }
        });
    }

    // To update text view of selected date from Calendar
    private void updateLabel(TextView mExpDateSelected,Calendar mCalendar) {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        mExpDateSelected.setText(sdf.format(mCalendar.getTime()));
    }

    // To load spinner data
    private void loadSpinnerData(String type) {
        Spinner categorySpinner = (Spinner) findViewById(R.id.einput_category);
        Spinner sourceSpinner = (Spinner) findViewById(R.id.einput_source);
        dbHelper= CommonUtilities.getDBObject(this);


        // Spinner Drop down elements
        List<String> categories = dbHelper.getCategoryData(type);
        List<String> sources = dbHelper.getSourceData();
        sourceDataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, sources);

        // Drop down layout style - list view with radio button
        sourceDataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        sourceSpinner.setAdapter(sourceDataAdapter);

        // Creating adapter for spinner
        dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        categorySpinner.setAdapter(dataAdapter);
    }


}