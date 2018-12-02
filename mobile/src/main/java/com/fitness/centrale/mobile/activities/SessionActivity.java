package com.fitness.centrale.mobile.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fitness.centrale.misc.AlertDialogBuilder;
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
    TextView textDuree;
    TextView textProdIm;
    TextView textCalorie;
    TextView textProdCum;
    Button stopSession;
    int maxGaugeValue = 500;
    LineChartView chart;
    List<PointValue> points = new ArrayList<PointValue>();
    LinkedList<Float> values = new LinkedList<>();
    LinkedList<Float> totalValues = new LinkedList<>();
    ProductionGetter getter;
    Float average = 0f;
    int count = 0;
    ImageView backArrow;
    float elecCrea = 0;
    int dureeSession = 0;
    int minutes = 0;
    float calorie = 0;

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


        AlertDialogBuilder.createAlertDialog(this, "Votre session sportive",
                "Ici vous pourrez constater votre production d'énergie en temps réel dans votre salle de sport et voir" +
                        "de courts diagrammes. N'hésitez pas à laisser des suggestion (page de profil) si vous avez de meilleures idées d'ergonomie!", "Ok").show();

        gauge = findViewById(R.id.gauge1);
        gauge.setEndValue(maxGaugeValue);
        sessionText = findViewById(R.id.SessionText);
        backArrow = findViewById(R.id.backArrow);
        stopSession = findViewById(R.id.stopSession);

        textDuree = findViewById(R.id.textDuree);

        stopSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getter= new ProductionGetter();
        getter.execute();


    }


    Thread TimeTread = new Thread() {
        @Override
        public void run() {

            while (!isInterrupted()) {

                try {
                    Thread.sleep(1000);  //1000ms = 1 sec

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            dureeSession++;
                            if (dureeSession >= 60) {
                                dureeSession = 0;
                                minutes++;
                            }
                            textDuree.setText("Duree : " + String.valueOf(minutes) + "min " + String.valueOf(dureeSession) + "sec");
                        }
                    });

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    };


    class ProductionGetter extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] objects) {

            while (canRun){

                if (Store.getStore().getDemoObject().demo){

                    Random r = new Random();
                    float random = 0 + r.nextFloat() * (5 - 0);
                    int convertedValue = (int) (random * 100);
                    if (maxGaugeValue < convertedValue){
                        maxGaugeValue = convertedValue;
                        gauge.setEndValue(maxGaugeValue);
                    }
                    elecCrea += random;
                    sessionText.setText(String.valueOf(random));
                    textProdIm.setText("Production: " + String.valueOf(random) +"W");
                    textProdCum.setText("Production totale: " + String.valueOf(elecCrea) +"W");
                    calorie = elecCrea / 3600 * 859;
                    textCalorie.setText(String.valueOf(calorie));
                    gauge.setValue(convertedValue);
                    values.add(random);
                    totalValues.add(random);
                    Prefs.getPrefs(getApplicationContext()).addTotal((int) random);
                    if (values.size() > 15){
                        values.removeFirst();
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

                    chart.setLineChartData(data);

                }else {
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

                                        double value = Double.parseDouble(yourList.get(yourList.size() - 1));
                                        Prefs.getPrefs(getApplicationContext()).addTotal((int) value);
                                        int convertedValue = (int) (value * 100);
                                        if (maxGaugeValue < convertedValue) {
                                            maxGaugeValue = convertedValue;
                                            gauge.setEndValue(maxGaugeValue);
                                        }
                                        sessionText.setText(String.valueOf(value));
                                        gauge.setValue(convertedValue);

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
                }


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
