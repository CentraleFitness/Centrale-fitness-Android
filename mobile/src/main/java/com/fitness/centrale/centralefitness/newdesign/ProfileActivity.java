package com.fitness.centrale.centralefitness.newdesign;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fitness.centrale.centralefitness.Constants;
import com.fitness.centrale.centralefitness.EditProfileActivity;
import com.fitness.centrale.centralefitness.ImageUtility;
import com.fitness.centrale.centralefitness.Prefs;
import com.fitness.centrale.centralefitness.R;
import com.fitness.centrale.centralefitness.VolleyUtility;
import com.fitness.centrale.centralefitness.store.Store;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {


    private int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        final ImageView session = findViewById(R.id.sessionButton);
        final ImageView center = findViewById(R.id.profileButton);


        session.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Pair<View, String> p1 = Pair.create((View)session, ViewCompat.getTransitionName(session));
                Pair<View, String> p2 = Pair.create((View)center, ViewCompat.getTransitionName(center));

                Intent intent = new Intent(ProfileActivity.this, CenterActivity.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(ProfileActivity.this, p1, p2);
                startActivity(intent, options.toBundle());

            }
        });




        final TextView loginView = findViewById(R.id.profileLogin);
        final ImageView profilePicture = findViewById(R.id.profileProfilePicture);



        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        final Map<String, String> params = new HashMap<>();
        params.put(Constants.TOKEN, Prefs.getToken());

        JsonObjectRequest request = new JsonObjectRequest(Constants.SERVER + Constants.GETPROFILE, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            System.out.println("Response code : " + response.getString("code"));
                            if (response.getString("code").equals("001")){
                                Store.getStore().setUser(response.getString("first name"),
                                        response.getString("last name"),
                                        response.getString("email address"),
                                        response.getString("login"),
                                        response.getString("phone number"));
                                loginView.setText(Store.getStore().getUserObject().login);
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


        final Map<String, String> params2 = new HashMap<>();
        params2.put(Constants.TOKEN, Prefs.getToken());

        JsonObjectRequest request2 = new JsonObjectRequest(Constants.SERVER + Constants.GETPROFILEPICTURE, new JSONObject(params2),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            System.out.println("Response code : " + response.getString("code"));
                            if (response.getString("code").equals("001")){
                                profilePicture.setImageBitmap(ImageUtility.base64ToImage(response.getString(Constants.PICTURE)));
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
        queue.add(request2);

        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });



        ImageView editProfileButton = findViewById(R.id.ProfileSettings);

        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                startActivity(intent);
                ProfileActivity.this.finish();
            }
        });




    }



    private void updateImage(final String b64img){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());


        Map<String, String> params = new HashMap<>();
        params.put(Constants.TOKEN, Prefs.getToken());
        params.put(Constants.PICTURE, b64img);
        JsonObjectRequest request = new JsonObjectRequest(Constants.SERVER + Constants.UPDATEPROFILEPICTURE, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getString("code").equals("001")){
                        ImageView imageView = (ImageView) findViewById(R.id.profileProfilePicture);
                        imageView.setImageBitmap(ImageUtility.base64ToImage(b64img));

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));

                updateImage(ImageUtility.bitmapToBase64(bitmap));


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
