package com.fitness.centrale.centralefitness.fragment.dialogs.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.fitness.centrale.centralefitness.R;

/**
 * Created by psyycker on 12/10/2017.
 */

public class ModifyProfileConnexionDialog extends DialogFragment {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View view = inflater.inflate(R.layout.dialog_profile_modify_connexion, null);


        dialog.setView(view)
                .setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        EditText firstName = (EditText) view.findViewById(R.id.DialogProfileModifyInformationsFirstName);
                        EditText lastName = (EditText) view.findViewById(R.id.DialogProfileModifyInformationsLastName);
                        EditText phoneNumber = (EditText) view.findViewById(R.id.DialogProfileModifyInformationsPhone);






                    }
                }).setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });


        return dialog.create();
    }
}
