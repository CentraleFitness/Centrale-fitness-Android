package com.fitness.centrale.mobile.activities.social;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fitness.centrale.misc.Constants;
import com.fitness.centrale.misc.Prefs;
import com.fitness.centrale.misc.store.Store;
import com.fitness.centrale.mobile.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SocialCardHolder extends RecyclerView.ViewHolder   {

    private Context context;
    private AppCompatActivity parent;


    private LinearLayout lyt;
    private TextView postContent;
    private TextView postDate;
    private TextView posterName;

    public SocialCardHolder(View itemView, Context context, AppCompatActivity parent) {
        super(itemView);
        this.context = context;
        this.parent = parent;


        postContent = itemView.findViewById(R.id.postContent);
        postDate = itemView.findViewById(R.id.postDate);
        posterName = itemView.findViewById(R.id.posterName);
        lyt = itemView.findViewById(R.id.postMainLayout);

    }


    public void setContent(final Date date, final String content){

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy hh:mm");
        String dateStr = format.format(date);

        postContent.setText(content);

        SocialCardHolder.this.postDate.setText(dateStr);
    }


    public void bind(final BasicSocialObject myObject){


        if (Store.getStore().getDemoObject().demo){

            posterName.setText(myObject.post.poster);
            setContent(myObject.post.postDate, myObject.post.content);

            return;
        }

        RequestQueue queue = Volley.newRequestQueue(context);


        final Map<String, Object> params = new HashMap<>();
        params.put(Constants.TOKEN, Prefs.getToken());
        params.put(Constants.POSTID, myObject.id);

        JsonObjectRequest request = new JsonObjectRequest(Constants.SERVER + Constants.GET_POST_CONTENT, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            System.out.println("Response code : " + response.getString("code"));
                            if (response.getString("code").equals("001")){

                                long postDate = response.getLong("post date");
                                Date date = new Date(postDate);
                                String content = response.getString("post content");

                                setContent(date, content);
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



