package com.fitness.centrale.mobile.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fitness.centrale.misc.Constants;
import com.fitness.centrale.misc.Prefs;
import com.fitness.centrale.mobile.R;
import com.fitness.centrale.mobile.activities.profile.ProfileActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText loginEdit = findViewById(R.id.registerLoginEdit);
        final EditText emailEdit = findViewById(R.id.registerEmailEdit);
        final EditText passwordEdit = findViewById(R.id.registerPasswordEdit);
        final EditText confirmEdit = findViewById(R.id.registerPasswordConfirmEdit);
        Button registerButton = findViewById(R.id.registerButton);


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!passwordEdit.getText().toString().equals(confirmEdit.getText().toString())) {

                    final AlertDialog.Builder builder;

                    builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setTitle("Erreur").setMessage("Les mots de passe ne correspondent pas").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).setCancelable(false).show();
                    return;
                }

                RequestQueue queue = Volley.newRequestQueue(getBaseContext());

                final Map<String, String> params = new HashMap<>();
                params.put(Constants.LOGIN, loginEdit.getText().toString());
                params.put(Constants.PASSWORD, passwordEdit.getText().toString());
                params.put(Constants.EMAIL, emailEdit.getText().toString());
                params.put(Constants.CONFIRM_PASSWORD, confirmEdit.getText().toString());
                params.put(Constants.FIRST_NAME, "");
                params.put(Constants.LAST_NAME, "");
                params.put(Constants.PHONE, "");

                JsonObjectRequest request = new JsonObjectRequest(Constants.SERVER + Constants.REGISTER, new JSONObject(params),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    System.out.println("Response code : " + response.getString("code"));
                                    if (response.getString("code").equals("101")){
                                        Prefs.setToken(response.getString(Constants.TOKEN));
                                        Prefs.setUsername(params.get(Constants.LOGIN));

                                        Intent intent = new Intent(RegisterActivity.this, AffiliationActivity.class);
                                        startActivity(intent);
                                    } else if (response.getString("code").equals("301")) {
                                        final AlertDialog.Builder builder;

                                        builder = new AlertDialog.Builder(RegisterActivity.this);
                                        builder.setTitle("Erreur").setMessage("Ce login n'est pas disponible").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        }).setCancelable(false).show();
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

    }
}
