package com.example.user.project1;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by USER on 9/12/2016.
 */
public class User implements Parcelable {

    private String name;
    private String phoneNumber;
    private String email;
    private Bitmap image;
    private int id;

    private double amountPay;
    private double amountBorrow;
    private Date lastPayDate;
    private Date lastBorrowDate;


    public User() {
    }


    public User(String name, String phoneNumber, String email) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.amountBorrow = 0;
        this.amountPay = 0;
        this.lastBorrowDate = new Date();
        this.lastPayDate = new Date();

    }

    public User(String name, String phoneNumber, String email, Bitmap image, double amountPay, double amountBorrow, Date lastPayDate, Date lastBorrowDate) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.image = image;
        this.amountPay = amountPay;
        this.amountBorrow = amountBorrow;
        this.lastPayDate = lastPayDate;
        this.lastBorrowDate = lastBorrowDate;
    }

    public User(String name, String phoneNumber, String email, Bitmap image, int id, double amountPay, double amountBorrow, Date lastPayDate, Date lastBorrowDate) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.image = image;
        this.id = id;
        this.amountPay = amountPay;
        this.amountBorrow = amountBorrow;
        this.lastPayDate = lastPayDate;
        this.lastBorrowDate = lastBorrowDate;
    }

    public User(String name, String phoneNumber, String email, int id, double amountPay, double amountBorrow, Date lastPayDate, Date lastBorrowDate) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.id = id;
        this.amountPay = amountPay;
        this.amountBorrow = amountBorrow;
        this.lastPayDate = lastPayDate;
        this.lastBorrowDate = lastBorrowDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public Bitmap getImage() {
        return image;
    }

    public int getId() {
        return id;
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
        return "User{" +
                "name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", amountPay=" + amountPay +
                ", amountBorrow=" + amountBorrow +
                ", lastPayDate=" + lastPayDate +
                ", lastBorrowDate=" + lastBorrowDate +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(name);
        dest.writeString(phoneNumber);
        dest.writeString(email);
        dest.writeDouble(amountPay);
        dest.writeDouble(amountBorrow);
        dest.writeSerializable(lastBorrowDate);
        dest.writeSerializable(lastPayDate);



    }

    public static final Parcelable.Creator<User> CREATOR = new Creator<User>() {

        public User createFromParcel(Parcel source) {

            User user = new User();



            user.name=source.readString();
            user.phoneNumber=source.readString();
            user.email=source.readString();
            user.amountPay=source.readDouble();
            user.amountBorrow=source.readDouble();
            user.lastPayDate= (Date) source.readSerializable();
            user.lastBorrowDate= (Date) source.readSerializable();


            return user;

        }

        public User[] newArray(int size) {

            return new User[size];

        }

    };



}
