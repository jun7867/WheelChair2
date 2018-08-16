package com.example.mjkim.wheelchair2.WatchReview;

public class WatchReviewList {

    private String name;
    private String link;
    private String description;
    private String reviewerLink;
    private String reviewerName;
    private String postdate;



    public String getName() { return name; }

    public String getLink() { return link; }

    public String getDescription() { return description; }

    public String getReviewerLink() { return reviewerLink; }

    public String getPostdate() { return postdate; }

    public String getBloogername() { return reviewerName; }



    public WatchReviewList(){}

    public WatchReviewList(String name, String link, String description, String reviewerLink, String reviewerName, String postdate){
        this.name = name;
        this.link = link;
        this.description = description;
        this.reviewerLink = reviewerLink;
        this.reviewerName = reviewerName;
        this.postdate = postdate;

    }


}
