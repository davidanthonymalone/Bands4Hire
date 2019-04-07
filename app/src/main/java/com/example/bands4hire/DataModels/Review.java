package com.example.bands4hire.DataModels;

public class Review {

    String review, leftBy;

    public Review(){}

    public Review(String review, String leftBy){
        this.review = review;
        this.leftBy = leftBy;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getLeftBy() {
        return leftBy;
    }

    public void setLeftBy(String leftBy) {
        this.leftBy = leftBy;
    }
}
