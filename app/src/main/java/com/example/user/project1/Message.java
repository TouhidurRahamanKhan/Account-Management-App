package com.example.user.project1;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by USER on 6/25/2016.
 */
public class Message {

    public static  void message(Context context, String message){

        Toast.makeText(context,message, Toast.LENGTH_LONG).show();
    }
}
