package com.fitness.centrale.mobile.activities.social;

import android.content.Context;

import com.fitness.centrale.misc.store.DemoObject;

public class BasicSocialObject {


    public String id;
    public Context context;
    public PostType type;
    public String eventId;
    public String eventTitle;
    public String eventDesc;
    public String eventPicture;

    public DemoObject.Post post;



    public BasicSocialObject(final String id, final Context context, final PostType type){
        this.id = id;
        this.context = context;
        this.type = type;
    }


    public enum PostType{

        PUBLICATION("PUBLICATION"),
        EVENT("EVENT"),
        PHOTO("PHOTO");

        public final  String value;

        PostType(String type) {
            this.value = type;
        }
    }

}
