package com.example.bands4hire.Fragments;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bands4hire.DataModels.Profile;
import com.example.bands4hire.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static com.example.bands4hire.Activities.MainActivity.advertTracker;
import static com.example.bands4hire.Activities.MainActivity.bandTracker;

/**
 * A simple {@link Fragment} subclass.
 */
public class SingleAdvert extends Fragment implements View.OnClickListener {

    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    TextView bandName, genre, location, price, date,
    email, phoneNumber, createdBy;

    Button emailButton, phoneButton, closeButton, bandProfileButton;

    public SingleAdvert() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_single_advert, container, false);

        bandName = view.findViewById(R.id.saBandName);
        genre = view.findViewById(R.id.saGenre);
        location = view.findViewById(R.id.saLocation);
        price = view.findViewById(R.id.saPrice);
        date = view.findViewById(R.id.saDate);
        email = view.findViewById(R.id.saEmail);
        phoneNumber = view.findViewById(R.id.saPhone);
        createdBy = view.findViewById(R.id.saCreatedBy);

        bandName.setText(advertTracker.getBandName());
        genre.setText(advertTracker.getGenre());
        location.setText(advertTracker.getLocation());
        price.setText(advertTracker.getPrice());
        date.setText(advertTracker.getDateAvailable());
        email.setText(advertTracker.getEmail());
        phoneNumber.setText(advertTracker.getPhoneNumber());
        createdBy.setText(advertTracker.getCreatedBy());

        emailButton = view.findViewById(R.id.emailButton);
        phoneButton = view.findViewById(R.id.phoneButton);
        closeButton = view.findViewById(R.id.backButton);
        /*I was in the process of setting up a profile for each band and letting users leave reviews on the profiles.
        * This was nearly setup but I had to give up on it due to time restraints*/
       // bandProfileButton = view.findViewById(R.id.bandProfileButton);

        emailButton.setOnClickListener(this);
        phoneButton.setOnClickListener(this);
        closeButton.setOnClickListener(this);

        /*
        bandProfileButton.setOnClickListener(this);
*/
        /*I was in the process of setting up a profile for each band and letting users leave reviews on the profiles.
         * This was nearly setup but I had to give up on it due to time restraints*/
        /*
        //storing band profile for use if user clicks View Profile button
        Query getBandProfile = mDatabase.child("bandProfiles").child(advertTracker.getBandId());
        getBandProfile.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    bandTracker = dataSnapshot.getValue(Profile.class);
                }else{
                    Toast.makeText(getContext(), "Profile Doesn't Exist", Toast.LENGTH_LONG).show();
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
*/
        return view;
    }

    @Override
    public void onClick(View v) {
        FragmentManager fragmentManager = getFragmentManager();
        switch (v.getId()){
            case R.id.emailButton:
                /*Code sourced from https://stackoverflow.com/questions/8701634/send-email-intent
                * The code for the launch email or launch phone number when a user wants to email or call a band was found above*/
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO,
                        Uri.fromParts("mailto", advertTracker.getEmail(),null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Booking Enquiry for "+advertTracker.getBandName()
                        +" "+advertTracker.getDateAvailable());
                emailIntent.putExtra(Intent.EXTRA_TEXT, "\n\n\n\n\n**Powered by Bands4Hire**");
                startActivity(Intent.createChooser(emailIntent,"Send email..."));
                break;

            case R.id.phoneButton:
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+advertTracker.getPhoneNumber()));
                startActivity(callIntent);
                break;

            case R.id.backButton:
                final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                AllAdverts mainFragment = new AllAdverts();

                fragmentTransaction.detach(SingleAdvert.this);
                fragmentTransaction.add(R.id.fragmentHolder, mainFragment);
                fragmentTransaction.commit();
                break;

            /*I was in the process of setting up a profile for each band and letting users leave reviews on the profiles.
             * This was nearly setup but I had to give up on it due to time restraints*/
            /*case R.id.bandProfileButton:

//                final FragmentTransaction fragmentTransaction1 = fragmentManager.beginTransaction();
//                BandProfile bandProfile = new BandProfile();
//
//                Log.v("SingleAdvert","BandTracker: "+bandTracker.getBandName());
//                fragmentTransaction1.detach(SingleAdvert.this);
//                fragmentTransaction1.add(R.id.fragmentHolder, bandProfile);
//                fragmentTransaction1.commit();
*/
        }
    }
}
