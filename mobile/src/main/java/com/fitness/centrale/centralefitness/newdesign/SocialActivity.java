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

public class SocialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social);


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
}
