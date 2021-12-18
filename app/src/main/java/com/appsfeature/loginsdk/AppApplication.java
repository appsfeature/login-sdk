package com.appsfeature.loginsdk;

import android.app.Application;

import com.appsfeature.login.LoginSDK;
import com.appsfeature.login.interfaces.LoginCallback;
import com.appsfeature.login.model.ApiRequest;
import com.appsfeature.login.model.Profile;
import com.appsfeature.login.network.LoginParams;
import com.appsfeature.login.network.LoginType;
import com.appsfeature.login.network.RequestType;

import java.util.HashMap;
import java.util.Map;

public class AppApplication extends Application {

    private static final String HOST_URL = "http://allinoneglobalmarket.com/mobile_app/";
    private static AppApplication instance;
    private LoginSDK loginSdk;

    public static AppApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public LoginSDK getLoginSdk() {
        if(loginSdk == null) {
            loginSdk = LoginSDK.getInstance(this, HOST_URL, BuildConfig.DEBUG)
                    .setApiRequests(getApiRequestKeys())
                    .setFacebookLogin(false)
                    .setGoogleLogin(true)
                    .setEmailLogin(true)
                    .setEnableForgetPass(false)
                    .setEnableSignup(true)
                    .addLoginListener(new LoginCallback.Listener() {
                        @Override
                        public void onSuccess(Profile response) {

                        }

                        @Override
                        public void onFailure(Exception e) {

                        }
                    });
        }
        return loginSdk;
    }

    public static HashMap<Integer, ApiRequest> getApiRequestKeys() {
        HashMap<Integer, ApiRequest> hashMap = new HashMap<>();
        Map<String, String> map;
        map = new HashMap<>();
        map.put(LoginParams.UserName, "email");
        map.put(LoginParams.Password, "password");
        hashMap.put(LoginType.LOGIN, new ApiRequest("login_ap", RequestType.POST_FORM, map));

        map = new HashMap<>();
        map.put(LoginParams.Name, "name");
        map.put(LoginParams.EmailOrMobile, "emailOrMobile");
        map.put(LoginParams.UserName, "username");
        map.put(LoginParams.Password, "password");
        hashMap.put(LoginType.SIGNUP, new ApiRequest("signup", RequestType.POST, map));

        map = new HashMap<>();
        map.put(LoginParams.EmailOrMobile, "emailOrMobile");
        hashMap.put(LoginType.GENERATE_OTP, new ApiRequest("generateOtp", RequestType.POST, map));

        map = new HashMap<>();
        map.put(LoginParams.EmailOrMobile, "emailOrMobile");
        map.put(LoginParams.Otp, "otp");
        hashMap.put(LoginType.VALIDATE_OTP, new ApiRequest("validateOtp", RequestType.POST, map));

        map = new HashMap<>();
        map.put(LoginParams.UserId, "userId");
        map.put(LoginParams.Password, "password");
        hashMap.put(LoginType.CHANGE_PASSWORD, new ApiRequest("changePassword", RequestType.POST, map));

        return hashMap;
    }
}
