package com.example.user.project1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by USER on 9/12/2016.
 */
public class UserDatabaseAdapter {


    UserDataBaseHelper helper;


    public UserDatabaseAdapter(Context context){
        helper=new UserDataBaseHelper(context);

    }

    public long insert(User user){


        ContentValues contentValues=new ContentValues();


        contentValues.put(UserDataBaseHelper.NAME_COL_NAME,user.getName());
        contentValues.put(UserDataBaseHelper.PHONE_NUMBER_COL_NAME,user.getPhoneNumber());
        contentValues.put(UserDataBaseHelper.EMAIL_COL_NAME,user.getEmail());

        contentValues.put(UserDataBaseHelper.AMOUNT_PAY_TRANSACTION_COL_NAME,user.getAmountPay());
        contentValues.put(UserDataBaseHelper.AMOUNT_BORROW_TRANSACTION_COL_NAME,user.getAmountBorrow());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        contentValues.put(UserDataBaseHelper.DATE_PAY_TRANSACTION_COL_NAME,dateFormat.format(user.getLastPayDate()));
        contentValues.put(UserDataBaseHelper.DATE_BORROW_TRANSACTION_COL_NAME,dateFormat.format(user.getLastBorrowDate()));

        SQLiteDatabase db=helper.getWritableDatabase();
        //row =row id that inserted
        long row=db.insert(UserDataBaseHelper.TABLE_NAME,null,contentValues);

        return  row;

    }


    public int  updateByName(User user,String userName){

        SQLiteDatabase db=helper.getWritableDatabase();

        ContentValues contentValues=new ContentValues();


        contentValues.put(UserDataBaseHelper.NAME_COL_NAME,user.getName());
        contentValues.put(UserDataBaseHelper.PHONE_NUMBER_COL_NAME,user.getPhoneNumber());
        contentValues.put(UserDataBaseHelper.EMAIL_COL_NAME,user.getEmail());

        contentValues.put(UserDataBaseHelper.AMOUNT_PAY_TRANSACTION_COL_NAME,user.getAmountPay());
        contentValues.put(UserDataBaseHelper.AMOUNT_BORROW_TRANSACTION_COL_NAME,user.getAmountBorrow());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        contentValues.put(UserDataBaseHelper.DATE_PAY_TRANSACTION_COL_NAME,dateFormat.format(user.getLastPayDate()));
        contentValues.put(UserDataBaseHelper.DATE_BORROW_TRANSACTION_COL_NAME,dateFormat.format(user.getLastBorrowDate()));

        Log.d("royal","USer update:"+user.toString()+" name:"+userName);

        String[] whereArgs={userName};
        return db.update(UserDataBaseHelper.TABLE_NAME,contentValues,UserDataBaseHelper.NAME_COL_NAME+" = ? ",whereArgs);
        //0=no update,else = number of row affected
    }

    public int deleteRow(String name){


        SQLiteDatabase db=helper.getWritableDatabase();
        String[] whereArgs={name};

        //return number of row affected
        return  db.delete(UserDataBaseHelper.TABLE_NAME,UserDataBaseHelper.NAME_COL_NAME+" = ? ",whereArgs);

    }




    public User getDataByName(String userName) throws ParseException {
        SQLiteDatabase sqLiteDatabase=helper.getWritableDatabase();


        String[] columns={UserDataBaseHelper.UID_COL_NAME, UserDataBaseHelper.NAME_COL_NAME, UserDataBaseHelper.PHONE_NUMBER_COL_NAME,UserDataBaseHelper.EMAIL_COL_NAME,UserDataBaseHelper.AMOUNT_PAY_TRANSACTION_COL_NAME,UserDataBaseHelper.AMOUNT_BORROW_TRANSACTION_COL_NAME,UserDataBaseHelper.DATE_PAY_TRANSACTION_COL_NAME,UserDataBaseHelper.DATE_BORROW_TRANSACTION_COL_NAME};//column to select
        Cursor cursor=sqLiteDatabase.query(UserDataBaseHelper.TABLE_NAME,columns, UserDataBaseHelper.NAME_COL_NAME+" = '"+userName+"'",null,null,null,null);


        User user=null;

        while(cursor.moveToNext()){

            int col_id_index=cursor.getColumnIndex(UserDataBaseHelper.UID_COL_NAME);
            int id=cursor.getInt(col_id_index);



            int col_name_index=cursor.getColumnIndex(UserDataBaseHelper.NAME_COL_NAME);
            String name=cursor.getString(col_name_index);

            int col_phone_index=cursor.getColumnIndex(UserDataBaseHelper.PHONE_NUMBER_COL_NAME);
            String phoneNumber=cursor.getString(col_phone_index);


            int col_email_index=cursor.getColumnIndex(UserDataBaseHelper.EMAIL_COL_NAME);
            String email=cursor.getString(col_email_index);


            int col_amount_pay_index=cursor.getColumnIndex(UserDataBaseHelper.AMOUNT_PAY_TRANSACTION_COL_NAME);
            double amountPay=cursor.getDouble(col_amount_pay_index);

            int col_amount_borrow_index=cursor.getColumnIndex(UserDataBaseHelper.AMOUNT_BORROW_TRANSACTION_COL_NAME);
            double amountBorrow=cursor.getDouble(col_amount_borrow_index);

            int col_date_pay_index=cursor.getColumnIndex(UserDataBaseHelper.DATE_PAY_TRANSACTION_COL_NAME);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date datePay=dateFormat.parse(cursor.getString(col_date_pay_index));


            int col_date_borrow_index=cursor.getColumnIndex(UserDataBaseHelper.DATE_BORROW_TRANSACTION_COL_NAME);

            Date dateBorrow=dateFormat.parse(cursor.getString(col_date_borrow_index));



             user=new User(name,phoneNumber,email,id,amountPay,amountBorrow,datePay,dateBorrow);



        }

        return user;

    }

    public ArrayList<User> getAllBorrowData() throws ParseException {


        SQLiteDatabase sqLiteDatabase=helper.getWritableDatabase();

        String[] selectionArgs={new Double(0).toString()};
        String[] columns={UserDataBaseHelper.UID_COL_NAME, UserDataBaseHelper.NAME_COL_NAME, UserDataBaseHelper.PHONE_NUMBER_COL_NAME,UserDataBaseHelper.EMAIL_COL_NAME,UserDataBaseHelper.AMOUNT_PAY_TRANSACTION_COL_NAME,UserDataBaseHelper.AMOUNT_BORROW_TRANSACTION_COL_NAME,UserDataBaseHelper.DATE_PAY_TRANSACTION_COL_NAME,UserDataBaseHelper.DATE_BORROW_TRANSACTION_COL_NAME};//column to select
        //Cursor cursor=sqLiteDatabase.query(UserDataBaseHelper.TABLE_NAME,columns,null,null,null,null,null);
        Cursor cursor=sqLiteDatabase.query(UserDataBaseHelper.TABLE_NAME,columns, UserDataBaseHelper.AMOUNT_BORROW_TRANSACTION_COL_NAME+" > ? ",selectionArgs,null,null,null);


        ArrayList<User> users=new ArrayList<>();

        while(cursor.moveToNext()){



            int col_id_index=cursor.getColumnIndex(UserDataBaseHelper.UID_COL_NAME);
            int id=cursor.getInt(col_id_index);



            int col_name_index=cursor.getColumnIndex(UserDataBaseHelper.NAME_COL_NAME);
            String name=cursor.getString(col_name_index);

            int col_phone_index=cursor.getColumnIndex(UserDataBaseHelper.PHONE_NUMBER_COL_NAME);
            String phoneNumber=cursor.getString(col_phone_index);


            int col_email_index=cursor.getColumnIndex(UserDataBaseHelper.EMAIL_COL_NAME);
            String email=cursor.getString(col_email_index);


            int col_amount_pay_index=cursor.getColumnIndex(UserDataBaseHelper.AMOUNT_PAY_TRANSACTION_COL_NAME);
            double amountPay=cursor.getDouble(col_amount_pay_index);

            int col_amount_borrow_index=cursor.getColumnIndex(UserDataBaseHelper.AMOUNT_BORROW_TRANSACTION_COL_NAME);
            double amountBorrow=cursor.getDouble(col_amount_borrow_index);

            int col_date_pay_index=cursor.getColumnIndex(UserDataBaseHelper.DATE_PAY_TRANSACTION_COL_NAME);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date datePay=dateFormat.parse(cursor.getString(col_date_pay_index));


            int col_date_borrow_index=cursor.getColumnIndex(UserDataBaseHelper.DATE_BORROW_TRANSACTION_COL_NAME);

            Date dateBorrow=dateFormat.parse(cursor.getString(col_date_borrow_index));



            User user=new User(name,phoneNumber,email,id,amountPay,amountBorrow,datePay,dateBorrow);

            users.add(user);

        }

        return users;

    }
    public ArrayList<User> getAllPayData() throws ParseException {


        SQLiteDatabase sqLiteDatabase=helper.getWritableDatabase();

        String[] selectionArgs={new Double(0).toString()};
        String[] columns={UserDataBaseHelper.UID_COL_NAME, UserDataBaseHelper.NAME_COL_NAME, UserDataBaseHelper.PHONE_NUMBER_COL_NAME,UserDataBaseHelper.EMAIL_COL_NAME,UserDataBaseHelper.AMOUNT_PAY_TRANSACTION_COL_NAME,UserDataBaseHelper.AMOUNT_BORROW_TRANSACTION_COL_NAME,UserDataBaseHelper.DATE_PAY_TRANSACTION_COL_NAME,UserDataBaseHelper.DATE_BORROW_TRANSACTION_COL_NAME};//column to select
        //Cursor cursor=sqLiteDatabase.query(UserDataBaseHelper.TABLE_NAME,columns,null,null,null,null,null);
        Cursor cursor=sqLiteDatabase.query(UserDataBaseHelper.TABLE_NAME,columns, UserDataBaseHelper.AMOUNT_PAY_TRANSACTION_COL_NAME+" > ? ",selectionArgs,null,null,null);


        ArrayList<User> users=new ArrayList<>();

        while(cursor.moveToNext()){



            int col_id_index=cursor.getColumnIndex(UserDataBaseHelper.UID_COL_NAME);
            int id=cursor.getInt(col_id_index);



            int col_name_index=cursor.getColumnIndex(UserDataBaseHelper.NAME_COL_NAME);
            String name=cursor.getString(col_name_index);

            int col_phone_index=cursor.getColumnIndex(UserDataBaseHelper.PHONE_NUMBER_COL_NAME);
            String phoneNumber=cursor.getString(col_phone_index);


            int col_email_index=cursor.getColumnIndex(UserDataBaseHelper.EMAIL_COL_NAME);
            String email=cursor.getString(col_email_index);


            int col_amount_pay_index=cursor.getColumnIndex(UserDataBaseHelper.AMOUNT_PAY_TRANSACTION_COL_NAME);
            double amountPay=cursor.getDouble(col_amount_pay_index);

            int col_amount_borrow_index=cursor.getColumnIndex(UserDataBaseHelper.AMOUNT_BORROW_TRANSACTION_COL_NAME);
            double amountBorrow=cursor.getDouble(col_amount_borrow_index);

            int col_date_pay_index=cursor.getColumnIndex(UserDataBaseHelper.DATE_PAY_TRANSACTION_COL_NAME);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date datePay=dateFormat.parse(cursor.getString(col_date_pay_index));


            int col_date_borrow_index=cursor.getColumnIndex(UserDataBaseHelper.DATE_BORROW_TRANSACTION_COL_NAME);

            Date dateBorrow=dateFormat.parse(cursor.getString(col_date_borrow_index));



            User user=new User(name,phoneNumber,email,id,amountPay,amountBorrow,datePay,dateBorrow);

            users.add(user);

        }

        return users;

    }

    public ArrayList<User> getAllData() throws ParseException {


        SQLiteDatabase sqLiteDatabase=helper.getWritableDatabase();


        String[] columns={UserDataBaseHelper.UID_COL_NAME, UserDataBaseHelper.NAME_COL_NAME, UserDataBaseHelper.PHONE_NUMBER_COL_NAME,UserDataBaseHelper.EMAIL_COL_NAME,UserDataBaseHelper.AMOUNT_PAY_TRANSACTION_COL_NAME,UserDataBaseHelper.AMOUNT_BORROW_TRANSACTION_COL_NAME,UserDataBaseHelper.DATE_PAY_TRANSACTION_COL_NAME,UserDataBaseHelper.DATE_BORROW_TRANSACTION_COL_NAME};//column to select
        Cursor cursor=sqLiteDatabase.query(UserDataBaseHelper.TABLE_NAME,columns,null,null,null,null,null);


       ArrayList<User> users=new ArrayList<>();

        while(cursor.moveToNext()){



            int col_id_index=cursor.getColumnIndex(UserDataBaseHelper.UID_COL_NAME);
            int id=cursor.getInt(col_id_index);



            int col_name_index=cursor.getColumnIndex(UserDataBaseHelper.NAME_COL_NAME);
            String name=cursor.getString(col_name_index);

            int col_phone_index=cursor.getColumnIndex(UserDataBaseHelper.PHONE_NUMBER_COL_NAME);
            String phoneNumber=cursor.getString(col_phone_index);


            int col_email_index=cursor.getColumnIndex(UserDataBaseHelper.EMAIL_COL_NAME);
            String email=cursor.getString(col_email_index);


            int col_amount_pay_index=cursor.getColumnIndex(UserDataBaseHelper.AMOUNT_PAY_TRANSACTION_COL_NAME);
            double amountPay=cursor.getDouble(col_amount_pay_index);

            int col_amount_borrow_index=cursor.getColumnIndex(UserDataBaseHelper.AMOUNT_BORROW_TRANSACTION_COL_NAME);
            double amountBorrow=cursor.getDouble(col_amount_borrow_index);

            int col_date_pay_index=cursor.getColumnIndex(UserDataBaseHelper.DATE_PAY_TRANSACTION_COL_NAME);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date datePay=dateFormat.parse(cursor.getString(col_date_pay_index));


            int col_date_borrow_index=cursor.getColumnIndex(UserDataBaseHelper.DATE_BORROW_TRANSACTION_COL_NAME);

            Date dateBorrow=dateFormat.parse(cursor.getString(col_date_borrow_index));



            User user=new User(name,phoneNumber,email,id,amountPay,amountBorrow,datePay,dateBorrow);

            users.add(user);

        }

        return users;

    }



 //Database Helper class

   private  static  class UserDataBaseHelper extends SQLiteOpenHelper {


       //database schema declaraion
        private static final String  DATBASE_NAME="paymentManagement";
        private static final String  TABLE_NAME="User_Detail";
        private static  final int   DATBASE_VERSION=1;

        //col name
        private static final String  UID_COL_NAME="_id";
        private static final  String  NAME_COL_NAME="User_Name";
        private static  final String  PHONE_NUMBER_COL_NAME="User_PhoneNumber";
        private static  final String  EMAIL_COL_NAME="User_Email";
        private static  final String  IMAGE_COL_NAME="User_Image";
       private static  final String  AMOUNT_PAY_TRANSACTION_COL_NAME="Amount_pay";
       private static  final String  AMOUNT_BORROW_TRANSACTION_COL_NAME="Amount_borrow";
       private static  final String  DATE_PAY_TRANSACTION_COL_NAME="LastUpdate_pay";
       private static  final String  DATE_BORROW_TRANSACTION_COL_NAME="LastUpdate_borrow";

        private Context context;


        //query
        private static final String CREATE_USER_TABLE = "create table "+TABLE_NAME+" ( " +
                " "+UID_COL_NAME+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                " "+NAME_COL_NAME+" VARCHAR(150) NOT NULL UNIQUE ,"+

                " "+PHONE_NUMBER_COL_NAME+" VARCHAR(30)NOT NULL,"+
                " "+EMAIL_COL_NAME+" VARCHAR(100) NOT NULL,"+
                " "+AMOUNT_PAY_TRANSACTION_COL_NAME+" DOUBLE NOT NULL,"+
                " "+AMOUNT_BORROW_TRANSACTION_COL_NAME+" DOUBLE NOT NULL,"+
                " "+DATE_PAY_TRANSACTION_COL_NAME+" DATETIME NOT NULL,"+
                " "+DATE_BORROW_TRANSACTION_COL_NAME+" DATETIME NOT NULL);";
        //private static final  String  CREATE_TABLE="CREATE TABLE "+TABLE_NAME+" ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+NAME+" VARCHAR(200), "+PASSWORD+" VARCHAR(200));";
        private static final  String  DROP_TABLE="DROP TABLE  IF EXISTS "+TABLE_NAME;
        @Override
        protected void finalize() throws Throwable {

        }

        public UserDataBaseHelper(Context context) {
            super(context, DATBASE_NAME, null, DATBASE_VERSION);
            this.context=context;
            //Message.message(context,"UserDataBaseHelper-Constructor Called");
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

           // Message.message(context,"UserDataBaseHelper-onCreate Called");

            try{

                db.execSQL(CREATE_USER_TABLE);

            }catch (SQLException e){
                Message.message(context,""+e);
            }


        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

           // Message.message(context,"UserDataBaseHelper-onUpgrade Called");


            try{

                db.execSQL(DROP_TABLE);
                onCreate(db);

            }catch (SQLException e){
                Message.message(context,""+e);
            }

        }
    }
}
