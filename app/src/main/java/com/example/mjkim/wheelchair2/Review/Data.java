package com.example.mjkim.wheelchair2.Review;

public class Data {
    private String title;
    private String context;
    private String name;
    private String email;
    public Data() { }

    public Data(String title, String context,String name,String email) {
        this.title = title;
        this.context = context;
        this.name=name;
        this.email=email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
