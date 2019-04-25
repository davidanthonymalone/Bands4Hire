package com.example.bands4hire.Fragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.bands4hire.DataModels.BandAdvert;
import com.example.bands4hire.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.widget.Toast.*;
import static com.example.bands4hire.Activities.MainActivity.advertTracker;
import static com.example.bands4hire.Fragments.AddAdvert.isValidEmail;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditAdvert extends Fragment implements View.OnClickListener {


    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    EditText editBandNameInput, editGenreInput, editDateInput, editEmailInput,
    editPhoneInput, editLocationInput;
    Spinner editPriceSpinner, editGenreSpinner;
    Button saveButton;
    Button deleteButton;
    MyAdverts myAdvertAdapter;

    public EditAdvert() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_advert, container, false);

        editBandNameInput = view.findViewById(R.id.editBandNameInput);
        editGenreSpinner = view.findViewById(R.id.genreSpinnerEdit);
        editDateInput = view.findViewById(R.id.editDateInput);
        editEmailInput = view.findViewById(R.id.editEmailInput);
        editPhoneInput = view.findViewById(R.id.editTelNumberInput);
        editLocationInput = view.findViewById(R.id.editLocationInput);
        editPriceSpinner = view.findViewById(R.id.editPriceSpinner);
        saveButton = view.findViewById(R.id.editBandButton);
        deleteButton = view.findViewById(R.id.deleteBandAdvert);

        saveButton.setOnClickListener(this);
        deleteButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
               // makeText(getContext(), "Clicked on Button", LENGTH_LONG).show();

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Are you sure you want to delete this review?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                mDatabase.child("adverts").child(advertTracker.getAdvertId()).removeValue();
                                mDatabase.child("users").child(currentUser.getUid()).child("myAdverts")
                                        .child(advertTracker.getAdvertId()).removeValue();

                                FragmentManager fragmentManager = getFragmentManager();
                                final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                MyAdverts myAdverts = new MyAdverts();
                                fragmentTransaction.detach(EditAdvert.this);
                                fragmentTransaction.add(R.id.fragmentHolder, myAdverts);
                                fragmentTransaction.commit();





                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                Dialog dialog = builder.create();
                dialog.show();

            }
        });

        editBandNameInput.setText(advertTracker.getBandName());
        editLocationInput.setText(advertTracker.getLocation());
        editPhoneInput.setText(advertTracker.getPhoneNumber());
        editEmailInput.setText(advertTracker.getEmail());
        editDateInput.setText(advertTracker.getDateAvailable());
       editGenreSpinner.setSelection(findIndex(editGenreSpinner, advertTracker.getGenre()));
        editPriceSpinner.setSelection(findIndex(editPriceSpinner, advertTracker.getPrice()));

        return view;
    }

    @Override
    public void onClick(View v) {
//Ensuring all fields have had data entered into them by currentUser
        if(editBandNameInput.getText().toString().equals("")  ||
                editLocationInput.getText().toString().equals("") || editDateInput.getText().toString().equals("") ||
                editPhoneInput.getText().toString().equals("") || editEmailInput.getText().toString().equals("")){
            makeText(getContext(), "All fields must be filled out before saving.", LENGTH_SHORT).show();
        }
        //Ensuring the date entered matches the desired format (further validation required to make sure dates are valid, eg not 31/02 etc.)
        else if(editDateInput.getText().toString().length() != 10 ||
                editDateInput.getText().charAt(2) != '/' || editDateInput.getText().charAt(5) != '/'){
            makeText(getContext(), "Date format incorrect, please follow model dd/mm/yyyy", LENGTH_SHORT).show();
        }
        //Ensuring that the email is in correct format
        else if(!isValidEmail(editEmailInput.getText().toString())){
            makeText(getContext(), "Email format incorrect, please check entered data.", LENGTH_SHORT).show();
        }
        //Validating phone number length, further validation to be added
        else if(editPhoneInput.getText().length() < 7){
            makeText(getContext(), "Phone number is too short.", LENGTH_SHORT).show();
        }
        else {
            final BandAdvert editAdvert = new BandAdvert(
                    editBandNameInput.getText().toString(),
                    editGenreSpinner.getSelectedItem().toString(),
                    editLocationInput.getText().toString(),
                    editPriceSpinner.getSelectedItem().toString(),
                    editDateInput.getText().toString(),
                    editEmailInput.getText().toString(),
                    editPhoneInput.getText().toString(),
                    currentUser.getUid(),
                    advertTracker.getAdvertId(),
                    null //TODO CHANGE TO BANDID
            );

            //pushing modified advert to all and user advert collections
            mDatabase.child("adverts").child(advertTracker.getAdvertId()).setValue(editAdvert);
            mDatabase.child("users").child(currentUser.getUid()).child("myAdverts")
                    .child(advertTracker.getAdvertId()).setValue(editAdvert);

            makeText(getContext(), "Changes have been saved.", LENGTH_SHORT).show();
            FragmentManager fragmentManager = getFragmentManager();
            final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            MyAdverts myAdverts = new MyAdverts();
            fragmentTransaction.detach(EditAdvert.this);
            fragmentTransaction.add(R.id.fragmentHolder, myAdverts);
            fragmentTransaction.commit();
        }
    }


    public void deleteAdvert(View v)
    {
        makeText(getContext(), "Clicked on Button", LENGTH_LONG).show();
    }
    //Finding position in array for given string, then setting spinner to match objects price value
    private int findIndex(Spinner spinner, String price){
        for(int i = 0; i<spinner.getCount(); i++){
            if(spinner.getItemAtPosition(i).toString().equalsIgnoreCase(price)){
                return i;
            }
        }
        return 0;
    }
}
