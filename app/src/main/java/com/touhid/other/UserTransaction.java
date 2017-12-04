package com.touhid.other;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class UserTransaction {

    private int id;
    private String name;
    private  double amountPay;
    private double amountBorrow;
    private Date lastPayDate;
    private Date lastBorrowDate;

    public UserTransaction(String name) {

        this.name = name;
        this.amountPay=0;
        this.amountBorrow=0;
        this.lastBorrowDate=new Date();
        this.lastPayDate=new Date();



    }

    public UserTransaction(int id, String name, double amountPay, double amountBorrow, Date lastPayDate, Date lastBorrowDate) {
        this.id = id;
        this.name = name;
        this.amountPay = amountPay;
        this.amountBorrow = amountBorrow;
        this.lastPayDate = lastPayDate;
        this.lastBorrowDate = lastBorrowDate;
    }

    public UserTransaction(Date lastPayDate, Date lastBorrowDate, double amountBorrow, double amountPay, String name) {
        this.lastPayDate = lastPayDate;
        this.lastBorrowDate = lastBorrowDate;
        this.amountBorrow = amountBorrow;
        this.amountPay = amountPay;
        this.name = name;
    }

    public UserTransaction(){}

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getAmountPay() {
        return amountPay;
    }

    public double getAmountBorrow() {
        return amountBorrow;
    }

    public Date getLastPayDate() {
        return lastPayDate;
    }

    public Date getLastBorrowDate() {
        return lastBorrowDate;
    }

    @Override
    public String toString() {
        return "UserTransaction{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", amountPay=" + amountPay +
                ", amountBorrow=" + amountBorrow +
                ", lastPayDate=" + lastPayDate +
                ", lastBorrowDate=" + lastBorrowDate +
                '}';
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmountPay(double amountPay) {
        this.amountPay = amountPay;
    }

    public void setAmountBorrow(double amountBorrow) {
        this.amountBorrow = amountBorrow;
    }

    public void setLastPayDate(Date lastPayDate) {
        this.lastPayDate = lastPayDate;
    }

    public void setLastBorrowDate(Date lastBorrowDate) {
        this.lastBorrowDate = lastBorrowDate;
    }
}
