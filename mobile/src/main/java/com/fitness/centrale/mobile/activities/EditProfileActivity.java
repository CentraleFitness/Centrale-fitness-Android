package com.fitness.centrale.mobile.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fitness.centrale.misc.Constants;
import com.fitness.centrale.misc.Prefs;
import com.fitness.centrale.mobile.R;
import com.fitness.centrale.mobile.activities.profile.ProfileActivity;
import com.fitness.centrale.misc.store.Store;
import com.fitness.centrale.misc.store.UserObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK){
            goBack();
        }


        return super.onKeyDown(keyCode, event);


    }

    private void goBack(){
        Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
        startActivity(intent);
        finish();

    }

    private void updateProfile(){
        RequestQueue queue = Volley.newRequestQueue(EditProfileActivity.this);

        final Map<String, String> params = new HashMap<>();
        params.put(Constants.TOKEN, Prefs.getPrefs(this).getToken());
        params.put(Constants.FIRST_NAME, firstName.getText().toString());
        params.put(Constants.LAST_NAME, lastName.getText().toString());
        params.put(Constants.EMAIL, email.getText().toString());
        params.put(Constants.PHONE, phone.getText().toString());

        JsonObjectRequest request = new JsonObjectRequest(Constants.SERVER + Constants.UPDATEPROFILE, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            System.out.println("Response code : " + response.getString("code"));
                            if (response.getString("code").equals("001")){

                                EditProfileActivity.super.onBackPressed();
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

    private void updatePassword(){

        final AlertDialog.Builder builder;

            builder = new AlertDialog.Builder(EditProfileActivity.this);




        if (oldPassword.getText().toString().equals("") && newPassword.getText().toString().equals("") && confirm.getText().toString().equals("")){
            updateProfile();
        }else if (!oldPassword.getText().toString().equals("")){
            if (newPassword.getText().toString().equals(confirm.getText().toString())){
                RequestQueue queue = Volley.newRequestQueue(EditProfileActivity.this);

                final Map<String, String> params = new HashMap<>();
                params.put(Constants.TOKEN, Prefs.getPrefs(this).getToken());
                params.put(Constants.PASSWORD, oldPassword.getText().toString());
                params.put(Constants.NEW_PASSWORD, newPassword.getText().toString());

                JsonObjectRequest request = new JsonObjectRequest(Constants.SERVER + Constants.CHANGEPASSWORD, new JSONObject(params),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    if (response.getString("code").equals("001")){

                                        String token = response.getString(Constants.TOKEN);
                                        Prefs.setToken(token);
                                        updateProfile();
                                    }else if (response.getString("code").equals("501")){
                                        builder.setTitle("Erreur").setMessage("Votre ancien mot de passe n'est pas bon").setPositiveButton("Ok", null).show();
                                    }else{
                                        builder.setTitle("Erreur").setMessage("Erreur inconnue : " + response.getString("code")).setPositiveButton("Ok", null).show();
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
            }else{
                builder.setTitle("Erreur").setMessage("Vos nouveaux mots de passe doivent correspondre").setPositiveButton("Ok", null).show();
            }
        }else{
            builder.setTitle("Erreur").setMessage("Vous devez reseigner votre ancien mot de passe pour le modifier").setPositiveButton("Ok", null).show();
        }
    }

    EditText email;
    EditText oldPassword;
    EditText newPassword;
    EditText confirm;
    EditText firstName;
    EditText lastName;
    EditText phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);


        email = findViewById(R.id.EditMail);
        oldPassword = findViewById(R.id.EditOldPassword);
        newPassword = findViewById(R.id.EditNewPassword);
        confirm = findViewById(R.id.EditConfirm);
        firstName = findViewById(R.id.EditFirstName);
        lastName = findViewById(R.id.EditLastName);
        phone = findViewById(R.id.EditPhone);

        ImageView back = findViewById(R.id.EditProfileBack);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        ImageView submit = findViewById(R.id.EditProfileSubmit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePassword();
            }
        });


        UserObject obj = Store.getStore().getUserObject();
        email.setText(obj.email);
        firstName.setText(obj.firstName);
        lastName.setText(obj.lastName);
        phone.setText(obj.phone);




    }
}
