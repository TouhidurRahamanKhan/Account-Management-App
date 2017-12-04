package com.example.user.project1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.ParseException;

public class UserDetail extends AppCompatActivity {

    public  static  final String IS_PAY="isPAY";
    private boolean isPayTransaction=false;
    private User user=null;
   private int count;
    private TextView tvName,tvPay,tvBorrow,tvPayDate,tvBorrowDate;
    private UserDatabaseAdapter userDatabaseAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        userDatabaseAdapter=new UserDatabaseAdapter(this);
        String name=getIntent().getStringExtra(UserListActivity.USERTAG);
        count=2;
        try {
            user=userDatabaseAdapter.getDataByName(name);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        init();

       // Message.message(getApplicationContext(),"Found User:"+user.toString());
    }

    private void init() {
        tvName=(TextView)findViewById(R.id.tvUserName);
        tvPay=(TextView)findViewById(R.id.tvUserPay);
        tvBorrow=(TextView)findViewById(R.id.tvUserBorrow);
        tvPayDate=(TextView)findViewById(R.id.tvUserPayLastDate);
        tvBorrowDate=(TextView)findViewById(R.id.tvUserBorrowLastDate);


        if (user!=null){
            tvName.setText(user.getName());
            tvPay.setText(String.valueOf(user.getAmountPay()));
            tvBorrow.setText(String.valueOf(user.getAmountBorrow()));
            tvPayDate.setText(user.getLastPayDate().toString());
            tvBorrowDate.setText(user.getLastBorrowDate().toString());


        }
    }

    public void payTransaction(View view){

        Intent intent=new Intent(getApplicationContext(),TransactionActivity.class);
        isPayTransaction=true;
        Bundle mBundle = new Bundle();

        mBundle.putParcelable(UserListActivity.USERTAG, user);
        mBundle.putBoolean(UserDetail.IS_PAY,isPayTransaction);

        count=1;
        intent.putExtras(mBundle);
        startActivity(intent);

    }

    public void borrowTransaction(View view){

        Intent intent=new Intent(getApplicationContext(),TransactionActivity.class);
        isPayTransaction=false;
        Bundle mBundle = new Bundle();

        mBundle.putParcelable(UserListActivity.USERTAG, user);
        mBundle.putBoolean(UserDetail.IS_PAY,isPayTransaction);
        count=1;
        intent.putExtras(mBundle);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
       // Message.message(getApplicationContext(),"ONStart:"+count);
        if (count==1){
            try {
                user=userDatabaseAdapter.getDataByName(user.getName());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (user!=null){
                tvName.setText(user.getName());
                tvPay.setText(String.valueOf(user.getAmountPay()));
                tvBorrow.setText(String.valueOf(user.getAmountBorrow()));
                tvPayDate.setText(user.getLastPayDate().toString());
                tvBorrowDate.setText(user.getLastBorrowDate().toString());


            }

        }

    }


}
