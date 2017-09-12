package com.android.acadgild.expensemanager.bean;

/**
 * Created by Jal on 08-09-2017.
 * BEAN POJO class for Category Recycler view  details
 */

public class ItemData {

    private String title;
    private int imageUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(int imageUrl) {
        this.imageUrl = imageUrl;
    }

   /* public ItemData(String title, int imageUrl) {
        this.title = title;
        this.imageUrl = imageUrl;
    }*/
    // getters & setters
}
