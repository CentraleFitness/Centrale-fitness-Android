package com.fitness.centrale.mobile.activities.profile;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
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
import com.fitness.centrale.misc.AlertDialogBuilder;
import com.fitness.centrale.misc.Constants;
import com.fitness.centrale.misc.ImageUtility;
import com.fitness.centrale.misc.Prefs;
import com.fitness.centrale.mobile.FireBaseEventLogger;
import com.fitness.centrale.mobile.R;
import com.fitness.centrale.misc.VolleyUtility;
import com.fitness.centrale.mobile.activities.EditProfileActivity;
import com.fitness.centrale.mobile.activities.FeedBackActivity;
import com.fitness.centrale.mobile.activities.dialogs.DisconnectDialog;
import com.fitness.centrale.mobile.activities.CenterActivity;
import com.fitness.centrale.mobile.activities.social.BasicSocialObject;
import com.fitness.centrale.mobile.activities.social.SocialActivity;
import com.fitness.centrale.mobile.activities.social.SocialCardsAdapter;
import com.fitness.centrale.misc.store.Store;
import com.vansuita.gaussianblur.GaussianBlur;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {


    private int PICK_IMAGE_REQUEST = 1;
    ImageView session;
    ImageView social;
    ImageView center;
    LinearLayout topProfileLyt;
    TextView presentationText;
    ImageView comment;

    private ArrayList<BasicSocialObject> itemsIdsList;
    private View view;
    private RecyclerView recyclerView;


    public void getGymId(){

        if (!Store.getStore().getUserObject().gymId.equals("")){
            return;
        }


        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        final Map<String, String> params = new HashMap<>();
        params.put(Constants.TOKEN, Prefs.getPrefs(this).getToken());

        JsonObjectRequest request = new JsonObjectRequest(Constants.SERVER + Constants.GET_AFFILIATION, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            System.out.println("Response code : " + response.getString("code"));
                            if (response.getString("code").equals("001")){

                                Store.getStore().getUserObject().gymId = response.getString(Constants.SPORTCENTERID);

                                refreshPosts();

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        FireBaseEventLogger.getLogger(this).logEvent(FireBaseEventLogger.Events.NEW_ACTIVITY, "open_profile_activity");

         session = findViewById(R.id.sessionButton);
         social = findViewById(R.id.socialButton);
         center = findViewById(R.id.profileButton);
         topProfileLyt = findViewById(R.id.topProfileLyt);
         presentationText = findViewById(R.id.presentationText);
         comment = findViewById(R.id.commentPicture);

         comment.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, FeedBackActivity.class);
                startActivity(intent);
             }
         });

         if (Store.getStore().getDemoObject().demo && !Store.getStore().getDemoObject().enterInDemo){
             AlertDialog.Builder builder = new AlertDialog.Builder(this);
             builder.setTitle("Bienvenue dans la beta Centrale Fitness !")
                     .setMessage("Durant cette session, toutes les données utilisées seront fausses.\n" +
                             "Néanmoins, l'expérience Central Fitness que vous allez essayer est elle parfaitement authentique.\n" +
                             "Les actions que vous allez effectuer durant cette phase de test ne seront pas sauvegardées une fois l'application quittée.\n" +
                             "Nous vous souhaitons une agréable expérience durant cette phase de test et vous remercions pour les commentaires !")
                     .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialog, int which) {
                             dialog.dismiss();
                             AlertDialogBuilder.createAlertDialog(ProfileActivity.this,
                                     "Page de profil",
                                     "La page de profil permet de visualiser sa photo de profil, son identifiant ainsi que les détails de la dernière session.\n" +
                                             "Vous pouvez également accéder via les petites icones en haut à la modification de votre profil ou à la deconnexion de votre compte.",
                                     "Ok").show();
                         }
                     });
             AlertDialog dialog = builder.create();
             dialog.show();
             Store.getStore().getDemoObject().enterInDemo = true;
         }




        session.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Pair<View, String> p1 = Pair.create((View)session, ViewCompat.getTransitionName(session));
                Pair<View, String> p2 = Pair.create((View)center, ViewCompat.getTransitionName(center));

                Intent intent = new Intent(ProfileActivity.this, CenterActivity.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(ProfileActivity.this, p1, p2);
                startActivity(intent, options.toBundle());

            }
        });

         social.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Pair<View, String> p1 = Pair.create((View)session, ViewCompat.getTransitionName(session));
                 Pair<View, String> p2 = Pair.create((View)social, ViewCompat.getTransitionName(social));

                 Intent intent = new Intent(ProfileActivity.this, SocialActivity.class);
                 ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(ProfileActivity.this, p1, p2);
                 startActivity(intent, options.toBundle());
             }
         });



        final TextView loginView = findViewById(R.id.profileLogin);
        final ImageView profilePicture = findViewById(R.id.profileProfilePicture);



        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        final Map<String, String> params = new HashMap<>();
        params.put(Constants.TOKEN, Prefs.getPrefs(this).getToken());

        JsonObjectRequest request = new JsonObjectRequest(Constants.SERVER + Constants.GETPROFILE, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            System.out.println("Response code : " + response.getString("code"));
                            if (response.getString("code").equals("001")){
                                Store.getStore().setUser(response.getString("first name"),
                                        response.getString("last name"),
                                        response.getString("email address"),
                                        response.getString("login"),
                                        response.getString("phone number"));
                                loginView.setText(Store.getStore().getUserObject().login);

                                getGymId();

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


        final Map<String, String> params2 = new HashMap<>();
        params2.put(Constants.TOKEN, Prefs.getPrefs(this).getToken());

        JsonObjectRequest request2 = new JsonObjectRequest(Constants.SERVER + Constants.GETPROFILEPICTURE, new JSONObject(params2),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            System.out.println("Response code : " + response.getString("code"));
                            if (response.getString("code").equals("001")){
                                Bitmap bitmap = ImageUtility.base64ToImage(response.getString(Constants.PICTURE));
                                profilePicture.setImageBitmap(bitmap);

                                Bitmap gaussianRender =  GaussianBlur.with(ProfileActivity.this).render(bitmap);
                                BitmapDrawable drawable = new BitmapDrawable(getResources(), gaussianRender);

                                topProfileLyt.setBackground(drawable);

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
        queue.add(request2);

        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });



        ImageView editProfileButton = findViewById(R.id.ProfileSettings);

        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                startActivity(intent);
                ProfileActivity.this.finish();
            }
        });

        LinearLayout disconnect = findViewById(R.id.ProfileDisconnect);

        disconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DisconnectDialog().show(getFragmentManager(), "Disconnect");
            }
        });


        recyclerView = findViewById(R.id.miniSocialView);

        itemsIdsList = new ArrayList<>();


        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(new SocialCardsAdapter(itemsIdsList,getBaseContext(), this, true));


    }

    private void refreshPosts(){



        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        itemsIdsList = new ArrayList<>();

        final Map<String, Object> params = new HashMap<>();
        params.put(Constants.TOKEN, Prefs.getPrefs(this).getToken());
        params.put(Constants.TARGETID, Store.getStore().getUserObject().gymId);
        params.put(Constants.START, 0);
        params.put(Constants.END, 5);

        //TODO Remplacer par la récupération de la dernière activité
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

                                    BasicSocialObject socialObject = new BasicSocialObject(postId, ProfileActivity.this, type);
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

        recyclerView.setAdapter(new SocialCardsAdapter(itemsIdsList,getBaseContext(), this, true));
        if (itemsIdsList.size() > 0){
            presentationText.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
        }else{
            presentationText.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);

        }

    }


    private void updateImage(final String b64img){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());


        Map<String, String> params = new HashMap<>();
        params.put(Constants.TOKEN, Prefs.getPrefs(this).getToken());
        params.put(Constants.PICTURE, b64img);
        JsonObjectRequest request = new JsonObjectRequest(Constants.SERVER + Constants.UPDATEPROFILEPICTURE, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getString("code").equals("001")){
                        ImageView imageView = (ImageView) findViewById(R.id.profileProfilePicture);
                        Bitmap bitmap = ImageUtility.base64ToImage(b64img);
                        imageView.setImageBitmap(bitmap);

                        Bitmap gaussianRender =  GaussianBlur.with(ProfileActivity.this).render(bitmap);
                        BitmapDrawable drawable = new BitmapDrawable(getResources(), gaussianRender);
                        topProfileLyt.setBackground(drawable);

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


    public Bitmap getResizedBitmap(Bitmap image, int bitmapWidth, int bitmapHeight) {
        return Bitmap.createScaledBitmap(image, bitmapWidth, bitmapHeight, true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));



                updateImage(ImageUtility.bitmapToBase64(getResizedBitmap(bitmap, 480, 270)));


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
