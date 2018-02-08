package com.fitness.centrale.centralefitness;

import android.app.ActionBar;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.fitness.centrale.centralefitness.customcomponent.BottomNavigationViewHelper;
import com.fitness.centrale.centralefitness.fragments.BadgeFragment;
import com.fitness.centrale.centralefitness.fragments.NotifFragment;
import com.fitness.centrale.centralefitness.fragments.PlusFragment;
import com.fitness.centrale.centralefitness.fragments.ProfileFragment;
import com.fitness.centrale.centralefitness.fragments.PromoFragment;


public class HomeActivity extends AppCompatActivity {


    final int PAGE_NUMBER = 5;
    ViewPager mpager;
    ScreenSlidePagerAdapter adapter;


    private static ProfileFragment profileFragment = null;
    private static PromoFragment promoFragment = null;
    private static BadgeFragment badgeFragment = null;
    private static NotifFragment notifFragment = null;
    private static PlusFragment plusFragment = null;


    private static ProfileFragment getProfileFragment(){

        if (profileFragment == null){
            profileFragment = ProfileFragment.newInstance();
        }
        return profileFragment;
    }

    private static PromoFragment getPromoFragment(){

        if (promoFragment == null){
            promoFragment = PromoFragment.newInstance(0, "Profile");
        }
        return promoFragment;
    }
    private static BadgeFragment getBadgeFragment(){

        if (badgeFragment == null){
            badgeFragment = BadgeFragment.newInstance(0, "Profile");
        }
        return badgeFragment;
    }
    private static NotifFragment getNotifFragment(){

        if (notifFragment == null){
            notifFragment = NotifFragment.newInstance(0, "Profile");
        }
        return notifFragment;
    }
    private static PlusFragment getPlusFragment(){

        if (plusFragment == null){
            plusFragment = PlusFragment.newInstance(0, "Profile");
        }
        return plusFragment;
    }





    private TextView mTextMessage;
    private FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_profile:
                    onClickOnButton(0);
                    return true;
                case R.id.navigation_promo:
                    onClickOnButton(1);

                    return true;
                case R.id.navigation_location:
                    onClickOnButton(2);

                    return true;
                case R.id.navigation_notifications:
                    onClickOnButton(3);

                    return true;
                case R.id.navigation_plus:
                    onClickOnButton(4);

                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);




        Prefs.initPreferencesManager(getBaseContext());

        mpager = (ViewPager) findViewById(R.id.mainFragment);
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

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


    public void button1(View view) {
        onClickOnButton(0);
    }

    public void button2(View view) {
        onClickOnButton(1);
    }

    public void button3(View view) {
        onClickOnButton(2);
    }

    public void button4(View view) {
        onClickOnButton(3);
    }

    public void button5(View view) {
        onClickOnButton(4);
    }

    public void onClickOnButton(int pos) {
        mpager.setCurrentItem(pos);


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


    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return getProfileFragment();
                case 1:
                    return getPromoFragment();
                case 2:
                    return getBadgeFragment();
                case 3:
                    return getNotifFragment();
                case 4:
                    return getPlusFragment();
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

