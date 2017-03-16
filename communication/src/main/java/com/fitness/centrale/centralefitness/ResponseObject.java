package com.fitness.centrale.centralefitness;

import java.util.HashMap;
import java.util.Objects;

/**
 * Created by remy on 16/03/17.
 */
public class ResponseObject extends HashMap<String, String> {


    public ResponseObject(boolean isAnError){
        put("error", String.valueOf(isAnError));
    }

    public ResponseObject(){

    }

    public boolean isAnError(){
        if (!containsKey("error"))
            return true;
        return (Objects.equals(get("error"), "true"));
    }


}