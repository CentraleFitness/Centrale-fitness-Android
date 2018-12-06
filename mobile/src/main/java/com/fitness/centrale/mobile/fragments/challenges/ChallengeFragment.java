package com.fitness.centrale.mobile.fragments.challenges;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fitness.centrale.misc.Constants;
import com.fitness.centrale.misc.Prefs;
import com.fitness.centrale.mobile.R;
import com.fitness.centrale.mobile.fragments.stats.SessionDetailsActivity;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Psyycker on 19/11/2017.
 */

public class ChallengeFragment extends Fragment {

    int ampoulePerHour = 40;
    int tvLEDPerHour = 90;
    int consolePerHour = 137;
    int machineALaverPerHour = 1000;


    int total;

    ImageView ampoule;
    ImageView console;
    ImageView tv;
    ImageView washmachine;

    ImageView ampouleBtn;
    ImageView consoleBtn;
    ImageView tvBtn;
    ImageView washMachineBtn;

    TextView wattCounter;
    TextView textDesc;
    TextView defiTime;
    TextView percentageText;
    ProgressBar progressBar;

    List<ImageView> images;

    public void setSelected(int index) {
        for (int i = 0; i < images.size(); i++) {
            if (i == index) {
                images.get(i).setVisibility(View.VISIBLE);
            }else {
                images.get(i).setVisibility(View.INVISIBLE);
            }
        }
    }


    public void changeAmpouleState(boolean state) {
        if (!state) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
            );
            Resources r = getActivity().getResources();
            int px = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    10,
                    r.getDisplayMetrics()
            );
            params.setMargins(px, px, px, px);
            ampouleBtn.setLayoutParams(params);
            return;
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        Resources r = getActivity().getResources();
        int px = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                0,
                r.getDisplayMetrics()
        );
        params.setMargins(px, px, px, px);
        ampouleBtn.setLayoutParams(params);
        textDesc.setText("Ceci qui permet de faire fonctionner une ampoule durant");

        int time = 60 * total / ampoulePerHour;
        defiTime.setText(time > 60 ? time / 60 > 24 ? time  /60 / 24 + " jours !" : time  /60 + " heures !" : time + " minutes !");

    }

    public void changeTvState(boolean state) {
        if (!state) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
            );
            Resources r = getActivity().getResources();
            int px = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    10,
                    r.getDisplayMetrics()
            );
            params.setMargins(px, px, px, px);
            tvBtn.setLayoutParams(params);
            return;
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        Resources r = getActivity().getResources();
        int px = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                0,
                r.getDisplayMetrics()
        );
        params.setMargins(px, px, px, px);
        tvBtn.setLayoutParams(params);
        textDesc.setText("Ceci qui permet de faire fonctionner une télévision durant");

        int time = 60 * total / tvLEDPerHour;
        defiTime.setText(time > 60 ? time / 60 > 24 ? time  /60 / 24 + " jours !" : time  /60 + " heures !" : time + " minutes !");

    }

    public void changeConsoleState(boolean state) {
        if (!state) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
            );
            Resources r = getActivity().getResources();
            int px = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    10,
                    r.getDisplayMetrics()
            );
            params.setMargins(px, px, px, px);
            consoleBtn.setLayoutParams(params);
            return;
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        Resources r = getActivity().getResources();
        int px = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                0,
                r.getDisplayMetrics()
        );
        params.setMargins(px, px, px, px);
        consoleBtn.setLayoutParams(params);
        textDesc.setText("Ceci qui permet de faire fonctionner une console de jeu durant");

        int time = 60 * total / consolePerHour;
        defiTime.setText(time > 60 ? time / 60 > 24 ? time  /60 / 24 + " jours !" : time  /60 + " heures !" : time + " minutes !");


    }

    public void changeWashMachineState(boolean state) {
        if (!state) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
            );
            Resources r = getActivity().getResources();
            int px = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    10,
                    r.getDisplayMetrics()
            );
            params.setMargins(px, px, px, px);
            washMachineBtn.setLayoutParams(params);
            return;
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        Resources r = getActivity().getResources();
        int px = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                0,
                r.getDisplayMetrics()
        );
        params.setMargins(px, px, px, px);
        washMachineBtn.setLayoutParams(params);
        textDesc.setText("Ceci qui permet de faire fonctionner une machine à laver durant");

        int time = 60 * total / machineALaverPerHour;
        defiTime.setText(time > 60 ? time / 60 > 24 ? time  /60 / 24 + " jours !" : time  /60 + " heures !" : time + " minutes !");
    }

    public void selectDetails(int index) {
        changeAmpouleState(index == 0);
        changeTvState(index == 1);
        changeConsoleState(index == 2);
        changeWashMachineState(index == 3);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.challenge_fragment, container, false);

        ampoule = view.findViewById(R.id.ampoule);
        console = view.findViewById(R.id.console);
        tv = view.findViewById(R.id.tv);
        washmachine = view.findViewById(R.id.washmachine);
        images = new ArrayList<>();
        images.add(ampoule);
        images.add(tv);
        images.add(console);
        images.add(washmachine);

        percentageText = view.findViewById(R.id.percentage);

        ampouleBtn = view.findViewById(R.id.ampouleButton);
        ampouleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDetails(0);
            }
        });
        tvBtn = view.findViewById(R.id.tvbutton);
        tvBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDetails(1);
            }
        });
        consoleBtn = view.findViewById(R.id.consolebutton);
        consoleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDetails(2);
            }
        });
        washMachineBtn = view.findViewById(R.id.washbutton);
        washMachineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDetails(3);
            }
        });

        progressBar = view.findViewById(R.id.progressBar);

        wattCounter = view.findViewById(R.id.wattCounter);
        defiTime = view.findViewById(R.id.defiTime);

        textDesc = view.findViewById(R.id.textDesc);

        RequestQueue queue = Volley.newRequestQueue(getContext());


        final Map<String, Object> params = new HashMap<>();
        params.put(Constants.TOKEN, Prefs.getPrefs(getContext()).getToken());

        JsonObjectRequest request = new JsonObjectRequest(Constants.SERVER + Constants.GETTOTALPRODUCTION, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            System.out.println("Response code : " + response.getString("code"));
                            if (response.getString("code").equals("001")){

                                Double production = Double.valueOf(response.getString("production"));

                                total = (int) Math.round(production);

                                wattCounter.setText(total + " Watts !!");

                                selectDetails(0);

                                if (total > 50 && total < 100){
                                    setSelected(0);
                                    int percentage = total * 100 / 100;
                                    percentageText.setText(percentage + "%");
                                    progressBar.setProgress(percentage);
                                } else if (total > 100 && total < 250) {
                                    setSelected(1);
                                    int percentage = total * 100 / 250;
                                    percentageText.setText(percentage + "%");
                                    progressBar.setProgress(percentage);
                                } else if (total > 250 && total < 500) {
                                    setSelected(2);
                                    int percentage = total * 100 / 500;
                                    percentageText.setText(percentage + "%");
                                    progressBar.setProgress(percentage);
                                } else if (total > 500) {
                                    setSelected(3);
                                    int percentage = total * 100 / 2000;
                                    if (percentage > 100) {
                                        percentageText.setVisibility(View.INVISIBLE);
                                        progressBar.setVisibility(View.INVISIBLE);
                                    }
                                    percentageText.setText(percentage + "%");
                                    progressBar.setProgress(percentage);
                                } else {
                                    int percentage = total * 100 / 50;
                                    percentageText.setText(percentage + "%");
                                    progressBar.setProgress(percentage);
                                }

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
        return view;


    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }


    //Ici, remplacer les int et autres par les vrais arguments de la map
    public static ChallengeFragment newInstance() {
        ChallengeFragment fragment = new ChallengeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }



}
