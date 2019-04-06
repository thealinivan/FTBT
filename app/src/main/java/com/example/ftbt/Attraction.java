package com.example.ftbt;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Attraction implements Parcelable {
    //paramteters
    private String name, description, location, category, imgUrl, linkUrl, userID;
    //constructor
    public Attraction(String name, String description, String location,
                      String category, String imgUrl, String linkUrl, String userID) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.category = category;
        this.imgUrl = imgUrl;
        this.linkUrl = linkUrl;
        this.userID = userID;
    }
    //empty constructor for outside access
    public Attraction(){}

    //getters and setters
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

    protected Attraction(Parcel in) {
        name = in.readString();
        description = in.readString();
        location = in.readString();
        category = in.readString();
        imgUrl = in.readString();
        linkUrl = in.readString();
        userID = in.readString();
    }
    public static final Creator<Attraction> CREATOR = new Creator<Attraction>() {
        @Override
        public Attraction createFromParcel(Parcel in) {
            return new Attraction(in);
        }

        @Override
        public Attraction[] newArray(int size) {
            return new Attraction[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(location);
        dest.writeString(category);
        dest.writeString(imgUrl);
        dest.writeString(linkUrl);
        dest.writeString(userID);
    }
}
