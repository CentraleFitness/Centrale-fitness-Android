package com.fitness.centrale.centralefitness.social;

import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fitness.centrale.centralefitness.Constants;
import com.fitness.centrale.centralefitness.Prefs;
import com.fitness.centrale.centralefitness.R;
import com.fitness.centrale.centralefitness.fragments.event.BasicEventObject;
import com.fitness.centrale.centralefitness.fragments.event.EventCardsAdapter;
import com.fitness.centrale.centralefitness.newdesign.CenterActivity;
import com.fitness.centrale.centralefitness.store.Store;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SocialActivity extends AppCompatActivity {


    private ArrayList<BasicSocialObject> itemsIdsList;
    private View view;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social);

        recyclerView = findViewById(R.id.socialRecyclerView);
        swipeRefreshLayout = findViewById(R.id.SocialRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                //refreshEvents();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        refreshPosts();



        final ImageView sessionBtn = findViewById(R.id.sessionButton);
        final ImageView socialBtn = findViewById(R.id.socialButton);

        sessionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pair<View, String> p1 = Pair.create((View)sessionBtn, ViewCompat.getTransitionName(sessionBtn));
                Pair<View, String> p2 = Pair.create((View)socialBtn, ViewCompat.getTransitionName(socialBtn));

                Intent intent = new Intent(SocialActivity.this, CenterActivity.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(SocialActivity.this, p1, p2);
                startActivity(intent, options.toBundle());

            }
        });

    }

    private void refreshPosts(){



        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        final Map<String, Object> params = new HashMap<>();
        params.put(Constants.TOKEN, Prefs.getToken());
        params.put(Constants.TARGETID, Store.getStore().getUserObject().gymId);
        params.put(Constants.START, 0);
        params.put(Constants.END, 10);

        JsonObjectRequest request = new JsonObjectRequest(Constants.SERVER + Constants.GET_POSTS, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            System.out.println("Response code : " + response.getString("code"));
                            if (response.getString("code").equals("001")){



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




        itemsIdsList = new ArrayList<>();
        BasicSocialObject obj = new BasicSocialObject("0", this, BasicSocialObject.PostType.CLASSIC);
        itemsIdsList.add(obj);


        obj = new BasicSocialObject("1", this, BasicSocialObject.PostType.CLASSIC);
        itemsIdsList.add(obj);
        obj = new BasicSocialObject("2", this, BasicSocialObject.PostType.EVENT);
        itemsIdsList.add(obj);
        obj = new BasicSocialObject("3", this, BasicSocialObject.PostType.CLASSIC);
        itemsIdsList.add(obj);
        obj = new BasicSocialObject("4", this, BasicSocialObject.PostType.EVENT);
        itemsIdsList.add(obj);

        setAdapter();



    }

    private void setAdapter(){


        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(new SocialCardsAdapter(itemsIdsList,getBaseContext(), this));

    }
}
