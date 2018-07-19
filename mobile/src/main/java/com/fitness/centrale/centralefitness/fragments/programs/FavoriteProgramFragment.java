package com.fitness.centrale.centralefitness.fragments.programs;

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

import java.util.ArrayList;


/**
 * Created by Psyycker on 19/11/2017.
 */

public class FavoriteProgramFragment extends Fragment {

    private ArrayList<BasicProgramObject> itemsIdsList;
    private View view;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.programs_favorite_fragment, container, false);

        recyclerView = view.findViewById(R.id.registeredFragmentRecyclerView);
        swipeRefreshLayout = view.findViewById(R.id.allEventSwypeRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshPrograms();
            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        itemsIdsList = new ArrayList<>();
        recyclerView.setAdapter(new ProgramCardsAdapter(itemsIdsList, getContext(), getActivity()));

        return view;
    }



    public void setListAdapter(){


        recyclerView = view.findViewById(R.id.registeredFragmentRecyclerView);
        /*swipeRefreshLayout = view.findViewById(R.id.allEventSwypeRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshPrograms();
            }
        });*/


        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setAdapter(new ProgramCardsAdapter(itemsIdsList, getContext(), getActivity()));

    }

    public void refreshPrograms(){

/*
        RequestQueue queue = Volley.newRequestQueue(getContext());



        Map<String, Object> params = new HashMap<>();
        params.put(Constants.TOKEN, Prefs.getToken());
        itemsIdsList = new ArrayList<>();
        params.put(Constants.START, 0);
        params.put(Constants.END, 10);
        params.put(Constants.ISREG, true);
        JsonObjectRequest request = new JsonObjectRequest(Constants.SERVER + Constants.GET_EVENTS_IDS, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    System.out.println(response.getString("code"));
                    if (response.getString("code").equals("001")){


                        JSONArray events = (JSONArray) response.get("events");
                        int index  = 0;

                        while (index != events.length()){

                            JSONArray subArray = events.getJSONArray(index);

                            if (subArray.getBoolean(2))
                                itemsIdsList.add(new BasicProgramObject(subArray.getString(1), subArray.getString(0), getContext()));



                            index++;

                        }

                        setListAdapter();

                        swipeRefreshLayout.setRefreshing(false);


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        VolleyUtility.fixDoubleRequests(request);

        queue.add(request);*/

        itemsIdsList = new ArrayList<>();
        setListAdapter();


    }




    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        this.view = view;

        this.refreshPrograms();

    }

    //Ici, remplacer les int et autres par les vrais arguments de la map
    public static FavoriteProgramFragment newInstance() {
        FavoriteProgramFragment fragment = new FavoriteProgramFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }




}
