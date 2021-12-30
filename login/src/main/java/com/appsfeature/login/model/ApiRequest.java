package com.appsfeature.login.model;


import androidx.annotation.NonNull;

import com.appsfeature.login.interfaces.ApiRequestType;

import java.util.Map;

public class ApiRequest {

    private String title;
    private String endPoint;
    //Used for testing environment
    private String username;
    private String password;

    @ApiRequestType
    private int reqType;
    private Map<String, String> params;

    public ApiRequest(@NonNull String endPoint, @ApiRequestType int reqType, Map<String, String> params) {
        this.endPoint = endPoint;
        this.reqType = reqType;
        this.params = params;
    }

    public ApiRequest(@NonNull String title, String endPoint, @ApiRequestType int reqType, Map<String, String> params) {
        this.title = title;
        this.endPoint = endPoint;
        this.reqType = reqType;
        this.params = params;
    }

    public String getTitle() {
        return title;
    }

    public ApiRequest setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public ApiRequest setEndPoint(String endPoint) {
        this.endPoint = endPoint;
        return this;
    }

    @ApiRequestType
    public int getReqType() {
        return reqType;
    }

    public ApiRequest setReqType(@ApiRequestType int reqType) {
        this.reqType = reqType;
        return this;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public ApiRequest setParams(Map<String, String> params) {
        this.params = params;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public ApiRequest setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public ApiRequest setPassword(String password) {
        this.password = password;
        return this;
    }
}
