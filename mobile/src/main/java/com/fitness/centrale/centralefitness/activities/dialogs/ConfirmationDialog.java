package com.fitness.centrale.centralefitness.activities.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.fitness.centrale.centralefitness.Prefs;
import com.fitness.centrale.centralefitness.activities.LoginActivity;
import com.fitness.centrale.centralefitness.store.Store;

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
