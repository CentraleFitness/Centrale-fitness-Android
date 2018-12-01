package com.fitness.centrale.mobile.activities.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fitness.centrale.misc.Constants;
import com.fitness.centrale.misc.Prefs;
import com.fitness.centrale.misc.store.DemoObject;
import com.fitness.centrale.misc.store.Store;
import com.fitness.centrale.mobile.R;
import com.fitness.centrale.mobile.activities.social.BasicSocialObject;
import com.fitness.centrale.mobile.activities.social.SocialActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NewPostDialog extends DialogFragment {


    private SocialActivity activity;

    public String getPostContent(){
        return txt.getText().toString();
    }

    View view;
    EditText txt;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());

        LayoutInflater inflater = LayoutInflater.from(getContext());

        view = inflater.inflate(R.layout.new_post_dialog, null, false);

        txt = view.findViewById(R.id.postContentEdit);
        Button submit = view.findViewById(R.id.submitButton);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Store.getStore().getDemoObject().demo){
                    DemoObject.Post post = new DemoObject.Post();
                    post.poster = "Vous";
                    post.content = txt.getText().toString();
                    Store.getStore().getDemoObject().postsList.add(post);
                    activity.refreshPosts();
                }else{
                    RequestQueue queue = Volley.newRequestQueue(getContext());


                    final Map<String, Object> params = new HashMap<>();
                    params.put(Constants.TOKEN, Prefs.getPrefs(getContext()).getToken());
                    params.put(Constants.POSTTYPE, BasicSocialObject.PostType.PUBLICATION.value);
                    params.put(Constants.POSTCONTENT, txt.getText().toString());
                    params.put(Constants.POSTICON, "");

                    JsonObjectRequest request = new JsonObjectRequest(Constants.SERVER + Constants.CREATE_POST, new JSONObject(params),
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        System.out.println("Response code : " + response.getString("code"));
                                        if (response.getString("code").equals("001")){
                                            getDialog().dismiss();
                                            activity.refreshPosts();

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
                    //TODO : Faire le rajout de post via API
                }
            }
        });

        builder.setView(view);



        return builder.create();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getDialog().getWindow().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);


        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void setActivity(SocialActivity activity) {
        this.activity = activity;
    }
}
