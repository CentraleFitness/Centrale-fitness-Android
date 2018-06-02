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

import com.fitness.centrale.centralefitness.R;
import com.fitness.centrale.centralefitness.fragments.event.BasicEventObject;
import com.fitness.centrale.centralefitness.fragments.event.EventCardsAdapter;
import com.fitness.centrale.centralefitness.newdesign.CenterActivity;

import java.util.ArrayList;

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


        itemsIdsList = new ArrayList<>();
        BasicSocialObject obj = new BasicSocialObject("0", this);
        itemsIdsList.add(obj);


        obj = new BasicSocialObject("1", this);
        itemsIdsList.add(obj);


        obj = new BasicSocialObject("2", this);
        itemsIdsList.add(obj);
        obj = new BasicSocialObject("3", this);
        itemsIdsList.add(obj);
        obj = new BasicSocialObject("4", this);
        itemsIdsList.add(obj);

        setAdapter();



    }

    private void setAdapter(){


        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(new SocialCardsAdapter(itemsIdsList,getBaseContext(), this));

    }
}
