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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class UpdateFriend extends AppCompatActivity {

    private static final int SELECT_PHOTO = 1;
    static final int REQUEST_IMAGE_CAPTURE = 2;

    private ImageView imageView;
    private  Dialog dialog;
    private EditText name;
    private EditText phone;
    private  EditText email;
    private  EditText payAmount;
    private  EditText borrowAmount;
    private Bitmap userImage;


    private UserDatabaseAdapter userDatabaseAdapter;
    private User user=null;
    private String userNameold="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_friend);
        imageView=(ImageView)findViewById(R.id.imageViewUpdate);
        name=(EditText)findViewById(R.id.edNameUpdate);
        phone=(EditText)findViewById(R.id.edPhoneUpdate);
        email=(EditText)findViewById(R.id.edEmailUpdate);
        payAmount=(EditText)findViewById(R.id.edPayAmount);
        borrowAmount=(EditText)findViewById(R.id.edBorrowAmount);
        userDatabaseAdapter=new UserDatabaseAdapter(this);
        user=getIntent().getParcelableExtra(UserListActivity.USERTAG);

        if (user!=null){
            userNameold=user.getName();
            name.setText(user.getName());
            phone.setText(user.getPhoneNumber());
            email.setText(user.getEmail());
            payAmount.setText(String.valueOf(user.getAmountPay()));
            borrowAmount.setText(String.valueOf(user.getAmountBorrow()));

            File file=new File(getFilesDir()+"/"+userNameold+".png");

            String imagePath=file.getAbsolutePath();

            userImage=shrinkBitmap(imagePath,80,53);
            imageView.setImageBitmap(userImage);
        }
    }

    public void addImageUpdate(View view){
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

    public void update(View view){
        //Message.message(this,"Updated calling");
        String userName=name.getText().toString();
        String userPhone=phone.getText().toString();
        String userEmail=email.getText().toString();
        String pay=payAmount.getText().toString();
        String borrow=borrowAmount.getText().toString();


        User user=new User(userName,userPhone,userEmail);
        user.setAmountPay(Double.parseDouble(pay));
        user.setAmountBorrow(Double.parseDouble(borrow));
        if (user.getLastBorrowDate()==null){
            user.setLastBorrowDate(new Date());
        }
        if (user.getLastPayDate()==null){
            user.setLastPayDate(new Date());
        }






        long rs=userDatabaseAdapter.updateByName(user,userNameold);
         if(rs>0){
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
           payAmount.setText("");
           borrowAmount.setText("");
             Message.message(this,"Updated");
       }
          else{
             Message.message(this,"Not Updated try again");
         }
        //Log.d("royal",user.toString()+" RS:"+rs);

    }

    public void showAllData(View view) throws ParseException {

        ArrayList<User> users=userDatabaseAdapter.getAllData();
        for(User user:users){
            // Log.d("royal",user.toString());
        }

    }

    public Bitmap getImage(String name){
        Bitmap bitmap=null;

        FileInputStream fileInputStream=null;
        try {
            fileInputStream=UpdateFriend.this.openFileInput(name);
            bitmap= BitmapFactory.decodeStream(fileInputStream);

        } catch (FileNotFoundException e) {
            Message.message(UpdateFriend.this,"File Not Found");
        }
        finally {
            if (fileInputStream!=null){
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return  bitmap;
    }

    Bitmap shrinkBitmap(String file, int width, int height){

        BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
        bmpFactoryOptions.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(file, bmpFactoryOptions);

        int heightRatio = (int)Math.ceil(bmpFactoryOptions.outHeight/(float)height);
        int widthRatio = (int)Math.ceil(bmpFactoryOptions.outWidth/(float)width);

        if (heightRatio > 1 || widthRatio > 1)
        {
            if (heightRatio > widthRatio)
            {
                bmpFactoryOptions.inSampleSize = heightRatio;
            } else {
                bmpFactoryOptions.inSampleSize = widthRatio;
            }
        }

        bmpFactoryOptions.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(file, bmpFactoryOptions);
        return bitmap;
    }
}
