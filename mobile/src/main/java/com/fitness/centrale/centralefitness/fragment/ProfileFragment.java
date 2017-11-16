package com.fitness.centrale.centralefitness.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
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
import com.fitness.centrale.centralefitness.Paths;
import com.fitness.centrale.centralefitness.Prefs;
import com.fitness.centrale.centralefitness.R;
import com.fitness.centrale.centralefitness.VolleyUtility;
import com.fitness.centrale.centralefitness.fragment.dialogs.fragments.ModifyProfileConnexionDialog;
import com.fitness.centrale.centralefitness.fragment.dialogs.fragments.ModifyProfileInformationsDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by remy on 21/03/17.
 */

public class ProfileFragment extends Fragment {


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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);



        ImageView profilePicture = (ImageView) view.findViewById(R.id.ProfileProfilePicture);
        ImageView gymPicture = (ImageView) view.findViewById(R.id.ProfileBackgroundPicture);

        profilePicture.setImageResource(R.drawable.profile_photo);
        gymPicture.setImageResource(R.drawable.sds);

        LinearLayout actualizeInfo = (LinearLayout) view.findViewById(R.id.ProfileActualizeInformations);
        LinearLayout actualizeConnexion = (LinearLayout) view.findViewById(R.id.ProfileChangeConnexionInformations);

        actualizeInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment fragment = new ModifyProfileInformationsDialog(firstName, lastName, phoneNumber, email);
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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
}
