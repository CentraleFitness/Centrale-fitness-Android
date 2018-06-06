package com.fitness.centrale.centralefitness.social;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.fitness.centrale.centralefitness.R;

public class SocialCardHolder extends RecyclerView.ViewHolder   {

    private Context context;
    private AppCompatActivity parent;


    private TextView title;
    private ImageView eventPicture;
    private FrameLayout lyt;

    public SocialCardHolder(View itemView, Context context, AppCompatActivity parent) {
        super(itemView);
        this.context = context;
        this.parent = parent;


        //title = itemView.findViewById(R.id.cardEventTitle);
        eventPicture = itemView.findViewById(R.id.eventPicture);






    }




    public void bind(final BasicSocialObject myObject){






    }







}



