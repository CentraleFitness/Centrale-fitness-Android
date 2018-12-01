package com.fitness.centrale.mobile.fragments.programs;

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


/**
 * Created by Psyycker on 19/11/2017.
 */

public class ProgramsFragment extends Fragment {

    final int PAGE_NUMBER = 2;
    ViewPager mpager;
    ProgramsFragment.ScreenSlidePagerAdapter adapter;
    private static int position = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.programs_fragment, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();


        System.out.println();
    }

    LinearLayout leftTab = null;
    TextView leftTabTitle = null;
    LinearLayout rightTab = null;
    TextView rightTabTitle = null;



    @Override
    public void onStop() {
        super.onStop();




    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mpager = (ViewPager) view.findViewById(R.id.programPager);

        adapter = new ProgramsFragment.ScreenSlidePagerAdapter(getChildFragmentManager());

        mpager.setAdapter(adapter);
        mpager.setCurrentItem(position);
        System.out.println();


        mpager.setCurrentItem(1);
    }



    //Ici, remplacer les int et autres par les vrais arguments de la map
    public static ProgramsFragment newInstance() {
        ProgramsFragment fragment = new ProgramsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }



    public void onClickOnButton(int pos) {
        position = pos;
        if (pos == 0)
            return;
        mpager.setCurrentItem(pos, false);

    }


    private AllProgramsFragment allEventsFragment;
    private FavoriteProgramFragment favoriteProgramFragment;

    private AllProgramsFragment getAllEventFragment(){
        if (allEventsFragment == null){
            allEventsFragment = AllProgramsFragment.newInstance();
        }
        return allEventsFragment;
    }

    private FavoriteProgramFragment getFavoriteProgramFragment(){
        if (favoriteProgramFragment == null){
            favoriteProgramFragment = FavoriteProgramFragment.newInstance();
        }
        return favoriteProgramFragment;
    }

    private class ScreenSlidePagerAdapter extends FragmentPagerAdapter {
        public ScreenSlidePagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return getFavoriteProgramFragment();
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
