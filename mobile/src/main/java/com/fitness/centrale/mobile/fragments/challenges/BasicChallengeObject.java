package com.fitness.centrale.mobile.fragments.challenges;

import android.content.Context;

import com.fitness.centrale.misc.store.DemoObject;

public class BasicChallengeObject {

    public final String id;
    public final String name;
    public final Context context;

    public DemoObject.Event event;

    public BasicChallengeObject(final String id, final String name, final Context context){
        this.id = id;
        this.name = name;
        this.context = context;
    }


}
