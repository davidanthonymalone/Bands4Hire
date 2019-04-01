package com.example.bands4hire.Fragments;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.bands4hire.DrawerLocker.DrawerLocker;
import com.example.bands4hire.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class FirstRunWizard extends Fragment implements View.OnClickListener {

    Button saveUserTypeButton;
    RadioButton bandRadioButton, bookerRadioButton;
    DatabaseReference myDatabase = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public FirstRunWizard() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first_run_wizard, container, false);

        ((DrawerLocker) getActivity()).setDrawerEnabled(false);

        saveUserTypeButton = view.findViewById(R.id.saveUserTypeButton);
        bandRadioButton = view.findViewById(R.id.bandRadioButton);
        bookerRadioButton = view.findViewById(R.id.bookerRadioButton);

        saveUserTypeButton.setOnClickListener(this);


        return view;
    }


    //When user clicks 'Save and Continue' button:
    // - check that one of radio buttons is selected, halt user if not,
    // - get value from selected radio button, push to database, then move user to main screen
    @Override
    public void onClick(View v) {
        boolean bandSelected = bandRadioButton.isChecked();
        boolean bookerSelected = bookerRadioButton.isChecked();

        FragmentManager fragmentManager = getFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Main mainFragment = new Main();

        if(!bandSelected && !bookerSelected){
            Toast.makeText(getActivity(), "A selection must be made before proceeding.", Toast.LENGTH_SHORT).show();
        }else if(bandSelected){
            myDatabase.child("users").child(user.getUid()).child("userType").setValue("Band");
            fragmentTransaction.detach(FirstRunWizard.this);
            fragmentTransaction.add(R.id.fragmentHolder, mainFragment);
            fragmentTransaction.commit();
        } else if(bookerSelected){
            myDatabase.child("users").child(user.getUid()).child("userType").setValue("Booker");
            fragmentTransaction.detach(FirstRunWizard.this);
            fragmentTransaction.add(R.id.fragmentHolder, mainFragment);
            fragmentTransaction.commit();
        }
    }
}
