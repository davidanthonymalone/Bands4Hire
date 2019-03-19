package com.example.bands4hire.Fragments;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bands4hire.Adapters.MyRecyclerViewAdapter;
import com.example.bands4hire.DataModels.BandAdvert;
import com.example.bands4hire.R;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Queue;

public class Main extends Fragment implements MyRecyclerViewAdapter.ItemClickListener {


    //RecyclerView code sourced from https://stackoverflow.com/questions/40584424/simple-android-recyclerview-example
    MyRecyclerViewAdapter adapter;
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
        adapter = new MyRecyclerViewAdapter(getActivity(), adverts);

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

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(getActivity(), "You clicked " + adapter.getItem(position).getBandName() + " on row number " + position, Toast.LENGTH_SHORT).show();
    }

}
