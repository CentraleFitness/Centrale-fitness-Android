package com.fitness.centrale.mobile.activities.social;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fitness.centrale.misc.AlertDialogBuilder;
import com.fitness.centrale.misc.Constants;
import com.fitness.centrale.misc.Prefs;
import com.fitness.centrale.misc.store.DemoObject;
import com.fitness.centrale.mobile.R;
import com.fitness.centrale.mobile.activities.dialogs.NewPostDialog;
import com.fitness.centrale.mobile.activities.CenterActivity;
import com.fitness.centrale.misc.store.Store;
import com.fitness.centrale.mobile.activities.profile.ProfileActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SocialActivity extends AppCompatActivity {


    private ArrayList<BasicSocialObject> itemsIdsList;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FloatingActionButton newPostButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social);

        if (Store.getStore().getDemoObject().demo && !Store.getStore().getDemoObject().enterInSocial){
            AlertDialogBuilder.createAlertDialog(this, "Page social",
                    "La page social vous permet de consulter le fil d'actualité propre à votre salle de sport.\n" +
                            "Vous avez la possibilité de voir ce que disent vos camarades de salle ainsi que votre salle de sport.\n" +
                            "Enfin, vous avez vous même la possibilité de poster quelque chose sur le mur afin de vous exprimer!", "Ok",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Store.getStore().getDemoObject().enterInSocial = true;
                        }
                    }).show();
        }

        recyclerView = findViewById(R.id.socialRecyclerView);
        newPostButton = findViewById(R.id.newPostButton);
        swipeRefreshLayout = findViewById(R.id.SocialRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                refreshPosts();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        newPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final NewPostDialog dialog = new NewPostDialog();
                dialog.setActivity(SocialActivity.this);
                dialog.show(getSupportFragmentManager(), "New Post");
            }
        });

        refreshPosts();
        final ImageView sessionBtn = findViewById(R.id.sessionButton);
        final ImageView profileBtn = findViewById(R.id.profileButton);
        final ImageView socialBtn = findViewById(R.id.socialButton);

        sessionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pair<View, String> p1 = Pair.create((View)sessionBtn, ViewCompat.getTransitionName(sessionBtn));
                Pair<View, String> p2 = Pair.create((View)socialBtn, ViewCompat.getTransitionName(socialBtn));

                Intent intent = new Intent(SocialActivity.this, CenterActivity.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(SocialActivity.this, p1, p2);
                startActivity(intent, options.toBundle());

            }
        });

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pair<View, String> p1 = Pair.create((View)profileBtn, ViewCompat.getTransitionName(profileBtn));
                Pair<View, String> p2 = Pair.create((View)socialBtn, ViewCompat.getTransitionName(socialBtn));

                Intent intent = new Intent(SocialActivity.this, ProfileActivity.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(SocialActivity.this, p1, p2);
                startActivity(intent, options.toBundle());

            }
        });

    }

    public void refreshPosts(){

        itemsIdsList = new ArrayList<>();

        if (Store.getStore().getDemoObject().demo){

            for (DemoObject.Post post :  Store.getStore().getDemoObject().postsList){

                BasicSocialObject obj = new BasicSocialObject("", this, BasicSocialObject.PostType.PUBLICATION);
                obj.post = post;
                itemsIdsList.add(obj);
            }

            Collections.reverse(itemsIdsList);

            setAdapter();

            return;
        }


        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());


        final Map<String, Object> params = new HashMap<>();
        params.put(Constants.TOKEN, Prefs.getPrefs(this).getToken());
        params.put(Constants.TARGETID, Store.getStore().getUserObject().gymId);
        params.put(Constants.START, 0);
        params.put(Constants.END, 10);

        JsonObjectRequest request = new JsonObjectRequest(Constants.SERVER + Constants.GET_POSTS, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            System.out.println("Response code : " + response.getString("code"));
                            if (response.getString("code").equals("001")){

                                JSONArray postsArray = response.getJSONArray("posts");

                                for (int index = 0; index < postsArray.length(); index++){

                                    JSONObject object = postsArray.getJSONObject(index);

                                    BasicSocialObject.PostType type = BasicSocialObject.PostType.valueOf(object.getString("post type"));
                                    String postId = object.getString("post id");

                                    BasicSocialObject socialObject = new BasicSocialObject(postId, SocialActivity.this, type);
                                    itemsIdsList.add(socialObject);

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

        recyclerView.setAdapter(new SocialCardsAdapter(itemsIdsList,getBaseContext(), this, false));

    }
}
