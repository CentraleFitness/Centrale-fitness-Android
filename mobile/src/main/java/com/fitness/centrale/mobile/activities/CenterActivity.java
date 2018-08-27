package com.fitness.centrale.mobile.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.Pair;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.fitness.centrale.misc.AlertDialogBuilder;
import com.fitness.centrale.misc.store.Store;
import com.fitness.centrale.mobile.R;
import com.fitness.centrale.mobile.activities.profile.ProfileActivity;
import com.fitness.centrale.mobile.fragments.ChallengeFragment;
import com.fitness.centrale.mobile.fragments.event.EventsFragment;
import com.fitness.centrale.mobile.fragments.StatsFragment;
import com.fitness.centrale.mobile.fragments.programs.ProgramsFragment;
import com.fitness.centrale.mobile.activities.social.SocialActivity;

public class CenterActivity extends FragmentActivity {


    ImageView challengeButton;
    ImageView eventButton;
    ImageView statsButton;
    ImageView programsButton;

    final int PAGE_NUMBER = 4;
    ViewPager mpager;
    PagerAdapter adapter;
    Boolean mShouldFinish = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center);


        final ImageView profileBtn = findViewById(R.id.profileButton);
        final ImageView sessionBtn = findViewById(R.id.sessionButton);
        final ImageView socialBtn = findViewById(R.id.socialButton);
        final ImageView sessionStartBtn = findViewById(R.id.sessionStartBtn);
        challengeButton = findViewById(R.id.challengesButton);
        eventButton = findViewById(R.id.eventButton);
        statsButton = findViewById(R.id.statsButton);
        programsButton = findViewById(R.id.programsButton);


        if (Store.getStore().getDemoObject().demo && !Store.getStore().getDemoObject().enterInSessionPage){
            AlertDialogBuilder.createAlertDialog(this, "Page principale",
                    "Cette page est la page principale de Centrale Fitness.\n" +
                            "Ici vous pourrez lancer une session, vous inscrire à des défis ou évenements ou consulter vos statistiques!", "Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            AlertDialogBuilder.createAlertDialog(CenterActivity.this,
                                    "Premier bouton : Page de challenges",
                                    "La page de challenge est la page dans laquelle vous pouvez créer des défis à accomplir tout seul ou les transformer en" +
                                            "challenges afin de les partager avec les autres membres de la salle de sport.",
                                    "Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            AlertDialogBuilder.createAlertDialog(CenterActivity.this, "Second bouton : Page d'événements",
                                                    "La page d'événements permet de s'inscrire à des événements organisés par la salle de sport!", "Ok",
                                                    new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            dialog.dismiss();
                                                            AlertDialogBuilder.createAlertDialog(CenterActivity.this, "Troisième bouton : Lancer une session",
                                                                    "Le bouton central permet le lancement de l'appairage de l'application avec un module Central Fitness." +
                                                                            "\nUne fois appairé, la session de sport peut enfin démarrer!", "Ok",
                                                                    new DialogInterface.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(DialogInterface dialog, int which) {
                                                                            dialog.dismiss();
                                                                            AlertDialogBuilder.createAlertDialog(CenterActivity.this, "Quatrième bouton : Les statistiques",
                                                                                    "La page de statistiques vous permet de voir les détails de vos dernières sessions.", "Ok",
                                                                                    new DialogInterface.OnClickListener() {
                                                                                        @Override
                                                                                        public void onClick(DialogInterface dialog, int which) {
                                                                                            dialog.dismiss();
                                                                                            AlertDialogBuilder.createAlertDialog(CenterActivity.this,
                                                                                                    "Cinquième bouton : Les programmes personnalisés",
                                                                                                    "Les programmes personnalisés sont des programmes faits par les autres utilisateurs ou par la" +
                                                                                                            "salle de sport permettant de suivre une marche à suivre durant votre passage dans la salle de sport.\n" +
                                                                                                            "Vous avez également la possibilité de créer vos propres programmes personnalisés.", "Ok", new DialogInterface.OnClickListener() {
                                                                                                        @Override
                                                                                                        public void onClick(DialogInterface dialog, int which) {
                                                                                                            dialog.dismiss();
                                                                                                            Store.getStore().getDemoObject().enterInSessionPage = true;
                                                                                                        }
                                                                                                    }).show();
                                                                                        }
                                                                                    }).show();
                                                                        }
                                                                    }).show();
                                                        }
                                                    }).show();
                                        }
                                    }).show();
                        }
                    }).show();
        }


        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pair<View, String> p1 = Pair.create((View)sessionBtn, ViewCompat.getTransitionName(sessionBtn));
                Pair<View, String> p2 = Pair.create((View)profileBtn, ViewCompat.getTransitionName(profileBtn));

                Intent intent = new Intent(CenterActivity.this, ProfileActivity.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(CenterActivity.this, p1, p2);
                startActivity(intent, options.toBundle());
                mShouldFinish = true;
                //supportFinishAfterTransition();



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
                //supportFinishAfterTransition();
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
                setButtonsTint(0);
                onClickOnButton(0);
            }
        });

        eventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setButtonsTint(1);

                onClickOnButton(1);
            }
        });

        statsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setButtonsTint(2);
                onClickOnButton(2);
            }
        });

        programsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setButtonsTint(3);
                onClickOnButton(3);
            }
        });


        mpager = (ViewPager) findViewById(R.id.centerPager);
        adapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());


        mpager.setAdapter(adapter);
        mpager.setCurrentItem(0);
        setButtonsTint(0);

    }

    public void setButtonsTint(final int position){
        switch (position){
            case 0:
                challengeButton.setColorFilter(null);
                eventButton.setColorFilter(Color.argb(255, 192, 192, 192));
                statsButton.setColorFilter(Color.argb(255, 192, 192, 192));
                programsButton.setColorFilter(Color.argb(255, 192, 192, 192));
                break;
            case 1:
                challengeButton.setColorFilter(Color.argb(255, 192, 192, 192));
                eventButton.setColorFilter(null);
                statsButton.setColorFilter(Color.argb(255, 192, 192, 192));
                programsButton.setColorFilter(Color.argb(255, 192, 192, 192));

                break;
            case 2:
                challengeButton.setColorFilter(Color.argb(255, 192, 192, 192));
                eventButton.setColorFilter(Color.argb(255, 192, 192, 192));
                statsButton.setColorFilter(null);
                programsButton.setColorFilter(Color.argb(255, 192, 192, 192));

                break;
            case 3:
                challengeButton.setColorFilter(Color.argb(255, 192, 192, 192));
                eventButton.setColorFilter(Color.argb(255, 192, 192, 192));
                statsButton.setColorFilter(Color.argb(255, 192, 192, 192));
                programsButton.setColorFilter(null);
                break;
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        if (mShouldFinish){
            finish();
        }

    }


    public void onClickOnButton(int pos) {
        mpager.setCurrentItem(pos, true);

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

    private class ScreenSlidePagerAdapter extends FragmentPagerAdapter {
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
