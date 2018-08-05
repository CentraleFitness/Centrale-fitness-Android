package com.fitness.centrale.mobile.fragments.stats;

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
import com.fitness.centrale.misc.Constants;
import com.fitness.centrale.misc.Prefs;
import com.fitness.centrale.mobile.R;
import com.fitness.centrale.mobile.fragments.StatsFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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

    public static Map<String, Integer> millisecondToMinuteSeconds(Long millisec){


        long initialTimeseconds = TimeUnit.MILLISECONDS.toSeconds(millisec);

        if (initialTimeseconds < 60){
            Map<String, Integer> map = new HashMap<>();
            map.put("minutes", 0);
            map.put("seconds", (int)initialTimeseconds);

            return map;

        }

        double time = (double)(initialTimeseconds / 60);
        double seconds = (Math.floor((time % 1) * 10) / 10) * 60;


        int minutes = (int) Math.floor(time);
        int finalseconds = (int) seconds;

        Map<String, Integer> result = new HashMap<>();
        result.put("minutes", minutes);
        result.put("seconds", finalseconds);

        return result;
    }

    public void bind(final StatsFragment.StatObject myObject){





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

                                final Long duration = response.getLong("duration");

                                Map<String, Integer> time = millisecondToMinuteSeconds(duration);




                                String type = response.getString("type");


                                title.setText(String.valueOf(date));
                                picture.setImageDrawable(context.getDrawable(StatsFragment.MachineTypes.BIKE.machineLogo));

                                if (time.get("minutes") == 0){
                                    StatCardHolder.this.duration.setText(time.get("seconds") + " secondes");
                                }else
                                StatCardHolder.this.duration.setText(time.get("minutes") + "min " + time.get("seconds"));

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



