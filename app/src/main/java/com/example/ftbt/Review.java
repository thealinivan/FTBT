package com.example.ftbt;

public class Review {
    //object parameters
    private String userID, attractionID, text;

    //constructor
    public Review(String userID, String attractionID, String text) {
        this.userID = userID;
        this.attractionID = attractionID;
        this.text = text;
    }
    //empty constructor for access
    public Review(){};

    //getters and setters
    public String getUserID() {
        return userID;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }
    public String getAttractionID() {
        return attractionID;
    }
    public void setAttractionID(String attractionID) {
        this.attractionID = attractionID;
    }
    public String getText() {
        return text;
    }
}


