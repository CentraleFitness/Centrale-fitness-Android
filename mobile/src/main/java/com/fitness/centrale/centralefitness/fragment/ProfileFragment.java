package com.fitness.centrale.centralefitness.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fitness.centrale.centralefitness.R;

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


        return view;
    }


}
