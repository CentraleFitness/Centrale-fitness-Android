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

import java.util.List;

public class SocialCardsAdapter extends RecyclerView.Adapter<EventCardHolder> {

    List<BasicEventObject> eventsIds;
    Context context;
    Activity parent;

    public SocialCardsAdapter(List<BasicEventObject> eventsIds, Context context, Activity parent){
        this.eventsIds = eventsIds;
        this.context = context;
        this.parent = parent;
    }

    @NonNull
    @Override
    public EventCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_card,parent,false);
        return new EventCardHolder(view, context, this.parent);    }

    @Override
    public void onBindViewHolder(@NonNull EventCardHolder holder, int position) {
        BasicEventObject myObject = eventsIds.get(position);
        holder.bind(myObject);
    }

    @Override
    public int getItemCount() {
        return eventsIds.size();
    }
}
