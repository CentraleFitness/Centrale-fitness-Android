package com.fitness.centrale.centralefitness;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by remy on 16/03/17.
 */

public class RequestLauncher extends Thread{

    final SendRequestObject object;
    final Paths type;

    RequestLauncher(SendRequestObject object, Paths type){
        this.object = object;
        this.type = type;

    }

    @Override
    public void run() {

        URL url = null;
        try {
            url = new URL(Constants.SERVER + type);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            con.setDoOutput(true);
            DataOutputStream wr;
            System.out.println("Etape 1");
            try {
                wr  = new DataOutputStream(con.getOutputStream());
            }catch (ConnectException e){
                object.response = new ResponseObject(true);
                object.isRunning = false;
                return;
            }
            wr.writeBytes(new GsonBuilder().create().toJson(object.messageToSend));
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            if (responseCode != 200){
                synchronized (object){
                    object.isRunning = false;
                    object.response = new ResponseObject(true);
                }
                return;
            }

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            synchronized (object){
                object.isRunning = false;
                Type listType = new TypeToken<HashMap<String, String>>() {}.getType();
                HashMap<String, String> tmp = new GsonBuilder().create().fromJson(response.toString(), listType);
                object.response = new ResponseObject();
                object.response.putAll(tmp);
            }
            return;

        } catch (IOException e) {
            e.printStackTrace();
        }

        synchronized (object){
            object.isRunning = false;
            object.response = new ResponseObject(true);
        }


    }
}
