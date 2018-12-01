package com.fitness.centrale.mobile.fragments.programs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
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
import com.fitness.centrale.mobile.activities.programs.ProgramDetailsActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProgramCardHolder extends RecyclerView.ViewHolder   {

    private Context context;
    private Activity parent;

    private CardView program;
    private TextView title;
    private ImageView programPicture;

    public ProgramCardHolder(View itemView, Context context, Activity parent) {
        super(itemView);
        this.context = context;
        this.parent = parent;

        program = itemView.findViewById(R.id.cardViewProgram);
        title = itemView.findViewById(R.id.cardProgramTitle);
        programPicture = itemView.findViewById(R.id.programPicture);
    }



    public void bind(final BasicProgramObject myObject){
        title.setText(myObject.name);


        RequestQueue queue = Volley.newRequestQueue(context);



        Map<String, Object> params = new HashMap<>();
        params.put(Constants.TOKEN, Prefs.getPrefs(context).getToken());
        params.put(Constants.CUSTOMPROGRAMID, myObject.id);
        params.put(Constants.GYMID, Store.getStore().getUserObject().gymId);
        JsonObjectRequest request = new JsonObjectRequest(Constants.SERVER + Constants.GET_CUSTOM_PROGRAM_PREVIEW, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(final JSONObject response) {
                try {
                    System.out.println(response.getString("code"));
                    if (response.getString("code").equals("001")){
                        final int duration = Integer.parseInt(response.getString("duration"));
                        final String logo = response.getString("logo");
                        if (!logo.equals("")) {
                            new B64ToImageTask(logo, programPicture).execute();
                        }
                        program.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(parent, ProgramDetailsActivity.class);
                                intent.putExtra("name", myObject.name);
                                intent.putExtra("id", myObject.id);
                                intent.putExtra("duration", String.valueOf(duration));
                                parent.startActivity(intent);
                            }
                        });

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
                programPicture.setImageBitmap((Bitmap) result);
                //programPictureBitMap = (Bitmap) result;
            }
        }

    }






}



