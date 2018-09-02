package com.fitness.centrale.mobile.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fitness.centrale.misc.Constants;
import com.fitness.centrale.misc.Prefs;
import com.fitness.centrale.mobile.R;
import com.fitness.centrale.misc.VolleyUtility;
import com.fitness.centrale.mobile.activities.profile.ProfileActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class SplashScreen extends AppCompatActivity {




    private void tryToConnect(){

        try {
            String resp = (String) new NetworkConnecter(getBaseContext()).execute().get();

            if (resp.equals("false")){
                final AlertDialog.Builder builder;

                builder = new AlertDialog.Builder(SplashScreen.this);
                builder.setTitle("Erreur").setMessage("Impossible de se connecter").setPositiveButton("Réessayer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        tryToConnect();
                    }
                }).show();

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slash_screen);


        PackageManager manager = getPackageManager();
        try {
            PackageInfo infos = manager.getPackageInfo(this.getPackageName(), 0);
            Constants.VERSION = infos.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        tryToConnect();





    }


    class NetworkConnecter extends AsyncTask{

        Context ctx;

        public NetworkConnecter(Context ctx){
            this.ctx = ctx;
        }

        @Override
        protected Object doInBackground(Object[] objects) {

            boolean exists = false;

            try {
                SocketAddress address = new InetSocketAddress(Constants.HOST, Integer.parseInt(Constants.PORT));

                Socket sock = new Socket();

                // This method will block no more than timeoutMs.
                // If the timeout occurs, SocketTimeoutException is thrown.
                int timeoutMs = 2000;   // 2 seconds
                sock.connect(address, timeoutMs);
                exists = true;


            } catch (IOException e) {
            }
            if (!exists){
                return "false";
            }



            RequestQueue queue = Volley.newRequestQueue(ctx);

            final Map<String, String> params = new HashMap<>();
            params.put(Constants.TOKEN, Prefs.getPrefs(SplashScreen.this).getToken());

            JsonObjectRequest request = new JsonObjectRequest(Constants.SERVER + Constants.AUTHTOKEN, new JSONObject(params),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                System.out.println("Response code : " + response.getString("code"));
                                if (response.getString("code").equals("201")){
                                    sendAffiliationRequest();
                                }else{
                                    Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
            queue.add(request);

            return "true";
        }
    }



    private void sendAffiliationRequest(){


        RequestQueue queue = Volley.newRequestQueue(getBaseContext());



        Map<String, Object> params = new HashMap<>();
        params.put(Constants.TOKEN, Prefs.getPrefs(this).getToken());
        JsonObjectRequest request = new JsonObjectRequest(Constants.SERVER + Constants.GET_AFFILIATION, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    System.out.println(response.getString("code"));
                    if (response.getString("code").equals("001")){


                        Intent intent = new Intent(SplashScreen.this, ProfileActivity.class);
                        startActivity(intent);
                        finish();

                    }else{

                        Intent intent = new Intent(SplashScreen.this, AffiliationActivity.class);
                        startActivity(intent);
                        finish();


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
