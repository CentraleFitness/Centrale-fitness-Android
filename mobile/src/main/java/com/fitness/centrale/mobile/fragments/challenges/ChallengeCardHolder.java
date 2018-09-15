package com.fitness.centrale.mobile.fragments.challenges;

import android.app.ActionBar;
import android.app.Activity;
import android.bluetooth.BluetoothAssignedNumbers;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fitness.centrale.misc.Constants;
import com.fitness.centrale.misc.ImageUtility;
import com.fitness.centrale.misc.Prefs;
import com.fitness.centrale.misc.VolleyUtility;
import com.fitness.centrale.misc.store.Store;
import com.fitness.centrale.mobile.R;
import com.fitness.centrale.mobile.activities.EventDetailsActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;

import lecho.lib.hellocharts.model.Line;

public class ChallengeCardHolder extends RecyclerView.ViewHolder   {

    private Context context;
    private Activity parent;

    private LinearLayout challenge;
    private LinearLayout challenge1Lyt;
    private LinearLayout challenge2Lyt;
    private LinearLayout challenge3Lty;
    private ImageView firstChallengeImg;
    private ImageView secondChallengeImg;
    private ImageView thirdChallengeImg;


    public ChallengeCardHolder(View itemView, Context context, Activity parent) {
        super(itemView);
        this.context = context;
        this.parent = parent;

        challenge1Lyt = itemView.findViewById(R.id.firstChallenge);
        challenge2Lyt = itemView.findViewById(R.id.secondChallenge);
        challenge3Lty = itemView.findViewById(R.id.thirdChallenge);
        firstChallengeImg = itemView.findViewById(R.id.firstChallengeImage);
        secondChallengeImg = itemView.findViewById(R.id.secondChallengeImage);
        thirdChallengeImg = itemView.findViewById(R.id.thirdChallengeImage);
        challenge = itemView.findViewById(R.id.cardViewChallenge);

        challenge.setLayoutParams(new LinearLayout.LayoutParams(0, 0));

    }


    public void setContent(final Date startDate,
                           final Date endDate,
                           final Boolean registered,
                           final String picture,
                           final String description,
                           final String name){







    }

    private void setFirstContent(BasicChallengeObject obj){
        Machines machines = Machines.getMachineEnum(obj.machine);
        firstChallengeImg.setImageDrawable(context.getDrawable(machines.drawable));
    }

    private void setSecondContent(BasicChallengeObject obj){
        Machines machines = Machines.getMachineEnum(obj.machine);
        secondChallengeImg.setImageDrawable(context.getDrawable(machines.drawable));

    }


    private void setThirdContent(BasicChallengeObject obj){
        Machines machines = Machines.getMachineEnum(obj.machine);
        thirdChallengeImg.setImageDrawable(context.getDrawable(machines.drawable));

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
            this.challenge3Lty.setVisibility(View.VISIBLE);
        }

    }








}



