package com.fitness.centrale.centralefitness.social;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fitness.centrale.centralefitness.R;

public class SocialCardHolder extends RecyclerView.ViewHolder   {

    private Context context;
    private Activity parent;


    private TextView title;
    private ImageView eventPicture;
    private LinearLayout lyt;

    public SocialCardHolder(View itemView, Context context, Activity parent) {
        super(itemView);
        this.context = context;
        this.parent = parent;



        title = itemView.findViewById(R.id.cardEventTitle);
        eventPicture = itemView.findViewById(R.id.eventPicture);
        lyt = itemView.findViewById(R.id.socialCardMainLayout);
    }



    public void bind(final BasicSocialObject myObject){
        title.setText(myObject.id);

        LayoutParams params = lyt.getLayoutParams();

        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 70, parent.getResources().getDisplayMetrics());

        params.height = height;

        lyt.setLayoutParams(params);




    }







}



