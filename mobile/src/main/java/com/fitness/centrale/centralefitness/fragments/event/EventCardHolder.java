package com.fitness.centrale.centralefitness.fragments.event;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.fitness.centrale.centralefitness.R;

public class EventCardHolder extends RecyclerView.ViewHolder {

    private TextView title;

    public EventCardHolder(View itemView) {
        super(itemView);

        title = itemView.findViewById(R.id.cardEventTitle);
    }

    public void bind(BasicEventObject myObject){
        title.setText(myObject.name);

    }
}
