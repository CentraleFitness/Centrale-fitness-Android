package com.fitness.centrale.mobile.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fitness.centrale.misc.Constants;
import com.fitness.centrale.misc.Prefs;
import com.fitness.centrale.misc.store.DemoObject;
import com.fitness.centrale.mobile.R;
import com.fitness.centrale.misc.VolleyUtility;
import com.fitness.centrale.misc.store.Store;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;
import pl.pawelkleczkowski.customgauge.CustomGauge;

public class SessionActivity extends AppCompatActivity {

    private boolean canRun = true;

    CustomGauge gauge;
    TextView sessionText;
    int maxGaugeValue = 500;
    LineChartView chart;
    List<PointValue> points = new ArrayList<PointValue>();
    LinkedList<Float> values = new LinkedList<>();
    LinkedList<Float> totalValues = new LinkedList<>();
    ProductionGetter getter;
    Float average = 0f;
    int count = 0;
    Button stopSession;
    TextView time;
    TextView total;
    TextView averageText;

    @Override
    public void onBackPressed() {


        final AlertDialog.Builder builder;

        builder = new AlertDialog.Builder(SessionActivity.this);
        builder.setTitle("Confirmer").setMessage("Voulez vous vraiment arrêter la session ?").setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {

                if(Store.getStore().getDemoObject().demo){
                    DemoObject.Session session = new DemoObject.Session();
                    session.values = values;
                    session.date = new Date().toString();
                    Store.getStore().getDemoObject().sessionList.add(session);
                    SessionActivity.super.onBackPressed();
                    dialog.dismiss();
                    getter.cancel(true);
                    canRun = false;
                    finish();

                }else{
                    RequestQueue queue = Volley.newRequestQueue(getApplication());


                    Map<String, String> params = new HashMap<>();
                    params.put(Constants.TOKEN, Prefs.getPrefs(SessionActivity.this).getToken());
                    JsonObjectRequest request = new JsonObjectRequest(Constants.SERVER + Constants.USER_PAIR_STOP, new JSONObject(params), new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if (response.getString("code").equals("001")){

                                    Store.getStore().setSession((int) (average / count));
                                    SessionActivity.super.onBackPressed();
                                    dialog.dismiss();
                                    getter.cancel(true);
                                    canRun = false;
                                    finish();


                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });

                    VolleyUtility.fixDoubleRequests(request);

                    queue.add(request);
                }


            }
        }).show();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);


        gauge = findViewById(R.id.gauge1);
        gauge.setEndValue(maxGaugeValue);
        sessionText = findViewById(R.id.SessionText);
        stopSession = findViewById(R.id.stopSession);
        time = findViewById(R.id.timeCounter);
        total = findViewById(R.id.totalCounter);
        averageText = findViewById(R.id.averageCounter);

        stopSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getter= new ProductionGetter();
        getter.execute();


    }




    class ProductionGetter extends AsyncTask{

        Handler handler = new Handler();

        @Override
        protected Object doInBackground(Object[] objects) {

            int timeCounter = 0;
            final double[] totalCounter = {0};
            final List<Double> allData = new ArrayList<>();

            while (canRun){

                RequestQueue queue = Volley.newRequestQueue(getApplication());
                Map<String, String> params = new HashMap<>();
                params.put(Constants.TOKEN, Prefs.getPrefs(SessionActivity.this).getToken());
                JsonObjectRequest request = new JsonObjectRequest(Constants.SERVER + Constants.GET_INSTANT_PRODUCTION, new JSONObject(params), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("code").equals("001")) {
                                Type listType = new TypeToken<List<String>>() {
                                }.getType();
                                List<String> yourList = new Gson().fromJson(response.getString("production"), listType);


                                if (yourList.size() > 0) {

                                    final double value = Double.parseDouble(yourList.get(yourList.size() - 1));

                                    totalCounter[0] += value;
                                    allData.add(value);


                                    final int convertedValue = (int) (value * 100);
                                    if (maxGaugeValue < convertedValue) {
                                        maxGaugeValue = convertedValue;
                                        gauge.setEndValue(maxGaugeValue);
                                    }

                                    handler.post(new Runnable(){
                                        public void run() {
                                            sessionText.setText(String.valueOf(value));
                                            gauge.setValue(convertedValue);
                                        }
                                    });

                                    for (String tmp : yourList) {

                                        Float number = Float.parseFloat(tmp);

                                        values.add(number);
                                        if (values.size() > 15) {
                                            values.removeFirst();
                                        }

                                    }

                                    points = new ArrayList<>();
                                    for (Float point : values) {
                                        average += point;
                                        count++;
                                        points.add(new PointValue(points.size(), point));

                                    }


                                    //In most cased you can call data model methods in builder-pattern-like manner.
                                    Line line = new Line(points).setColor(Color.BLUE).setCubic(true);
                                    List<Line> lines = new ArrayList<Line>();
                                    lines.add(line);

                                    LineChartData data = new LineChartData();
                                    data.setLines(lines);



                                }
                            } else {
                                SessionActivity.super.onBackPressed();
                                getter.cancel(true);
                                finish();
                                canRun = false;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

                VolleyUtility.fixDoubleRequests(request);

                queue.add(request);


                timeCounter++;
                final int MINUTES_IN_AN_HOUR = 60;
                final int SECONDS_IN_A_MINUTE = 60;

                final int seconds = timeCounter % SECONDS_IN_A_MINUTE;
                int totalMinutes = timeCounter / SECONDS_IN_A_MINUTE;
                final int minutes = totalMinutes % MINUTES_IN_AN_HOUR;

                handler.post(new Runnable(){
                    public void run() {
                        time.setText(minutes + " minutes " + seconds + " secondes");
                        total.setText(String.valueOf(Math.round(totalCounter[0] * 100.0) / 100.0));
                         averageText.setText(String.valueOf(Math.round(totalCounter[0] / allData.size() * 100.0) / 100.0));
                    }
                });

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }
    }


}
