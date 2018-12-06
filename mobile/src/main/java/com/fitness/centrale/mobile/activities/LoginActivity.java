package com.fitness.centrale.mobile.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fitness.centrale.misc.Constants;
import com.fitness.centrale.misc.Prefs;
import com.fitness.centrale.mobile.R;
import com.fitness.centrale.misc.VolleyUtility;
import com.fitness.centrale.mobile.activities.dialogs.ErrorDialog;
import com.fitness.centrale.mobile.activities.profile.ProfileActivity;

import org.json.JSONException;
import org.json.JSONObject;

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

                    }else if (response.getString("code").equals("502")){
                        final AlertDialog.Builder builder;

                        builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.setTitle("Erreur").setMessage("Votre compte a été désactivé. Contactez le support pour plus d'informations").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setCancelable(false).show();
                    }else
                    {

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

    private void login(){


        if (login.getText().toString().equals("") || password.getText().toString().equals("")){

            new ErrorDialog().setType(ErrorDialog.ErrorType.EMPTY_FIELDS).show(getFragmentManager(), "errorDialog");
            return;
        }



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


                            }else if (response.getString("code").equals("501")){
                                new ErrorDialog().setType(ErrorDialog.ErrorType.BAD_CREDS).show(getFragmentManager(), "errorDialog");
                                return;



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
    EditText login;
    EditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);



        Button loginButton = findViewById(R.id.loginButton);
         login = findViewById(R.id.loginEditLogin);
         password = findViewById(R.id.loginEditPassword);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                login();


            }
        });

        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                login();
                return false;
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
