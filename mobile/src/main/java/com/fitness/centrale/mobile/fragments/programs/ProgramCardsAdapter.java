package com.fitness.centrale.mobile.fragments.programs;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fitness.centrale.mobile.R;

import java.util.List;

public class ProgramCardsAdapter extends RecyclerView.Adapter<ProgramCardHolder> {

    List<BasicProgramObject> eventsIds;
    Context context;
    Activity parent;

    public ProgramCardsAdapter(List<BasicProgramObject> eventsIds, Context context, Activity parent){
        this.eventsIds = eventsIds;
        this.context = context;
        this.parent = parent;
    }

    @NonNull
    @Override
    public ProgramCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.program_card,parent,false);
        return new ProgramCardHolder(view, context, this.parent);    }

    @Override
    public void onBindViewHolder(@NonNull ProgramCardHolder holder, int position) {
        BasicProgramObject myObject = eventsIds.get(position);
        holder.bind(myObject);
    }

    @Override
    public int getItemCount() {
        return eventsIds.size();
    }
}
