package com.fitness.centrale.centralefitness.newdesign;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.fitness.centrale.centralefitness.HomeActivity;
import com.fitness.centrale.centralefitness.NFCScanActivity;
import com.fitness.centrale.centralefitness.R;
import com.fitness.centrale.centralefitness.fragments.ChallengeFragment;
import com.fitness.centrale.centralefitness.fragments.EventsFragment;
import com.fitness.centrale.centralefitness.fragments.ProfileFragment;
import com.fitness.centrale.centralefitness.fragments.ProgramsFragment;
import com.fitness.centrale.centralefitness.fragments.StatsFragment;

public class CenterActivity extends AppCompatActivity {


    final int PAGE_NUMBER = 4;
    ViewPager mpager;
    ScreenSlidePagerAdapter adapter;
    Boolean mShouldFinish = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center);


        final ImageView profileBtn = findViewById(R.id.profileButton);
        final ImageView sessionBtn = findViewById(R.id.sessionButton);
        final ImageView socialBtn = findViewById(R.id.socialButton);
        final ImageView sessionStartBtn = findViewById(R.id.sessionStartBtn);
        final ImageView challengeButton = findViewById(R.id.challengesButton);
        final ImageView eventButton = findViewById(R.id.eventButton);
        final ImageView statsButton = findViewById(R.id.statsButton);
        final ImageView programsButton = findViewById(R.id.programsButton);




        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pair<View, String> p1 = Pair.create((View)sessionBtn, ViewCompat.getTransitionName(sessionBtn));
                Pair<View, String> p2 = Pair.create((View)profileBtn, ViewCompat.getTransitionName(profileBtn));

                Intent intent = new Intent(CenterActivity.this, ProfileActivity.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(CenterActivity.this, p1, p2);
                startActivity(intent, options.toBundle());
                mShouldFinish = true;

            }
        });

        socialBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pair<View, String> p1 = Pair.create((View)sessionBtn, ViewCompat.getTransitionName(sessionBtn));
                Pair<View, String> p2 = Pair.create((View)socialBtn, ViewCompat.getTransitionName(socialBtn));

                Intent intent = new Intent(CenterActivity.this, SocialActivity.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(CenterActivity.this, p1, p2);
                startActivity(intent, options.toBundle());
                mShouldFinish = true;
            }
        });

        sessionStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CenterActivity.this, NFCScanActivity.class);
                startActivity(intent);

            }
        });

        challengeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickOnButton(0);
            }
        });

        eventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickOnButton(1);
            }
        });

        statsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickOnButton(2);
            }
        });

        programsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickOnButton(3);
            }
        });


        mpager = (ViewPager) findViewById(R.id.centerPager);
        adapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());


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



    }

    @Override
    public void onStop() {
        super.onStop();
        if(mShouldFinish)
            finish();
    }


    public void onClickOnButton(int pos) {
        mpager.setCurrentItem(pos, false);


        //Passer les images en selectionnées ou non (a voir plus tard)
        /*for (int i = 0; i < PAGE_NUMBER; i++) {
            footerImages[i].setImageResource(footerImagesUnselected[i]);
            if (footerTextes[i] != null) {
                footerTextes[i].setTextColor(getResources().getColor(R.color.white));
            }
        }
        footerImages[pos].setImageResource(footerImagesSelected[pos]);
        if (footerTextes[pos] != null) {
            footerTextes[pos].setTextColor(getResources().getColor(R.color.ourBlue));
        }*/
    }

    private static EventsFragment eventsFragment = null;
    private static ChallengeFragment challengeFragment = null;
    private static StatsFragment statsFragment = null;
    private static ProgramsFragment programsFragment = null;


    private static EventsFragment getEventsFragment(){

        if (eventsFragment == null){
            eventsFragment = EventsFragment.newInstance();
        }

        return eventsFragment;
    }

    private static ChallengeFragment getChallengeFragment(){
        if (challengeFragment == null){
            challengeFragment = ChallengeFragment.newInstance();
        }
        return challengeFragment;
    }

    private static StatsFragment getStatsFragment(){
        if (statsFragment == null){
            statsFragment = StatsFragment.newInstance();
        }
        return statsFragment;
    }


    private static ProgramsFragment getProgramsFragment(){
        if( programsFragment == null){
            programsFragment = ProgramsFragment.newInstance();
        }
        return  programsFragment;
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return getChallengeFragment();
                case 1:
                    return getEventsFragment();
                case 2:
                    return getStatsFragment();
                case 3:
                    return getProgramsFragment();
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
