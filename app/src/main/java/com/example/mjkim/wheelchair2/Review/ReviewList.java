package com.example.mjkim.wheelchair2.Review;

public class ReviewList {

    float rating;
    int tag[] = new int[6];
    //Boolean tag[] = new Boolean[6];
    String review;

    public float getRating() {
        return rating;
    }

    public int[] getTag() {
        return tag;
    }

    public String getReview() {
        return review;
    }


    public ReviewList(){}

    public ReviewList(float rating, int tag[], String review){
        this.rating = rating;
        this.tag = tag;
        this.review = review;
    }
}
