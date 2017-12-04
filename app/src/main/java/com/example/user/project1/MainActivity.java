package com.example.user.project1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.text.ParseException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

  public  static final String CONDITION="Condition";
    public  static final String PAY_CONDITION="pay";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void addFriend(View view){
        Intent intent=new Intent();
        intent.setClass(MainActivity.this,NewFriend.class);
        startActivity(intent);
    }

    public void showAll(View view){
        Intent intent=new Intent();
        Bundle bundle=new Bundle();
        bundle.putString(CONDITION,"");
        intent.putExtras(bundle);
        intent.setClass(MainActivity.this,UserListActivity.class);
        startActivity(intent);
    }

    public void showBorrowList(View view){
        Intent intent=new Intent();
        Bundle bundle=new Bundle();
        bundle.putString(CONDITION,UserListActivity.BORROW_CONDITION);
        intent.putExtras(bundle);
        intent.setClass(MainActivity.this,UserListActivity.class);
        startActivity(intent);
    }

    public void showPayList(View view){
        Intent intent=new Intent();
        Bundle bundle=new Bundle();
        bundle.putString(CONDITION,UserListActivity.PAY_CONDITION);
        intent.putExtras(bundle);
        intent.setClass(MainActivity.this,UserListActivity.class);
        startActivity(intent);
    }


}
