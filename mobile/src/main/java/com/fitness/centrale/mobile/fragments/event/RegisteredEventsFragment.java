package com.fitness.centrale.mobile.fragments.event;

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
import com.fitness.centrale.misc.Constants;
import com.fitness.centrale.misc.Prefs;
import com.fitness.centrale.misc.store.DemoObject;
import com.fitness.centrale.misc.store.Store;
import com.fitness.centrale.mobile.R;
import com.fitness.centrale.misc.VolleyUtility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Psyycker on 19/11/2017.
 */

public class RegisteredEventsFragment extends Fragment {

    private ArrayList<BasicEventObject> itemsIdsList;
    private View view;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.event_registered_fragment, container, false);

        recyclerView = view.findViewById(R.id.registeredFragmentRecyclerView);
        swipeRefreshLayout = view.findViewById(R.id.allEventSwypeRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshEvents();
            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        itemsIdsList = new ArrayList<>();
        recyclerView.setAdapter(new EventCardsAdapter(itemsIdsList, getContext(), getActivity()));


        return view;
    }



    public void setListAdapter(){


        recyclerView.setAdapter(new EventCardsAdapter(itemsIdsList, getContext(), getActivity()));
        swipeRefreshLayout.setRefreshing(false);

    }

    public void refreshEvents(){


        itemsIdsList = new ArrayList<>();

        if (Store.getStore().getDemoObject().demo){

            for (DemoObject.Event event : Store.getStore().getDemoObject().getRegisteredEvents()){
                BasicEventObject obj = new BasicEventObject(event.id, event.name, getContext());
                obj.event = event;
                itemsIdsList.add(obj);
            }

            setListAdapter();

            return;
        }



        RequestQueue queue = Volley.newRequestQueue(getContext());



        Map<String, Object> params = new HashMap<>();
        params.put(Constants.TOKEN, Prefs.getPrefs(getContext()).getToken());
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
                                itemsIdsList.add(new BasicEventObject(subArray.getString(1), subArray.getString(0), getContext()));



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

        queue.add(request);

    }




    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        this.view = view;

        this.refreshEvents();

    }

    //Ici, remplacer les int et autres par les vrais arguments de la map
    public static RegisteredEventsFragment newInstance() {
        RegisteredEventsFragment fragment = new RegisteredEventsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }




}
