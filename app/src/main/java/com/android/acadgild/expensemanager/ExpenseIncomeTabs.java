package com.android.acadgild.expensemanager;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.android.acadgild.expensemanager.adapter.TabsPagerAdapter;

/**
 * Created by Jal on 06-09-2017.
 * This is tab pager view activity class to show Tab Pager View on screen
 */

public class ExpenseIncomeTabs extends ActionBarActivity implements
        ActionBar.TabListener, ViewPager.OnPageChangeListener {

    public ExpenseIncomeTabs() {
        super();
    }

    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    private ActionBar actionBar;

    // Tab titles
    private String[] tabs = { "Expense", "Income" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_income);

        //To show CLOSE FAB Button on screen
        FloatingActionButton fab1 = (FloatingActionButton) findViewById(R.id.expfab);
        fab1.show();

        // Initilization
        viewPager = (ViewPager) findViewById(R.id.pager);
        //getSupportActionBar
        actionBar = getSupportActionBar();
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        //Set TabsPagerAdapter
        viewPager.setAdapter(mAdapter);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Adding Tabs
        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(this));
        }

        /**
         * on swiping the viewpager make respective tab selected
         * */
        viewPager.setOnPageChangeListener(this);
    }

    @Override
    public void onPageSelected(int position) {
        // on changing the page
        // make respected tab selected
        actionBar.setSelectedNavigationItem(position);
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {
        // on tab selected
        // show respected fragment view
        viewPager.setCurrentItem(tab.getPosition());
    }


}
