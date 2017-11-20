package com.fitness.centrale.centralefitness.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fitness.centrale.centralefitness.Constants;
import com.fitness.centrale.centralefitness.ImageUtility;
import com.fitness.centrale.centralefitness.Paths;
import com.fitness.centrale.centralefitness.Prefs;
import com.fitness.centrale.centralefitness.R;
import com.fitness.centrale.centralefitness.VolleyUtility;
import com.fitness.centrale.centralefitness.fragment.dialogs.fragments.ModifyProfileConnexionDialog;
import com.fitness.centrale.centralefitness.fragment.dialogs.fragments.ModifyProfileInformationsDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

/**
 * Created by remy on 21/03/17.
 */

public class ProfileFragment extends Fragment {


    private int PICK_IMAGE_REQUEST = 1;

    private String firstName = "";
    private String lastName = "";
    private String phoneNumber = "";
    private String email = "";
    private String login = "";


    private void actualizeProfile(JSONObject jsonObject){
        System.out.println();
        try {
            firstName = jsonObject.getString("first name");
            lastName = jsonObject.getString("last name");
            email = jsonObject.getString("email address");
            login = jsonObject.getString("login");
            phoneNumber = jsonObject.getString("phone number");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        TextView login = (TextView) getView().findViewById(R.id.ProfileFragmentName);

        if (firstName.equals("") || lastName.equals("")){
            login.setText(this.login);
        }else{
            login.setText(firstName + " " + lastName);
        }

    }

    private void updateImage(final String b64img){
        RequestQueue queue = Volley.newRequestQueue(getContext());


        Map<String, String> params = new HashMap<>();
        params.put(Constants.TOKEN, Prefs.getToken());
        params.put(Constants.PICTURE, b64img);
        JsonObjectRequest request = new JsonObjectRequest(Constants.SERVER + Constants.UPDATEPROFILEPICTURE, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getString("code").equals("001")){
                        ImageView imageView = (ImageView) getView().findViewById(R.id.ProfileProfilePicture);
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
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));

                updateImage(ImageUtility.bitmapToBase64(bitmap));


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);



        ImageView profilePicture = (ImageView) view.findViewById(R.id.ProfileProfilePicture);
        ImageView gymPicture = (ImageView) view.findViewById(R.id.ProfileBackgroundPicture);

        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });

        profilePicture.setImageResource(R.drawable.profile_photo);
        gymPicture.setImageResource(R.drawable.sds);

        LinearLayout actualizeInfo = (LinearLayout) view.findViewById(R.id.ProfileActualizeInformations);
        LinearLayout actualizeConnexion = (LinearLayout) view.findViewById(R.id.ProfileChangeConnexionInformations);

        actualizeInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment fragment = new ModifyProfileInformationsDialog(firstName, lastName, phoneNumber, email, ProfileFragment.this);

                fragment.show(getFragmentManager(), "Modifier informations de profil");

            }
        });

        actualizeConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment fragment = new ModifyProfileConnexionDialog();

                fragment.show(getFragmentManager(), "Modifier informations de connexion");

            }
        });


        return view;
    }

    public void getProfilePicture(){
        RequestQueue queue = Volley.newRequestQueue(getContext());

        Map<String, String> params = new HashMap<>();
        params.put(Constants.TOKEN, Prefs.getToken());
        JsonObjectRequest request = new JsonObjectRequest(Constants.SERVER + Constants.GETPROFILEPICTURE, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getString("code").equals("001")) {
                        ImageView profile = (ImageView) getView().findViewById(R.id.ProfileProfilePicture);
                        profile.setImageBitmap(ImageUtility.base64ToImage(response.getString(Constants.PICTURE)));
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

    public void updateProfile(){
        RequestQueue queue = Volley.newRequestQueue(getContext());

        Map<String, String> params = new HashMap<>();
        params.put(Constants.TOKEN, Prefs.getToken());
        JsonObjectRequest request = new JsonObjectRequest(Constants.SERVER + Paths.GETPROFILE, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                actualizeProfile(response);
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateProfile();
        getProfilePicture();

    }
}
