package com.fitness.centrale.centralefitness.fragments.programs;

import android.content.Context;

public class BasicProgramObject {

    public final String id;
    public final String name;
    public final Context context;

    public BasicProgramObject(final String id, final String name, final Context context){
        this.id = id;
        this.name = name;
        this.context = context;
    }


}
