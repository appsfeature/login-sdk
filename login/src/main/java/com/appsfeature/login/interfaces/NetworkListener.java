package com.appsfeature.login.interfaces;

public interface NetworkListener<T> {
    void onPreExecute();
    void onSuccess(T response);
    void onError(Exception e);
}
