package com.fitness.centrale.mobile.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fitness.centrale.misc.AlertDialogBuilder;
import com.fitness.centrale.misc.Constants;
import com.fitness.centrale.misc.Prefs;
import com.fitness.centrale.misc.store.Store;
import com.fitness.centrale.misc.store.UserObject;
import com.fitness.centrale.mobile.R;
import com.fitness.centrale.mobile.activities.profile.ProfileActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FeedBackActivity extends AppCompatActivity {


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK){
            goBack();
        }


        return super.onKeyDown(keyCode, event);


    }

    private void goBack(){
        Intent intent = new Intent(FeedBackActivity.this, ProfileActivity.class);
        startActivity(intent);
        finish();

    }

    private void sendFeedback(){

        if (email.getText().toString().equals("") || content.getText().toString().equals("")){
            AlertDialogBuilder.createAlertDialog(this, "Erreur", "Veuillez entrer une adresse mail et un feedback.", "Ok").show();
            return;
        }

        RequestQueue queue = Volley.newRequestQueue(FeedBackActivity.this);

        final Map<String, String> params = new HashMap<>();
        params.put("email", email.getText().toString());
        params.put("content", content.getText().toString());
        params.put("version", Constants.VERSION);
        params.put("date", new Date().toString());
        JsonObjectRequest request = new JsonObjectRequest("http://psyycker.fr.nf:5002/postfeedback", new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        AlertDialogBuilder.createAlertDialog(FeedBackActivity.this, "Merci !", "Merci pour votre commentaire !", "Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FeedBackActivity.super.onBackPressed();
                                finish();
                            }
                        }).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        queue.add(request);
    }



    EditText email;
    EditText content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);


        email = findViewById(R.id.EditMail);
        content = findViewById(R.id.content);

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
                sendFeedback();
            }
        });

        UserObject obj = Store.getStore().getUserObject();
        email.setText(obj.email);




    }
}
