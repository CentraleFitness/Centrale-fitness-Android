package com.fitness.centrale.mobile.activities.social;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
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
import com.fitness.centrale.misc.AlertDialogBuilder;
import com.fitness.centrale.misc.Constants;
import com.fitness.centrale.misc.Prefs;
import com.fitness.centrale.misc.store.Store;
import com.fitness.centrale.mobile.R;
import com.fitness.centrale.mobile.activities.profile.ProfileActivity;

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
    private TextView comment;
    private TextView name;
    private LinearLayout lyt;


    public CommentCardHolder(View itemView, Context context, AppCompatActivity parent) {
        super(itemView);
        this.context = context;
        this.parent = parent;

        comment = itemView.findViewById(R.id.commentContent);
        name = itemView.findViewById(R.id.commentName);
        lyt = itemView.findViewById(R.id.postMainLayout);


    }





    public void bind(final BasicCommentObject myObject){

        comment.setText(myObject.postContent);
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy hh:mm");
        String dateStr = format.format(myObject.date);
        name.setText(myObject.name + " le " + dateStr);

        lyt.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                if (myObject.isMine || myObject.signaledByMe || myObject.isCenter){

                    AlertDialog.Builder builder = new AlertDialog.Builder(parent);
                    builder.setTitle("Erreur")
                            .setMessage(myObject.isMine ? "Vous ne pouvez pas signaler votre propre post" : myObject.signaledByMe ? "Vous avez déjà signalé ce post": myObject.isCenter ? "Vous ne pouvez pas signaler le post de votre salle de sport" : "")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();

                    return false;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(parent);
                builder.setTitle("Signaler")
                        .setMessage("Voulez vous signaler ce post ?")
                        .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                RequestQueue queue = Volley.newRequestQueue(context);


                                final Map<String, Object> params = new HashMap<>();
                                params.put(Constants.TOKEN, Prefs.getPrefs(context).getToken());
                                params.put(Constants.POSTID, myObject.id);

                                JsonObjectRequest request = new JsonObjectRequest(Constants.SERVER + "/post-report", new JSONObject(params),
                                        new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject response) {
                                                try {
                                                    System.out.println("Response code : " + response.getString("code"));
                                                    if (response.getString("code").equals("001")){

                                                        AlertDialog.Builder builder = new AlertDialog.Builder(parent);
                                                        builder.setTitle("Post signalé !")
                                                                .setMessage("Merci de nous aider à rendre Centrale Fitness plus convivial !")
                                                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(DialogInterface dialog, int which) {
                                                                        ((CommentsActivity)parent).refreshComments();
                                                                        dialog.dismiss();
                                                                    }
                                                                });
                                                        AlertDialog dialog = builder.create();
                                                        dialog.show();

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
                        })
                        .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
                return false;
            }
        });

    }



}



