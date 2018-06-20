package com.fitness.centrale.centralefitness.activities.programs.run.program;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.fitness.centrale.centralefitness.R;
import com.fitness.centrale.centralefitness.newdesign.CenterActivity;

public class ProgramEndingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_ending);

        LinearLayout lyt = findViewById(R.id.submitButton);

        lyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(ProgramEndingActivity.this, CenterActivity.class);
                ProgramEndingActivity.super.onBackPressed();
                finish();
            }
        });



    }
}
