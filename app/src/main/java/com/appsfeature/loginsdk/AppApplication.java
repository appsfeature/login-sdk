package com.appsfeature.loginsdk;

import android.app.Application;

import com.appsfeature.login.LoginSDK;
import com.appsfeature.login.interfaces.LoginCallback;
import com.appsfeature.login.model.Profile;

public class AppApplication extends Application {

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
            loginSdk = LoginSDK.getInstance(this, LoginDataMap.LOGIN_BASE_URL, BuildConfig.DEBUG)
                    .setApiRequests(LoginDataMap.getApiRequestKeys())
                    .setSignupForm(LoginDataMap.getSignupFormDetail())
                    .setFacebookLogin(false)
                    .setGoogleLogin(true)
                    .setEmailLogin(true)
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
}
