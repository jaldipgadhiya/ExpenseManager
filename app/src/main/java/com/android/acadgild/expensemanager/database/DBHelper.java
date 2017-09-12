package com.android.acadgild.expensemanager.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;


import com.android.acadgild.expensemanager.R;
import com.android.acadgild.expensemanager.bean.Category;
import com.android.acadgild.expensemanager.bean.ExpIncData;
import com.android.acadgild.expensemanager.bean.ExpenseIncomeData;
import com.android.acadgild.expensemanager.bean.ItemData;
import com.android.acadgild.expensemanager.bean.Source;
import com.android.acadgild.expensemanager.utils.Constants;

import java.util.ArrayList;

/**
 * Created by Jal on 28-07-2017.
 * Class for complete Database operations
 */

public class DBHelper {
    private SQLiteDatabase db;
    private final Context context;
    private final TablesClass dbHelper;
    public static int no;
    public static DBHelper db_helper = null;

    public static DBHelper getInstance(Context context){
        try{
            if(db_helper == null){
                db_helper = new DBHelper(context);
                db_helper.open();
            }
        }catch(IllegalStateException e){
            //db_helper already open
        }
        return db_helper;
    }

    /*
	 * set context of the class and initialize TableClass Object
	 */

    public DBHelper(Context c) {
        context = c;
        dbHelper = new TablesClass(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    /*
     * close databse.
     */
    public void close() {
        if (db.isOpen()) {
            db.close();
        }
    }

    public boolean dbOpenCheck() {
        try{
            return db.isOpen();
        }catch(Exception e){
            return false;
        }
    }

    /*
     * open com.android.acadgild.todolist.database
     */
    public void open() throws SQLiteException {
        try {
            db = dbHelper.getWritableDatabase();
        } catch (Exception e) {
            Log.v("open Exception", "error==" + e.getMessage());
            db = dbHelper.getReadableDatabase();
        }
    }

    public long insertContentVals(String tableName, ContentValues content){
        long id=0;
        try{
            db.beginTransaction();
            id = db.insert(tableName, null, content);
            db.setTransactionSuccessful();
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
        return id;
    }

    public Cursor getTableRecords(String tablename, String[] columns, String where, String orderby){
        Cursor cursor =  db.query(false, tablename, columns,where, null, null, null, orderby, null);
        return cursor;
    }

    /*
	 * Get count of all tables in a table as per the condition
	 */

    public int getFullCount(String table, String where) {
        Cursor cursor = db.query(false, table, null, where, null, null, null, null, null);
        try {
            if (cursor != null) {
                cursor.moveToFirst();
                no = cursor.getCount();
                cursor.close();
            }
        } finally {
            cursor.close();
        }
        return no;
    }


    public void deleteRecords(String table, String whereClause, String[] whereArgs){
        try {
            db.beginTransaction();
            db.delete(table, whereClause, whereArgs);
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
    }

    /*
	 * Get value of any table as per the condition.
	 */

    public String getValue(String table, String column, String where) {
        Cursor result = db.query(false, table, new String[] { column }, where,
                null, null, null, Constants.ID, null);
        String value = "";
        try {
            if (result.moveToFirst()) {
                value = result.getString(0);
            } else {
                return null;
            }
        } finally {
            result.close();
        }
        return value;
    }
	/*
	 * Get Multiple Values from column of any specified table.
	 */

    public String[] getValues(boolean b, String table, String column,
                              String where, String orderBy) {
        ArrayList<String> savedAns = new ArrayList<String>();
        Cursor result = null;
        String[] y;
        try {
            result = db.query(b, table, new String[] { column }, where, null,
                    null, null, orderBy, null);
            if (result.moveToFirst()) {
                do {
                    savedAns.add(result.getString(result.getColumnIndex(column)));
                } while (result.moveToNext());
            } else {
                return null;
            }
            y = savedAns.toArray(new String[result.getCount()]);
        } finally {
            result.close();
        }
        return y;
    }

    public int updateRecords(String table, ContentValues values,
                             String whereClause, String[] whereArgs) {
        int a=0;
        try {
            db.beginTransaction();
             a = db.update(table, values, whereClause, whereArgs);
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
        return a;
    }

    //To get EXP INC Details
    public ArrayList<ExpIncData> getExpIncDataFromList(int id) {
        ArrayList<ExpIncData> expincs = new ArrayList<ExpIncData>();

        // select EXP INC query
        String query = "SELECT  * FROM " + Constants.EXP_INC_ENTRY_RECORD + " where entry_id = "+id;

        // get reference of the EXP INC DB
        Cursor cursor = db.rawQuery(query, null);


        // parse all results
        ExpIncData expinc = null;

        if (cursor.moveToFirst()) {
            do {
                expinc = new ExpIncData();
                expinc.setEntryId(Integer.parseInt(cursor.getString(0).toString()));
                expinc.setEntryAmount(cursor.getFloat(1));
                expinc.setEntryCategory(cursor.getString(2).toString());
                expinc.setEntrySource(cursor.getString(3).toString());
                expinc.setEntryDescription(cursor.getString(4).toString());
                expinc.setEntryDate(cursor.getString(5).toString());
                expinc.setEntryType(cursor.getString(6).toString());
                expincs.add(expinc);
            } while (cursor.moveToNext());
        }        return expincs;
    }

    //To get EXP INC Details
    public ArrayList<ExpIncData> getAllExpIncData() {
        ArrayList<ExpIncData> expincs = new ArrayList<ExpIncData>();

        // select EXP INC query
        String query = "SELECT  * FROM " + Constants.EXP_INC_ENTRY_RECORD +" order by entry_date desc";

        // get reference of the EXP INC DB
        Cursor cursor = db.rawQuery(query, null);


        // parse all results
        ExpIncData expinc = null;

        if (cursor.moveToFirst()) {
            do {
                expinc = new ExpIncData();
                expinc.setEntryId(Integer.parseInt(cursor.getString(0).toString()));
                expinc.setEntryAmount(cursor.getFloat(1));
                expinc.setEntryCategory(cursor.getString(2).toString());
                expinc.setEntrySource(cursor.getString(3).toString());
                expinc.setEntryDescription(cursor.getString(4).toString());
                expinc.setEntryDate(cursor.getString(5).toString());
                expinc.setEntryType(cursor.getString(6).toString());
                expincs.add(expinc);
            } while (cursor.moveToNext());
        }
        return expincs;
    }

    //To get EXP  Details to show on balance screen
    public ArrayList<ExpIncData> getAllExpData() {
        ArrayList<ExpIncData> expincs = new ArrayList<ExpIncData>();

        // select ExpIncData query
        String query = "SELECT  * FROM " + Constants.EXP_INC_ENTRY_RECORD +" where entry_type ="+"\""+"E"+"\"" +" order by entry_date desc";

        // get reference of the ExpIncData DB
        Cursor cursor = db.rawQuery(query, null);


        // parse all results
        ExpIncData expinc = null;

        if (cursor.moveToFirst()) {
            do {
                expinc = new ExpIncData();
                expinc.setEntryId(Integer.parseInt(cursor.getString(0).toString()));
                expinc.setEntryAmount(cursor.getFloat(1));
                expinc.setEntryCategory(cursor.getString(2).toString());
                expinc.setEntrySource(cursor.getString(3).toString());
                expinc.setEntryDescription(cursor.getString(4).toString());
                expinc.setEntryDate(cursor.getString(5).toString());
                expinc.setEntryType(cursor.getString(6).toString());
                expincs.add(expinc);
            } while (cursor.moveToNext());
        }
        return expincs;
    }

    //To get INC  Details to show on Balance Screen
    public ArrayList<ExpIncData> getAllIncData() {
        ArrayList<ExpIncData> expincs = new ArrayList<ExpIncData>();

        // select ExpIncData query
        String query = "SELECT  * FROM " + Constants.EXP_INC_ENTRY_RECORD +" where entry_type ="+"\""+"I"+"\"" +" order by entry_date desc";

        // get reference of the ExpIncData DB
        Cursor cursor = db.rawQuery(query, null);


        // parse all results
        ExpIncData expinc = null;

        if (cursor.moveToFirst()) {
            do {
                expinc = new ExpIncData();
                expinc.setEntryId(Integer.parseInt(cursor.getString(0).toString()));
                expinc.setEntryAmount(cursor.getFloat(1));
                expinc.setEntryCategory(cursor.getString(2).toString());
                expinc.setEntrySource(cursor.getString(3).toString());
                expinc.setEntryDescription(cursor.getString(4).toString());
                expinc.setEntryDate(cursor.getString(5).toString());
                expinc.setEntryType(cursor.getString(6).toString());
                expincs.add(expinc);
            } while (cursor.moveToNext());
        }
        return expincs;
    }


    // To get All categories details based on category type
    public ArrayList<String> getCategoryData(String catType) {
        ArrayList<String> categories = new ArrayList<String>();

        // select category query
        String query = "SELECT  category_name FROM " + Constants.CATEGORY_RECORD +" where category_type ="+"\""+catType+"\"";

        // get reference of the category DB
        Cursor cursor = db.rawQuery(query, null);


        // parse all results
        Category category = null;

        if (cursor.moveToFirst()) {
            do {
                category = new Category();
                categories.add(cursor.getString(0).toString());
            } while (cursor.moveToNext());
        }
        return categories;
    }

    // To get all categories details (For all category types)
    public ArrayList<ItemData> getAllCategoryData() {
        ArrayList<ItemData> categories = new ArrayList<ItemData>();

        // select category query
        String query = "SELECT  * FROM " + Constants.CATEGORY_RECORD;

        // get reference of the category DB
        Cursor cursor = db.rawQuery(query, null);


        // parse all results
        ItemData category = null;

        if (cursor.moveToFirst()) {
            do {
                if(cursor.getString(2).toString().equalsIgnoreCase("E") ) {
                    if(!cursor.getString(1).toString().equalsIgnoreCase("-")) {
                        category = new ItemData();
                        category.setTitle(cursor.getString(1).toString());
                        category.setImageUrl(R.drawable.exp_give);
                        categories.add(category);
                    }
                }
                else
                {
                    if(!cursor.getString(1).toString().equalsIgnoreCase("-")) {
                        category = new ItemData();
                        category.setTitle(cursor.getString(1).toString());
                        category.setImageUrl(R.drawable.income);
                        categories.add(category);
                    }
                }
                //categories.add(cursor.getString(0).toString());
            } while (cursor.moveToNext());
        }
        return categories;
    }

    // To get all sources details
    public ArrayList<String> getSourceData() {
        ArrayList<String> sources = new ArrayList<String>();

        // select Source query
        String query = "SELECT  source_name FROM " + Constants.SOURCE_RECORD;

        // get reference of the Source DB
        Cursor cursor = db.rawQuery(query, null);


        // parse all results
        Source source = null;

        if (cursor.moveToFirst()) {
            do {
                source = new Source();
                sources.add(cursor.getString(0).toString());
            } while (cursor.moveToNext());
        }
        return sources;
    }





}
