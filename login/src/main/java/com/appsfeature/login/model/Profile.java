package com.appsfeature.login.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Profile {

    @SerializedName("userId")
    @Expose
    private String userId;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("validity")
    @Expose
    private String validity;

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("mobile")
    @Expose
    private String mobile;

    @SerializedName("email")
    @Expose
    private String email;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
