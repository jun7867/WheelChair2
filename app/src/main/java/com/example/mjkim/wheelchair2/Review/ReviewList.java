package com.example.mjkim.wheelchair2.Review;

public class ReviewList {

    private double rating;
    private Boolean tag1;
    private Boolean tag2;
    private Boolean tag3;
    private Boolean tag4;
    private Boolean tag5;
    private Boolean tag6;
    private String review;
    private String name;
    private String email;
    private double mapx;
    private double mapy;
    private String imageUrl1;
    private String imageUrl2;
    private String imageUrl3;

    public String getImageUrl2() { return imageUrl2; }

    public void setImageUrl2(String imageUrl2) { this.imageUrl2 = imageUrl2; }

    public String getImageUrl3() { return imageUrl3; }

    public void setImageUrl3(String imageUrl3) { this.imageUrl3 = imageUrl3; }

    public String getImageUrl1() { return imageUrl1; }

    public void setImageUrl1(String imageUrl1) { this.imageUrl1 = imageUrl1; }

    public double getMapx() {
        return mapx;

    }

    public void setMapx(double mapx) {
        this.mapx = mapx;
    }

    public double getMapy() {
        return mapy;
    }

    public void setMapy(double mapy) {
        this.mapy = mapy;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Boolean getTag1() {
        return tag1;
    }

    public void setTag1(Boolean tag1) {
        this.tag1 = tag1;
    }

    public Boolean getTag2() {
        return tag2;
    }

    public void setTag2(Boolean tag2) {
        this.tag2 = tag2;
    }

    public Boolean getTag3() {
        return tag3;
    }

    public void setTag3(Boolean tag3) {
        this.tag3 = tag3;
    }

    public Boolean getTag4() {
        return tag4;
    }

    public void setTag4(Boolean tag4) {
        this.tag4 = tag4;
    }

    public Boolean getTag5() {
        return tag5;
    }

    public void setTag5(Boolean tag5) {
        this.tag5 = tag5;
    }

    public Boolean getTag6() {
        return tag6;
    }

    public void setTag6(Boolean tag6) {
        this.tag6 = tag6;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getRating() {
        return rating;
    }

    public String getReview() {
        return review;
    }


    public ReviewList(){}

    public ReviewList(double rating, Boolean tag1, Boolean tag2, Boolean tag3,
                      Boolean tag4, Boolean tag5, Boolean tag6, String review, double mapx, double mapy){
        this.rating = rating;
        this.tag1 = tag1;
        this.tag2 = tag2;
        this.tag3 = tag3;
        this.tag4 = tag4;
        this.tag5 = tag5;
        this.tag6 = tag6;
        this.review = review;
        this.mapx = mapx;
        this.mapy = mapy;
    }
}
