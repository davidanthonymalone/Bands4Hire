package com.example.bands4hire.Fragments;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static com.example.bands4hire.Activities.MainActivity.advertTracker;
import static com.example.bands4hire.Fragments.AddBand.isValidEmail;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditAdvert extends Fragment implements View.OnClickListener {


    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    EditText editBandNameInput, editGenreInput, editDateInput, editEmailInput,
    editPhoneInput, editLocationInput;
    Spinner editPriceSpinner;
    Button saveButton;

    public EditAdvert() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_advert, container, false);

        editBandNameInput = view.findViewById(R.id.editBandNameInput);
        editGenreInput = view.findViewById(R.id.editGenreInput);
        editDateInput = view.findViewById(R.id.editDateInput);
        editEmailInput = view.findViewById(R.id.editEmailInput);
        editPhoneInput = view.findViewById(R.id.editTelNumberInput);
        editLocationInput = view.findViewById(R.id.editLocationInput);
        editPriceSpinner = view.findViewById(R.id.editPriceSpinner);
        saveButton = view.findViewById(R.id.editBandButton);

        saveButton.setOnClickListener(this);

        editBandNameInput.setText(advertTracker.getBandName());
        editLocationInput.setText(advertTracker.getLocation());
        editPhoneInput.setText(advertTracker.getPhoneNumber());
        editEmailInput.setText(advertTracker.getEmail());
        editDateInput.setText(advertTracker.getDateAvailable());
        editGenreInput.setText(advertTracker.getGenre());

        editPriceSpinner.setSelection(findIndex(editPriceSpinner, advertTracker.getPrice()));

        return view;
    }

    @Override
    public void onClick(View v) {
//Ensuring all fields have had data entered into them by currentUser
        if(editBandNameInput.getText().toString().equals("") || editGenreInput.getText().toString().equals("") ||
                editLocationInput.getText().toString().equals("") || editDateInput.getText().toString().equals("") ||
                editPhoneInput.getText().toString().equals("") || editEmailInput.getText().toString().equals("")){
            Toast.makeText(getContext(), "All fields must be filled out before saving.", Toast.LENGTH_SHORT).show();
        }
        //Ensuring the date entered matches the desired format (further validation required to make sure dates are valid, eg not 31/02 etc.)
        else if(editDateInput.getText().toString().length() != 10 ||
                editDateInput.getText().charAt(2) != '/' || editDateInput.getText().charAt(5) != '/'){
            Toast.makeText(getContext(), "Date format incorrect, please follow model dd/mm/yyyy", Toast.LENGTH_SHORT).show();
        }
        //Ensuring that the email is in correct format
        else if(!isValidEmail(editEmailInput.getText().toString())){
            Toast.makeText(getContext(), "Email format incorrect, please check entered data.", Toast.LENGTH_SHORT).show();
        }
        //Validating phone number length, further validation to be added
        else if(editPhoneInput.getText().length() < 7){
            Toast.makeText(getContext(), "Phone number is too short.", Toast.LENGTH_SHORT).show();
        }
        else {
            final BandAdvert editAdvert = new BandAdvert(
                    editBandNameInput.getText().toString(),
                    editGenreInput.getText().toString(),
                    editLocationInput.getText().toString(),
                    editPriceSpinner.getSelectedItem().toString(),
                    editDateInput.getText().toString(),
                    editEmailInput.getText().toString(),
                    editPhoneInput.getText().toString(),
                    currentUser.getUid(),
                    advertTracker.getAdvertId()
            );

            //pushing modified advert to all and user advert collections
            mDatabase.child("adverts").child(advertTracker.getAdvertId()).setValue(editAdvert);
            mDatabase.child("users").child(currentUser.getUid()).child("myAdverts")
                    .child(advertTracker.getAdvertId()).setValue(editAdvert);

            Toast.makeText(getContext(), "Changes have been saved.", Toast.LENGTH_SHORT).show();
            FragmentManager fragmentManager = getFragmentManager();
            final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            MyAdverts myAdverts = new MyAdverts();
            fragmentTransaction.detach(EditAdvert.this);
            fragmentTransaction.add(R.id.fragmentHolder, myAdverts);
            fragmentTransaction.commit();
        }
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
