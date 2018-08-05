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




    LineChart chart;
    TextView title;
    TextView duration;
    TextView maxProd;
    TextView averageProd;
    ImageView machineLogo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_details);

        title = findViewById(R.id.statsDetailsTitle);
        duration = findViewById(R.id.statsDetailsDuration);
        maxProd = findViewById(R.id.statsDetailsMaxProd);
        averageProd = findViewById(R.id.statsDetailsAverageProd);
        machineLogo = findViewById(R.id.statsDetailsMachineLogo);

        chart = findViewById(R.id.statsChart);



        String date = getIntent().getStringExtra("date");
        String id = getIntent().getStringExtra("id");
        StatsFragment.MachineTypes type = StatsFragment.MachineTypes.valueOf(getIntent().getStringExtra("machine").toUpperCase());
        long duration = getIntent().getLongExtra("duration", 0);

        title.setText("Session du " + date);
        machineLogo.setImageDrawable(getDrawable(type.machineLogo));

        Map<String, Integer> time = StatCardHolder.millisecondToMinuteSeconds(duration);
        if (time.get("minutes") == 0) {
            this.duration.setText("Durée : " + time.get("seconds") + " secondes");
        }else{
            this.duration.setText("Durée : " + time.get("minutes") + "min" + time.get("seconds"));
        }


        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());


        final Map<String, Object> params = new HashMap<>();
        params.put(Constants.TOKEN, Prefs.getToken());
        params.put(Constants.SESSIONID, id);

        JsonObjectRequest request = new JsonObjectRequest(Constants.SERVER + Constants.GET_STATS_DETAILS, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            System.out.println("Response code : " + response.getString("code"));
                           // if (response.getString("code").equals("001")){

                                System.out.println();

                                List<Entry> entries = new ArrayList<>();

                                entries.add(new Entry(0, 10));
                                entries.add(new Entry(1, 15));
                                entries.add(new Entry(2, 23));
                                entries.add(new Entry(3, 35));
                                entries.add(new Entry(4, 24));


                                LineDataSet dataSet = new LineDataSet(entries, "Production");
                                LineData lineData = new LineData(dataSet);
                                lineData.setValueTextSize(12);

                                Description desc = new Description();
                                desc.setText("");


                                chart.setTouchEnabled(false);


                                IAxisValueFormatter wattsFormatter = new WattsValueFormater();
                                IAxisValueFormatter minFormatter = new MinValueFormater();

                                XAxis xAxis = chart.getXAxis();
                                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                                xAxis.setDrawGridLines(false);
                                xAxis.setGranularity(1f); // only intervals of 1 day
                                xAxis.setLabelCount(7);
                                xAxis.setValueFormatter(minFormatter);



                                YAxis leftAxis = chart.getAxisLeft();
                                leftAxis.setLabelCount(8, false);
                                leftAxis.setValueFormatter(wattsFormatter);
                                leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
                                leftAxis.setSpaceTop(15f);
                                leftAxis.setAxisMinimum(0f);




                                chart.setDescription(desc);
                                chart.setData(lineData);

                           // }
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



    public class WattsValueFormater implements IAxisValueFormatter
    {

        private DecimalFormat mFormat;

        public WattsValueFormater() {
            mFormat = new DecimalFormat("###,###,###,##0.0");
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return mFormat.format(value) + " Watts";
        }
    }

    public class MinValueFormater implements IAxisValueFormatter
    {

        private DecimalFormat mFormat;

        public MinValueFormater() {
            mFormat = new DecimalFormat("###,###,###,##0.0");
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return mFormat.format(value) + " Min";
        }
    }

}
