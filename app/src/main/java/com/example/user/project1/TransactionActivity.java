package com.example.user.project1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.user.project1.Message;
import com.example.user.project1.R;
import com.example.user.project1.User;
import com.example.user.project1.UserDatabaseAdapter;
import com.example.user.project1.UserDetail;
import com.example.user.project1.UserListActivity;

import java.util.Date;

public class TransactionActivity extends AppCompatActivity {

    private User user=null;
    private  boolean isPay=false;
    private  static final String borrowTransTitle="ENTER BORROW AMOUNT :";
    private  static final String payTransTitle="ENTER PAY AMOUNT :";
    private TextView tvTransactionTitle;
    private EditText edTransactionAmount;
    private UserDatabaseAdapter userDatabaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        user=getIntent().getParcelableExtra(UserListActivity.USERTAG);
        isPay=getIntent().getBooleanExtra(UserDetail.IS_PAY,false);
        //Message.message(getApplicationContext(),"Received:"+user.toString()+" TF:"+isPay);
        tvTransactionTitle=(TextView)findViewById(R.id.tvTransactionTitle);
        edTransactionAmount=(EditText)findViewById(R.id.edTransactionAmount);
        userDatabaseAdapter=new UserDatabaseAdapter(this);
        if (isPay){
            tvTransactionTitle.setText(payTransTitle);
        }
        else {
            tvTransactionTitle.setText(borrowTransTitle);
        }

    }

    public void save(View view){
        String name=user.getName();
        User userDb=null;
        String s=edTransactionAmount.getText().toString();

        try{
            double amount=Double.parseDouble(s);
            userDb=userDatabaseAdapter.getDataByName(name);

            if(userDb!=null ) {
               if (isPay){
                   double preAmount=userDb.getAmountPay();
                   userDb.setAmountPay(preAmount+amount);
                   userDb.setLastPayDate(new Date());
                   //Message.message(getApplicationContext(),"PAy....."+userDb.toString());
               }
                else {
                   double preAmount=userDb.getAmountBorrow();
                   userDb.setAmountBorrow(preAmount+amount);
                   userDb.setLastBorrowDate(new Date());
                   //Message.message(getApplicationContext(),"Borrow....."+userDb.toString());
               }

                int rs=userDatabaseAdapter.updateByName(userDb,userDb.getName());

                if (rs>0){
                    edTransactionAmount.setText("");
                    //Message.message(getApplicationContext(),"Saved.....");
                }

                else {
                   // Message.message(getApplicationContext(),"Not Saved.....");
                }
            }
            else{
                Message.message(getApplicationContext(),"User Not found");
            }
        }catch (Exception e){
            Message.message(getApplicationContext(),"Error.....");
        }

    }


}
