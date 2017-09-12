package com.android.acadgild.expensemanager.bean;

/**
 * Created by Jal on 09-09-2017.
 * BEAN POJO class for Category details
 */

public class Category {

    int categoryId;
    String categoryName;
    String categoryType;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }
}
