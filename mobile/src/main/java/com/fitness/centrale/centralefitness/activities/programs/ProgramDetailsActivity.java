package com.fitness.centrale.centralefitness.activities.programs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fitness.centrale.centralefitness.R;
import com.fitness.centrale.centralefitness.activities.programs.run.program.RunProgramActivity;
import com.fitness.centrale.centralefitness.fragments.event.BasicEventObject;
import com.fitness.centrale.centralefitness.fragments.event.EventCardsAdapter;
import com.fitness.centrale.centralefitness.fragments.programs.ProgramCardsAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

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

        recyclerView = findViewById(R.id.programContentRecycler);
        startBtn = findViewById(R.id.startProgramButton);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemsIdsList = new ArrayList<>();
        itemsIdsList.add(new BasicActivityObject("1", "Vélo", getApplicationContext(), 10));
        itemsIdsList.add(new BasicActivityObject("1", "Repos", getApplicationContext(), 30));
        itemsIdsList.add(new BasicActivityObject("1", "Vélo", getApplicationContext(), 600));
        itemsIdsList.add(new BasicActivityObject("1", "Repos", getApplicationContext(), 30));

        recyclerView.setAdapter(new ProgramActivityCardsAdapter(itemsIdsList, getApplicationContext(), this));

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
