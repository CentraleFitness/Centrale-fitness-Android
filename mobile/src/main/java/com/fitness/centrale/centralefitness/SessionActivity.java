package com.fitness.centrale.centralefitness;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SessionActivity extends AppCompatActivity {


    @Override
    public void onBackPressed() {


        final AlertDialog.Builder builder;

        builder = new AlertDialog.Builder(SessionActivity.this);
        builder.setTitle("Confirmer").setMessage("Voulez vous vraiment arrêter la session ?").setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                SessionActivity.super.onBackPressed();
                dialog.dismiss();

            }
        }).show();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);
    }
}
