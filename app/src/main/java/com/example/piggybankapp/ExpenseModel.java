package com.example.piggybankapp;

// ExpenseModel.java
public class ExpenseModel {
    private String expenseName;
    private String amount;

    private String spentAmount;

    public ExpenseModel(String expenseName, String amount) {
        this.expenseName = expenseName;
        this.amount = amount;
    }

    public ExpenseModel(String spentAmount) {
        this.spentAmount = spentAmount;

    }

    public String getExpenseName() {
        return expenseName;
    }

    public String getAmount() {
        return amount;
    }

    public String getSpentAmount() {
        return spentAmount;
    }

    public void setSpentAmount(String spentAmount) {
        this.spentAmount = spentAmount;
    }

}
