package com.appsfeature.login.network;

import android.content.Context;

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

    public void changePassword(String newPassword, LoginListener<Void> networkListener) {

    }

    public void changePassword2(String newPassword, LoginListener<Void> networkListener) {

    }


    public void forgotPassword(String username, String otp, boolean isOtpSend, LoginListener<Void> voidLoginListener) {
        if(!isOtpSend) {
//            taskReq.execute(ApiRequest.POST, ConfigLibrary.FORGOT_PASSWORD, RequestBuilder.generateForgotPass(username));
        }else{
//            taskReq.execute(ApiRequest.POST, ConfigLibrary.VALIDATE_FORGOT_PASSWORD, RequestBuilder.valideteForgotPass(username, otp));
        }
    }

    public void loginAdmin(String username, String password, LoginListener<Void> voidLoginListener) {

    }

    public void loginUser(String username, String password, LoginListener<Void> voidLoginListener) {

    }

    public void signUp(String name, String email, String password, LoginListener<Void> voidLoginListener) {

    }
}
