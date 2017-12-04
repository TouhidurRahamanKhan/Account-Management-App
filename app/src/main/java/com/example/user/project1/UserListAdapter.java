package com.example.user.project1;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;


public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.MyViewHolder> {
    private List<User> userList;

    private Activity activity;
    private Context context;
    public class MyViewHolder extends RecyclerView.ViewHolder {
       public ImageView img;
        public TextView title;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tvTitle);
            img=(ImageView)view.findViewById(R.id.img_single);
        }
    }

    public void updateList(List<User> data) {
        this.userList = data;
        notifyDataSetChanged();
    }
    public void addItem(int position,User user) {
        userList.add(position, user);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        userList.remove(position);
        notifyItemRemoved(position);
    }
    public UserListAdapter(List<User> userList, Context context) {
        this.userList = userList;

        //Log.d("royal","Total user:"+this.userList.size());
        activity= (Activity) context;
        this.context=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

         User user=userList.get(position);
       // Log.d("royal","Current User:"+user);
         holder.title.setText(user.getName());
        File file=new File(context.getFilesDir()+"/"+user.getName()+".png");

        String imagePath=file.getAbsolutePath();
         Bitmap bitmap=shrinkBitmap(imagePath,80,53);
       // holder.img.setImageBitmap(getImage(user.getName()));
        holder.img.setImageBitmap(bitmap);






    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
    public Bitmap getImage(String name){
        Bitmap bitmap=null;

        FileInputStream fileInputStream=null;
        try {
            fileInputStream=activity.openFileInput(name);
            bitmap= BitmapFactory.decodeStream(fileInputStream);

        } catch (FileNotFoundException e) {
            Message.message(activity,"File Not Found");
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
