package com.fitness.centrale.centralefitness;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fitness.centrale.centralefitness.fragments.event.EventCardHolder;
import com.fitness.centrale.centralefitness.newdesign.ProfileActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {


    private void sendAffiliationRequest(final String token){


        RequestQueue queue = Volley.newRequestQueue(getBaseContext());



        Map<String, Object> params = new HashMap<>();
        params.put(Constants.TOKEN, token);
        JsonObjectRequest request = new JsonObjectRequest(Constants.SERVER + Constants.GET_AFFILIATION, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    System.out.println(response.getString("code"));
                    if (response.getString("code").equals("001")){


                        Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                        startActivity(intent);

                    }else{

                        Intent intent = new Intent(LoginActivity.this, AffiliationActivity.class);
                        startActivity(intent);


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        VolleyUtility.fixDoubleRequests(request);

        queue.add(request);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);



        Button loginButton = findViewById(R.id.loginButton);
        final EditText login = findViewById(R.id.loginEditLogin);
        final EditText password = findViewById(R.id.loginEditPassword);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RequestQueue queue = Volley.newRequestQueue(getBaseContext());

                final Map<String, String> params = new HashMap<>();
                params.put(Constants.LOGIN, login.getText().toString());
                params.put(Constants.PASSWORD, password.getText().toString());

                JsonObjectRequest request = new JsonObjectRequest(Constants.SERVER + Constants.AUTHCREDS, new JSONObject(params),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    System.out.println("Response code : " + response.getString("code"));
                                    if (response.getString("code").equals("201")){
                                        Prefs.setToken(response.getString(Constants.TOKEN));
                                        Prefs.setUsername(params.get(Constants.LOGIN));




                                        sendAffiliationRequest(response.getString(Constants.TOKEN));






                                    }else{
                                        System.out.println("Mauvaise combinaison");
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
        });


        TextView notRegistered = findViewById(R.id.notregisterText);

        notRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


    }
}
