package com.fitness.centrale.mobile.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fitness.centrale.mobile.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ChallengeActivity extends AppCompatActivity {


    String title;
    String desc;
    String machine;
    String endDate;
    int pointsNeeded;
    List<Integer> steps;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);

        Bundle bundle = this.getIntent().getExtras();
        this.title = bundle.getString("title");
        this.desc = bundle.getString("desc");
        this.machine = bundle.getString("machine");
        this.endDate = bundle.getString("endDate");
        this.type = bundle.getString("type");
        this.pointsNeeded = Integer.parseInt(bundle.getString("pointsNeeded"));
        String steps = bundle.getString("steps");
        Type listType = new TypeToken<ArrayList<Integer>>(){}.getType();
        this.steps = new Gson().fromJson(steps, listType);
    }
}
