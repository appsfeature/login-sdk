package com.appsfeature.login.network;

public interface NetworkListener {
    void onPreExecute();
    void onResponse(String response);
    void onError(String response);
    void onRetryClick();
}
