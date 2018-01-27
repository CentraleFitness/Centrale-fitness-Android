package com.fitness.centrale.centralefitness.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fitness.centrale.centralefitness.R;


/**
 * Created by Psyycker on 19/11/2017.
 */

public class BadgeFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.badge_fragment, container, false);
    }

    //Ici, remplacer les int et autres par les vrais arguments de la map
    public static BadgeFragment newInstance(int someInt, String someTitle) {
        BadgeFragment fragment = new BadgeFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", someInt);
        args.putString("someTitle", someTitle);
        fragment.setArguments(args);
        return fragment;
    }


}
