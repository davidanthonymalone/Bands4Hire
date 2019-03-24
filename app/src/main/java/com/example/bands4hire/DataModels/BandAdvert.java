package com.example.bands4hire.DataModels;

public class BandAdvert {

    String bandName, genre, location, price, dateAvailable, email, phoneNumber, createdBy, advertId;

    public BandAdvert(){}

    public BandAdvert(String bandName, String genre, String location, String price,
                      String dateAvailable, String email, String phoneNumber,
                      String createdBy, String advertId){
        this.bandName = bandName;
        this.genre = genre;
        this.location = location;
        this.price = price;
        this.dateAvailable = dateAvailable;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.createdBy = createdBy;
        this.advertId = advertId;
    }

    public String getBandName() {
        return bandName;
    }

    public void setBandName(String bandName) {
        this.bandName = bandName;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDateAvailable() {
        return dateAvailable;
    }

    public void setDateAvailable(String dateAvailable) {
        this.dateAvailable = dateAvailable;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) { this.createdBy = createdBy;}

    public String getAdvertId() {
        return advertId;
    }

    public void setAdvertId(String advertId) {
        this.advertId = advertId;
    }
}

