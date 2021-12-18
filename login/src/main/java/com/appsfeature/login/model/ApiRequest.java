package com.appsfeature.login.model;

import com.appsfeature.login.network.RequestType;

import java.util.Map;

public class ApiRequest {

    private String endPoint;
    private int reqType = RequestType.GET;
    private Map<String, String> params;

    public ApiRequest(String endPoint, int reqType, Map<String, String> params) {
        this.endPoint = endPoint;
        this.reqType = reqType;
        this.params = params;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public int getReqType() {
        return reqType;
    }

    public void setReqType(int reqType) {
        this.reqType = reqType;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }
}
