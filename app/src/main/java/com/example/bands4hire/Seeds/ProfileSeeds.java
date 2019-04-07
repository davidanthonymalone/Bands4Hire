package com.example.bands4hire.Seeds;

import com.example.bands4hire.DataModels.Profile;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileSeeds {

    static DatabaseReference myDatabase = FirebaseDatabase.getInstance().getReference();

    public static void seedProfiles(){
        Profile profile1 = new Profile(
                "The Ramones",
                "Punk Rock",
                "manager@theramones.com",
                "00112341234",
                "band001",
                "The Ramones were an American punk rock band that formed in the New York City neighborhood of Forest Hills, Queens, in 1974. They are sometimes cited as the first true punk rock group. Despite achieving only limited commercial success, the band was influential in the United States and the United Kingdom."
        );

        Profile profile2 = new Profile(
                "U2",
                "Pop Rock",
                "manager@u2.ie",
                "00353145674567",
                "band002",
                "U2 are an Irish rock band from Dublin formed in 1976. The group consists of Bono, the Edge, Adam Clayton, and Larry Mullen Jr."
        );

        Profile profile3 = new Profile(
                "The Waxies",
                "Pub Band",
                "manager@thewaxies.ie",
                "003535145674567",
                "band003",
                "Fun party band with 20 years experience of being the resident band for the summer in Mallorca and many European tours. Available for all types of occasions"
        );

        Profile profile4 = new Profile(
                "The Duke Silver Trio",
                "Jazz",
                "ron@swanson.com",
                "0018483782920",
                "band004",
                "Duke Silver is a jazz saxophonist, playing the tenor saxophone, and is the leader of The Duke Silver Trio. He performs at Cozy's Bar in Eagleton, Indiana, on the second Thursday of every month"
        );

        myDatabase.child("bandProfiles").child(profile1.getBandId()).setValue(profile1);
        myDatabase.child("bandProfiles").child(profile2.getBandId()).setValue(profile2);
        myDatabase.child("bandProfiles").child(profile3.getBandId()).setValue(profile3);
        myDatabase.child("bandProfiles").child(profile4.getBandId()).setValue(profile4);
    }
}
