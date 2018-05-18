package com.fitness.centrale.centralefitness;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.fitness.centrale.centralefitness.store.Store;

import org.json.JSONException;
import org.json.JSONObject;

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


        byte[] byteArray = getIntent().getByteArrayExtra("picture");
        title = findViewById(R.id.eventTitle);
        title.setText(getIntent().getStringExtra("name"));


        TextView txt = findViewById(R.id.registerButtonEventText);
        final boolean registered = getIntent().getBooleanExtra("registered", false);
        if (registered)
            txt.setText("INSCRIT");

        eventPictureBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        eventPicture.setImageBitmap(eventPictureBitmap);

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

                if (registered)
                    return;

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

                final Map<String, String> params = new HashMap<>();
                params.put(Constants.TOKEN, Prefs.getToken());
                params.put(Constants.EVENTID, getIntent().getStringExtra("id"));

                JsonObjectRequest request = new JsonObjectRequest(Constants.SERVER + Constants.GETPROFILE, new JSONObject(params),
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
}
