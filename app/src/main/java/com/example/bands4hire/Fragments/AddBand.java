package com.example.bands4hire.Fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bands4hire.DataModels.BandAdvert;
import com.example.bands4hire.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class AddBand extends Fragment implements View.OnClickListener {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase database;
    DatabaseReference myRef;

    EditText bandNameInput, genreInput, locationInput, dateInput,
            telNumberInput, emailInput;
    Spinner priceSpinner;
    Button saveBandButton;

    public AddBand() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_band, container, false);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        bandNameInput = view.findViewById(R.id.bandNameInput);
        genreInput = view.findViewById(R.id.genreInput);
        locationInput = view.findViewById(R.id.locationInput);
        dateInput = view.findViewById(R.id.dateInput);
        telNumberInput = view.findViewById(R.id.telNumberInput);
        emailInput = view.findViewById(R.id.emailInput);
        priceSpinner = view.findViewById(R.id.priceSpinner);
        saveBandButton = view.findViewById(R.id.saveBandButton);

        saveBandButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {

        //Ensuring all fields have had data entered into them by user
        if(bandNameInput.getText().toString().equals("") || genreInput.getText().toString().equals("") ||
                locationInput.getText().toString().equals("") || dateInput.getText().toString().equals("") ||
                telNumberInput.getText().toString().equals("") || emailInput.getText().toString().equals("")){
            Toast.makeText(getContext(), "All fields must be filled out before saving.", Toast.LENGTH_SHORT).show();
        }
        //Ensuring the date entered matches the desired format (further validation required to make sure dates are valid, eg not 31/02 etc.)
        else if(dateInput.getText().toString().length() != 10 ||
                dateInput.getText().charAt(2) != '/' || dateInput.getText().charAt(5) != '/'){
            Toast.makeText(getContext(), "Date format incorrect, please follow model dd/mm/yyyy", Toast.LENGTH_SHORT).show();
        }
        //Ensuring that the email is in correct format
        else if(!isValidEmail(emailInput.getText().toString())){
            Toast.makeText(getContext(), "Email format incorrect, please check entered data.", Toast.LENGTH_SHORT).show();
        }
        //Validating phone number length, further validation to be added
        else if(telNumberInput.getText().length() < 7){
            Toast.makeText(getContext(), "Phone number is too short.", Toast.LENGTH_SHORT).show();
        }
        else {
            final BandAdvert newAdvert = new BandAdvert(
                    bandNameInput.getText().toString(),
                    genreInput.getText().toString(),
                    locationInput.getText().toString(),
                    priceSpinner.getSelectedItem().toString(),
                    dateInput.getText().toString(),
                    telNumberInput.getText().toString(),
                    emailInput.getText().toString()
            );

            myRef.child("adverts").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //getting current number of adverts to create ID for new advert
                    int currentNumber = (int) dataSnapshot.getChildrenCount();
                    int newID = currentNumber++;

                    //adding advert to collection, when complete reset form and display success message
                    myRef.child("adverts").child("advert00" + newID).setValue(newAdvert).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            bandNameInput.setText("");
                            genreInput.setText("");
                            locationInput.setText("");
                            priceSpinner.setSelection(0);
                            dateInput.setText("");
                            telNumberInput.setText("");
                            emailInput.setText("");

                            Toast.makeText(getContext(), "Advert saved successfully", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getContext(), "Something went wrong, please try again: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }

    }

    //Method sourced from https://stackoverflow.com/a/15808057
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

}
