package com.android.acadgild.expensemanager.adapter;

import android.content.Context;
import android.media.audiofx.BassBoost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.acadgild.expensemanager.R;
import com.android.acadgild.expensemanager.bean.ExpIncData;

import java.util.ArrayList;

/**
 * Created by Jal on 11-09-2017.
 * Adapter class to show setting activity list
 */

public class SettingsAdapter extends BaseAdapter {


    Context context;
    ArrayList data;
    LayoutInflater inflater;
    String txtSetting;
    String txtSubSetting;

    public SettingsAdapter(Context context, ArrayList data) {
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
        final SettingsAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row_settings, parent, false);
            holder = new SettingsAdapter.ViewHolder();
            //holder.view = (TextView) convertView.findViewById(R.id.view);
            holder.textSettings = (TextView) convertView.findViewById(R.id.textSettings);
            holder.textSubSettings = (TextView) convertView.findViewById(R.id.textSubSettings);
            holder.btnToggle = (Button) convertView.findViewById(R.id.btnToggle);
            convertView.setTag(holder);
        } else {
            holder = (SettingsAdapter.ViewHolder) convertView.getTag();
        }

        int first_index = data.get(position).toString().indexOf("\n");
        Log.e("first_index", "= " + first_index);
        if(first_index!=-1) {
             txtSetting = data.get(position).toString().substring(0, first_index);
             txtSubSetting = data.get(position).toString().substring(first_index + 1);
        }
        else
        {
            txtSetting = data.get(position).toString();
            txtSubSetting =null;
        }
        if(txtSetting.equalsIgnoreCase("Show balance view"))
        {
            holder.btnToggle.setVisibility(convertView.VISIBLE);
            txtSubSetting = holder.btnToggle.getText().toString();
        }

        holder.btnToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtSubSetting = holder.btnToggle.getText().toString();
            }
        });

        holder.textSettings.setText(txtSetting);
        holder.textSubSettings.setText(txtSubSetting);


        return convertView;
    }


    public class ViewHolder {
        TextView textSettings, textSubSettings;
        Button btnToggle;
    }
}
