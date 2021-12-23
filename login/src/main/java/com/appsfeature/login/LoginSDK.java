package com.appsfeature.login;

import android.content.Context;
import android.content.Intent;

import com.appsfeature.login.activity.LoginActivity;
import com.appsfeature.login.activity.ProfileActivity;
import com.appsfeature.login.interfaces.LoginCallback;
import com.appsfeature.login.interfaces.LoginType;
import com.appsfeature.login.model.ApiRequest;
import com.appsfeature.login.model.Profile;
import com.appsfeature.login.network.ApiInterface;
import com.appsfeature.login.network.RetrofitGenerator;
import com.appsfeature.login.util.LoginConstant;
import com.appsfeature.login.util.LoginPrefUtil;
import com.appsfeature.login.util.LoginUtil;
import com.formbuilder.model.FormBuilderModel;
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
    private ApiInterface apiInterface;
    private final Retrofit retrofit;
    private LoginCallback.TermUseListener termsOfUseListener;
    private final HashMap<Integer, LoginCallback.Listener> mLoginListener = new HashMap<>();
    private HashMap<Integer, HashMap<Integer, ApiRequest>> apiRequests;
    private HashMap<Integer, FormBuilderModel> signupFormDetail;

    public static Profile getLoginCredentials(Context context, @LoginType int loginType) {
        return LoginPrefUtil.getUserProfile(context, loginType);
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

    public static boolean isDebugMode() {
        return BuildConfig.DEBUG;
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

    public void openLoginPage(final Context context, @LoginType int loginType) {
        if (!LoginPrefUtil.isLoginComplete(context, loginType)) {
            context.startActivity(new Intent(context, LoginActivity.class)
                    .putExtra(LoginConstant.LOGIN_TYPE, loginType));
        }else {
            LoginUtil.showToast(context, "User already logged in.");
        }
    }

    public void openLoginPage(final Context context, @LoginType int loginType, final boolean isOpenProfile, final boolean isOpenEditProfile) {
        if (isOpenProfile && LoginPrefUtil.isRegComplete(context, loginType)) {
            context.startActivity(new Intent(context, ProfileActivity.class)
                    .putExtra(LoginConstant.OPEN_EDIT_PROFILE, isOpenEditProfile));
        } else if (!LoginPrefUtil.isLoginComplete(context, loginType)) {
            context.startActivity(new Intent(context, LoginActivity.class)
                    .putExtra(LoginConstant.LOGIN_TYPE, loginType));
        }else {
            LoginUtil.showToast(context, "Already logged in.");
        }
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

    public boolean isEnableLogin() {
        return enableLogin;
    }

    public LoginSDK setEnableLogin(boolean enableLogin) {
        this.enableLogin = enableLogin;
        return this;
    }

    public boolean isEnableSignup(ApiRequest apiRequest) {
        return apiRequest != null;
    }


    public boolean isEnableForgetPass(ApiRequest apiRequest) {
        return apiRequest != null;
    }

    public boolean isEnableAuthentication(ApiRequest apiRequest) {
        return apiRequest != null;
    }


    public LoginSDK setApiRequests(HashMap<Integer, HashMap<Integer, ApiRequest>> apiRequests) {
        this.apiRequests = apiRequests;
        return this;
    }

    public HashMap<Integer, HashMap<Integer, ApiRequest>> getApiRequests() {
        return apiRequests;
    }


    public LoginSDK setSignupForm(HashMap<Integer, FormBuilderModel> signupFormDetail) {
        this.signupFormDetail = signupFormDetail;
        return this;
    }

    public HashMap<Integer, FormBuilderModel> getSignupFormDetail() {
        return signupFormDetail;
    }
}
