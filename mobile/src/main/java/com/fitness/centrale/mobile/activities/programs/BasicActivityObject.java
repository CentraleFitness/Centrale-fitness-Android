package com.fitness.centrale.mobile.activities.programs;

import android.content.Context;

import com.google.gson.GsonBuilder;

public class BasicActivityObject  {

    public final String id;
    public final String name;
    public final Context context;
    public final int duration;
    public final String iconB64;
    public final boolean isModule;

    public class MinimalActivityObject{
        public String id;
        public String name;
        public int duration;
        public String logo;
        public boolean isModule;

        public MinimalActivityObject(String id, String name, int duration, String logo, boolean isModule){
            this.id = id;
            this.name = name;
            this.duration = duration;
            this.logo = logo;
            this.isModule = isModule;
        }
    }

    public BasicActivityObject(final String id, final String name, final Context context, final int duration, String icon, boolean isModule){
        this.id = id;
        this.name = name;
        this.context = context;
        this.duration = duration;
        this.iconB64 = icon;
        this.isModule = isModule;
    }

    public String toJson(){

        return new GsonBuilder().create().toJson(new MinimalActivityObject(this.id, this.name, this.duration, this.iconB64, this.isModule));

    }


}
