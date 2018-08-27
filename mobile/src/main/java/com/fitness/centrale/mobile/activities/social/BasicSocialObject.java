package com.fitness.centrale.mobile.activities.social;

import android.content.Context;

import com.fitness.centrale.misc.store.DemoObject;

public class BasicSocialObject {


    public String id;
    public Context context;
    public PostType type;

    public DemoObject.Post post;



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
