package com.android.acadgild.expensemanager;

// This class is to compare image to sort Navigation view list in ascending order

import android.util.Log;

import com.android.acadgild.expensemanager.bean.CustomHandler;

import java.util.Comparator;

class ImageComparator implements Comparator<CustomHandler> {
    @Override
    public int compare(CustomHandler lhs, CustomHandler rhs) {
        Integer d1 = ((CustomHandler) lhs).getImage().intValue();
        Integer d2 = ((CustomHandler) rhs).getImage().intValue();
        return d1.intValue() > d2.intValue() ? 1 :(d1.intValue() < d2.intValue() ? -1 : 0);
    }
}