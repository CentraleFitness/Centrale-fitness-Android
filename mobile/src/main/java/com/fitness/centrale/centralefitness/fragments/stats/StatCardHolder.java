package com.fitness.centrale.centralefitness.fragments.stats;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
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
import com.fitness.centrale.centralefitness.Prefs;
import com.fitness.centrale.centralefitness.R;
import com.fitness.centrale.centralefitness.fragments.StatsFragment;

import org.json.JSONException;
import org.json.JSONObject;

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

       /*
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
        });*/



        RequestQueue queue = Volley.newRequestQueue(context);


        final Map<String, Object> params = new HashMap<>();
        params.put(Constants.TOKEN, Prefs.getToken());
        params.put(Constants.SESSIONID, myObject.id);

        JsonObjectRequest request = new JsonObjectRequest(Constants.SERVER + Constants.GET_STATS_SESSION, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            System.out.println("Response code : " + response.getString("code"));
                            if (response.getString("code").equals("001")){

                                final long dateLong = response.getLong("date");
                                final Date dateObj = new Date(dateLong);

                                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
                                final String date = format.format(dateObj);
                                final long duration = response.getLong("duration");
                                String type = response.getString("type");


                                title.setText(String.valueOf(date));
                                picture.setImageDrawable(context.getDrawable(StatsFragment.MachineTypes.BIKE.machineLogo));
                                StatCardHolder.this.duration.setText(String.valueOf(duration) + " minutes");

                                cardView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        Intent intent = new Intent(parent, SessionDetailsActivity.class);

                                        intent.putExtra("date", date);
                                        intent.putExtra("machine", StatsFragment.MachineTypes.BIKE.machineName);
                                        intent.putExtra("duration", duration);
                                        intent.putExtra("id", myObject.id);

                                        Pair<View, String> p1 = Pair.create(cardView, "statsOpening");

                                        ActivityOptionsCompat options = ActivityOptionsCompat.
                                                makeSceneTransitionAnimation(parent, p1);

                                        context.startActivity(intent, options.toBundle());

                                    }
                                });



                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        queue.add(request);



    }








}



