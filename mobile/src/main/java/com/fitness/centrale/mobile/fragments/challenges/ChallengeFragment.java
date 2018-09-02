package com.fitness.centrale.mobile.fragments.challenges;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fitness.centrale.mobile.R;
import com.fitness.centrale.mobile.fragments.event.EventsFragment;


/**
 * Created by Psyycker on 19/11/2017.
 */

public class ChallengeFragment extends Fragment {


    final int PAGE_NUMBER = 2;
    ViewPager mpager;
    ScreenSlidePagerAdapter adapter;
    private int position = 0;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.challenge_fragment, container, false);


        leftTab = view.findViewById(R.id.ChallengesRegisteredTab);
        leftTabTitle = view.findViewById(R.id.challenges_registered_tab_text);
        rightTab = view.findViewById(R.id.ChallengesAllTab);
        rightTabTitle = view.findViewById(R.id.challenges_all_tab_text);

        if (position == 0){
            setLeftTabSelected();
        }else{
            setRightTabSelected();
        }

        mpager = view.findViewById(R.id.challengesPager);

        adapter = new ScreenSlidePagerAdapter(getChildFragmentManager());

        mpager.setAdapter(adapter);
        mpager.setCurrentItem(position);

        leftTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLeftTabSelected();
                onClickOnButton(0);
            }
        });

        rightTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRightTabSelected();
                onClickOnButton(1);
            }
        });

        return view;


    }






    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    LinearLayout leftTab = null;
    TextView leftTabTitle = null;
    LinearLayout rightTab = null;
    TextView rightTabTitle = null;


    private void setLeftTabSelected(){
        setRightTabUnselected();
        leftTab.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.event_registered_tab_selected));
        leftTabTitle.setTextColor(ContextCompat.getColor(getContext(), R.color.white));

    }

    private void setLeftTabUnselected(){
        leftTab.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.event_registered_tab_unselected));
        leftTabTitle.setTextColor(ContextCompat.getColor(getContext(), R.color.ourRed));
    }

    private void setRightTabSelected(){
        setLeftTabUnselected();
        rightTab.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.event_all_tab_selected));
        rightTabTitle.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
    }

    private void setRightTabUnselected(){
        rightTab.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.event_all_tab_unselected));
        rightTabTitle.setTextColor(ContextCompat.getColor(getContext(), R.color.ourRed));
    }


    public void onClickOnButton(int pos) {
        position = pos;
        mpager.setCurrentItem(pos, false);

    }


    //Ici, remplacer les int et autres par les vrais arguments de la map
    public static ChallengeFragment newInstance() {
        ChallengeFragment fragment = new ChallengeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private AllChallengesFragment allChallengesFragment;
    private RegisteredChallengesFragment registeredChallengesFragment;

    private AllChallengesFragment getAllChallengesFragment(){
        if (allChallengesFragment == null){
            allChallengesFragment = AllChallengesFragment.newInstance();
        }
        return allChallengesFragment;
    }

    private RegisteredChallengesFragment getRegisteredChallengesFragment(){
        if (registeredChallengesFragment == null){
            registeredChallengesFragment = RegisteredChallengesFragment.newInstance();
        }
        return registeredChallengesFragment;
    }


    private class ScreenSlidePagerAdapter extends FragmentPagerAdapter {
        public ScreenSlidePagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return getRegisteredChallengesFragment();
                case 1:
                    return getAllChallengesFragment();

                default:
                    return null;
            }
        }



        @Override
        public int getCount() {
            return PAGE_NUMBER;
        }
    }


}
