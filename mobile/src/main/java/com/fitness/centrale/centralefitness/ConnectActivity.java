package com.fitness.centrale.centralefitness;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ConnectActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {


    private ZXingScannerView mScannerView;
    private boolean scannerOn = false;

    @Override
    protected void onPause() {
        super.onPause();
        mScannerView.stopCamera();
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

    @Override
    public void handleResult(Result result) {
        System.out.println("RESULT : " + result.getText());
        mScannerView.stopCamera();
        scannerOn = false;
        //TODO : Send machine id to server before go to play activity
        Intent intent = new Intent(ConnectActivity.this, PlayActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void stopScanner(){
        mScannerView.stopCamera();
        scannerOn = false;
        Intent intent = new Intent(ConnectActivity.this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);


                mScannerView = new ZXingScannerView(ConnectActivity.this);
                setContentView(mScannerView);
                mScannerView.setResultHandler(ConnectActivity.this);
                mScannerView.startCamera();
                scannerOn = true;
    }

}
