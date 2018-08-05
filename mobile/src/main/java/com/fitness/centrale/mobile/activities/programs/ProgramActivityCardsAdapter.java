package com.fitness.centrale.mobile.activities.programs;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fitness.centrale.mobile.R;
import com.fitness.centrale.mobile.fragments.event.BasicEventObject;

import java.util.List;

public class ProgramActivityCardsAdapter extends RecyclerView.Adapter<ProgramActivityCardHolder> {

    List<BasicActivityObject> activitiesIds;
    Context context;
    Activity parent;

    public ProgramActivityCardsAdapter(List<BasicActivityObject> activitiesIds, Context context, Activity parent){
        this.activitiesIds = activitiesIds;
        this.context = context;
        this.parent = parent;
    }

    @NonNull
    @Override
    public ProgramActivityCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.program_activity_card,parent,false);
        return new ProgramActivityCardHolder(view, context, this.parent);    }

    @Override
    public void onBindViewHolder(@NonNull ProgramActivityCardHolder holder, int position) {
        BasicActivityObject myObject = activitiesIds.get(position);
        holder.bind(myObject);
    }

    @Override
    public int getItemCount() {
        return activitiesIds.size();
    }
}
