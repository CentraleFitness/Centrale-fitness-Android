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

import com.fitness.centrale.centralefitness.R;
import com.fitness.centrale.centralefitness.fragment.dialogs.fragments.ModifyProfileInformationsDialog;

/**
 * Created by remy on 21/03/17.
 */

public class ProfileFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        ImageView profilePicture = (ImageView) view.findViewById(R.id.ProfileProfilePicture);
        ImageView gymPicture = (ImageView) view.findViewById(R.id.ProfileBackgroundPicture);

        profilePicture.setImageResource(R.drawable.profile_photo);
        gymPicture.setImageResource(R.drawable.sds);

        LinearLayout actualizeInfo = (LinearLayout) view.findViewById(R.id.ProfileActualizeInformations);

        actualizeInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment fragment = new ModifyProfileInformationsDialog();
                fragment.show(getFragmentManager(), "coucou");
            }
        });


        return view;
    }


}
