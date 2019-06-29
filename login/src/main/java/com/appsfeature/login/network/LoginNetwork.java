package com.appsfeature.login.network;

import android.content.Context;

import com.appsfeature.login.LoginSDK;
import com.appsfeature.login.model.Profile;
import com.google.gson.JsonSyntaxException;

public class LoginNetwork {
    private static LoginNetwork instance;
    private final Context context;


    public LoginNetwork(Context context) {
        this.context=context;

    }

    public static LoginNetwork getInstance(Context context) {
        if(instance == null){
            instance = new LoginNetwork(context);
        }
        return instance;
    }

    public void changePassword(String userId, String newPassword, final LoginListener<Boolean> callback) {
        callback.onPreExecute();
        LoginSDK.getInstance().getApiInterface().changePassword(userId, newPassword).enqueue(new ResponseCallBack(new ResponseCallBack.OnNetworkCall() {
            @Override
            public void onComplete(boolean status, String data) {
                callback.onSuccess(status);
            }

            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }
        }));
    }



    public void forgotPassword(String username, String otp, boolean isOtpSend, final LoginListener<Boolean> callback) {
        callback.onPreExecute();
        if(!isOtpSend) {
            LoginSDK.getInstance().getApiInterface().forgotPass(username).enqueue(new ResponseCallBack(new ResponseCallBack.OnNetworkCall() {
                @Override
                public void onComplete(boolean status, String data) {
                    callback.onSuccess(status);
                }

                @Override
                public void onError(Exception e) {
                    callback.onError(e);
                }
            }));
        }else{
            LoginSDK.getInstance().getApiInterface().validateOtp(username, otp).enqueue(new ResponseCallBack(new ResponseCallBack.OnNetworkCall() {
                @Override
                public void onComplete(boolean status, String data) {
                    callback.onSuccess(status);
                }

                @Override
                public void onError(Exception e) {
                    callback.onError(e);
                }
            }));
        }
    }

    public void loginUser(String username, String password, final LoginListener<Profile> callback) {
        callback.onPreExecute();
        LoginSDK.getInstance().getApiInterface().loginUser(username, password).enqueue(new ResponseCallBack(new ResponseCallBack.OnNetworkCall() {
            @Override
            public void onComplete(boolean status, String data) {
                try {
                    Profile profile = LoginSDK.getGson().fromJson(data, Profile.class);
                    callback.onSuccess(profile);
                } catch (JsonSyntaxException e) {
                    callback.onError(e);
                }
            }

            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }
        }));
    }

    public void signUp(String name, String email, String password, final LoginListener<Profile> callback) {
        callback.onPreExecute();
        LoginSDK.getInstance().getApiInterface().signUp(name,"" ,email, password).enqueue(new ResponseCallBack(new ResponseCallBack.OnNetworkCall() {
            @Override
            public void onComplete(boolean status, String data) {
                try {
                    Profile profile = LoginSDK.getGson().fromJson(data, Profile.class);
                    callback.onSuccess(profile);
                } catch (JsonSyntaxException e) {
                    callback.onError(e);
                }
            }

            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }
        }));
    }


}
