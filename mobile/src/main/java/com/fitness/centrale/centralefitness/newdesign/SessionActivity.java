package com.fitness.centrale.centralefitness.newdesign;

import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.fitness.centrale.centralefitness.NFCScanActivity;
import com.fitness.centrale.centralefitness.R;

public class SessionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session2);


        final ImageView profileBtn = findViewById(R.id.profileButton);
        final ImageView sessionBtn = findViewById(R.id.sessionButton);
        final ImageView socialBtn = findViewById(R.id.socialButton);
        final ImageView sessionStartBtn = findViewById(R.id.sessionStartBtn);

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pair<View, String> p1 = Pair.create((View)sessionBtn, ViewCompat.getTransitionName(sessionBtn));
                Pair<View, String> p2 = Pair.create((View)profileBtn, ViewCompat.getTransitionName(profileBtn));

                Intent intent = new Intent(SessionActivity.this, ProfileActivity.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(SessionActivity.this, p1, p2);
                startActivity(intent, options.toBundle());

            }
        });

        socialBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pair<View, String> p1 = Pair.create((View)sessionBtn, ViewCompat.getTransitionName(sessionBtn));
                Pair<View, String> p2 = Pair.create((View)socialBtn, ViewCompat.getTransitionName(socialBtn));

                Intent intent = new Intent(SessionActivity.this, SocialActivity.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(SessionActivity.this, p1, p2);
                startActivity(intent, options.toBundle());
            }
        });

        sessionStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SessionActivity.this, NFCScanActivity.class);
                startActivity(intent);

            }
        });


    }
}
