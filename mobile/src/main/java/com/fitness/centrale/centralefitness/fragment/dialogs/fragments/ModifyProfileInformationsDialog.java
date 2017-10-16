package com.fitness.centrale.centralefitness.fragment.dialogs.fragments;

import android.app.AlertDialog;
import android.app.Dialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fitness.centrale.centralefitness.R;

/**
 * Created by psyycker on 12/10/2017.
 */

public class ModifyProfileInformationsDialog extends DialogFragment {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        View view = getLayoutInflater().inflate(R.layout.dialog_profile_modify_information);
        dialog.setView(R.layout.dialog_profile_modify_information);

        EditText firstName = (EditText) view.findViewById(R.id.DialogProfileModfifyInformationsFirstName);
        EditText lastName = (EditText) view.findViewById(R.id.DialogProfileModfifyInformationsLastName);
        EditText phone = (EditText) view.findViewById(R.id.DialogProfileModfifyInformationsPhone);
        Button submit = (Button) view.findViewById(R.id.DialogProfileModfifyInformationsSubmit);
        Button cancel = (Button) view.findViewById(R.id.DialogProfileModfifyInformationsCancel);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });


        return dialog.create();
    }
}
