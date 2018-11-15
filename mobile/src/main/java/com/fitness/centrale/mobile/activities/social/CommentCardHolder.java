package com.fitness.centrale.mobile.activities.social;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fitness.centrale.misc.Constants;
import com.fitness.centrale.misc.Prefs;
import com.fitness.centrale.misc.store.Store;
import com.fitness.centrale.mobile.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CommentCardHolder extends RecyclerView.ViewHolder   {

    private Context context;
    private AppCompatActivity parent;


    public CommentCardHolder(View itemView, Context context, AppCompatActivity parent) {
        super(itemView);
        this.context = context;
        this.parent = parent;



    }





    public void bind(final BasicCommentObject myObject){


    }



}



