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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fitness.centrale.centralefitness.Constants;
import com.fitness.centrale.centralefitness.Prefs;
import com.fitness.centrale.centralefitness.R;
import com.fitness.centrale.centralefitness.fragments.event.BasicEventObject;
import com.fitness.centrale.centralefitness.fragments.event.EventCardsAdapter;
import com.fitness.centrale.centralefitness.fragments.stats.StatsCardsAdapter;
import com.fitness.centrale.centralefitness.social.BasicSocialObject;
import com.fitness.centrale.centralefitness.social.SocialActivity;
import com.fitness.centrale.centralefitness.store.Store;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        public String id;


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



        RequestQueue queue = Volley.newRequestQueue(getContext());

        itemsList = new ArrayList<>();

        final Map<String, Object> params = new HashMap<>();
        params.put(Constants.TOKEN, Prefs.getToken());
        params.put(Constants.START, 0);
        params.put(Constants.END, 10);

        JsonObjectRequest request = new JsonObjectRequest(Constants.SERVER + Constants.GET_STATS_SESSIONS, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            System.out.println("Response code : " + response.getString("code"));
                            if (response.getString("code").equals("001")){

                                JSONArray postsArray = response.getJSONArray("session id").getJSONArray(0);


                                for (int index = 0; index < postsArray.length(); index++){

                                    String id = postsArray.getString(index);

                                    StatObject object = new StatObject();
                                    object.id = id;
                                    object.device = MachineTypes.BIKE;
                                    object.date = "12/03/2018";
                                    object.duration = 120;
                                    itemsList.add(object);



                                }

                                setListAdapter();



                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        queue.add(request);



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
