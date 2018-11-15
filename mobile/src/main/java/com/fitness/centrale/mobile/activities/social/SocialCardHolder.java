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
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import lecho.lib.hellocharts.model.Line;

public class SocialCardHolder extends RecyclerView.ViewHolder   {

    private Context context;
    private AppCompatActivity parent;


    private LinearLayout lyt;
    private TextView postContent;
    private TextView postDate;
    private TextView posterName;
    private LinearLayout likeBtn;
    private LinearLayout commentBtn;
    private TextView likeNumber;
    private TextView commentTxt;
    private JSONArray comments;

    public SocialCardHolder(View itemView, Context context, AppCompatActivity parent) {
        super(itemView);
        this.context = context;
        this.parent = parent;


        postContent = itemView.findViewById(R.id.postContent);
        postDate = itemView.findViewById(R.id.postDate);
        posterName = itemView.findViewById(R.id.posterName);
        lyt = itemView.findViewById(R.id.postMainLayout);
        likeBtn = itemView.findViewById(R.id.LikeBtn);
        commentBtn = itemView.findViewById(R.id.CommentBtn);
        commentTxt = itemView.findViewById(R.id.CommentBtnTxt);
        likeNumber = itemView.findViewById(R.id.likeNumber);


    }


    public void setContent(final Date date, final String content){

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy hh:mm");
        String dateStr = format.format(date);

        postContent.setText(content);

        SocialCardHolder.this.postDate.setText(dateStr);
    }

    public void refreshLikes(BasicSocialObject myObject) {
        RequestQueue queue = Volley.newRequestQueue(context);


        final Map<String, Object> params = new HashMap<>();
        params.put(Constants.TOKEN, Prefs.getPrefs(context).getToken());
        params.put(Constants.POSTID, myObject.id);

        JsonObjectRequest request = new JsonObjectRequest(Constants.SERVER + Constants.GET_POST_LIKES, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("code").equals("001")){
                                int likes = response.getInt("likes");
                                likeNumber.setText(String.valueOf(likes));
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

    private void refreshComs(BasicSocialObject myObject) {
        RequestQueue queue = Volley.newRequestQueue(context);


        final Map<String, Object> params = new HashMap<>();
        params.put(Constants.TOKEN, Prefs.getPrefs(context).getToken());
        params.put(Constants.POSTID, myObject.id);
        params.put(Constants.START, 0);
        params.put(Constants.END, 100);

        JsonObjectRequest request = new JsonObjectRequest(Constants.SERVER + Constants.GET_POST_COMMENTS, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("code").equals("001")){
                                JSONArray comments = response.getJSONArray("comments");
                                SocialCardHolder.this.comments = comments;
                                commentTxt.setText("Commentaires " + comments.length());
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


    public void bind(final BasicSocialObject myObject){

        if (likeBtn != null)
            this.refreshLikes(myObject);

        if (commentBtn != null)
            this.refreshComs(myObject);

        if (Store.getStore().getDemoObject().demo){

            posterName.setText(myObject.post.poster);
            setContent(myObject.post.postDate, myObject.post.content);

            return;
        }

        if (likeBtn != null)
            likeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RequestQueue queue = Volley.newRequestQueue(context);


                    final Map<String, Object> params = new HashMap<>();
                    params.put(Constants.TOKEN, Prefs.getPrefs(context).getToken());
                    params.put(Constants.POSTID, myObject.id);

                    JsonObjectRequest request = new JsonObjectRequest(Constants.SERVER + Constants.LIKE_POST, new JSONObject(params),
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        if (response.getString("code").equals("001")){
                                            refreshLikes(myObject);
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

        if (commentBtn != null) {
            commentBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, CommentsActivity.class);
                    intent.putExtra("postid", myObject.id);
                    parent.startActivityForResult(intent, 0);
                }
            });
        }

        RequestQueue queue = Volley.newRequestQueue(context);


        final Map<String, Object> params = new HashMap<>();
        params.put(Constants.TOKEN, Prefs.getPrefs(context).getToken());
        params.put(Constants.POSTID, myObject.id);

        JsonObjectRequest request = new JsonObjectRequest(Constants.SERVER + Constants.GET_POST_CONTENT, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            System.out.println("Response code : " + response.getString("code"));
                            if (response.getString("code").equals("001")){

                                long postDate = response.getLong("post date");
                                Date date = new Date(postDate);
                                String content = response.getString("post content");

                                setContent(date, content);
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



}



