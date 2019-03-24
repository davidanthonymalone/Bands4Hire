package com.example.bands4hire.Fragments;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bands4hire.R;

import static com.example.bands4hire.Activities.MainActivity.advertTracker;

/**
 * A simple {@link Fragment} subclass.
 */
public class SingleAdvert extends Fragment implements View.OnClickListener {


    TextView bandName, genre, location, price, date,
    email, phoneNumber, createdBy;

    Button emailButton, phoneButton, closeButton;

    public SingleAdvert() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_single_advertisement, container, false);

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

        emailButton.setOnClickListener(this);
        phoneButton.setOnClickListener(this);
        closeButton.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.emailButton:
                //Code sourced from https://stackoverflow.com/questions/8701634/send-email-intent
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
                FragmentManager fragmentManager = getFragmentManager();
                final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Main mainFragment = new Main();

                fragmentTransaction.detach(SingleAdvert.this);
                fragmentTransaction.add(R.id.fragmentHolder, mainFragment);
                fragmentTransaction.commit();
                break;
        }
    }
}
