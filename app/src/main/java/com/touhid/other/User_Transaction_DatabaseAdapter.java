package com.touhid.other;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.user.project1.Message;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by USER on 9/12/2016.
 */
public class User_Transaction_DatabaseAdapter {

    UserTransactionDataBaseHelper helper;


    public User_Transaction_DatabaseAdapter(Context context){
        helper=new UserTransactionDataBaseHelper(context);

    }

    public long insert(UserTransaction userTransaction){


        ContentValues contentValues=new ContentValues();


        contentValues.put(UserTransactionDataBaseHelper.NAME_TRANSACTION_COL_NAME,userTransaction.getName());
        contentValues.put(UserTransactionDataBaseHelper.AMOUNT_PAY_TRANSACTION_COL_NAME,userTransaction.getAmountPay());
        contentValues.put(UserTransactionDataBaseHelper.AMOUNT_BORROW_TRANSACTION_COL_NAME,userTransaction.getAmountBorrow());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        contentValues.put(UserTransactionDataBaseHelper.DATE_PAY_TRANSACTION_COL_NAME,dateFormat.format(userTransaction.getLastPayDate()));
        contentValues.put(UserTransactionDataBaseHelper.DATE_BORROW_TRANSACTION_COL_NAME,dateFormat.format(userTransaction.getLastBorrowDate()));



        SQLiteDatabase db=helper.getWritableDatabase();
        //row =row id that inserted
        long row=db.insert(UserTransactionDataBaseHelper.TABLE_NAME,null,contentValues);

        return  row;

    }


    public int  updateAmount(UserTransaction userTransaction){

        SQLiteDatabase db=helper.getWritableDatabase();

        ContentValues contentValues=new ContentValues();


        contentValues.put(UserTransactionDataBaseHelper.NAME_TRANSACTION_COL_NAME,userTransaction.getName());
        contentValues.put(UserTransactionDataBaseHelper.AMOUNT_PAY_TRANSACTION_COL_NAME,userTransaction.getAmountPay());
        contentValues.put(UserTransactionDataBaseHelper.AMOUNT_BORROW_TRANSACTION_COL_NAME,userTransaction.getAmountBorrow());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        contentValues.put(UserTransactionDataBaseHelper.DATE_PAY_TRANSACTION_COL_NAME,dateFormat.format(userTransaction.getLastPayDate()));
        contentValues.put(UserTransactionDataBaseHelper.DATE_BORROW_TRANSACTION_COL_NAME,dateFormat.format(userTransaction.getLastBorrowDate()));



        String[] whereArgs={userTransaction.getName()};
        return db.update(UserTransactionDataBaseHelper.TABLE_NAME,contentValues, UserTransactionDataBaseHelper.NAME_TRANSACTION_COL_NAME+" = ? ",whereArgs);
        //0=no update,else = number of row affected
    }

    public int deleteRow(String name){


        SQLiteDatabase db=helper.getWritableDatabase();
        String[] whereArgs={name};

        //return number of row affected
        return  db.delete(UserTransactionDataBaseHelper.TABLE_NAME, UserTransactionDataBaseHelper.NAME_TRANSACTION_COL_NAME+" = ? ",whereArgs);

    }

    public ArrayList<UserTransaction> getDataByName(String userName) throws ParseException {
        SQLiteDatabase sqLiteDatabase=helper.getWritableDatabase();


        String[] columns={UserTransactionDataBaseHelper.UID_COL_NAME,UserTransactionDataBaseHelper.NAME_TRANSACTION_COL_NAME, UserTransactionDataBaseHelper.AMOUNT_PAY_TRANSACTION_COL_NAME,UserTransactionDataBaseHelper.AMOUNT_BORROW_TRANSACTION_COL_NAME, UserTransactionDataBaseHelper.DATE_PAY_TRANSACTION_COL_NAME,UserTransactionDataBaseHelper.DATE_BORROW_TRANSACTION_COL_NAME};//column to select
        Cursor cursor=sqLiteDatabase.query(UserTransactionDataBaseHelper.TABLE_NAME,columns, UserTransactionDataBaseHelper.NAME_TRANSACTION_COL_NAME+" = '"+userName+"'",null,null,null,null);


        ArrayList<UserTransaction> user_transactions=new ArrayList<>();

        while(cursor.moveToNext()){

           UserTransaction userTransaction=new UserTransaction();

            int col_id_index=cursor.getColumnIndex(UserTransactionDataBaseHelper.UID_COL_NAME);
            int id=cursor.getInt(col_id_index);



            int col_name_index=cursor.getColumnIndex(UserTransactionDataBaseHelper.NAME_TRANSACTION_COL_NAME);
            String name=cursor.getString(col_name_index);

            int col_amount_pay_index=cursor.getColumnIndex(UserTransactionDataBaseHelper.AMOUNT_PAY_TRANSACTION_COL_NAME);
            double amountPay=cursor.getDouble(col_amount_pay_index);

            int col_amount_borrow_index=cursor.getColumnIndex(UserTransactionDataBaseHelper.AMOUNT_BORROW_TRANSACTION_COL_NAME);
            double amountBorrow=cursor.getDouble(col_amount_borrow_index);



            int col_date_pay_index=cursor.getColumnIndex(UserTransactionDataBaseHelper.DATE_PAY_TRANSACTION_COL_NAME);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date datePay=dateFormat.parse(cursor.getString(col_date_pay_index));


            int col_date_borrow_index=cursor.getColumnIndex(UserTransactionDataBaseHelper.DATE_BORROW_TRANSACTION_COL_NAME);

            Date dateBorrow=dateFormat.parse(cursor.getString(col_date_borrow_index));


            userTransaction.setId(id);
            userTransaction.setName(name);
            userTransaction.setAmountBorrow(amountBorrow);
            userTransaction.setAmountPay(amountPay);
            userTransaction.setLastBorrowDate(dateBorrow);
            userTransaction.setLastPayDate(datePay);

            user_transactions.add(userTransaction);

        }

        return user_transactions;

    }




    public ArrayList<UserTransaction> getAllData() throws ParseException {


        SQLiteDatabase sqLiteDatabase=helper.getWritableDatabase();


        String[] columns={UserTransactionDataBaseHelper.UID_COL_NAME,UserTransactionDataBaseHelper.NAME_TRANSACTION_COL_NAME, UserTransactionDataBaseHelper.AMOUNT_PAY_TRANSACTION_COL_NAME,UserTransactionDataBaseHelper.AMOUNT_BORROW_TRANSACTION_COL_NAME, UserTransactionDataBaseHelper.DATE_PAY_TRANSACTION_COL_NAME,UserTransactionDataBaseHelper.DATE_BORROW_TRANSACTION_COL_NAME};//column to select
        Cursor cursor=sqLiteDatabase.query(UserTransactionDataBaseHelper.TABLE_NAME,columns,null,null,null,null,null);


        ArrayList<UserTransaction> user_transactions=new ArrayList<>();

        while(cursor.moveToNext()){

            UserTransaction userTransaction=new UserTransaction();

            int col_id_index=cursor.getColumnIndex(UserTransactionDataBaseHelper.UID_COL_NAME);
            int id=cursor.getInt(col_id_index);



            int col_name_index=cursor.getColumnIndex(UserTransactionDataBaseHelper.NAME_TRANSACTION_COL_NAME);
            String name=cursor.getString(col_name_index);

            int col_amount_pay_index=cursor.getColumnIndex(UserTransactionDataBaseHelper.AMOUNT_PAY_TRANSACTION_COL_NAME);
            double amountPay=cursor.getDouble(col_amount_pay_index);

            int col_amount_borrow_index=cursor.getColumnIndex(UserTransactionDataBaseHelper.AMOUNT_BORROW_TRANSACTION_COL_NAME);
            double amountBorrow=cursor.getDouble(col_amount_borrow_index);



            int col_date_pay_index=cursor.getColumnIndex(UserTransactionDataBaseHelper.DATE_PAY_TRANSACTION_COL_NAME);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date datePay=dateFormat.parse(cursor.getString(col_date_pay_index));


            int col_date_borrow_index=cursor.getColumnIndex(UserTransactionDataBaseHelper.DATE_BORROW_TRANSACTION_COL_NAME);

            Date dateBorrow=dateFormat.parse(cursor.getString(col_date_borrow_index));


            userTransaction.setId(id);
            userTransaction.setName(name);
            userTransaction.setAmountBorrow(amountBorrow);
            userTransaction.setAmountPay(amountPay);
            userTransaction.setLastBorrowDate(dateBorrow);
            userTransaction.setLastPayDate(datePay);

            user_transactions.add(userTransaction);
          

        }

        return user_transactions;

    }



    //Database Helper class

    private  static  class UserTransactionDataBaseHelper extends SQLiteOpenHelper {


        //database schema declaraion
        private static final String  DATBASE_NAME="paymentManagement";
        private static final String  TABLE_NAME="User_transaction_Detail";
        private static  final int   DATBASE_VERSION=1;

        //col name
        private static final String  UID_COL_NAME="_id";
        private static final  String  NAME_TRANSACTION_COL_NAME="User_Name";
        private static  final String  AMOUNT_PAY_TRANSACTION_COL_NAME="Amount_pay";
        private static  final String  AMOUNT_BORROW_TRANSACTION_COL_NAME="Amount_borrow";
        private static  final String  DATE_PAY_TRANSACTION_COL_NAME="LastUpdate_pay";
        private static  final String  DATE_BORROW_TRANSACTION_COL_NAME="LastUpdate_borrow";


        private Context context;


        //query
        private static final String CREATE_USER_TABLE = "create table "+TABLE_NAME+" ( " +
                " "+UID_COL_NAME+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                " "+NAME_TRANSACTION_COL_NAME+" VARCHAR(150) NOT NULL UNIQUE ,"+
                " "+AMOUNT_PAY_TRANSACTION_COL_NAME+" DOUBLE NOT NULL,"+
                " "+AMOUNT_BORROW_TRANSACTION_COL_NAME+" DOUBLE NOT NULL,"+
                " "+DATE_PAY_TRANSACTION_COL_NAME+" DATETIME NOT NULL,"+
                " "+DATE_BORROW_TRANSACTION_COL_NAME+" DATETIME NOT NULL);";

       
        private static final  String  DROP_TABLE="DROP TABLE  IF EXISTS "+TABLE_NAME;
        @Override
        protected void finalize() throws Throwable {

        }

        public UserTransactionDataBaseHelper(Context context) {
            super(context, DATBASE_NAME, null, DATBASE_VERSION);
            this.context=context;
            Message.message(context," UserTransactionDataBaseHelper-Constructor Called");
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            Message.message(context,"UserTransactionDataBaseHelper-onCreate Called");

            try{

                db.execSQL(CREATE_USER_TABLE);

            }catch (SQLException e){
                Message.message(context,"ms-1 :"+e);
            }


        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            Message.message(context,"UserTransactionDataBaseHelper-onUpgrade Called");


            try{

                db.execSQL(DROP_TABLE);
                onCreate(db);

            }catch (SQLException e){
                Message.message(context,""+e);
            }

        }
    }
}
