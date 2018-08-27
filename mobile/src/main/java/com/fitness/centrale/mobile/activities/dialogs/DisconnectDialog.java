package com.fitness.centrale.mobile.activities.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.fitness.centrale.misc.store.Store;
import com.fitness.centrale.mobile.activities.SplashScreen;


public class DisconnectDialog extends DialogFragment {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final ConfirmationDialog confirmationDialog = new ConfirmationDialog();

        builder.setMessage("Vous pouvez vous déconnecter de votre compte ou quitter votre salle de sport")
                .setPositiveButton("Déconnexion", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Bundle args = new Bundle();
                        args.putString("type", ConfirmationDialog.ConfirmationType.DISCONNECT.value);
                        confirmationDialog.setArguments(args);
                        confirmationDialog.show(getFragmentManager(), "confirmDialog");

                        // FIRE ZE MISSILES!
                    }
                })
                .setNegativeButton("Quitter la salle", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog

                        Bundle args = new Bundle();
                        args.putString("type", ConfirmationDialog.ConfirmationType.LEAVE.value);
                        confirmationDialog.setArguments(args);
                        confirmationDialog.show(getFragmentManager(), "confirmDialog");


                    }
                });

        if (Store.getStore().getDemoObject().demo){
            builder.setNeutralButton("[BETA] Quitter le mode découverte", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(getActivity(), SplashScreen.class);
                    getActivity().startActivity(intent);
                    getActivity().finish();
                }
            });
        }

        // Create the AlertDialog object and return it
        return builder.create();
    }
}

