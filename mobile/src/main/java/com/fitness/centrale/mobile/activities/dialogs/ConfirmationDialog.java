package com.fitness.centrale.mobile.activities.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fitness.centrale.misc.Constants;
import com.fitness.centrale.misc.Prefs;
import com.fitness.centrale.misc.store.Store;
import com.fitness.centrale.mobile.activities.AffiliationActivity;
import com.fitness.centrale.mobile.activities.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ConfirmationDialog extends DialogFragment {


    public enum ConfirmationType{
        DISCONNECT("DISCONNECT", "Voulez vous vraiment vous déconnecter ?"),
        LEAVE("LEAVE", "Voulez vous vraiment quitter la salle de sport ? Cette action est définitive"),
        DEFAULT("DEFAULT", "Rien à afficher");

        public final String value;
        public final String message;

        ConfirmationType(String value, String message) {
            this.value = value;
            this.message = message;
        }
    }


    ConfirmationType type;
    @Override
    public void setArguments(Bundle args) {

        type = ConfirmationType.valueOf(args.getString("type"));

    }



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {



        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(type.message)
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        if (type == ConfirmationType.DISCONNECT){

                            Prefs.removeToken();
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            getActivity().startActivity(intent);
                            Store.getStore().removeUser();
                            getActivity().finish();


                        }else if (type == ConfirmationType.LEAVE){
                            RequestQueue queue = Volley.newRequestQueue(getActivity());


                            final Map<String, Object> params = new HashMap<>();
                            params.put(Constants.TOKEN, Prefs.getPrefs(getActivity()).getToken());
                            final Activity activity = getActivity();

                            //TODO Remplacer par la récupération de la dernière activité
                            JsonObjectRequest request = new JsonObjectRequest(Constants.SERVER + Constants.UNAFFILIATE, new JSONObject(params),
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            try {
                                                System.out.println("Response code : " + response.getString("code"));
                                                if (response.getString("code").equals("001")){
                                                    Intent intent = new Intent(activity, AffiliationActivity.class);
                                                    activity.startActivity(intent);
                                                    activity.finish();


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

                        // FIRE ZE MISSILES!
                    }
                })
                .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

}
