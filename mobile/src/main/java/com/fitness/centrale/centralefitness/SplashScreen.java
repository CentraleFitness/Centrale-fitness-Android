package com.fitness.centrale.centralefitness;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slash_screen);

        Prefs.initPreferencesManager(getBaseContext());

        RequestQueue queue = Volley.newRequestQueue(getBaseContext());

        final Map<String, String> params = new HashMap<>();
        params.put(Constants.TOKEN, Prefs.getToken());

        JsonObjectRequest request = new JsonObjectRequest(Constants.SERVER + Constants.AUTHTOKEN, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            System.out.println("Response code : " + response.getString("code"));
                            if (response.getString("code").equals("201")){

                                Intent intent = new Intent(SplashScreen.this, HomeActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        queue.add(request);

    }
}
