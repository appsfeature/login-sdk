package com.appsfeature.login;

import android.content.Context;
import android.content.Intent;

import com.appsfeature.login.interfaces.LoginCallback;
import com.appsfeature.login.model.ApiRequest;
import com.appsfeature.login.model.Profile;
import com.appsfeature.login.network.ApiInterface;
import com.appsfeature.login.network.RetrofitGenerator;
import com.appsfeature.login.util.LoginConstant;
import com.appsfeature.login.util.LoginPrefUtil;
import com.appsfeature.login.util.LoginUtil;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Retrofit;


public class LoginSDK {
    private static LoginSDK instance;
    private boolean isFacebookLogin = true;
    private boolean isGoogleLogin = true;
    private boolean isEmailLogin = true;
    private boolean enableLogin = true;
    private boolean enableSignup = true;
    private boolean enableForgetPass = true;
    private String titleLogin = "Login";
    private String titleSignup = "Sign up";
    private ApiInterface apiInterface;
    private final Retrofit retrofit;
    private LoginCallback.TermUseListener termsOfUseListener;
    private final HashMap<Integer, LoginCallback.Listener> mLoginListener = new HashMap<>();
    private HashMap<Integer, ApiRequest> apiRequests;

    public static Profile getLoginCredentials(Context context) {
        return LoginPrefUtil.getUserProfile(context);
    }

    private LoginSDK(Context context, String baseUrl, boolean isDebug) {
        retrofit = RetrofitGenerator.getClient(baseUrl, LoginUtil.getSecurityCode(context), isDebug);
        setApiInterface(retrofit);
    }

    public static LoginSDK getInstance() {
        return instance;
    }

    public static LoginSDK getInstance(Context context, String baseUrl, boolean isDebug) {
        if (instance == null) {
            instance = new LoginSDK(context, baseUrl, isDebug);
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

    private static final Gson gson = new Gson();

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

    public void openLoginPage(final Context context) {
        if (!LoginPrefUtil.isLoginComplete(context)) {
            context.startActivity(new Intent(context, LoginActivity.class));
        }else {
            LoginUtil.showToast(context, "User already logged in.");
        }
    }

    public void openLoginPage(final Context context, final boolean isOpenProfile, final boolean isOpenEditProfile) {
        if (isOpenProfile && LoginPrefUtil.isRegComplete(context)) {
            context.startActivity(new Intent(context, ProfileActivity.class)
                    .putExtra(LoginConstant.OPEN_EDIT_PROFILE, isOpenEditProfile));
        } else if (!LoginPrefUtil.isLoginComplete(context)) {
            context.startActivity(new Intent(context, LoginActivity.class));
        }else {
            LoginUtil.showToast(context, "Already logged in.");
        }
    }

    public String getUserName(Context context) {
        return LoginPrefUtil.getUserName(context);
    }

    public String getUserImage(Context context) {
        return LoginPrefUtil.getUserImage(context);
    }

    public String getUserId(Context context) {
        return LoginPrefUtil.getUserId(context);
    }

      public String getUserMobile(Context context) {
        return LoginPrefUtil.getUserMobile(context);
    }

    public String getEmailId(Context context) {
        return LoginPrefUtil.getEmailId(context);
    }

    public String getProfileJson(Context context) {
        return LoginPrefUtil.getProfileJson(context);
    }

    public Profile getUserProfile(Context context) {
        return LoginUtil.getUserProfileData(context);
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

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public LoginCallback.TermUseListener getTermsOfUseListener() {
        return termsOfUseListener;
    }

    public void setTermsOfUseListener(LoginCallback.TermUseListener termsOfUseListener) {
        this.termsOfUseListener = termsOfUseListener;
    }

    public LoginSDK addLoginListener(LoginCallback.Listener callback) {
        synchronized (mLoginListener) {
            this.mLoginListener.put(callback.hashCode(), callback);
        }
        return this;
    }

    public void removeLoginListener(LoginCallback.Listener callback) {
        if (callback != null && mLoginListener.get(callback.hashCode()) != null) {
            synchronized (mLoginListener) {
                this.mLoginListener.remove(callback.hashCode());
            }
        }
    }

    public void dispatchLoginListener(Profile profile, Exception e) {
        try {
            if (mLoginListener.size() > 0) {
                for (Map.Entry<Integer, LoginCallback.Listener> entry : mLoginListener.entrySet()) {
                    Integer key = entry.getKey();
                    LoginCallback.Listener callback = entry.getValue();
                    if (callback != null) {
                        if(profile != null) {
                            callback.onSuccess(profile);
                        }else {
                            callback.onFailure(e);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String getTitleLogin() {
        return titleLogin;
    }

    public LoginSDK setTitleLogin(String titleLogin) {
        this.titleLogin = titleLogin;
        return this;
    }

    public String getTitleSignup() {
        return titleSignup;
    }

    public LoginSDK setTitleSignup(String titleSignup) {
        this.titleSignup = titleSignup;
        return this;
    }

    public boolean isEnableLogin() {
        return enableLogin;
    }

    public LoginSDK setEnableLogin(boolean enableLogin) {
        this.enableLogin = enableLogin;
        return this;
    }

    public boolean isEnableSignup() {
        return enableSignup;
    }

    public LoginSDK setEnableSignup(boolean enableSignup) {
        this.enableSignup = enableSignup;
        return this;
    }

    public boolean isEnableForgetPass() {
        return enableForgetPass;
    }

    public LoginSDK setEnableForgetPass(boolean enableForgetPass) {
        this.enableForgetPass = enableForgetPass;
        return this;
    }

    public LoginSDK setApiRequests(HashMap<Integer, ApiRequest> apiRequests) {
        this.apiRequests = apiRequests;
        return this;
    }

    public HashMap<Integer, ApiRequest> getApiRequests() {
        return apiRequests;
    }
}
