package com.fitness.centrale.mobile.activities.social;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fitness.centrale.mobile.R;

import java.util.List;

public class SocialCommentAdapter extends RecyclerView.Adapter<CommentCardHolder> {

    List<BasicCommentObject> socialIds;
    Context context;
    AppCompatActivity parent;
    int viewIndex;
    boolean minimify;

    public SocialCommentAdapter(List<BasicCommentObject> socialIds, Context context, AppCompatActivity parent, boolean minimify){
        this.socialIds = socialIds;
        this.context = context;
        this.parent = parent;
        this.minimify = minimify;
        viewIndex = 0;

    }

    @NonNull
    @Override
    public CommentCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        int layout;
        layout = R.layout.comment_card;
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent,false);
        return new CommentCardHolder(view, context, this.parent);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentCardHolder holder, int position) {
        BasicCommentObject myObject = socialIds.get(position);
        holder.bind(myObject);
    }

    @Override
    public int getItemCount() {
        if (socialIds == null) {
            return 0;
        }
        return socialIds.size();
    }
}
