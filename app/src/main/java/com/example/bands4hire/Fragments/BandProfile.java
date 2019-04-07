package com.example.bands4hire.Fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bands4hire.Adapters.ReviewAdapter;
import com.example.bands4hire.DataModels.Review;
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

import static com.example.bands4hire.Activities.MainActivity.advertTracker;
import static com.example.bands4hire.Activities.MainActivity.bandTracker;

/**
 * A simple {@link Fragment} subclass.
 */
public class BandProfile extends Fragment implements View.OnClickListener {

    static DatabaseReference myDatabase = FirebaseDatabase.getInstance().getReference();
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    TableLayout profileTable, reviewTable;
    TextView bandName, genre, profile;
    Button reviewPanelButton, submitReviewButton,
    clearReviewButton, phoneButton, emailButton;
    EditText reviewEditText;

    RecyclerView commentsRecyclerView;
    ArrayList<Review> reviews = new ArrayList<>();
    ReviewAdapter reviewAdapter;

    public BandProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_band_profile, container, false);

        profileTable = view.findViewById(R.id.profileTable);
        reviewTable = view.findViewById(R.id.reviewTable);

        bandName = view.findViewById(R.id.bandName);
        genre = view.findViewById(R.id.genre);
        profile = view.findViewById(R.id.profile);

        bandName.setText(bandTracker.getBandName());
        genre.setText(bandTracker.getGenre());
        profile.setText(bandTracker.getProfile());

        reviewPanelButton = view.findViewById(R.id.reviewButton);
        submitReviewButton = view.findViewById(R.id.submitReviewButton);
        clearReviewButton = view.findViewById(R.id.clearReviewButton);
        phoneButton = view.findViewById(R.id.phoneButton);
        emailButton = view.findViewById(R.id.emailButton);

        commentsRecyclerView = view.findViewById(R.id.commentsRecyclerView);
        commentsRecyclerView.setHasFixedSize(true);
        commentsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        reviewEditText = view.findViewById(R.id.reviewEditText);

        reviewPanelButton.setOnClickListener(this);
        submitReviewButton.setOnClickListener(this);
        clearReviewButton.setOnClickListener(this);
        phoneButton.setOnClickListener(this);
        emailButton.setOnClickListener(this);


        reviewAdapter = new ReviewAdapter(getActivity(), reviews);

        Query getReviews = myDatabase.child("bandReviews").child(bandTracker.getBandId());
        getReviews.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot thisSnapshot : dataSnapshot.getChildren()){
                        Review review = thisSnapshot.getValue(Review.class);
                        reviews.add(review);
                    }
                }else{
                    Review empty = new Review(
                            "No reviews have been left for this band",
                            null
                    );
                    reviews.add(empty);
                }
                reviewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        commentsRecyclerView.setAdapter(reviewAdapter);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.reviewButton:
                String buttonText = reviewPanelButton.getText().toString();

                if(buttonText.contains("Leave a review?")){
                    reviewPanelButton.setText("Close review panel");
                    reviewTable.setVisibility(View.VISIBLE);
                }else{
                    reviewPanelButton.setText("Leave a review?");
                    reviewTable.setVisibility(View.GONE);
                }
                break;

            case R.id.submitReviewButton:
                String enteredText = reviewEditText.getText().toString();

                if(enteredText.isEmpty()){
                    Toast.makeText(getContext(),"The review field cannot be empty!", Toast.LENGTH_SHORT).show();
                }else {
                    myDatabase.child("bandReviews").child(bandTracker.getBandId()).child(currentUser.getUid()).child("review").setValue(enteredText);
                    myDatabase.child("bandReviews").child(bandTracker.getBandId()).child(currentUser.getUid()).child("leftBy").setValue(currentUser.getDisplayName());
                    reviewEditText.setText("");
                    reviewTable.setVisibility(View.GONE);
                    reviewPanelButton.setText("Leave a review?");
                    Toast.makeText(getContext(),"Your review has been posted, thank you.", Toast.LENGTH_SHORT).show();
                    reviews.add(new Review(enteredText,currentUser.getDisplayName()));
                    reviewAdapter.notifyDataSetChanged();
                }
                break;

            case R.id.clearReviewButton:
                reviewEditText.setText("");
                break;

            case R.id.phoneButton:
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+bandTracker.getPhoneNumber()));
                startActivity(callIntent);
                break;

            case R.id.emailButton:
                //Code sourced from https://stackoverflow.com/questions/8701634/send-email-intent
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO,
                        Uri.fromParts("mailto", bandTracker.getEmail(),null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Booking Enquiry for "+bandTracker.getBandName());
                emailIntent.putExtra(Intent.EXTRA_TEXT, "\n\n\n\n\n**Powered by Bands4Hire**");
                startActivity(Intent.createChooser(emailIntent,"Send email..."));
                break;
        }
    }
}
