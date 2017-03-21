package com.fitness.centrale.centralefitness.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.fitness.centrale.centralefitness.DetailsDialog;
import com.fitness.centrale.centralefitness.R;

/**
 * Created by remy on 21/03/17.
 */

public class StatsFragment extends android.support.v4.app.Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stats, container, false);


        LinearLayout velo = (LinearLayout) view.findViewById(R.id.VeloLayout);
        LinearLayout elliptique  = (LinearLayout) view.findViewById(R.id.ellipLayout);
        LinearLayout rameur = (LinearLayout) view.findViewById(R.id.RameurLayout);

        velo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DetailsDialog dialog = new DetailsDialog(getContext(), "Velo", "10", "30", "120", "650", "0.3", "0.9", "3", "8");
                dialog.setTitle("Velo");
                dialog.show();
            }
        });

        return view;
    }
}
