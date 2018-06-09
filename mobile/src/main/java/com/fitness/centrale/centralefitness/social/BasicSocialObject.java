package com.fitness.centrale.centralefitness.social;

import android.content.Context;

public class BasicSocialObject {


    public String id;
    public Context context;
    public PostType type;



    public BasicSocialObject(final String id, final Context context, final PostType type){
        this.id = id;
        this.context = context;
        this.type = type;
    }


    public enum PostType{

        PUBLICATION("PUBLICATION"),
        EVENT("EVENT");

        public final  String value;

        PostType(String type) {
            this.value = type;
        }
    }

}
