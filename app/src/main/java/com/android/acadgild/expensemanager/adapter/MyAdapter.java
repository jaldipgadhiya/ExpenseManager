package com.android.acadgild.expensemanager.adapter;


import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.acadgild.expensemanager.bean.Category;
import com.android.acadgild.expensemanager.bean.ExpIncData;
import com.android.acadgild.expensemanager.bean.ItemData;
import com.android.acadgild.expensemanager.R;

import java.util.ArrayList;

/**
 * Created by Jal on 08-09-2017.
 * Adapter class to show Category recycler view list
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private ArrayList<ItemData> data;

    public MyAdapter(ArrayList<ItemData> data) {
        this.data = data;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row, null);

        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // - get data from your itemsData at this position
        // - replace the contents of the view with that itemsData

        viewHolder.txtViewTitle.setText(data.get(position).getTitle());
        viewHolder.imgViewIcon.setImageResource(data.get(position).getImageUrl());
        viewHolder.txtViewTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Clicked on "+ data.get(position).getTitle(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });

    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtViewTitle;
        public ImageView imgViewIcon;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            txtViewTitle = (TextView) itemLayoutView.findViewById(R.id.item_title);
            imgViewIcon = (ImageView) itemLayoutView.findViewById(R.id.item_icon);
        }
    }





    // Return the size of your itemsData (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return data.size();
    }
}

