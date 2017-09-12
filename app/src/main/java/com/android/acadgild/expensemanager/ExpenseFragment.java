package com.android.acadgild.expensemanager;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.acadgild.expensemanager.bean.Category;
import com.android.acadgild.expensemanager.database.DBHelper;
import com.android.acadgild.expensemanager.utils.CommonUtilities;
import com.android.acadgild.expensemanager.utils.Constants;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Jal on 01-09-2017.
 * Expense Fragment class to be shown on page adapter view inside Expense tab.
 */

public class ExpenseFragment extends Fragment {

    DBHelper dbHelper;
    ArrayAdapter<String> sourceDataAdapter;
    ArrayAdapter<String> dataAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflating View
        View rootView = inflater.inflate(R.layout.fragment_expense, container, false);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        Button vbtnSaveExp = (Button) getActivity().findViewById(R.id.btnSaveExp);
        ImageView vImgExpCal = (ImageView) getActivity().findViewById(R.id.imgExpCal);
        final TextView vExpDateSelected = (TextView) getActivity().findViewById(R.id.txtExpDateSelected);
        Spinner categorySpinner = (Spinner)getActivity().findViewById(R.id.input_category);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Object item = adapterView.getItemAtPosition(i);
                String text = item.toString();

                if(text.equalsIgnoreCase("Add New Category"))
                {
                    Intent intent = new Intent(getActivity(), RecyclerClass.class);
                    startActivity(intent);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        loadSpinnerData();

        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(vExpDateSelected,myCalendar);
            }

        };

        vImgExpCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        vbtnSaveExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText mExpAmount = (EditText) getActivity().findViewById(R.id.input_amount);
                Spinner categorySpinner = (Spinner)getActivity().findViewById(R.id.input_category);
                String mExpCategory = categorySpinner.getSelectedItem().toString();
                Spinner sourceSpinner = (Spinner)getActivity().findViewById(R.id.input_source);
                String mExpSource = sourceSpinner.getSelectedItem().toString();
                EditText mExpDesc = (EditText) getActivity().findViewById(R.id.input_desc);
                TextView mExpDateSelected = (TextView) getActivity().findViewById(R.id.txtExpDateSelected);

                insertExpRecord(mExpAmount,mExpCategory,mExpSource,mExpDesc,mExpDateSelected,"E");

                Snackbar.make(view, "Record Saved Successfully.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        FloatingActionButton expfabbtn = (FloatingActionButton) getActivity().findViewById(R.id.expfab);
        expfabbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private void updateLabel(TextView mExpDateSelected,Calendar mCalendar) {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        mExpDateSelected.setText(sdf.format(mCalendar.getTime()));
    }


    public void insertExpRecord(EditText vExpAmount,String vExpCategory, String
            vExpSource,EditText vExpDesc,TextView vExpDateSelected,String s){

        dbHelper= CommonUtilities.getDBObject(getActivity());
        // To validate & insert values inside DB
        if(!vExpAmount.getText().toString().isEmpty() && (!vExpCategory.isEmpty() && !vExpCategory.equalsIgnoreCase("-"))
                && (!vExpSource.isEmpty() && !vExpSource.equalsIgnoreCase("-"))  && !vExpDateSelected.getText().toString().isEmpty()
                && !s.isEmpty())
        {
            ContentValues vals = new ContentValues();
            vals.put(Constants.ENTRY_AMOUNT, Float.parseFloat(vExpAmount.getText().toString()));
            vals.put(Constants.ENTRY_CATEGORY, vExpCategory);
            vals.put(Constants.ENTRY_SOURCE, vExpSource);
            vals.put(Constants.ENTRY_DESCRIPTION, vExpDesc.getText().toString());
            vals.put(Constants.ENTRY_DATE, vExpDateSelected.getText().toString());
            vals.put(Constants.ENTRY_TYPE, s);
            // To insert values
            long insId = dbHelper.insertContentVals(Constants.EXP_INC_ENTRY_RECORD, vals);
            Log.d("Insert","insId: " + insId);
            if(insId != -1){
                Toast.makeText(getActivity(), "New row added, row id: " + insId, Toast.LENGTH_SHORT).show();}

            else
                Toast.makeText(getActivity(), "Something wrong", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Snackbar.make(getView(), "Kindly enter all details properly.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

        }
    }
    // To load spinner data from DB
    private void loadSpinnerData() {
        String s = "E";
        Spinner categorySpinner = (Spinner) getActivity().findViewById(R.id.input_category);
        Spinner sourceSpinner = (Spinner) getActivity().findViewById(R.id.input_source);
        // database handler
        dbHelper= CommonUtilities.getDBObject(getActivity());

        List<String> categories = dbHelper.getCategoryData(s);
        List<String> sources = dbHelper.getSourceData();

        sourceDataAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, sources);


        // Drop down layout style - list view with radio button
        sourceDataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        sourceSpinner.setAdapter(sourceDataAdapter);

        // Creating adapter for spinner
        dataAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        categorySpinner.setAdapter(dataAdapter);
    }
}
