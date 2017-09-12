package com.android.acadgild.expensemanager.adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.acadgild.expensemanager.bean.ExpIncData;
import com.android.acadgild.expensemanager.bean.ExpenseIncomeData;
import com.android.acadgild.expensemanager.R;

import java.util.ArrayList;
/**
 * Created by Jal on 08-09-2017.
 * Adapter class for displaying Expense Income Information List
 */

public class CustomAdapterMainList extends BaseAdapter {

    Context context;
    ArrayList<ExpIncData> data;
    LayoutInflater inflater;

    public CustomAdapterMainList(Context context, ArrayList<ExpIncData> data) {
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.custom_row_main_list, parent, false);
            holder = new ViewHolder();
            holder.expIncAmount = (TextView) convertView.findViewById(R.id.expIncAmount);
            holder.expIncDesc = (TextView) convertView.findViewById(R.id.expIncDesc);
            holder.expIncDate = (TextView) convertView.findViewById(R.id.expIncDate);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if(data.get(position).getEntryType().equalsIgnoreCase("E"))
        {
            holder.expIncAmount.setBackgroundResource(R.color.colorExp);
        }
        else
        {
            holder.expIncAmount.setBackgroundResource(R.color.colorInc);
        }
        holder.expIncAmount.setText(data.get(position).getEntryAmount().toString());
        holder.expIncDesc.setText(data.get(position).getEntryDescription().toString());
        holder.expIncDate.setText(data.get(position).getEntryDate().toString());


        return convertView;
    }


    public class ViewHolder {
        //TextView view;
        TextView expIncAmount, expIncDesc, expIncDate;
    }
}
