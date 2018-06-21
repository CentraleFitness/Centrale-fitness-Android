package com.fitness.centrale.centralefitness.social;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fitness.centrale.centralefitness.R;

import java.util.ArrayList;
import java.util.List;

public class SocialCardsAdapter extends RecyclerView.Adapter<SocialCardHolder> {

    List<BasicSocialObject> socialIds;
    Context context;
    AppCompatActivity parent;
    int viewIndex;
    boolean minimify;

    public SocialCardsAdapter(ArrayList<BasicSocialObject> socialIds, Context context, AppCompatActivity parent, boolean minimify){
        this.socialIds = socialIds;
        this.context = context;
        this.parent = parent;
        this.minimify = minimify;
        viewIndex = 0;

    }

    @NonNull
    @Override
    public SocialCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        int layout;

        BasicSocialObject obj = socialIds.get(viewIndex);

        switch (obj.type){
            case PUBLICATION:
                if (!minimify)
                layout = R.layout.social_card;
                else
                    layout = R.layout.social_card_minimified;
                break;
            case EVENT:
                if (!minimify)
                layout = R.layout.social_event_card;
                else{
                    layout = R.layout.social_event_card;
                }
                break;
            default:
                layout = R.layout.social_card;
        }

        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent,false);

        viewIndex++;
        return new SocialCardHolder(view, context, this.parent);
    }

    @Override
    public void onBindViewHolder(@NonNull SocialCardHolder holder, int position) {
        BasicSocialObject myObject = socialIds.get(position);
        holder.bind(myObject);
    }

    @Override
    public int getItemCount() {
        return socialIds.size();
    }
}
