package com.fitness.centrale.centralefitness;

import android.content.Intent;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

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

                                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                        startActivity(intent);
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
