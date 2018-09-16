package com.fitness.centrale.mobile.fragments.challenges;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.fitness.centrale.mobile.R;
import com.fitness.centrale.mobile.activities.ChallengeActivity;

import java.util.List;

public class ChallengeCardHolder extends RecyclerView.ViewHolder   {

    private Context context;
    private Activity parent;

    private LinearLayout challenge;
    private LinearLayout challenge1Lyt;
    private LinearLayout challenge2Lyt;
    private LinearLayout challenge3Lyt;
    private ImageView firstChallengeImg;
    private ImageView secondChallengeImg;
    private ImageView thirdChallengeImg;


    public ChallengeCardHolder(View itemView, Context context, Activity parent) {
        super(itemView);
        this.context = context;
        this.parent = parent;

        challenge1Lyt = itemView.findViewById(R.id.firstChallenge);
        challenge2Lyt = itemView.findViewById(R.id.secondChallenge);
        challenge3Lyt = itemView.findViewById(R.id.thirdChallenge);
        firstChallengeImg = itemView.findViewById(R.id.firstChallengeImage);
        secondChallengeImg = itemView.findViewById(R.id.secondChallengeImage);
        thirdChallengeImg = itemView.findViewById(R.id.thirdChallengeImage);
        challenge = itemView.findViewById(R.id.cardViewChallenge);

        challenge.setLayoutParams(new LinearLayout.LayoutParams(0, 0));

    }


    private void onChallengePress(BasicChallengeObject object){
        Intent intent = new Intent(context, ChallengeActivity.class);
        intent.putExtra("title", object.title);
        intent.putExtra("machine", object.machine);
        intent.putExtra("desc", object.desc);
        intent.putExtra("endDate", object.endDate);
        intent.putExtra("pointsNeeded", object.pointsNeeded);
        intent.putExtra("steps", object.steps);
        intent.putExtra("type", object.type);
        context.startActivity(intent);
    }

    private void setFirstContent(final BasicChallengeObject obj){
        Machines machines = Machines.getMachineEnum(obj.machine);
        firstChallengeImg.setImageDrawable(context.getDrawable(machines.drawable));
        challenge1Lyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onChallengePress(obj);
            }
        });
    }

    private void setSecondContent(final BasicChallengeObject obj){
        Machines machines = Machines.getMachineEnum(obj.machine);
        secondChallengeImg.setImageDrawable(context.getDrawable(machines.drawable));
        challenge2Lyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onChallengePress(obj);
            }
        });

    }


    private void setThirdContent(final BasicChallengeObject obj){
        Machines machines = Machines.getMachineEnum(obj.machine);
        thirdChallengeImg.setImageDrawable(context.getDrawable(machines.drawable));
        challenge3Lyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onChallengePress(obj);
            }
        });

    }


    public void bind(final List<BasicChallengeObject> myObject){

        BasicChallengeObject first = null;
        BasicChallengeObject second = null;
        BasicChallengeObject third = null;

        for (BasicChallengeObject obj : myObject){
            if (first == null)
                first = obj;
            else if (second == null)
                second = obj;
            else if (third == null)
                third = obj;
        }


        if (first != null){
            setFirstContent(first);
            this.challenge1Lyt.setVisibility(View.VISIBLE);
            challenge.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT));
        }
        if (second != null){
            setSecondContent(second);
            this.challenge2Lyt.setVisibility(View.VISIBLE);
        }
        if (third != null){
            setThirdContent(third);
            this.challenge3Lyt.setVisibility(View.VISIBLE);
        }

    }








}



