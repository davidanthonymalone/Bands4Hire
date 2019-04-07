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

        BandAdvert advert5 = new BandAdvert(
                "U2",
                "Pop Rock",
                "Dublin",
                "250",
                "16/06/2019",
                "manager@u2.ie",
                "00353145674567",
                "U2 Management",
                "advert005",
                "band002"
        );

        BandAdvert advert6 = new BandAdvert(
                "U2",
                "Pop Rock",
                "Dublin",
                "250",
                "17/06/2019",
                "manager@u2.ie",
                "00353145674567",
                "U2 Management",
                "advert006",
                "band002"
        );

        BandAdvert advert7 = new BandAdvert(
                "U2",
                "Pop Rock",
                "Dublin",
                "250",
                "18/06/2019",
                "manager@u2.ie",
                "00353145674567",
                "U2 Management",
                "advert007",
                "band002"
        );

        BandAdvert advert8 = new BandAdvert(
                "U2",
                "Pop Rock",
                "Dublin",
                "250",
                "19/06/2019",
                "manager@u2.ie",
                "00353145674567",
                "U2 Management",
                "advert008",
                "band002"
        );

        BandAdvert advert9 = new BandAdvert(
                "The Waxies",
                "Pub Band",
                "Waterford",
                "50",
                "27/03/2019",
                "manager@thewaxies.ie",
                "003535145674567",
                "Waxies Management",
                "advert009",
                "band003"
        );

        BandAdvert advert10 = new BandAdvert(
                "The Waxies",
                "Pub Band",
                "Waterford",
                "50",
                "28/03/2019",
                "manager@thewaxies.ie",
                "003535145674567",
                "Waxies Management",
                "advert0010",
                "band003"
        );

        BandAdvert advert11 = new BandAdvert(
                "The Waxies",
                "Pub Band",
                "Waterford",
                "50",
                "29/03/2019",
                "manager@thewaxies.ie",
                "003535145674567",
                "Waxies Management",
                "advert0011",
                "band003"
        );

        BandAdvert advert12 = new BandAdvert(
                "The Waxies",
                "Pub Band",
                "Waterford",
                "50",
                "30/03/2019",
                "manager@thewaxies.ie",
                "003535145674567",
                "Waxies Management",
                "advert0013",
                "band003"
        );


        myDatabase.child("adverts").child("advert001").setValue(advert1);
        myDatabase.child("adverts").child("advert002").setValue(advert2);
        myDatabase.child("adverts").child("advert003").setValue(advert3);
        myDatabase.child("adverts").child("advert004").setValue(advert4);
        myDatabase.child("adverts").child("advert005").setValue(advert5);
        myDatabase.child("adverts").child("advert006").setValue(advert6);
        myDatabase.child("adverts").child("advert007").setValue(advert7);
        myDatabase.child("adverts").child("advert008").setValue(advert8);
        myDatabase.child("adverts").child("advert009").setValue(advert9);
        myDatabase.child("adverts").child("advert0010").setValue(advert10);
        myDatabase.child("adverts").child("advert0011").setValue(advert11);
        myDatabase.child("adverts").child("advert0013").setValue(advert12);
    }
}
