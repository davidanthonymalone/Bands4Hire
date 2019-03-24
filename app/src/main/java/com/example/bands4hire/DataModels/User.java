package com.example.bands4hire.DataModels;

public class User {

    public String email, userName, userType, userId;

    public User(){}

    public User(String email, String userName, String userType, String userId){
        this.email = email;
        this.userName = userName;
        this.userType = userType;
        this.userId = userId;
    }
}
