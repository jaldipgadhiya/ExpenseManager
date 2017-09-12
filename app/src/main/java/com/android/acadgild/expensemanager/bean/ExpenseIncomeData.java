package com.android.acadgild.expensemanager.bean;

/**
 * Created by Jal on 08-09-2017.
 * BEAN POJO class for Expense Income list details
 */

public class ExpenseIncomeData {

    private String expIncDesc,expIncDate;
    private Float expIncAmount;

    public String getExpIncDesc() {
        return expIncDesc;
    }

    public void setExpIncDesc(String expIncDesc) {
        this.expIncDesc = expIncDesc;
    }

    public String getExpIncDate() {
        return expIncDate;
    }

    public void setExpIncDate(String expIncDate) {
        this.expIncDate = expIncDate;
    }

    public Float getExpIncAmount() {
        return expIncAmount;
    }

    public void setExpIncAmount(Float expIncAmount) {
        this.expIncAmount = expIncAmount;
    }

}
