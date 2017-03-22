package com.fitness.centrale.centralefitness;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.fitness.centrale.centralefitness.requests.InstantWattRequest;


import pl.pawelkleczkowski.customgauge.CustomGauge;

/**
 * Created by remy on 22/03/17.
 */

public class PlayActivity extends AppCompatActivity{


    double gaugeValue = 0;
    boolean threadCanRun = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        Toolbar bar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(bar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        bar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_home));
        bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                threadCanRun = false;
                Intent intent = new Intent(PlayActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                //What to do on back clicked
            }
        });




        CustomGauge gauge = (CustomGauge) findViewById(R.id.gauge1);
        System.out.println("gauge value : " + gaugeValue);
        gauge.setValue(200 + 60 * (int)gaugeValue);
        threadCanRun = true;
        new UpdateGaugeThread().start();
    }





    private class UpdateGaugeThread extends Thread{

        InstantWattRequest request = new InstantWattRequest();

        @Override
        public void run() {
            while (threadCanRun) {
                ResponseObject obj = request.prepareRequest();
                if (!obj.isAnError()) {
                    gaugeValue = Double.valueOf(obj.get("instant Watt"));
                    System.out.println("value : " + gaugeValue);
                    final CustomGauge gauge = (CustomGauge) findViewById(R.id.gauge1);
                    gauge.post(new Runnable() {
                        @Override
                        public void run() {
                            gauge.setValue(200 + 60 * (int)gaugeValue);
                        }
                    });

                }
            }
        }
    }

}
