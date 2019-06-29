package com.appsfeature.login;

import android.content.Context;
import android.content.Intent;

import com.appsfeature.login.model.Profile;
import com.appsfeature.login.network.ApiInterface;
import com.appsfeature.login.util.LoginConstant;
import com.appsfeature.login.util.LoginPrefUtil;
import com.appsfeature.login.util.LoginUtil;
import com.google.gson.Gson;

import retrofit2.Retrofit;


public class LoginSDK {
    private static LoginSDK instance;
    private final Context context;
    Listener listener;
    private boolean isHeaderTitle = true;
    private boolean isFacebookLogin = true;
    private boolean isGoogleLogin = true;
    private boolean isEmailLogin = true;
    private String appPackageName;
    private ApiInterface apiInterface;

    public static Profile getLoginCredentials() {
        return new Profile();
    }

    public interface Listener {
        void onSuccess(Profile response);

        void onFailure(Exception e);
    }


    private LoginSDK(Context context) {
        this.context = context;
        this.appPackageName = context.getPackageName();

    }

    public Context getContext() {
        return context;
    }

    public static LoginSDK getInstance() {
        return instance;
    }

    public static LoginSDK getInstance(Context context, Retrofit retrofit) {
        if (instance == null) {
            instance = new LoginSDK(context);
            instance.setApiInterface(retrofit);
        }
        return instance;
    }

    public LoginSDK setFacebookLogin(boolean facebookLogin) {
        isFacebookLogin = facebookLogin;
        return this;
    }

    public LoginSDK setGoogleLogin(boolean googleLogin) {
        isGoogleLogin = googleLogin;
        return this;
    }

    public LoginSDK setEmailLogin(boolean emailLogin) {
        isEmailLogin = emailLogin;
        return this;
    }

    private static Gson gson = new Gson();

    public static Gson getGson() {
        return gson;
    }

    public ApiInterface getApiInterface() {
        return apiInterface;
    }

    public void setApiInterface(Retrofit retrofit) {
        if (retrofit != null) {
            this.apiInterface = retrofit.create(ApiInterface.class);
        }
    }

    public LoginSDK addListener(Listener listener) {
        this.listener = listener;
        return this;
    }


    public void openLoginPage(final Context context, final boolean isOpenProfile) {
        openLoginPage(context, isOpenProfile, false);
    }

    public void openLoginPage(final Context context, final boolean isOpenProfile, final boolean isOpenEditProfile) {
        if (isOpenProfile && LoginPrefUtil.isRegComplete()) {
            context.startActivity(new Intent(context, ProfileActivity.class)
                    .putExtra(LoginConstant.OPEN_EDIT_PROFILE, isOpenEditProfile));
        } else if (!LoginPrefUtil.isLoginComplete()) {
            context.startActivity(new Intent(context, LoginActivity.class));
        }
    }

    public String getUserName() {
        return LoginPrefUtil.getUserName();
    }

    public String getUserImage() {
        return LoginPrefUtil.getUserImage();
    }

    public String getUserId() {
        return LoginPrefUtil.getUserId();
    }

      public String getUserMobile() {
        return LoginPrefUtil.getUserMobile();
    }

    public String getEmailId() {
        return LoginPrefUtil.getEmailId();
    }
    public Profile getUserProfile() {
        return LoginUtil.getUserProfileData();
    }


    public boolean isFacebookLogin() {
        return isFacebookLogin;
    }

    public boolean isGoogleLogin() {
        return isGoogleLogin;
    }

    public boolean isEmailLogin() {
        return isEmailLogin;
    }
}
