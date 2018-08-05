package com.fitness.centrale.mobile.fragments.programs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fitness.centrale.misc.ImageUtility;
import com.fitness.centrale.mobile.R;
import com.fitness.centrale.mobile.activities.programs.ProgramDetailsActivity;

public class ProgramCardHolder extends RecyclerView.ViewHolder   {

    private Context context;
    private Activity parent;

    private CardView program;
    private TextView title;
    private ImageView eventPicture;

    public ProgramCardHolder(View itemView, Context context, Activity parent) {
        super(itemView);
        this.context = context;
        this.parent = parent;

        program = itemView.findViewById(R.id.cardViewProgram);
        title = itemView.findViewById(R.id.cardProgramTitle);
        //eventPicture = itemView.findViewById(R.id.programPicture);
    }



    public void bind(final BasicProgramObject myObject){
        title.setText(myObject.name);


        program.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(parent, ProgramDetailsActivity.class);

                intent.putExtra("name", myObject.name);
                parent.startActivity(intent);
            }
        });



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
                eventPicture.setImageBitmap((Bitmap) result);
                //eventPictureBitmap = (Bitmap) result;
            }
        }

    }






}



