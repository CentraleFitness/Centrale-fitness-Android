package com.centrale.fitness;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.wearable.activity.WearableActivity;
import android.widget.TextView;

import pl.pawelkleczkowski.customgauge.CustomGauge;

public class MainActivity extends WearableActivity {




    double gaugeValue = 0;
    boolean threadCanRun = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        CustomGauge gauge = (CustomGauge) findViewById(R.id.gauge1);
        gauge.setValue((int)(gaugeValue * 100));
        threadCanRun = true;
        final TextView txt = (TextView) findViewById(R.id.wattValue);
        txt.setText("0.OW");
    }









}