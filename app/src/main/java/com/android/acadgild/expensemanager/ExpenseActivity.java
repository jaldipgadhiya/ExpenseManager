package com.android.acadgild.expensemanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by Jal on 08-09-2017.
 */

public class ExpenseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this, "FragmentLayout: OnCreate()", Toast.LENGTH_SHORT)
                .show();

        setContentView(R.layout.activity_fragment_expense);
    }

}
