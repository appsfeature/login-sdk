package com.appsfeature.login.model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Profile {

    @Expose
    @SerializedName(value="userId", alternate={"user_id"})
    private String userId;

    @SerializedName("name")
    @Expose
    private String name;

    @Expose
    @SerializedName(value="image", alternate={"user_profile"})
    private String image;

    @Expose
    @SerializedName(value="mobile", alternate={"mobile_no"})
    private String mobile;

    @Expose
    @SerializedName(value="email", alternate={"email_id"})
    private String email;

    private String jsonData;

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

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }

    public <T> T getJsonModel(Class<T> classOfT) {
        return new Gson().fromJson(jsonData, classOfT);
    }
}
