package com.fitness.centrale.centralefitness;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.google.zxing.Result;
import com.google.zxing.integration.android.IntentIntegrator;

public class ConnectActivity extends AppCompatActivity  {



    private boolean scannerOn = false;

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                if (scannerOn){
                    stopScanner();
                }
                return true;
        }
        return false;
    }

    public void handleResult(Result result) {
        System.out.println("RESULT : " + result.getText());
        scannerOn = false;
        //TODO : Send machine id to server before go to play activity
        Intent intent = new Intent(ConnectActivity.this, PlayActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void stopScanner(){
        scannerOn = false;
        Intent intent = new Intent(ConnectActivity.this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

           IntentIntegrator intentIntegrator =  new IntentIntegrator(this);
           intentIntegrator.setOrientationLocked(true);
           intentIntegrator.setBarcodeImageEnabled(true);
           intentIntegrator.initiateScan();

        scannerOn = true;
    }

}
