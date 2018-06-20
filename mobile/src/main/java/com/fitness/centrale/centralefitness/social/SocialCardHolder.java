package com.fitness.centrale.centralefitness.social;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fitness.centrale.centralefitness.Constants;
import com.fitness.centrale.centralefitness.Prefs;
import com.fitness.centrale.centralefitness.R;
import com.fitness.centrale.centralefitness.store.Store;

import org.json.JSONArray;
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

    public SocialCardHolder(View itemView, Context context, AppCompatActivity parent) {
        super(itemView);
        this.context = context;
        this.parent = parent;


        postContent = itemView.findViewById(R.id.postContent);
        postDate = itemView.findViewById(R.id.postDate);
        lyt = itemView.findViewById(R.id.postMainLayout);



    }




    public void bind(final BasicSocialObject myObject){

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
                                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy hh:mm");
                                String dateStr = format.format(date);


                                postContent.setText(content);
                                //int lines = postContent.getLineCount();

                                //ViewGroup.LayoutParams params = lyt.getLayoutParams();

                                //int size = 100 + (lines * 23);

                                //Resources r = parent.getResources();
                                //params.height = Math.round(TypedValue.applyDimension(
                                //        TypedValue.COMPLEX_UNIT_DIP, size,r.getDisplayMetrics())); //size;
                                //lyt.setLayoutParams(params);

                                SocialCardHolder.this.postDate.setText(dateStr);
                                System.out.println();

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



