package com.fitness.centrale.centralefitness;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.renderscript.Element;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.BoxInsetLayout;
import android.support.wearable.view.DotsPageIndicator;
import android.support.wearable.view.GridViewPager;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
