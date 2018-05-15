package com.fitness.centrale.centralefitness.fragments.event;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.fitness.centrale.centralefitness.Constants;
import com.fitness.centrale.centralefitness.Prefs;
import com.fitness.centrale.centralefitness.R;
import com.fitness.centrale.centralefitness.VolleyUtility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EventCardHolder extends RecyclerView.ViewHolder {

    private TextView title;

    public EventCardHolder(View itemView) {
        super(itemView);

        title = itemView.findViewById(R.id.cardEventTitle);
    }

    public void bind(BasicEventObject myObject){
        title.setText(myObject.name);



        RequestQueue queue = Volley.newRequestQueue(myObject.context);



        Map<String, Object> params = new HashMap<>();
        params.put(Constants.TOKEN, Prefs.getToken());
        itemsIdsList = new ArrayList<BasicEventObject>();
        params.put(Constants.START, 0);
        params.put(Constants.END, 10);
        JsonObjectRequest request = new JsonObjectRequest(Constants.SERVER + Constants.GET_EVENTS_IDS, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    System.out.println(response.getString("code"));
                    if (response.getString("code").equals("001")){


                        JSONArray events = (JSONArray) response.get("events");
                        int index  = 0;

                        while (index != events.length()){

                            JSONArray subArray = events.getJSONArray(index);

                            itemsIdsList.add(new BasicEventObject(subArray.getString(1), subArray.getString(0)));

                            index++;

                        }

                        setListAdapter();


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        VolleyUtility.fixDoubleRequests(request);

        queue.add(request);



    }
}
