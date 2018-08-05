package com.fitness.centrale.mobile.activities.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class ErrorDialog extends DialogFragment {


    public enum ErrorType{

        BAD_CREDS("Login ou mot de passe incorrect"),
        EMPTY_FIELDS("Tous les champs doivent être remplis");

        public final String value;

        ErrorType(String value) {
            this.value = value;
        }
    }

    ErrorType type;

    @Override
    public void setArguments(Bundle args) {

        type = ErrorType.valueOf(args.getString("type"));

    }


    public ErrorDialog setType(final ErrorType type){

        this.type = type;

        return this;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage(type.value)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

}
