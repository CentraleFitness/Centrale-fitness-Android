package com.fitness.centrale.mobile.fragments.challenges;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fitness.centrale.mobile.R;

import java.util.ArrayList;
import java.util.List;

public class ChallengeCardsAdapter extends RecyclerView.Adapter<ChallengeCardHolder> {

    List<BasicChallengeObject> eventsIds;
    List<BasicChallengeObject> line = new ArrayList<>();
    Context context;
    Activity parent;

    public ChallengeCardsAdapter(List<BasicChallengeObject> eventsIds, Context context, Activity parent){
        this.eventsIds = eventsIds;
        this.context = context;
        this.parent = parent;
    }

    @NonNull
    @Override
    public ChallengeCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.challenge_card,parent,false);
        return new ChallengeCardHolder(view, context, this.parent);    }

    @Override
    public void onBindViewHolder(@NonNull ChallengeCardHolder holder, int position) {
        BasicChallengeObject myObject = eventsIds.get(position);
        line.add(myObject);
        if (line.size() == 3) {
            holder.bind(line);
            line = new ArrayList<>();
            return;
        }
        if (eventsIds.size() == position + 1 && line.size() > 0)
            holder.bind(line);
    }

    @Override
    public int getItemCount() {
        return eventsIds.size();
    }
}
