package com.example.bands4hire.Fragments;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.bands4hire.Adapters.MyRecyclerViewAdapter;
import com.example.bands4hire.DataModels.BandAdvert;
import com.example.bands4hire.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Comparator;

import static com.example.bands4hire.Activities.MainActivity.advertTracker;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyAdverts extends Fragment implements MyRecyclerViewAdapter.ItemClickListener {

    public MyRecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<BandAdvert> myAdverts = new ArrayList<>();
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    public MyAdverts() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_adverts, container, false);

        //if arraylist already has data clear to avoid duplication
        if(!myAdverts.isEmpty()){ myAdverts.clear(); }

        //configure recyclerview and adapter
        recyclerView = view.findViewById(R.id.myAdvertsRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        adapter = new MyRecyclerViewAdapter(getActivity(), myAdverts);

        //populating array with data from Firebase
        Query retrieveAdverts = mDatabase.child("users").child(currentUser.getUid()).child("myAdverts");
        retrieveAdverts.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //if adverts exists in this collection add them to adverts array
                if(dataSnapshot.exists()){
                    for(DataSnapshot thisSnapshot: dataSnapshot.getChildren()){
                        BandAdvert thisAdvert = thisSnapshot.getValue(BandAdvert.class);
                        myAdverts.add(thisAdvert);
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
        advertTracker = myAdverts.get(position);

        FragmentManager fragmentManager = getFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        EditAdvert editAdvert = new EditAdvert();
        fragmentTransaction.detach(MyAdverts.this);
        fragmentTransaction.add(R.id.fragmentHolder, editAdvert);
        fragmentTransaction.commit();
    }
}
