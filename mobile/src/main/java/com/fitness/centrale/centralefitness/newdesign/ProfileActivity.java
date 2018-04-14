package com.fitness.centrale.centralefitness.newdesign;

import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.fitness.centrale.centralefitness.R;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        final ImageView session = findViewById(R.id.sessionButton);
        final ImageView center = findViewById(R.id.profileButton);


        session.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Pair<View, String> p1 = Pair.create((View)session, ViewCompat.getTransitionName(session));
                Pair<View, String> p2 = Pair.create((View)center, ViewCompat.getTransitionName(center));

                Intent intent = new Intent(ProfileActivity.this, SessionActivity.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(ProfileActivity.this, p1, p2);
                startActivity(intent, options.toBundle());

            }
        });


    }
}
