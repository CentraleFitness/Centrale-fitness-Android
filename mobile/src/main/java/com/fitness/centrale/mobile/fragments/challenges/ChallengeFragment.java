package com.fitness.centrale.mobile.fragments.challenges;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fitness.centrale.misc.Constants;
import com.fitness.centrale.misc.Prefs;
import com.fitness.centrale.misc.VolleyUtility;
import com.fitness.centrale.misc.store.DemoObject;
import com.fitness.centrale.misc.store.Store;
import com.fitness.centrale.mobile.R;
import com.fitness.centrale.mobile.fragments.event.EventsFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Psyycker on 19/11/2017.
 */

public class ChallengeFragment extends Fragment {

    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<BasicChallengeObject> itemsIdsList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.challenge_fragment, container, false);

        recyclerView = view.findViewById(R.id.challengeRecycler);
        swipeRefreshLayout = view.findViewById(R.id.challengeSwipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshChallenges();
            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        itemsIdsList = new ArrayList<>();
        recyclerView.setAdapter(new ChallengeCardsAdapter(itemsIdsList, getContext(), getActivity()));

        refreshChallenges();


        return view;


    }


    public void setListAdapter(){
        recyclerView.setAdapter(new ChallengeCardsAdapter(itemsIdsList, getContext(), getActivity()));
        swipeRefreshLayout.setRefreshing(false);
    }


    public void refreshChallenges(){

        itemsIdsList = new ArrayList<>();


        RequestQueue queue = Volley.newRequestQueue(getContext());



        Map<String, Object> params = new HashMap<>();
        params.put(Constants.TOKEN, Prefs.getPrefs(getContext()).getToken());
        JsonObjectRequest request = new JsonObjectRequest(Constants.SERVER + Constants.GET_CHALLENGES, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    System.out.println(response.getString("code"));
                    if (response.getString("code").equals("001")){


                        JSONArray challenges = (JSONArray) response.get("challenges");
                        int index  = 0;
                        while (index != challenges.length()){

                            JSONArray subArray = challenges.getJSONArray(index);
                            JSONObject obj = (JSONObject) subArray.get(0);
                            BasicChallengeObject object = new BasicChallengeObject(ChallengeFragment.this.getContext());
                            object.type = obj.getString("type");
                            object.title = obj.getString("title");
                            object.desc = obj.getString("description");
                            object.endDate = obj.getString("endDate");
                            object.owner = obj.getString("owner");
                            object.steps = obj.getString("steps");
                            object.machine = obj.getString("machine");
                            object.pointsNeeded = obj.getString("pointsNeeded");

                            itemsIdsList.add(object);



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


    }


    //Ici, remplacer les int et autres par les vrais arguments de la map
    public static ChallengeFragment newInstance() {
        ChallengeFragment fragment = new ChallengeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }



}
