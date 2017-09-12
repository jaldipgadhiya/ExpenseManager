package com.android.acadgild.expensemanager.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.acadgild.expensemanager.utils.Constants;


/**
 * Created by Jal on 28-07-2017.
 * Class to created all required DB Tables
 */

public class TablesClass extends SQLiteOpenHelper {
    /**
     * Write all create table statements here in this class on oncreate method
     * If any changes in table structure go for onUpgrade method
     */

    Context context;

    public TablesClass(Context context, String DatabaseName, String nullColumnHack, int databaseVersion) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String table1 = "CREATE TABLE IF NOT EXISTS " + Constants.EXP_INC_ENTRY_RECORD + " ("
                + Constants.ENTRY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Constants.ENTRY_AMOUNT + " REAL, "
                + Constants.ENTRY_CATEGORY + " TEXT, "
                + Constants.ENTRY_SOURCE + " TEXT, "
                + Constants.ENTRY_DESCRIPTION + " TEXT, "
                + Constants.ENTRY_DATE + " TEXT, "
                + Constants.ENTRY_TYPE + " TEXT) ";
            db.execSQL(table1);

        String table2 = "CREATE TABLE IF NOT EXISTS " + Constants.CATEGORY_RECORD + " ("
                + Constants.CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Constants.CATEGORY_NAME + " TEXT, "
                + Constants.CATEGORY_TYPE + " TEXT) ";
        db.execSQL(table2);

        String table3 = "CREATE TABLE IF NOT EXISTS " + Constants.SOURCE_RECORD + " ("
                + Constants.SOURCE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Constants.SOURCE_NAME + " TEXT) ";
        db.execSQL(table3);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        context.deleteDatabase(Constants.DATABASE_NAME);
        onCreate(db);
    }
}