package com.example.ftbt;

import java.io.Serializable;

public class Attraction implements Serializable {

    private String name, description, location, category, imgUrl, linkUrl, userID;


    public Attraction(String name, String description, String location, String category, String imgUrl, String linkUrl, String userID) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.category = category;
        this.imgUrl = imgUrl;
        this.linkUrl = linkUrl;
        this.userID = userID;
    }

    public Attraction(){}



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
