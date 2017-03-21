package com.fitness.centrale.centralefitness;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fitness.centrale.centralefitness.HomeActivity;
import com.fitness.centrale.centralefitness.requests.AuthWithToken;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {


    private void goToLogin(){
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }, 2000);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setTheme(R.style.AppTheme);

        //TODO : Check connexion to server first

        Prefs.initPreferencesManager(getBaseContext());

        String token = Prefs.getToken();
        if (!Objects.equals(token, "none")){
            ResponseObject obj = new AuthWithToken(token).prepareRequest();
            if (!obj.isAnError()){
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }, 2000);

            }else{
                goToLogin();
            }
        }else {
            goToLogin();
        }

    }
}
