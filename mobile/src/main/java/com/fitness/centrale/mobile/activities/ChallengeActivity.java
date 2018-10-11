package com.fitness.centrale.mobile.activities;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fitness.centrale.misc.AlertDialogBuilder;
import com.fitness.centrale.mobile.R;
import com.fitness.centrale.mobile.fragments.challenges.Machines;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Mac;

public class ChallengeActivity extends AppCompatActivity {


    String title;
    String desc;
    String machine;
    String endDate;
    int pointsNeeded;
    ArrayList<Integer> steps;
    String type;
    Integer current;

    ImageView machineImg;
    TextView percentage;
    Button help;
    TextView medal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);

        machineImg = findViewById(R.id.machineImg);
        percentage = findViewById(R.id.percentageText);
        help = findViewById(R.id.helpButton);
        medal = findViewById(R.id.medal);

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
        this.current = bundle.getInt("current");

        Machines machines = Machines.getMachineEnum(this.machine);
        machineImg.setImageDrawable(getDrawable(machines.drawable));

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialogBuilder.createAlertDialog(ChallengeActivity.this, "Vos challenges", "Cette page de dialoque vous permet de voir où vous en êtes dans la progression de vos challenges." +
                        "Chaque étape est représentée par une couleur de médaille. Ces couleurs vont donc de bronze, argent, or puis platine. Vous pouvez également constater votre avancée jusqu'à" +
                        "la réussite du challenge.", "Ok").show();
            }
        });

        if (current != null){
            if (current > this.steps.get(0) && current < this.steps.get(1)){
                machineImg.setColorFilter(Color.argb(255, 80, 50, 20));
                percentage.setTextColor(Color.argb(255, 80, 50, 20));
                medal.setTextColor(Color.argb(255, 80, 50, 20));
                medal.setText("BRONZE");

            }else if (current > this.steps.get(1) && current < this.steps.get(2)){
                machineImg.setColorFilter(Color.argb(255, 192, 192, 192));
                percentage.setTextColor(Color.argb(255, 192, 192, 192));
                medal.setTextColor(Color.argb(255, 192, 192, 192));
                medal.setText("ARGENT");

            }else if (current > this.steps.get(2) && current < this.steps.get(3)){
                machineImg.setColorFilter(Color.argb(255, 255, 215, 0));
                percentage.setTextColor(Color.argb(255, 255, 215, 0));
                medal.setTextColor(Color.argb(255, 255, 215, 0));
                medal.setText("OR");

            }else if (current > this.steps.get(3)){
                machineImg.setColorFilter(Color.argb(255, 250, 240, 192));
                percentage.setTextColor(Color.argb(255, 250, 240, 192));
                medal.setTextColor(Color.argb(255, 250, 240, 192));
                medal.setText("PLATINE");

            }
            percentage.setText(this.current + "/" + this.pointsNeeded + "Watts");
        }



    }

    public static void main(String[] args) {
        System.out.println((100 - 50) / 50 * 100);
    }

}
