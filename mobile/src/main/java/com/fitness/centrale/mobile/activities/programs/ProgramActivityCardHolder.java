package com.fitness.centrale.mobile.activities.programs;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fitness.centrale.misc.ImageUtility;
import com.fitness.centrale.mobile.R;

public class ProgramActivityCardHolder extends RecyclerView.ViewHolder   {

    private Context context;
    private Activity parent;

    private TextView name;
    private TextView duration;
    private ImageView logo;
    private Bitmap activityPictureBitmap;

    public ProgramActivityCardHolder(View itemView, Context context, Activity parent) {
        super(itemView);
        this.context = context;
        this.parent = parent;


        name = itemView.findViewById(R.id.activityName);
        duration = itemView.findViewById(R.id.activityDuration);
        logo = itemView.findViewById(R.id.activityLogo);

    }



    public void bind(final BasicActivityObject myObject){

        this.name.setText(myObject.name);

        new B64ToImageTask(myObject.iconB64, logo).execute();


        if (myObject.duration > 60){

            double initialTime = myObject.duration;

            double time = initialTime / 60;
            double seconds = (Math.floor((time % 1) * 10) / 10) * 60;

            int finalMinutes = (int) Math.floor(time);

            duration.setText(String.valueOf(finalMinutes) + " minutes " + ((int) seconds == 0 ? "" : String.valueOf((int)seconds)));

        }else{
            duration.setText(String.valueOf(myObject.duration) + " secondes");
        }
    }



    private class B64ToImageTask extends AsyncTask{

        final String pictureB64;
        final ImageView imageView;

        public B64ToImageTask(final String pictureB64, final ImageView imageView){
            this.pictureB64 = pictureB64;
            this.imageView = imageView;
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            String []splitted = pictureB64.split(",");
            if (splitted.length == 2){
                return ImageUtility.base64ToImage(splitted[1]);
            }



            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            if (result != null) {
                activityPictureBitmap = (Bitmap) result;
                imageView.setImageBitmap(activityPictureBitmap);
            }
        }

    }






}



