package com.example.bands4hire.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bands4hire.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SingleAdvert extends Fragment {


    public SingleAdvert() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_single_advertisement, container, false);
    }

}
