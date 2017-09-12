package com.android.acadgild.expensemanager.adapter;

/**
 * Created by Jal on 01-09-2017.
 * Adapter class to show Tabs view of Inserting Expense & Income details
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import com.android.acadgild.expensemanager.ExpenseFragment;
import com.android.acadgild.expensemanager.IncomeFragment;

public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                // Buttons fragment activity
                return new ExpenseFragment();
            case 1:
                // Colors fragment activity
                return new IncomeFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 2;
    }

}