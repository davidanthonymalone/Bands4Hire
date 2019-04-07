package com.example.bands4hire.DataModels;

public class Profile {

    String bandName, genre, email, phoneNumber, bandId, profile;

    public Profile(){}

    public Profile(String bandName, String genre, String email,
                   String phoneNumber, String bandId, String profile){
        this.bandName = bandName;
        this.genre = genre;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.bandId = bandId;
        this.profile = profile;
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

    public String getBandId() {
        return bandId;
    }

    public void setBandId(String bandId) {
        this.bandId = bandId;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
