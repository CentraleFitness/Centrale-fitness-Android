package com.fitness.centrale.centralefitness.fragments.event;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fitness.centrale.centralefitness.Constants;
import com.fitness.centrale.centralefitness.EventDetailsActivity;
import com.fitness.centrale.centralefitness.ImageUtility;
import com.fitness.centrale.centralefitness.Prefs;
import com.fitness.centrale.centralefitness.R;
import com.fitness.centrale.centralefitness.VolleyUtility;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EventCardHolder  extends RecyclerView.ViewHolder   {

    private Context context;
    private Activity parent;

    private CardView event;
    private TextView title;
    private TextView startDateView;
    private TextView endDateView;
    private ImageView eventPicture;
    private TextView registeredText;
    private Bitmap eventPictureBitmap;

    public EventCardHolder(View itemView, Context context, Activity parent) {
        super(itemView);
        this.context = context;
        this.parent = parent;

        event = itemView.findViewById(R.id.cardViewEvent);
        title = itemView.findViewById(R.id.cardEventTitle);
        startDateView = itemView.findViewById(R.id.startDateText);
        endDateView = itemView.findViewById(R.id.endDateText);
        eventPicture = itemView.findViewById(R.id.eventPicture);
        registeredText = itemView.findViewById(R.id.registeredEventText);
    }



    public void bind(final BasicEventObject myObject){
        title.setText(myObject.name);



        RequestQueue queue = Volley.newRequestQueue(myObject.context);



        Map<String, Object> params = new HashMap<>();
        params.put(Constants.TOKEN, Prefs.getToken());
        params.put(Constants.EVENTID, myObject.id);
        JsonObjectRequest request = new JsonObjectRequest(Constants.SERVER + Constants.GET_EVENTS_PREVIEW, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    System.out.println(response.getString("code"));
                    if (response.getString("code").equals("001")){

                        Date startDate = new Date(Long.valueOf(response.getString("start date")));
                        Date endDate = new Date(Long.valueOf(response.getString("end date")));
                        final Boolean registered = Boolean.valueOf(response.getString("user_registered"));
                        final String picture = response.getString("picture");
                        final String description = response.getString("description");

                        new B64ToImageTask(picture, eventPicture).execute();

                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                        final String startDateStr = format.format(startDate);
                        final String endDateStr = format.format(endDate);

                        startDateView.setText(startDateStr);
                        endDateView.setText(endDateStr);



                        event.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(parent, EventDetailsActivity.class);
                                intent.putExtra("name", myObject.name);
                                intent.putExtra("id", myObject.id);
                                intent.putExtra("startDate", startDateStr);
                                intent.putExtra("endDate", endDateStr);
                                intent.putExtra("registered", registered);
                                intent.putExtra("description", description);

                                ByteArrayOutputStream bStream = new ByteArrayOutputStream();
                                eventPictureBitmap.compress(Bitmap.CompressFormat.PNG, 100, bStream);
                                byte[] byteArray = bStream.toByteArray();

                                intent.putExtra("picture", byteArray);



                                Pair<View, String> p1 = Pair.create((View)event, "eventOpening");
                             //   Pair<View, String> p2 = Pair.create((View)eventPicture, "eventPictureAnimation");
                                Pair<View, String> p3 = Pair.create((View)title, "eventTitleTransition");

                                ActivityOptionsCompat options = ActivityOptionsCompat.
                                        makeSceneTransitionAnimation(parent, p1);

                                context.startActivity(intent, options.toBundle());
                            }
                        });

                        registeredText.setText(registered ? "Inscrit" : "Non inscrit");


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



    private class B64ToImageTask extends AsyncTask{

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



