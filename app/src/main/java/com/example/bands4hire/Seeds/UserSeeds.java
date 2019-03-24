package com.example.bands4hire.Seeds;

import com.example.bands4hire.DataModels.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserSeeds {

    static DatabaseReference myDatabase = FirebaseDatabase.getInstance().getReference();

    public static void seedUsers(){
        User user1 = new User(
               "manager@theramones.com",
                "Ramones Management",
                "Band",
                "1111222233334444"
        );

        User user2 = new User(
                "manager@u2.ie",
                "U2 Management",
                "Band",
                "1111222233334441"
        );

        User user3 = new User(
                "manager@thewaxies.ie",
                "Waxies Management",
                "Band",
                "1111222233334442"
        );

        myDatabase.child("users").child(user1.userId).setValue(user1);
        myDatabase.child("users").child(user2.userId).setValue(user2);
        myDatabase.child("users").child(user3.userId).setValue(user3);
    }
}
