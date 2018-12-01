package com.fitness.centrale.mobile.fragments.stats;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fitness.centrale.misc.Constants;
import com.fitness.centrale.misc.Prefs;
import com.fitness.centrale.mobile.R;
import com.fitness.centrale.mobile.fragments.StatsFragment;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SessionDetailsActivity extends AppCompatActivity {




    TextView title;
    TextView duration;
    TextView maxProd;
    TextView averageProd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_details);

        title = findViewById(R.id.statsDetailsTitle);
        duration = findViewById(R.id.statsDetailsDuration);
        maxProd = findViewById(R.id.statsDetailsMaxProd);
        averageProd = findViewById(R.id.statsDetailsAverageProd);




        String date = getIntent().getStringExtra("date");
        String id = getIntent().getStringExtra("id");
        StatsFragment.MachineTypes type = StatsFragment.MachineTypes.valueOf(getIntent().getStringExtra("machine").toUpperCase());
        long duration = getIntent().getLongExtra("duration", 0);

        title.setText("Session du " + date);

        Map<String, Integer> time = StatCardHolder.millisecondToMinuteSeconds(duration);
        if (time.get("minutes") == 0) {
            this.duration.setText("Durée : " + time.get("seconds") + " secondes");
        }else{
            this.duration.setText("Durée : " + time.get("minutes") + " minutes");
        }


        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());


        final Map<String, Object> params = new HashMap<>();
        params.put(Constants.TOKEN, Prefs.getPrefs(this).getToken());
        params.put(Constants.SESSIONID, id);

        JsonObjectRequest request = new JsonObjectRequest(Constants.SERVER + Constants.GET_STATS_DETAILS, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            System.out.println("Response code : " + response.getString("code"));
                            if (response.getString("code").equals("001")){

                                List<Double> entries = new ArrayList<>();

                                double total = 0;
                                for (int i = 0; i < response.getJSONArray("production").length(); i++) {
                                    Double value = response.getJSONArray("production").getDouble(i);
                                    entries.add(value);
                                    total += value;
                                }

                                double average = total / entries.size();
                                maxProd.setText("Production Totale: " + Math.round(total * 100.0) / 100.0 + " Watts");
                                averageProd.setText("Production Moyenne: " + Math.round(average * 100.0) / 100.0 + " Watts");



                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        queue.add(request);





    }


}
