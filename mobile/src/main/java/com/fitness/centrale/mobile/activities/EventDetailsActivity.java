package com.fitness.centrale.mobile.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fitness.centrale.misc.Constants;
import com.fitness.centrale.misc.ImageUtility;
import com.fitness.centrale.misc.Prefs;
import com.fitness.centrale.misc.VolleyUtility;
import com.fitness.centrale.misc.store.Store;
import com.fitness.centrale.mobile.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EventDetailsActivity extends AppCompatActivity {


    ImageView eventPicture;
    Bitmap eventPictureBitmap;
    TextView title;
    TextView description;
    TextView startDate;
    TextView endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);



        eventPicture = findViewById(R.id.eventPicture);


        if (Store.getStore().getDemoObject().demo){
            eventPicture.setImageDrawable(getDrawable(R.drawable.roundlogo));
        }else  {

            RequestQueue queue = Volley.newRequestQueue(this);



            Map<String, Object> params = new HashMap<>();
            params.put(Constants.TOKEN, Prefs.getPrefs(this).getToken());
            params.put(Constants.EVENTID, getIntent().getStringExtra("id"));
            JsonObjectRequest request = new JsonObjectRequest(Constants.SERVER + Constants.GET_EVENTS_PREVIEW, new JSONObject(params), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        System.out.println(response.getString("code"));
                        if (response.getString("code").equals("001")){
                            final String picture = response.getString("picture");
                            new B64ToImageTask(picture, eventPicture).execute();
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

        title = findViewById(R.id.eventTitle);
        title.setText(getIntent().getStringExtra("name"));


        final TextView txt = findViewById(R.id.registerButtonEventText);
        final boolean registered = getIntent().getBooleanExtra("registered", false);
        if (registered)
            txt.setText("INSCRIT");


        description = findViewById(R.id.eventDescription);

        description.setText(getIntent().getStringExtra("description"));

        startDate = findViewById(R.id.eventDetailsStart);
        endDate = findViewById(R.id.eventDetailsEnd);
        startDate.setText(getIntent().getStringExtra("startDate"));
        endDate.setText(getIntent().getStringExtra("endDate"));


        LinearLayout lyt = findViewById(R.id.registerButtonEvent);
        lyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Store.getStore().getDemoObject().demo){

                    Store.getStore().getDemoObject().getEventById(getIntent().getStringExtra("id")).registered = true;

                    txt.setText("INSCRIT");
                    return;
                }

                if (registered)
                    return;

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

                final Map<String, String> params = new HashMap<>();
                params.put(Constants.TOKEN, Prefs.getPrefs(EventDetailsActivity.this).getToken());
                params.put(Constants.EVENTID, getIntent().getStringExtra("id"));

                JsonObjectRequest request = new JsonObjectRequest(Constants.SERVER + Constants.REGISTER_EVENT, new JSONObject(params),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    System.out.println("Response code : " + response.getString("code"));
                                    if (response.getString("code").equals("001")){
                                        Toast.makeText(getApplicationContext(), "Vous êtes bien inscrit à l'évenement", Toast.LENGTH_SHORT).show();


                                        TextView txt = findViewById(R.id.registerButtonEventText);
                                        txt.setText("INSCRIT");

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

            }
        });


    }


    private class B64ToImageTask extends AsyncTask {

        final String pictureB64;
        final ImageView imageView;

        public B64ToImageTask(final String pictureB64, final ImageView imageView){
            this.pictureB64 = pictureB64;
            this.imageView = imageView;
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            String []splitted = pictureB64.split(",");
            if (splitted.length == 2){
                return ImageUtility.base64ToImage(splitted[1]);
            }



            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            if (result != null) {
                eventPicture.setImageBitmap((Bitmap) result);
                eventPictureBitmap = (Bitmap) result;
            }
        }

    }
}
