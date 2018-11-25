package com.fitness.centrale.mobile.activities.social;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fitness.centrale.misc.Constants;
import com.fitness.centrale.misc.ImageUtility;
import com.fitness.centrale.misc.Prefs;
import com.fitness.centrale.misc.store.Store;
import com.fitness.centrale.mobile.R;
import com.fitness.centrale.mobile.activities.EventDetailsActivity;
import com.stfalcon.frescoimageviewer.ImageViewer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class SocialCardHolder extends RecyclerView.ViewHolder   {

    private Context context;
    private AppCompatActivity parent;


    //POST
    private LinearLayout lyt;
    private TextView postContent;
    private TextView postDate;
    private TextView posterName;
    private LinearLayout likeBtn;
    private LinearLayout commentBtn;
    private TextView likeNumber;
    private TextView commentTxt;
    private JSONArray comments;
    private CircleImageView circleImageView;

    //EVENT
    private ImageView eventPicture;
    private TextView eventTitle;
    private TextView eventDesc;

    //PHOTO
    private ImageView photo;
    private TextView photoTitle;
    private TextView photoDesc;

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
        circleImageView = itemView.findViewById(R.id.postImageView);

        eventPicture = itemView.findViewById(R.id.eventPicture);
        eventTitle = itemView.findViewById(R.id.eventTitle);
        eventDesc = itemView.findViewById(R.id.eventDescription);

        photo = itemView.findViewById(R.id.photoPicture);
        photoTitle = itemView.findViewById(R.id.photoTitle);
        photoDesc = itemView.findViewById(R.id.photoDescription);
    }


    public void setContent(final Date date, final String content, String name, String isCenter){

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy hh:mm");
        String dateStr = format.format(date);

        postContent.setText(content);
        posterName.setText(name);
        int id = isCenter.equals("true") ? R.drawable.store : R.drawable.boy;

        circleImageView.setImageDrawable(parent.getDrawable(id));

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
            setContent(myObject.post.postDate, myObject.post.content, "toto", "true");

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
                                if (myObject.type == BasicSocialObject.PostType.EVENT) {
                                    String picture = response.getString("post picture");
                                    String title = response.getString("post title");
                                    String desc = response.getString("post content");

                                    Date startDate = new Date(Long.valueOf(response.getString("post start_date")));
                                    Date endDate = new Date(Long.valueOf(response.getString("post end_date")));
                                    setContentEvent(picture, title, desc);

                                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                                    final String startDateStr = format.format(startDate);
                                    final String endDateStr = format.format(endDate);

                                    final Intent intent = new Intent(parent, EventDetailsActivity.class);
                                    intent.putExtra("name", title);
                                    intent.putExtra("id", response.getString("post event_id"));
                                    intent.putExtra("startDate", startDateStr);
                                    intent.putExtra("endDate", endDateStr);
                                    intent.putExtra("registered", response.getString("isreg"));
                                    intent.putExtra("description", desc);

                                    lyt.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            parent.startActivity(intent);
                                        }
                                    });

                                    return;
                                } else if (myObject.type == BasicSocialObject.PostType.PHOTO) {
                                    setContentPicture(response.getString("post picture"),
                                            response.getString("post title"),
                                            response.getString("post content"));
                                    return;
                                }

                                setContent(date, content, response.getString("name"), response.getString("isCenter"));
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

    public void setContentEvent(String picture, String title, String desc) {
        new B64ToImageTask(picture, eventPicture).execute();
        eventTitle.setText(title);
        eventDesc.setText(desc);
    }

    public void setContentPicture(String picture, String title, String desc) {
        new B64ToImageTask(picture, photo).execute();
        photoTitle.setText(title);
        photoDesc.setText(desc);
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
        protected void onPostExecute(final Object result) {
            if (result != null) {
                imageView.setImageBitmap((Bitmap) result);

            }
        }

    }


}



