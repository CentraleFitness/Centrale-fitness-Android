package com.fitness.centrale.mobile.fragments.challenges;

import android.content.Context;

import com.fitness.centrale.misc.store.DemoObject;

public class BasicChallengeObject {

    public String type;
    public String title;
    public String owner;
    public String steps;
    public String desc;
    public String machine;
    public String endDate;
    public String pointsNeeded;
    public final Context context;

    public DemoObject.Event event;

    public BasicChallengeObject(final Context context){
        this.context = context;
    }


}
