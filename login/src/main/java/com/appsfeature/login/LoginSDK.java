package com.appsfeature.login;

import android.content.Context;

import com.appsfeature.login.model.Login;


public class LoginSDK {
    private static LoginSDK instance;
    private final Context context;
    public Listener listener;

    public static Login getLoginCredentials() {
        return new Login();
    }

    public interface Listener  {
        void onSuccess(Login response);
        void onFailure(Exception e);
    }


    public LoginSDK(Context context) {
        this.context=context;

    }

    public static LoginSDK newInstance(Context context) {
        if(instance == null){
            instance = new LoginSDK(context);
        }
        return instance;
    }
    
    public static LoginSDK getInstance() {
        return instance;
    }

    public void open(Listener listener) {
        this.listener=listener;
    }

    public static String getUserId(Context context) {
        return null;
    }


}
