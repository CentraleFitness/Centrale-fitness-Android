package com.fitness.centrale.mobile.fragments.stats;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fitness.centrale.mobile.R;
import com.fitness.centrale.mobile.fragments.StatsFragment;
import com.fitness.centrale.mobile.fragments.event.BasicEventObject;
import com.fitness.centrale.mobile.fragments.event.EventCardHolder;

import java.util.List;

public class StatsCardsAdapter extends RecyclerView.Adapter<StatCardHolder> {

    List<StatsFragment.StatObject> eventsIds;
    Context context;
    Activity parent;

    public StatsCardsAdapter(List<StatsFragment.StatObject> eventsIds, Context context, Activity parent){
        this.eventsIds = eventsIds;
        this.context = context;
        this.parent = parent;
    }

    @NonNull
    @Override
    public StatCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stat_card,parent,false);
        return new StatCardHolder(view, context, this.parent);    }

    @Override
    public void onBindViewHolder(@NonNull StatCardHolder holder, int position) {
        StatsFragment.StatObject myObject = eventsIds.get(position);
        holder.bind(myObject);
    }

    @Override
    public int getItemCount() {
        return eventsIds.size();
    }
}
