package com.fitness.centrale.centralefitness.activities.programs;

import android.content.Context;

import com.google.gson.GsonBuilder;

public class BasicActivityObject  {

    public final String id;
    public final String name;
    public final Context context;
    public final int duration;

    public class MinimalActivityObject{
        public String id;
        public String name;
        public int duration;

        public MinimalActivityObject(String id, String name, int duration){
            this.id = id;
            this.name = name;
            this.duration = duration;
        }
    }

    public BasicActivityObject(final String id, final String name, final Context context, final int duration){
        this.id = id;
        this.name = name;
        this.context = context;
        this.duration = duration;
    }

    public String toJson(){

        return new GsonBuilder().create().toJson(new MinimalActivityObject(this.id, this.name, this.duration));

    }


}