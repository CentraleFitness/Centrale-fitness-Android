package com.fitness.centrale.centralefitness;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.toolbox.JsonObjectRequest;

/**
 * Created by Psyycker on 16/11/2017.
 */

public class VolleyUtility {


    public static void fixDoubleRequests(JsonObjectRequest postRequest){
        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

}
