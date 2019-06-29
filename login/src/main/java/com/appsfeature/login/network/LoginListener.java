package com.appsfeature.login.network;

public interface LoginListener<T> {
    void onPreExecute();
    void onSuccess(T response);
    void onError(Exception e);
}
