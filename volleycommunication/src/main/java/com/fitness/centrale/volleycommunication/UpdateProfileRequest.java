package com.fitness.centrale.volleycommunication;


import android.content.Context;

import com.android.volley.VolleyError;
import com.fitness.centrale.centralefitness.Constants;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by Psyycker on 16/11/2017.
 */

public class UpdateProfileRequest extends RequestSender {



    public UpdateProfileRequest(Context context, Map<String, String> params){
        super(context, params, Constants.SERVER + Paths.UPDATEPROFILE);
    }


    @Override
    protected void onSuccess(JSONObject response) {

    }

    @Override
    protected void onFail(VolleyError error) {

    }
}
