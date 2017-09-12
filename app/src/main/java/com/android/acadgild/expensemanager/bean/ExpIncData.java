package com.android.acadgild.expensemanager.bean;

/**
 * Created by Jal on 28-07-2017.
 * BEAN POJO class for Expense Income list details
 */

public class ExpIncData {

    int entryId;
    Float entryAmount;
    String entryCategory;
    String entrySource;
    String entryDescription;
    String entryDate;
    String entryType;
    String id;

    public int getEntryId() {
        return entryId;
    }

    public void setEntryId(int entryId) {
        this.entryId = entryId;
    }

    public Float getEntryAmount() {
        return entryAmount;
    }

    public void setEntryAmount(Float entryAmount) {
        this.entryAmount = entryAmount;
    }

    public String getEntryCategory() {
        return entryCategory;
    }

    public void setEntryCategory(String entryCategory) {
        this.entryCategory = entryCategory;
    }

    public String getEntrySource() {
        return entrySource;
    }

    public void setEntrySource(String entrySource) {
        this.entrySource = entrySource;
    }

    public String getEntryDescription() {
        return entryDescription;
    }

    public void setEntryDescription(String entryDescription) {
        this.entryDescription = entryDescription;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getEntryType() {
        return entryType;
    }

    public void setEntryType(String entryType) {
        this.entryType = entryType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
