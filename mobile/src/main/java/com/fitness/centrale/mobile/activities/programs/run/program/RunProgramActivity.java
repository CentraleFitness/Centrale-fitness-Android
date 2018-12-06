package com.fitness.centrale.mobile.activities.programs.run.program;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fitness.centrale.misc.AlertDialogBuilder;
import com.fitness.centrale.misc.ImageUtility;
import com.fitness.centrale.mobile.R;
import com.fitness.centrale.mobile.activities.NFCScanActivity;
import com.fitness.centrale.mobile.activities.profile.ProfileActivity;
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
    TextView activityName;
    TextView activityDuration;
    LinearLayout noMoreActivityLyt;
    LinearLayout nextActivityLyt;
    LinearLayout quitButton;

    int minutes;
    int seconds;

    Decounter decounter;

    @Override
    public void onBackPressed() {
    }

    final ArrayList<BasicActivityObject.MinimalActivityObject> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_program);


        title = findViewById(R.id.activityTitle);
        counter = findViewById(R.id.timeCounter);
        skipBtn = findViewById(R.id.skipButton);
        logo = findViewById(R.id.activityLogo);
        skipText = findViewById(R.id.skipText);
        activityName = findViewById(R.id.activityName);
        activityDuration = findViewById(R.id.activityDuration);
        noMoreActivityLyt = findViewById(R.id.noMoreActivityLyt);
        nextActivityLyt = findViewById(R.id.nextActivityLyt);
        quitButton = findViewById(R.id.quitButton);

        ArrayList<String> arrayJson = getIntent().getStringArrayListExtra("array");


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

        final BasicActivityObject.MinimalActivityObject obj = list.get(0);
        if (list.size() > 1) {
            nextActivityLyt.setVisibility(View.VISIBLE);
            BasicActivityObject.MinimalActivityObject nextObj = list.get(1);
            new B64ToImageTask(nextObj.logo, logo).execute();
            this.activityName.setText(nextObj.name);
            if (nextObj.duration > 60){

                double initialTime = nextObj.duration;

                double time = initialTime / 60;
                double seconds = (Math.floor((time % 1) * 10) / 10) * 60;

                int finalMinutes = (int) Math.floor(time);

                activityDuration.setText(String.valueOf(finalMinutes) + " minutes " + ((int) seconds == 0 ? "" : String.valueOf((int)seconds)));

            }else{
                activityDuration.setText(String.valueOf(nextObj.duration) + " secondes");
            }
        } else {
            noMoreActivityLyt.setVisibility(View.VISIBLE);
        }

        title.setText(obj.name);

        if (obj.duration > 60){

            double initialTime = obj.duration;

            double time = initialTime / 60;
            double seconds = (Math.floor((time % 1) * 10) / 10) * 60;

            minutes = (int) Math.floor(time);
            this.seconds = (int) seconds;

            counter.setText(String.valueOf(minutes) + ":" + String.valueOf(this.seconds));

        }else{

            this.minutes = 0;
            this.seconds = obj.duration;

            counter.setText(String.valueOf(obj.duration) + " secondes");
        }

        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RunProgramActivity.this);
                builder.setTitle("Arrêter le programme personnalisé ?")
                        .setMessage("Si vous quittez le programme maintenant, vous devrez le refaire en entier. Confirmer ?")
                        .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                RunProgramActivity.super.onBackPressed();
                                finish();
                            }
                        })
                .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        if (obj.isModule) {
            this.skipText.setText("Appairage");
            this.skipBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(RunProgramActivity.this, NFCScanActivity.class);
                    intent.putExtra("fromProgram", true);
                    intent.putExtra("duration", obj.duration);
                    startActivityForResult(intent, 1);
                }
            });
        }else {

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

                            for (BasicActivityObject.MinimalActivityObject obj : list) {
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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



    private class B64ToImageTask extends AsyncTask{

        final String pictureB64;
        final ImageView imageView;

        public B64ToImageTask(final String pictureB64, final ImageView imageView){
            this.pictureB64 = pictureB64;
            this.imageView = imageView;
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            String []splitted = pictureB64.split(",");
            if (splitted.length == 2){
                return ImageUtility.base64ToImage(splitted[1]);
            }



            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            if (result != null) {
                logo.setImageBitmap((Bitmap) result);
            }
        }

    }

}
