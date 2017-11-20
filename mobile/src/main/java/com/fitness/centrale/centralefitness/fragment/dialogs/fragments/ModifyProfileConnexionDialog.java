package com.fitness.centrale.centralefitness.fragment.dialogs.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fitness.centrale.centralefitness.Constants;
import com.fitness.centrale.centralefitness.Prefs;
import com.fitness.centrale.centralefitness.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by psyycker on 12/10/2017.
 */

public class ModifyProfileConnexionDialog extends DialogFragment {


    int value = 1;

    public EditText old;
    public EditText newPassword;
    public EditText confirmPassword;

    TextView errorLabel;

    Button cancel;
    Button submit;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View view = inflater.inflate(R.layout.dialog_profile_modify_connexion, null);

        old = (EditText) view.findViewById(R.id.DialogProfileModfifyConnexionOld);
        newPassword = (EditText) view.findViewById(R.id.DialogProfileModfifyConnexionNew);
        confirmPassword = (EditText) view.findViewById(R.id.DialogProfileModfifyConnexionConfirm);

        errorLabel = (TextView) view.findViewById(R.id.DialogProfileErrorLabel);

        cancel = (Button) view.findViewById(R.id.ProfileModifyCancel);
        submit = (Button) view.findViewById(R.id.ProfileModifySubmit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                RequestQueue queue = Volley.newRequestQueue(getContext());

                Map<String, String> params = new HashMap<>();
                params.put(Constants.TOKEN, Prefs.getToken());
                params.put(Constants.PASSWORD, old.getText().toString());
                params.put("new password", confirmPassword.getText().toString());

                JsonObjectRequest request = new JsonObjectRequest(Constants.SERVER + Constants.CHANGEPASSWORD, new JSONObject(params),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                System.out.println();
                                try {
                                    if (response.getString("code").equals("001")) {
                                        Prefs.setToken(response.getString("token"));
                                        getDialog().dismiss();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println();
                    }
                });

                queue.add(request);


               // getDialog().dismiss();
            }
        });

        dialog.setView(view);


        return dialog.create();
    }

    @Override
    public void dismiss() {
        System.out.println("On dismiss");
    }


}
