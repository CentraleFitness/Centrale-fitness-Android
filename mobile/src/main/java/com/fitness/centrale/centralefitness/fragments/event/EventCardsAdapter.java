package com.fitness.centrale.centralefitness.fragments.event;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fitness.centrale.centralefitness.R;

import java.util.List;

public class EventCardsAdapter extends RecyclerView.Adapter<EventCardHolder> {

    List<BasicEventObject> eventsIds;

    public EventCardsAdapter(List<BasicEventObject> eventsIds){
        this.eventsIds = eventsIds;
    }

    @NonNull
    @Override
    public EventCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_card,parent,false);
        return new EventCardHolder(view);    }

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
