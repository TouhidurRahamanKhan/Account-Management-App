package com.example.user.project1;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class UserListActivity extends AppCompatActivity {

    int count=0;
    private String condition="";
    private List<User> userList = new ArrayList<>();
    private RecyclerView recyclerView;
    private UserListAdapter mAdapter;
    private  UserDatabaseAdapter userDatabaseAdapter;

    public static final String USERTAG="user";
    public static final String BORROW_CONDITION="borrow";
    public static final String PAY_CONDITION="pay";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        condition=getIntent().getStringExtra(MainActivity.CONDITION);
        //Message.message(getApplicationContext(),"COND:"+condition);
        // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        userDatabaseAdapter=new UserDatabaseAdapter(this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        prepareUserData();
        mAdapter = new UserListAdapter(userList,this);
        count=1;
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

               // Message.message(getApplicationContext(),"Called 2");
                User user=userList.get(position);
                Intent intent=new Intent(getApplicationContext(),UserDetail.class);

                Bundle mBundle = new Bundle();
               mBundle.putString(USERTAG,user.getName());


                intent.putExtras(mBundle);
               // Toast.makeText(getApplicationContext(), user.toString(), Toast.LENGTH_SHORT).show();
                startActivity(intent);


            }

            @Override
            public void onLongClick(View view, final int position) {

               // Message.message(getApplicationContext(),"Called");
              final Dialog  dialog = new Dialog(UserListActivity.this);
                dialog.setContentView(R.layout.list_item_action_dialog);
                dialog.setTitle("Select Action of Selected Item");
                Button btnExit = (Button) dialog.findViewById(R.id.btnCancel);
                btnExit.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.findViewById(R.id.btnDelete)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override public void onClick(View v) {
                                //activeGallery();
                                //Message.message(getApplicationContext(),"Click");
                                User user=userList.get(position);
                                int rs=userDatabaseAdapter.deleteRow(user.getName());

                                if (rs>0){
                                    dialog.dismiss();
                                   // Message.message(getApplicationContext(),"Calling5");
                                    mAdapter.removeItem(position);
                                    Message.message(getApplicationContext(),"Deleted");




                                }
                            }
                        });
                dialog.findViewById(R.id.btnUpdate)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override public void onClick(View v) {
                                dialog.dismiss();
                                count=2;
                                Intent intent=new Intent(UserListActivity.this,UpdateFriend.class);
                                Bundle bundle=new Bundle();
                                bundle.putParcelable(UserListActivity.USERTAG,userList.get(position));
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });

                // show dialog on screen
                dialog.show();

            }
        }));


    }

    private void prepareUserData() {



        try {
            if (condition.equalsIgnoreCase(UserListActivity.PAY_CONDITION)){
                userList =userDatabaseAdapter.getAllPayData();
                //Message.message(getApplicationContext(),"borrow");

            }
           else if (condition.equalsIgnoreCase(UserListActivity.BORROW_CONDITION)){
                userList =userDatabaseAdapter.getAllBorrowData();
               // Message.message(getApplicationContext(),"borrow");

            }
            else {
                userList = userDatabaseAdapter.getAllData();
                //Message.message(getApplicationContext(),"All");

            }


        } catch (ParseException e) {
            e.printStackTrace();
        }



    }

    @Override
    protected void onStart() {
        super.onStart();
        if (count==2){
            try {
                if (condition.equalsIgnoreCase(UserListActivity.PAY_CONDITION)){
                    userList =userDatabaseAdapter.getAllPayData();
                    Message.message(getApplicationContext(),"borrow");
                    mAdapter.updateList(userList);

                }
                else if (condition.equalsIgnoreCase(UserListActivity.BORROW_CONDITION)){
                    userList =userDatabaseAdapter.getAllBorrowData();
                     Message.message(getApplicationContext(),"borrow");
                    mAdapter.updateList(userList);

                }
                else {
                    userList = userDatabaseAdapter.getAllData();
                    Message.message(getApplicationContext(),"All...");
                    mAdapter.updateList(userList);

                }


            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
