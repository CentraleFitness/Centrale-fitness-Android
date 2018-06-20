package com.fitness.centrale.centralefitness.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.fitness.centrale.centralefitness.Constants;
import com.fitness.centrale.centralefitness.Prefs;
import com.fitness.centrale.centralefitness.R;
import com.fitness.centrale.centralefitness.VolleyUtility;
import com.fitness.centrale.centralefitness.newdesign.ProfileActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AffiliationActivity extends AppCompatActivity implements QRCodeReaderView.OnQRCodeReadListener {


    private QRCodeReaderView qrCodeReaderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affiliation);


        qrCodeReaderView = findViewById(R.id.qrcodeView);
        qrCodeReaderView.setOnQRCodeReadListener(this);

        // Use this function to enable/disable decoding
        qrCodeReaderView.setQRDecodingEnabled(true);



        qrCodeReaderView.setBackCamera();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        1234);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.


            // Permission is not granted
        }




    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        System.out.println();
        switch (requestCode) {
            case 1234: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        qrCodeReaderView.startCamera();


    }

    @Override
    protected void onPause() {
        super.onPause();

        qrCodeReaderView.stopCamera();
    }

    @Override
    public void onQRCodeRead(String text, PointF[] points) {

        qrCodeReaderView.stopCamera();

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        Map<String, Object> params = new HashMap<>();
        params.put(Constants.TOKEN, Prefs.getToken());
        params.put(Constants.AFFILIATIONTOKEN, text);
        JsonObjectRequest request = new JsonObjectRequest(Constants.SERVER + Constants.AFFILIATE, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    System.out.println(response.getString("code"));
                    if (response.getString("code").equals("001")){


                        Intent intent = new Intent(AffiliationActivity.this, ProfileActivity.class);
                        startActivity(intent);
                        finish();



                    }else{
                        qrCodeReaderView.startCamera();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        VolleyUtility.fixDoubleRequests(request);

        queue.add(request);


    }
}
