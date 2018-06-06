package com.fitness.centrale.centralefitness.fragments.stats;

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
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fitness.centrale.centralefitness.Constants;
import com.fitness.centrale.centralefitness.EventDetailsActivity;
import com.fitness.centrale.centralefitness.ImageUtility;
import com.fitness.centrale.centralefitness.Prefs;
import com.fitness.centrale.centralefitness.R;
import com.fitness.centrale.centralefitness.VolleyUtility;
import com.fitness.centrale.centralefitness.fragments.StatsFragment;
import com.fitness.centrale.centralefitness.fragments.event.BasicEventObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class StatCardHolder extends RecyclerView.ViewHolder   {

    private Context context;
    private Activity parent;

    private TextView title;
    private TextView duration;
    private ImageView picture;
    private View cardView;


    public StatCardHolder(View itemView, Context context, Activity parent) {
        super(itemView);
        this.context = context;
        this.parent = parent;

        title = itemView.findViewById(R.id.cardStatDate);
        picture = itemView.findViewById(R.id.statPicture);
        duration = itemView.findViewById(R.id.cardStatDuration);
        cardView = itemView.findViewById(R.id.cardViewStat);


    }



    public void bind(final StatsFragment.StatObject myObject){
        title.setText(myObject.date);
        picture.setImageDrawable(context.getDrawable(myObject.device.machineLogo));
        duration.setText(String.valueOf(myObject.duration) + " minutes");

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(parent, SessionDetailsActivity.class);

                intent.putExtra("date", myObject.date);
                intent.putExtra("machine", myObject.device.machineName);
                intent.putExtra("duration", myObject.duration);

                Pair<View, String> p1 = Pair.create(cardView, "statsOpening");

                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(parent, p1);

                context.startActivity(intent, options.toBundle());

            }
        });



    }








}


