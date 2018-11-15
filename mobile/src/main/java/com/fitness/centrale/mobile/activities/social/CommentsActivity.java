package com.fitness.centrale.mobile.activities.social;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fitness.centrale.misc.Constants;
import com.fitness.centrale.misc.Prefs;
import com.fitness.centrale.mobile.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentsActivity extends AppCompatActivity {

    List<BasicCommentObject> objects;
    SwipeRefreshLayout refresh;
    RecyclerView recyclerView;
    String postId;

    private void refreshComments() {
        RequestQueue queue = Volley.newRequestQueue(this);


        final Map<String, Object> params = new HashMap<>();
        params.put(Constants.TOKEN, Prefs.getPrefs(this).getToken());
        params.put(Constants.POSTID, postId);
        params.put(Constants.START, 0);
        params.put(Constants.END, 100);

        JsonObjectRequest request = new JsonObjectRequest(Constants.SERVER + Constants.GET_POST_COMMENTS, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("code").equals("001")){
                                JSONArray comments = response.getJSONArray("comments");
                                for (int i = 0; i < comments.length(); i++) {
                                    JSONObject object = comments.getJSONObject(i);
                                    BasicCommentObject commentObject = new BasicCommentObject();
                                    commentObject.postContent = object.getString("comment content");
                                }
                                setAdapter();
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

    private void setAdapter(){


        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(new SocialCommentAdapter(objects,  getBaseContext(), this, false));

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        refresh = findViewById(R.id.commentRefresh);
        recyclerView = findViewById(R.id.commentRecyclerView);
        postId = getIntent().getStringExtra("postid");
        this.refreshComments();


    }
}
