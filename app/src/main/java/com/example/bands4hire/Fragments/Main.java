package com.example.bands4hire.Fragments;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bands4hire.Adapters.BandAdvertsAdapter;
import com.example.bands4hire.DataModels.BandAdvert;
import com.example.bands4hire.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.bands4hire.Activities.MainActivity.advertTracker;

public class Main extends Fragment implements BandAdvertsAdapter.ItemClickListener {


    //RecyclerView code sourced from https://stackoverflow.com/questions/40584424/simple-android-recyclerview-example
    public static BandAdvertsAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<BandAdvert> adverts = new ArrayList<>();
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();


    public Main() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        //if arraylist already has data clear to avoid duplication
        if(!adverts.isEmpty()){ adverts.clear(); }

        //configure recyclerview and adapter
        recyclerView = view.findViewById(R.id.allAdvertsRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        adapter = new BandAdvertsAdapter(getActivity(), adverts);

        //populating array with data from Firebase
        Query retrieveAdverts = mDatabase.child("adverts");
        retrieveAdverts.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //if adverts exists in this collection add them to adverts array
                if(dataSnapshot.exists()){
                    for(DataSnapshot thisSnapshot: dataSnapshot.getChildren()){
                        BandAdvert thisAdvert = thisSnapshot.getValue(BandAdvert.class);
                        adverts.add(thisAdvert);
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //finish configuration
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState){
        setHasOptionsMenu(true);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater){
        //making search icon in toolbar visible to user to allow filtering of adverts
        menu.findItem(R.id.action_search).setVisible(true);
        super.onCreateOptionsMenu(menu,menuInflater);
    }

    @Override
    public void onItemClick(View view, int position) {
        advertTracker = adverts.get(position);

        FragmentManager fragmentManager = getFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        SingleAdvert singleAdvert = new SingleAdvert();
        fragmentTransaction.detach(Main.this);
        fragmentTransaction.add(R.id.fragmentHolder, singleAdvert);
        fragmentTransaction.commit();

    }

}
