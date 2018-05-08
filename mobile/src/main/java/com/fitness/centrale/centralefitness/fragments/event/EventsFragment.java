package com.fitness.centrale.centralefitness.fragments.event;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fitness.centrale.centralefitness.R;
import com.fitness.centrale.centralefitness.newdesign.CenterActivity;


/**
 * Created by Psyycker on 19/11/2017.
 */

public class EventsFragment extends Fragment {

    final int PAGE_NUMBER = 2;
    ViewPager mpager;
    EventsFragment.ScreenSlidePagerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.event_fragment, container, false);
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

    @Override
    public void onStop() {
        super.onStop();




    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        leftTab = view.findViewById(R.id.EventRegisteredTab);
        leftTabTitle = view.findViewById(R.id.event_registered_tab_text);
        rightTab = view.findViewById(R.id.EventAllTab);
        rightTabTitle = view.findViewById(R.id.event_all_tab_text);

        setLeftTabSelected();


        mpager = (ViewPager) view.findViewById(R.id.eventPager);
        adapter = new EventsFragment.ScreenSlidePagerAdapter(getActivity().getSupportFragmentManager());


        mpager.setAdapter(adapter);
        mpager.setCurrentItem(0);

        mpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                onClickOnButton(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });





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


    }

    //Ici, remplacer les int et autres par les vrais arguments de la map
    public static EventsFragment newInstance() {
        EventsFragment fragment = new EventsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }



    public void onClickOnButton(int pos) {
        mpager.setCurrentItem(pos, false);

    }


    private AllEventsFragment allEventsFragment;
    private RegisteredEventsFragment registeredEventsFragment;

    private AllEventsFragment getAllEventFragment(){
        if (allEventsFragment == null){
            allEventsFragment = AllEventsFragment.newInstance();
        }
        return allEventsFragment;
    }

    private RegisteredEventsFragment getRegisteredEventsFragment(){
        if (registeredEventsFragment == null){
            registeredEventsFragment = RegisteredEventsFragment.newInstance();
        }
        return registeredEventsFragment;
    }

    private class ScreenSlidePagerAdapter extends FragmentPagerAdapter {
        public ScreenSlidePagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return getRegisteredEventsFragment();
                case 1:
                    return getAllEventFragment();

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
