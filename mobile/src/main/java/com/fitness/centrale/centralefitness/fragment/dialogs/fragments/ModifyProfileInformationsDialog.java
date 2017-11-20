package com.fitness.centrale.centralefitness.fragment.dialogs.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.fitness.centrale.centralefitness.Constants;
import com.fitness.centrale.centralefitness.Prefs;
import com.fitness.centrale.centralefitness.R;
import com.fitness.centrale.centralefitness.fragment.ProfileFragment;
import com.fitness.centrale.volleycommunication.UpdateProfileRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by psyycker on 12/10/2017.
 */

@SuppressLint("ValidFragment")
public class ModifyProfileInformationsDialog extends DialogFragment {


    String firstName;
    String lastName;
    String phoneNumber;
    String email;
    ProfileFragment frg;

    @SuppressLint("ValidFragment")
    public ModifyProfileInformationsDialog(String firstName, String lastName, String phoneNumber, String email, ProfileFragment profileFragment) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        frg = profileFragment;
    }

    EditText firstNameEdit;
    EditText lastNameEdit;
    EditText phoneNumberEdit;
    EditText emailEdit;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View view = inflater.inflate(R.layout.dialog_profile_modify_information, null);


        firstNameEdit = (EditText) view.findViewById(R.id.DialogProfileModifyInformationsFirstName);
        lastNameEdit = (EditText) view.findViewById(R.id.DialogProfileModifyInformationsLastName);
        phoneNumberEdit = (EditText) view.findViewById(R.id.DialogProfileModifyInformationsPhone);
        emailEdit  = (EditText) view.findViewById(R.id.DialogProfileModifyInformationsEmail);

        firstNameEdit.setText(ModifyProfileInformationsDialog.this.firstName);
        lastNameEdit.setText(ModifyProfileInformationsDialog.this.lastName);
        phoneNumberEdit.setText(ModifyProfileInformationsDialog.this.phoneNumber);
        emailEdit.setText(ModifyProfileInformationsDialog.this.email);

        dialog.setView(view)
                .setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {





                        String newFirstName = firstNameEdit.getText().toString();
                        String newLastName = lastNameEdit.getText().toString();
                        String newphoneNumber = phoneNumberEdit.getText().toString();
                        String newEmail = emailEdit.getText().toString();

                        Map<String, String> param = new HashMap<>();
                        param.put(Constants.TOKEN, Prefs.getToken());
                        param.put(Constants.FIRST_NAME, newFirstName);
                        param.put(Constants.LAST_NAME, newLastName);
                        param.put(Constants.PHONE, newphoneNumber);
                        param.put(Constants.EMAIL, newEmail);

                        new UpdateProfileRequest(getContext(), param).sendRequest();
                        frg.updateProfile();


                        //TODO : Envoyer la modif de profil




                    }
                }).setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });


        return dialog.create();
    }
}
