package com.fitness.centrale.mobile.activities.programs.run.program;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fitness.centrale.mobile.R;
import com.fitness.centrale.mobile.activities.programs.BasicActivityObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class RunProgramActivity extends AppCompatActivity {

    TextView title;
    TextView counter;
    LinearLayout skipBtn;
    ImageView logo;
    TextView skipText;

    int minutes;
    int seconds;

    Decounter decounter;

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_program);


        title = findViewById(R.id.activityTitle);
        counter = findViewById(R.id.timeCounter);
        skipBtn = findViewById(R.id.skipButton);
        logo = findViewById(R.id.activityLogo);
        skipText = findViewById(R.id.skipText);

        ArrayList<String> arrayJson = getIntent().getStringArrayListExtra("array");



        final ArrayList<BasicActivityObject.MinimalActivityObject> list = new ArrayList<>();

        Gson gson = new GsonBuilder().create();

        for (String tmp : arrayJson){
            list.add(gson.fromJson(tmp, BasicActivityObject.MinimalActivityObject.class));
        }

        if (list.size() == 0){
            Intent intent = new Intent(RunProgramActivity.this, ProgramEndingActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        BasicActivityObject.MinimalActivityObject obj = list.get(0);

        title.setText(obj.name);

        if (obj.duration > 60){

            double initialTime = obj.duration;

            double time = initialTime / 60;
            double seconds = (Math.floor((time % 1) * 10) / 10) * 60;
            System.out.println(time);

            minutes = (int) Math.floor(time);
            this.seconds = (int) seconds;

            counter.setText(String.valueOf(minutes) + ":" + String.valueOf(this.seconds));

        }else{

            this.minutes = 0;
            this.seconds = obj.duration;

            counter.setText(String.valueOf(obj.duration) + " secondes");
        }


        if (obj.name.equals("Repos")){
            this.logo.setImageDrawable(getDrawable(R.drawable.rest_logo));
        }

        this.skipText.setText("Démarrer");

        this.skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decounter = new Decounter(RunProgramActivity.this, list);
                decounter.execute();
                skipText.setText("Passer");
                skipBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        decounter.cancel(true);

                        list.remove(0);
                        ArrayList<String> jsons = new ArrayList<>();

                        for (BasicActivityObject.MinimalActivityObject obj : list){
                            jsons.add(new GsonBuilder().create().toJson(obj));
                        }

                        Intent intent = new Intent(RunProgramActivity.this, RunProgramActivity.class);
                        intent.putStringArrayListExtra("array", jsons);
                        startActivity(intent);
                        finish();
                    }
                });

            }
        });


    }


    class Decounter extends AsyncTask{

        final AppCompatActivity appCompatActivity;
        final ArrayList<BasicActivityObject.MinimalActivityObject> arrayList;

        public Decounter(AppCompatActivity appCompatActivity, ArrayList<BasicActivityObject.MinimalActivityObject> arrayList){
            this.appCompatActivity = appCompatActivity;
            this.arrayList = arrayList;
        }

        @Override
        protected Object doInBackground(Object[] objects) {


            while (true){

                seconds--;
                if (seconds == -1){
                    System.out.println();

                    minutes--;


                    if (minutes == -1 && seconds == -1){

                        arrayList.remove(0);
                        ArrayList<String> jsons = new ArrayList<>();

                        for (BasicActivityObject.MinimalActivityObject obj : arrayList){
                            jsons.add(new GsonBuilder().create().toJson(obj));
                        }

                        Intent intent = new Intent(RunProgramActivity.this, RunProgramActivity.class);
                        intent.putStringArrayListExtra("array", jsons);
                        startActivity(intent);
                        finish();
                        return null;
                    }
                    seconds = 59;
                }

                if (minutes == 0){
                    counter.setText(String.valueOf(seconds) + " secondes");
                }else{
                    counter.setText(String.valueOf(minutes) + ":" + String.valueOf(seconds));
                }


                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return null;
                }
            }

        }
    }

}
