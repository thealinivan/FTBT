package com.example.ftbt;

public class Review {
    private String userID, attractionID, text;

    public Review(){};

    public Review(String userID, String attractionID, String text) {
        this.userID = userID;
        this.attractionID = attractionID;
        this.text = text;
    }


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

    public void setText(String text) {
        this.text = text;
    }
}


