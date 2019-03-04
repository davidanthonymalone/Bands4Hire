package com.example.bands4hire.Activities;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.bands4hire.Fragments.AddBand;
import com.example.bands4hire.Fragments.Main;
import com.example.bands4hire.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    FragmentManager fragmentManager = getFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager.beginTransaction().replace(R.id.fragmentHolder, new AddBand()).addToBackStack("MainFragment").commit();
        fragmentManager.executePendingTransactions();

    }
}
