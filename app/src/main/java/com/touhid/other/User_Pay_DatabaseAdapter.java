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
public class User_Pay_DatabaseAdapter {

    UserPayDataBaseHelper helper;


    public User_Pay_DatabaseAdapter(Context context){
        helper=new UserPayDataBaseHelper(context);

    }

    public long insert(User_Transaction user_transaction){


        ContentValues contentValues=new ContentValues();


        contentValues.put(UserPayDataBaseHelper.NAME_PAY_COL_NAME,user_transaction.getUserName());
        contentValues.put(UserPayDataBaseHelper.AMOUNT_PAY_COL_NAME,user_transaction.getAmount());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        contentValues.put(UserPayDataBaseHelper.DATE_PAY_COL_NAME,dateFormat.format(user_transaction.getDate()));



        SQLiteDatabase db=helper.getWritableDatabase();
        //row =row id that inserted
        long row=db.insert(UserPayDataBaseHelper.TABLE_NAME,null,contentValues);

        return  row;

    }


    public int  updateAmount(User_Transaction user_transaction){

        SQLiteDatabase db=helper.getWritableDatabase();

        ContentValues contentValues=new ContentValues();


        contentValues.put(UserPayDataBaseHelper.NAME_PAY_COL_NAME,user_transaction.getUserName());
        contentValues.put(UserPayDataBaseHelper.AMOUNT_PAY_COL_NAME,user_transaction.getAmount());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        contentValues.put(UserPayDataBaseHelper.DATE_PAY_COL_NAME,dateFormat.format(user_transaction.getDate()));



        String[] whereArgs={user_transaction.getUserName()};
        return db.update(UserPayDataBaseHelper.TABLE_NAME,contentValues,UserPayDataBaseHelper.NAME_PAY_COL_NAME+" = ? ",whereArgs);
        //0=no update,else = number of row affected
    }

    public int deleteRow(String name){


        SQLiteDatabase db=helper.getWritableDatabase();
        String[] whereArgs={name};

        //return number of row affected
        return  db.delete(UserPayDataBaseHelper.TABLE_NAME,UserPayDataBaseHelper.NAME_PAY_COL_NAME+" = ? ",whereArgs);

    }

    public ArrayList<User_Transaction> getDataByName(String userName) throws ParseException {
        SQLiteDatabase sqLiteDatabase=helper.getWritableDatabase();


        String[] columns={UserPayDataBaseHelper.NAME_PAY_COL_NAME, UserPayDataBaseHelper.AMOUNT_PAY_COL_NAME,UserPayDataBaseHelper.DATE_PAY_COL_NAME};//column to select
        Cursor cursor=sqLiteDatabase.query(UserPayDataBaseHelper.TABLE_NAME,columns, UserPayDataBaseHelper.NAME_PAY_COL_NAME+" = '"+userName+"'",null,null,null,null);


        ArrayList<User_Transaction> user_transactions=new ArrayList<>();

        while(cursor.moveToNext()){

            User_Transaction user_transaction=new User_Transaction();

            int col_id_index=cursor.getColumnIndex(UserPayDataBaseHelper.UID_COL_NAME);
            int id=cursor.getInt(col_id_index);



            int col_name_index=cursor.getColumnIndex(UserPayDataBaseHelper.NAME_PAY_COL_NAME);
            String name=cursor.getString(col_name_index);

            int col_amount_index=cursor.getColumnIndex(UserPayDataBaseHelper.AMOUNT_PAY_COL_NAME);
            double amount=cursor.getDouble(col_amount_index);


            int col_date_index=cursor.getColumnIndex(UserPayDataBaseHelper.DATE_PAY_COL_NAME);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
           Date date=dateFormat.parse(cursor.getString(col_date_index));



            user_transaction.setAmount(amount);
            user_transaction.setUserName(name);
            user_transaction.setDate(date);

            user_transactions.add(user_transaction);
        }

        return user_transactions;

    }




    public ArrayList<User_Transaction> getAllData() throws ParseException {


        SQLiteDatabase sqLiteDatabase=helper.getWritableDatabase();


        String[] columns={UserPayDataBaseHelper.UID_COL_NAME, UserPayDataBaseHelper.NAME_PAY_COL_NAME, UserPayDataBaseHelper.AMOUNT_PAY_COL_NAME,UserPayDataBaseHelper.DATE_PAY_COL_NAME};//column to select
        Cursor cursor=sqLiteDatabase.query(UserPayDataBaseHelper.TABLE_NAME,columns,null,null,null,null,null);


        ArrayList<User_Transaction> user_transactions=new ArrayList<>();

        while(cursor.moveToNext()){


            User_Transaction user_transaction=new User_Transaction();

            int col_id_index=cursor.getColumnIndex(UserPayDataBaseHelper.UID_COL_NAME);
            int id=cursor.getInt(col_id_index);



            int col_name_index=cursor.getColumnIndex(UserPayDataBaseHelper.NAME_PAY_COL_NAME);
            String name=cursor.getString(col_name_index);

            int col_amount_index=cursor.getColumnIndex(UserPayDataBaseHelper.AMOUNT_PAY_COL_NAME);
            double amount=cursor.getDouble(col_amount_index);


            int col_date_index=cursor.getColumnIndex(UserPayDataBaseHelper.DATE_PAY_COL_NAME);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date=dateFormat.parse(cursor.getString(col_date_index));



            user_transaction.setAmount(amount);
            user_transaction.setUserName(name);
            user_transaction.setDate(date);

            user_transactions.add(user_transaction);







        }

        return user_transactions;

    }



    //Database Helper class

    private  static  class UserPayDataBaseHelper extends SQLiteOpenHelper {


        //database schema declaraion
        private static final String  DATBASE_NAME="paymentManagement";
        private static final String  TABLE_NAME="User_Pay_Detail";
        private static  final int   DATBASE_VERSION=1;

        //col name
        private static final String  UID_COL_NAME="_id";
        private static final  String  NAME_PAY_COL_NAME="User_Name";
        private static  final String  AMOUNT_PAY_COL_NAME="Amount";
        private static  final String  DATE_PAY_COL_NAME="LastUpdate";


        private Context context;


        //query
        private static final String CREATE_USER_TABLE = "create table "+TABLE_NAME+" ( " +
                " "+UID_COL_NAME+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                " "+NAME_PAY_COL_NAME+" VARCHAR(150) NOT NULL UNIQUE ,"+
                " "+AMOUNT_PAY_COL_NAME+" DOUBLE NOT NULL,"+
                " "+DATE_PAY_COL_NAME+" DATE NOT NULL);";


        private static final  String  DROP_TABLE="DROP TABLE  IF EXISTS "+TABLE_NAME;
        @Override
        protected void finalize() throws Throwable {

        }

        public UserPayDataBaseHelper(Context context) {
            super(context, DATBASE_NAME, null, DATBASE_VERSION);
            this.context=context;
            Message.message(context,"Constructor Called");
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            Message.message(context,"onCreate Called");

            try{

                db.execSQL(CREATE_USER_TABLE);

            }catch (SQLException e){
                Message.message(context,""+e);
            }


        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            Message.message(context,"onUpgrade Called");


            try{

                db.execSQL(DROP_TABLE);
                onCreate(db);

            }catch (SQLException e){
                Message.message(context,""+e);
            }

        }
    }
}
