package com.appsfeature.loginsdk;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppProfileModel {

    @Expose
    @SerializedName(value="username", alternate={"mobile_no"})
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
