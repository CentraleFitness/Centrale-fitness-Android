package com.fitness.centrale.centralefitness.activities.programs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fitness.centrale.centralefitness.Constants;
import com.fitness.centrale.centralefitness.ImageUtility;
import com.fitness.centrale.centralefitness.Prefs;
import com.fitness.centrale.centralefitness.R;
import com.fitness.centrale.centralefitness.VolleyUtility;
import com.fitness.centrale.centralefitness.activities.EventDetailsActivity;
import com.fitness.centrale.centralefitness.fragments.event.BasicEventObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ProgramActivityCardHolder extends RecyclerView.ViewHolder   {

    private Context context;
    private Activity parent;

    private TextView name;
    private TextView duration;
    private ImageView logo;
    private Bitmap eventPictureBitmap;

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

        if (myObject.duration > 60){

            double initialTime = myObject.duration;

            double time = initialTime / 60;
            double seconds = (Math.floor((time % 1) * 10) / 10) * 60;
            System.out.println(time);

            int finalMinutes = (int) Math.floor(time);

            duration.setText(String.valueOf(finalMinutes) + " minutes " + ((int) seconds == 0 ? "" : String.valueOf((int)seconds)));

        }else{
            duration.setText(String.valueOf(myObject.duration) + " secondes");
        }


        if (myObject.name.equals("Repos")){
            this.logo.setImageDrawable(parent.getDrawable(R.drawable.rest_logo));
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
                //eventPicture.setImageBitmap((Bitmap) result);
                eventPictureBitmap = (Bitmap) result;
            }
        }

    }






}



