package com.fitness.centrale.mobile.fragments.event;

import android.content.Context;

import com.fitness.centrale.misc.store.DemoObject;

public class BasicEventObject {

    public final String id;
    public final String name;
    public final Context context;

    public DemoObject.Event event;

    public BasicEventObject(final String id, final String name, final Context context){
        this.id = id;
        this.name = name;
        this.context = context;
    }


}
