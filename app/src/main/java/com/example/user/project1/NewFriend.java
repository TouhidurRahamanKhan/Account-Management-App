package com.example.user.project1;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class NewFriend extends AppCompatActivity {

    private static final int SELECT_PHOTO = 1;
    static final int REQUEST_IMAGE_CAPTURE = 2;

    private ImageView imageView;
    private  Dialog dialog;
    private EditText name;
    private EditText phone;
    private  EditText email;
    private Bitmap userImage;


    private UserDatabaseAdapter userDatabaseAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_friend);
        imageView=(ImageView)findViewById(R.id.imageView);
        name=(EditText)findViewById(R.id.edName);
        phone=(EditText)findViewById(R.id.edPhone);
        email=(EditText)findViewById(R.id.edEmail);
        userDatabaseAdapter=new UserDatabaseAdapter(this);


    }

    public void addImage(View view){
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog_box);
        dialog.setTitle("Alert Dialog View");
        Button btnExit = (Button) dialog.findViewById(R.id.btnExit);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.btnChoosePath)
                .setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        activeGallery();
                    }
                });
        dialog.findViewById(R.id.btnTakePhoto)
                .setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        activeTakePhoto();
                    }
                });

        // show dialog on screen
        dialog.show();
    }

    private void activeTakePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    /**
     * to gallery
     */
    private void activeGallery() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SELECT_PHOTO) {

            if (resultCode == RESULT_OK  ) {
                if (data != null) {
                    // Get the URI of the selected file
                    final Uri uri = data.getData();
                    useImage(uri);
                }
            }
            super.onActivityResult(requestCode, resultCode, data);

        }

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

           // Message.message(this,"REQUEST_IMAGE_CAPTURE");
            dialog.dismiss();
            userImage=imageBitmap;
            imageView.setImageBitmap(imageBitmap);
           // mImageView.setImageBitmap(imageBitmap);
        }
    }

    void useImage(Uri uri)
    {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
           // Message.message(this,"useImage");
            dialog.dismiss();
            userImage=bitmap;

            imageView.setImageBitmap(bitmap);


        } catch (IOException e) {
            e.printStackTrace();
        }
        //use the bitmap as you like
       // imageView.setImageBitmap(bitmap);
    }

    public void submit(View view){

        String userName=name.getText().toString();
        String userPhone=phone.getText().toString();
        String userEmail=email.getText().toString();


        User user=new User(userName,userPhone,userEmail);



        FileOutputStream fileOutputStream=null;
        try {
            fileOutputStream=openFileOutput(userName+".png",MODE_PRIVATE);
            userImage.compress(Bitmap.CompressFormat.PNG,100,fileOutputStream);

        } catch (FileNotFoundException e) {
            //Message.message(this,"Error in image save");
        }
        finally{
            if (fileOutputStream!=null){
                try {
                   // Message.message(this,"saved at "+fileOutputStream.toString());
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        name.setText("");
        phone.setText("");
        email.setText("");
        imageView.setImageBitmap(null);

        long rs=userDatabaseAdapter.insert(user);
        if (rs>0){
            Message.message(this,"Saved");
        }

        //Log.d("royal",user.toString()+" RS:"+rs);

    }

    public void showAllData(View view) throws ParseException {

        ArrayList<User> users=userDatabaseAdapter.getAllData();
        for(User user:users){
           // Log.d("royal",user.toString());
        }

    }
}
