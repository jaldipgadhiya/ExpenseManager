package com.android.acadgild.expensemanager;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
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

import com.android.acadgild.expensemanager.database.DBHelper;
import com.android.acadgild.expensemanager.utils.CommonUtilities;
import com.android.acadgild.expensemanager.utils.Constants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Jal on 01-09-2017.
 * Income Fragment class to be shown on page adapter view inside Income tab.
 */

public class IncomeFragment extends Fragment {

    DBHelper dbHelper;
    ArrayAdapter<String> sourceDataAdapter;
    ArrayAdapter<String> dataAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflating View
        View rootView = inflater.inflate(R.layout.fragment_income, container, false);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);

        Button vbtnSaveIncome = (Button) getActivity().findViewById(R.id.btnSaveIncome);
        ImageView vImgIncomeCal = (ImageView) getActivity().findViewById(R.id.imgIncomeCal);
        final TextView vIncomeDateSelected = (TextView) getActivity().findViewById(R.id.txtIncomeDateSelected);
        Spinner categorySpinner = (Spinner)getActivity().findViewById(R.id.input_income_category);

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
                updateLabel(vIncomeDateSelected,myCalendar);
            }

        };

        vImgIncomeCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        vbtnSaveIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText mIncomeAmount = (EditText) getActivity().findViewById(R.id.input_income_amount);
                Spinner categorySpinner = (Spinner)getActivity().findViewById(R.id.input_income_category);
                String mIncomeCategory = categorySpinner.getSelectedItem().toString();
                Spinner sourceSpinner = (Spinner)getActivity().findViewById(R.id.input_income_source);
                String mIncomeSource = sourceSpinner.getSelectedItem().toString();
                EditText mIncomeDesc = (EditText) getActivity().findViewById(R.id.input_income_desc);
                TextView mIncomeDateSelected = (TextView) getActivity().findViewById(R.id.txtIncomeDateSelected);

                insertIncomeRecord(mIncomeAmount,mIncomeCategory,mIncomeSource,mIncomeDesc,mIncomeDateSelected,"I");

                Snackbar.make(view, "Record Saved Successfully.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                //startActivity(new Intent(ExpenseFragment.this, MainActivity.class));
            }
        });





    }

    public void insertIncomeRecord(EditText vIncomeAmount,String vIncomeCategory, String
            vIncomeSource,EditText vIncomeDesc,TextView vIncomeDateSelected,String s){

        dbHelper= CommonUtilities.getDBObject(getActivity());

        if(!vIncomeAmount.getText().toString().isEmpty() && (!vIncomeCategory.isEmpty() && !vIncomeCategory.equalsIgnoreCase("-"))
                && (!vIncomeSource.isEmpty() && !vIncomeSource.equalsIgnoreCase("-")) && !vIncomeDateSelected.getText().toString().isEmpty()
                && !s.isEmpty())
        {
            ContentValues vals = new ContentValues();
            vals.put(Constants.ENTRY_AMOUNT, Float.parseFloat(vIncomeAmount.getText().toString()));
            vals.put(Constants.ENTRY_CATEGORY, vIncomeCategory);
            vals.put(Constants.ENTRY_SOURCE, vIncomeSource);
            vals.put(Constants.ENTRY_DESCRIPTION, vIncomeDesc.getText().toString());
            vals.put(Constants.ENTRY_DATE, vIncomeDateSelected.getText().toString());
            vals.put(Constants.ENTRY_TYPE, s);
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

    private void updateLabel(TextView mIncomeDateSelected,Calendar mCalendar) {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        mIncomeDateSelected.setText(sdf.format(mCalendar.getTime()));
    }

    private void loadSpinnerData() {
        String s = "I";
        Spinner categorySpinner = (Spinner) getActivity().findViewById(R.id.input_income_category);
        Spinner sourceSpinner = (Spinner) getActivity().findViewById(R.id.input_income_source);
        // database handler
        dbHelper= CommonUtilities.getDBObject(getActivity());


        // Spinner Drop down elements
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
