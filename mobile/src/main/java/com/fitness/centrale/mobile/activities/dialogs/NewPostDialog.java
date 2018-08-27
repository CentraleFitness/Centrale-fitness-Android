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

import com.fitness.centrale.misc.store.DemoObject;
import com.fitness.centrale.misc.store.Store;
import com.fitness.centrale.mobile.R;
import com.fitness.centrale.mobile.activities.social.SocialActivity;

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
                    //TODO : Faire le rajout de post via API
                }
                getDialog().dismiss();
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
