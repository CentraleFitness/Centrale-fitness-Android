package com.fitness.centrale.centralefitness.fragments.stats;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.fitness.centrale.centralefitness.R;
import com.fitness.centrale.centralefitness.fragments.StatsFragment;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

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
        StatsFragment.MachineTypes type = StatsFragment.MachineTypes.valueOf(getIntent().getStringExtra("machine").toUpperCase());
        int duration = getIntent().getIntExtra("duration", 0);

        title.setText("Session du " + date);
        machineLogo.setImageDrawable(getDrawable(type.machineLogo));
        this.duration.setText("Durée : " + duration + " minutes");



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
