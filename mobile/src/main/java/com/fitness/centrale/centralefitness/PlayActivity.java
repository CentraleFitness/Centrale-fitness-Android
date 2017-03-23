package com.fitness.centrale.centralefitness;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

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
        gauge.setValue((int)(gaugeValue * 100));
        threadCanRun = true;
        final TextView txt = (TextView) findViewById(R.id.PlayValue);
        txt.setText("0.OW");
        new UpdateGaugeThread().start();
    }





    private class UpdateGaugeThread extends Thread{

        InstantWattRequest request = new InstantWattRequest();

        @Override
        public void run() {
            while (threadCanRun) {
                ResponseObject obj = request.prepareRequest();
                if (!obj.isAnError()) {
                    try {
                        gaugeValue = Double.valueOf(obj.get("instant watt"));
                    }catch (NullPointerException e){
                        e.printStackTrace();
                    }
                    final CustomGauge gauge = (CustomGauge) findViewById(R.id.gauge1);
                    gauge.post(new Runnable() {
                        @Override
                        public void run() {
                            gauge.setValue((int)(gaugeValue * 100));
                        }
                    });
                    final TextView txt = (TextView) findViewById(R.id.PlayValue);
                    txt.post(new Runnable() {
                        @Override
                        public void run() {
                            txt.setText(String.valueOf(gaugeValue) + "W");
                        }
                    });

                }
            }
        }
    }

}
