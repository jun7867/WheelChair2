package com.example.mjkim.wheelchair2.Review;

public class ReviewList {


    private String key;
    private double rating;
    private Boolean tag1;
    private Boolean tag2;
    private Boolean tag3;
    private Boolean tag4;
    private Boolean tag5;
    private Boolean tag6;
    private String location_name;
    private String review;
    private String name;
    private String email;
    private double mapx;
    private double mapy;
    private String imageUrl1;
    private String imageUrl2;
    private String imageUrl3;
    private String imageUrl4;
    private String imageUrl5;
    private String imageUrl6;
    private String imageUrl7;
    private String imageUrl8;
    private String imageUrl9;
    private String date;
    private String review_name;

    public String getKey() { return key; }

    public void setKey(String key) { this.key = key; }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    private String imageName;

    public String getReview_name() {
        return review_name;
    }

    public void setReview_name(String review_name) {
        this.review_name = review_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getImageUrl7() {
        return imageUrl7;
    }

    public void setImageUrl7(String imageUrl7) {
        this.imageUrl7 = imageUrl7;
    }

    public String getImageUrl8() {
        return imageUrl8;
    }

    public void setImageUrl8(String imageUrl8) {
        this.imageUrl8 = imageUrl8;
    }

    public String getImageUrl9() {
        return imageUrl9;
    }

    public void setImageUrl9(String imageUrl9) {
        this.imageUrl9 = imageUrl9;
    }



    public String getImageUrl4() {
        return imageUrl4;
    }

    public void setImageUrl4(String imageUrl4) {
        this.imageUrl4 = imageUrl4;
    }

    public String getLocation_name() {
        return location_name;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }

    public String getImageUrl5() {
        return imageUrl5;
    }

    public void setImageUrl5(String imageUrl5) {
        this.imageUrl5 = imageUrl5;
    }



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

    public ReviewList(String location_name, String review_name, double rating, Boolean tag1, Boolean tag2, Boolean tag3,
                      Boolean tag4, Boolean tag5, Boolean tag6, String review, String name, String email, double mapx, double mapy, String date,
                      String imageUrl1, String imageUrl2, String imageUrl3,String imageUrl4,String imageUrl5,String imageUrl6,
                      String imageUrl7,String imageUrl8,String imageUrl9){
        this.location_name = location_name;
        this.review_name = review_name;
        this.rating = rating;
        this.tag1 = tag1;
        this.tag2 = tag2;
        this.tag3 = tag3;
        this.tag4 = tag4;
        this.tag5 = tag5;
        this.tag6 = tag6;
        this.review = review;
        this.name = name;
        this.email = email;
        this.mapx = mapx;
        this.mapy = mapy;
        this.date = date;
        this.imageUrl1 = imageUrl1;
        this.imageUrl2 = imageUrl2;
        this.imageUrl3 = imageUrl3;
        this.imageUrl4 = imageUrl4;
        this.imageUrl5 = imageUrl5;
        this.imageUrl6 = imageUrl6;
        this.imageUrl7 = imageUrl7;
        this.imageUrl8 = imageUrl8;
        this.imageUrl9 = imageUrl9;


    }

    public ReviewList(String key, String location_name, String review_name, double rating, Boolean tag1, Boolean tag2, Boolean tag3,
                      Boolean tag4, Boolean tag5, Boolean tag6, String review, String name, String email, double mapx, double mapy, String date,
                      String imageUrl1, String imageUrl2, String imageUrl3,String imageUrl4,String imageUrl5,String imageUrl6,
                      String imageUrl7,String imageUrl8,String imageUrl9){
        this.key=key;
        this.location_name = location_name;
        this.review_name = review_name;
        this.rating = rating;
        this.tag1 = tag1;
        this.tag2 = tag2;
        this.tag3 = tag3;
        this.tag4 = tag4;
        this.tag5 = tag5;
        this.tag6 = tag6;
        this.review = review;
        this.name = name;
        this.email = email;
        this.mapx = mapx;
        this.mapy = mapy;
        this.date = date;
        this.imageUrl1 = imageUrl1;
        this.imageUrl2 = imageUrl2;
        this.imageUrl3 = imageUrl3;
        this.imageUrl4 = imageUrl4;
        this.imageUrl5 = imageUrl5;
        this.imageUrl6 = imageUrl6;
        this.imageUrl7 = imageUrl7;
        this.imageUrl8 = imageUrl8;
        this.imageUrl9 = imageUrl9;


    }

    public ReviewList(String review_name, double rating, Boolean tag1, Boolean tag2, Boolean tag3,
                      Boolean tag4, Boolean tag5, Boolean tag6, String review, double mapx, double mapy, String date){
        this.review_name = review_name;
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
        this.date = date;
        imageUrl1 = "";
        imageUrl2 = "";
        imageUrl3 = "";
        imageUrl4 = "";
        imageUrl5 = "";
        imageUrl6 = "";
        imageUrl7 = "";
        imageUrl8 = "";
        imageUrl9 = "";
    }



    public void setImageUrl6(String imageUrl6) {
        this.imageUrl6 = imageUrl6;
    }
    public String getImageUrl6() {
        return imageUrl6;
    }
}
