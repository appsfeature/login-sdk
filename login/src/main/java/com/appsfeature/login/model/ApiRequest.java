package com.appsfeature.login.model;


import androidx.annotation.NonNull;

import com.appsfeature.login.interfaces.ApiRequestType;

import java.util.Map;

public class ApiRequest {

    private String title;
    private String endPoint;

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

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    @ApiRequestType
    public int getReqType() {
        return reqType;
    }

    public void setReqType(@ApiRequestType int reqType) {
        this.reqType = reqType;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }
}
