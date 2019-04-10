package com.example.bands4hire.Seeds;

import com.example.bands4hire.DataModels.BandAdvert;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Queue;

public class AdvertSeeds {

    static DatabaseReference myDatabase = FirebaseDatabase.getInstance().getReference();

    public static void seedAdverts(){
        BandAdvert advert1 = new BandAdvert(
                "The Ramones",
                "Punk Rock",
                "New York",
                "250",
                "01/01/2020",
                "manager@theramones.com",
                "00112341234",
                "Ramones Management",
                "advert001",
                "band001"
        );

        BandAdvert advert2 = new BandAdvert(
                "The Ramones",
                "Punk Rock",
                "New York",
                "250",
                "02/01/2020",
                "manager@theramones.com",
                "00112341234",
                "Ramones Management",
                "advert002",
                "band001"
        );

        BandAdvert advert3 = new BandAdvert(
                "The Ramones",
                "Punk Rock",
                "New York",
                "250",
                "03/01/2020",
                "manager@theramones.com",
                "00112341234",
                "Ramones Management",
                "advert003",
                "band001"
        );

        BandAdvert advert4 = new BandAdvert(
                "The Ramones",
                "Punk Rock",
                "New York",
                "250",
                "04/01/2020",
                "manager@theramones.com",
                "00112341234",
                "Ramones Management",
                "advert004",
                "band001"
        );




        myDatabase.child("adverts").child("advert001").setValue(advert1);
        myDatabase.child("adverts").child("advert002").setValue(advert2);
        myDatabase.child("adverts").child("advert003").setValue(advert3);
        myDatabase.child("adverts").child("advert004").setValue(advert4);

    }
}
