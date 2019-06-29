package com.appsfeature.login.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Amit on 8/25/2017.
 */

public class LoginUserResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("user")
    @Expose
    private Profile loginUser ;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Profile getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(Profile loginUser) {
        this.loginUser = loginUser;
    }
}
