package com.fitness.centrale.mobile.fragments.challenges;

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
import com.fitness.centrale.misc.Constants;
import com.fitness.centrale.misc.ImageUtility;
import com.fitness.centrale.misc.Prefs;
import com.fitness.centrale.misc.VolleyUtility;
import com.fitness.centrale.misc.store.Store;
import com.fitness.centrale.mobile.R;
import com.fitness.centrale.mobile.activities.EventDetailsActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ChallengeCardHolder extends RecyclerView.ViewHolder   {

    private Context context;
    private Activity parent;

    private CardView event;
    private TextView title;
    private ImageView eventPicture;
    private Bitmap eventPictureBitmap;
    private ImageView registeredImage;

    public ChallengeCardHolder(View itemView, Context context, Activity parent) {
        super(itemView);
        this.context = context;
        this.parent = parent;

        event = itemView.findViewById(R.id.cardViewEvent);
        title = itemView.findViewById(R.id.cardEventTitle);
        eventPicture = itemView.findViewById(R.id.eventPicture);
        registeredImage = itemView.findViewById(R.id.registerPicture);
    }


    public void setContent(final Date startDate,
                           final Date endDate,
                           final Boolean registered,
                           final String picture,
                           final String description,
                           final String id,
                           final String name){

        if (Store.getStore().getDemoObject().demo){
            eventPicture.setImageDrawable(context.getDrawable(R.drawable.roundlogo));
        }else
            new B64ToImageTask(picture, eventPicture).execute();

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        final String startDateStr = format.format(startDate);
        final String endDateStr = format.format(endDate);




        event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(parent, EventDetailsActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("id", id);
                intent.putExtra("startDate", startDateStr);
                intent.putExtra("endDate", endDateStr);
                intent.putExtra("registered", registered);
                intent.putExtra("description", description);

                ByteArrayOutputStream bStream = new ByteArrayOutputStream();
                if (!Store.getStore().getDemoObject().demo) {
                    eventPictureBitmap.compress(Bitmap.CompressFormat.PNG, 100, bStream);
                    byte[] byteArray = bStream.toByteArray();
                    intent.putExtra("picture", byteArray);
                }



                Pair<View, String> p1 = Pair.create((View)event, "eventOpening");
                //   Pair<View, String> p2 = Pair.create((View)eventPicture, "eventPictureAnimation");
                Pair<View, String> p3 = Pair.create((View)title, "eventTitleTransition");

                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(parent, p1);

                context.startActivity(intent, options.toBundle());
            }
        });

        if (!registered){
            registeredImage.setImageDrawable(context.getDrawable(R.drawable.error));
        }


    }

    public void bind(final BasicChallengeObject myObject){

        if (Store.getStore().getDemoObject().demo){

            title.setText(myObject.event.name);
            setContent(myObject.event.startDate,
                    myObject.event.endDate,
                    myObject.event.registered,
                    myObject.event.picture,
                    myObject.event.description,
                    myObject.event.id,
                    myObject.event.name);
            return;
        }

        title.setText(myObject.name);


        RequestQueue queue = Volley.newRequestQueue(myObject.context);



        Map<String, Object> params = new HashMap<>();
        params.put(Constants.TOKEN, Prefs.getPrefs(context).getToken());
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

                        setContent(startDate, endDate, registered, picture, description, myObject.id, myObject.name);


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



