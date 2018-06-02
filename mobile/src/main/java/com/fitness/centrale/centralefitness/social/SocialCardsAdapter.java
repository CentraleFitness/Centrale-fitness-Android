package com.fitness.centrale.centralefitness.social;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fitness.centrale.centralefitness.R;
import com.fitness.centrale.centralefitness.fragments.event.BasicEventObject;
import com.fitness.centrale.centralefitness.fragments.event.EventCardHolder;

import java.util.ArrayList;
import java.util.List;

public class SocialCardsAdapter extends RecyclerView.Adapter<SocialCardHolder> {

    List<BasicSocialObject> eventsIds;
    Context context;
    Activity parent;

    public SocialCardsAdapter(ArrayList<BasicSocialObject> eventsIds, Context context, Activity parent){
        this.eventsIds = eventsIds;
        this.context = context;
        this.parent = parent;
    }

    @NonNull
    @Override
    public SocialCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.social_card,parent,false);
        return new SocialCardHolder(view, context, this.parent);
    }

    @Override
    public void onBindViewHolder(@NonNull SocialCardHolder holder, int position) {
        BasicSocialObject myObject = eventsIds.get(position);
        holder.bind(myObject);
    }

    @Override
    public int getItemCount() {
        return eventsIds.size();
    }
}
