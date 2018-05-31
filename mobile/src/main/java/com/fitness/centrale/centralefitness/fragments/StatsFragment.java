package com.fitness.centrale.centralefitness.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fitness.centrale.centralefitness.R;
import com.fitness.centrale.centralefitness.fragments.event.BasicEventObject;
import com.fitness.centrale.centralefitness.fragments.event.EventCardsAdapter;
import com.fitness.centrale.centralefitness.fragments.stats.StatsCardsAdapter;

import java.util.ArrayList;

import javax.crypto.Mac;


/**
 * Created by Psyycker on 19/11/2017.
 */

public class StatsFragment extends Fragment {


    public enum MachineTypes{
        BIKE("bike", R.drawable.bikelogo),
        ELLIPTICAL("elliptical", R.drawable.elliptiquelogo);


        public final String machineName;
        public final int machineLogo;

        MachineTypes(String machine, int machineLogo) {
            this.machineName = machine;
            this.machineLogo = machineLogo;
        }
    }


    public class StatObject{

        public String date;
        public int duration;
        public MachineTypes device;


    }

    private ArrayList<StatObject> itemsList;
    private View view;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;


    public void setListAdapter(){


        recyclerView = view.findViewById(R.id.StatFragmentRecyclerView);
        //swipeRefreshLayout = view.findViewById(R.id.allEventSwypeRefresh);
/*        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshStats();
            }
        });*/


        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setAdapter(new StatsCardsAdapter(itemsList, getContext(), getActivity()));

    }


    private void refreshStats(){

        itemsList = new ArrayList<>();

        StatObject obj = new StatObject();
        obj.date = "12/03/2018";
        obj.device = MachineTypes.BIKE;
        obj.duration = 120;

        itemsList.add(obj);

         obj = new StatObject();
        obj.date = "13/03/2018";
        obj.device = MachineTypes.BIKE;
        obj.duration = 140;

        itemsList.add(obj);

        obj = new StatObject();
        obj.date = "14/03/2018";
        obj.device = MachineTypes.ELLIPTICAL;
        obj.duration = 90;

        itemsList.add(obj);



        setListAdapter();

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {




        return inflater.inflate(R.layout.stats_fragment, container, false);


    }






    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        this.view = view;
        refreshStats();



        super.onViewCreated(view, savedInstanceState);


    }

    //Ici, remplacer les int et autres par les vrais arguments de la map
    public static StatsFragment newInstance() {
        StatsFragment fragment = new StatsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


}
