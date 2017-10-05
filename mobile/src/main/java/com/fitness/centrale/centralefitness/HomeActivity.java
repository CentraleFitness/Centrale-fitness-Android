package com.fitness.centrale.centralefitness;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.fitness.centrale.centralefitness.fragment.ChallengesFragment;
import com.fitness.centrale.centralefitness.fragment.ElectricityFragment;
import com.fitness.centrale.centralefitness.fragment.HomeFragment;
import com.fitness.centrale.centralefitness.fragment.OptionsFragment;
import com.fitness.centrale.centralefitness.fragment.ProfileFragment;
import com.fitness.centrale.centralefitness.fragment.SocialFragment;
import com.fitness.centrale.centralefitness.fragment.StatsFragment;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {





    private void initContent(){
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.qrCodeButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ConnectActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }});

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        HomeFragment frag = new HomeFragment();
        transaction.replace(R.id.layoutContent, frag);
        transaction.commit();
        navigationView.setCheckedItem(0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContent();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Prefs.removeToken();
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (id == R.id.nav_home) {
            HomeFragment frag = new HomeFragment();
            transaction.replace(R.id.layoutContent, frag);
            // Handle the camera action
        } else if (id == R.id.nav_stats) {
            StatsFragment frag = new StatsFragment();
            transaction.replace(R.id.layoutContent, frag);
        } else if (id == R.id.nav_social) {
            SocialFragment frag = new SocialFragment();
            transaction.replace(R.id.layoutContent, frag);

        } else if (id == R.id.nav_profile) {
            ProfileFragment frag = new ProfileFragment();
            transaction.replace(R.id.layoutContent, frag);
        } else if (id == R.id.nav_options) {
            OptionsFragment frag = new OptionsFragment();
            transaction.replace(R.id.layoutContent, frag);

        }else if (id == R.id.nav_electricity){
            ElectricityFragment frag = new ElectricityFragment();
            transaction.replace(R.id.layoutContent, frag);

        }else if (id == R.id.nav_challenges){
            ChallengesFragment frag = new ChallengesFragment();
            transaction.replace(R.id.layoutContent, frag);

        }
        transaction.commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}