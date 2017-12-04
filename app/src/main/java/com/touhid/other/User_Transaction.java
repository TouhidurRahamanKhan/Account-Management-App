package com.touhid.other;

import java.util.Date;

/**
 * Created by USER on 9/12/2016.
 */
public class User_Transaction {

    private String userName;
    private double amount;
    private Date date;

    public User_Transaction(){}

    public User_Transaction(String userName) {
        this.userName = userName;
        amount=0;
        date=new Date();
    }

    public User_Transaction(double amount, Date date, String userName) {
        this.amount = amount;
        this.date = date;
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public double getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
