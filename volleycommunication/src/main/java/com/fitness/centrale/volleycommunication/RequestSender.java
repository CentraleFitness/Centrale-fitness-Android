package com.fitness.centrale.volleycommunication;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fitness.centrale.centralefitness.Constants;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by Psyycker on 16/11/2017.
 *
 * This class children are just made to send
 */

public abstract class RequestSender {



    protected Context context;
    protected Map<String, String> params;
    protected String url;

    protected RequestSender(Context context, Map<String, String> params, String url){
        this.context = context;
        this.params = params;
        this.url = url;
    }

    public void sendRequest(){
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest request = new JsonObjectRequest(url, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        onFail(error);
                    }
                });

        request.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(request);
    }

    protected abstract void onSuccess(JSONObject response);
    protected abstract void onFail(VolleyError error);








}
