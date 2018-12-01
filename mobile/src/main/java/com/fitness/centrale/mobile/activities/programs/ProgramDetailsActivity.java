package com.fitness.centrale.mobile.activities.programs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fitness.centrale.misc.Constants;
import com.fitness.centrale.misc.Prefs;
import com.fitness.centrale.misc.VolleyUtility;
import com.fitness.centrale.misc.store.Store;
import com.fitness.centrale.mobile.R;
import com.fitness.centrale.mobile.activities.programs.run.program.RunProgramActivity;
import com.fitness.centrale.mobile.fragments.event.BasicEventObject;
import com.fitness.centrale.mobile.fragments.event.EventCardsAdapter;
import com.fitness.centrale.mobile.fragments.programs.ProgramCardsAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProgramDetailsActivity extends AppCompatActivity {

    private List<BasicActivityObject> itemsIdsList;
    private RecyclerView recyclerView;
    private LinearLayout startBtn;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program);

        TextView title = findViewById(R.id.programTitle);

        title.setText(getIntent().getStringExtra("name"));
        String id  = getIntent().getStringExtra("id");

        recyclerView = findViewById(R.id.programContentRecycler);
        startBtn = findViewById(R.id.startProgramButton);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemsIdsList = new ArrayList<>();

        RequestQueue queue = Volley.newRequestQueue(this);



        Map<String, Object> params = new HashMap<>();
        params.put(Constants.TOKEN, Prefs.getPrefs(this).getToken());
        params.put(Constants.CUSTOMPROGRAMID, id);
        params.put(Constants.GYMID, Store.getStore().getUserObject().gymId);
        final JsonObjectRequest request = new JsonObjectRequest(Constants.SERVER + Constants.GET_CUSTOM_PROGRAM_STEPS, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(final JSONObject response) {
                try {
                    System.out.println(response.getString("code"));
                    if (response.getString("code").equals("001")){
                        JSONArray steps = response.getJSONArray("steps");
                        for (int i = 0; i < steps.length(); i++) {
                            JSONObject step = steps.getJSONObject(i);
                            String name = step.getString("name");
                            String time = step.getString("time");
                            String icon = step.getString("icon");
                            boolean isModule = step.getBoolean("is_module");
                            itemsIdsList.add(new BasicActivityObject("1", name, getApplicationContext(), Integer.parseInt(time), icon, isModule));
                        }
                        recyclerView.setAdapter(new ProgramActivityCardsAdapter(itemsIdsList, getApplicationContext(), ProgramDetailsActivity.this));
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


        //TODO : Bien le mettre dans la récupération du contenu du programme pour qu'on ai bien tout récupéré avant de débuter le programme
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProgramDetailsActivity.this, RunProgramActivity.class);


                ArrayList<String> jsons = new ArrayList<>();

                for (BasicActivityObject obj : itemsIdsList){
                    jsons.add(obj.toJson());
                }

                intent.putStringArrayListExtra("array", jsons);

                startActivity(intent);
                finish();
            }
        });

    }




}
