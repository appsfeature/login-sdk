package com.appsfeature.login;

import android.content.Context;

import com.appsfeature.login.base.LoginClass;
import com.appsfeature.login.interfaces.LoginCallback;
import com.appsfeature.login.interfaces.LoginType;
import com.appsfeature.login.model.ApiRequest;
import com.appsfeature.login.model.Profile;
import com.appsfeature.login.network.ApiInterface;
import com.appsfeature.login.util.LoginPrefUtil;
import com.formbuilder.model.FormBuilderModel;
import com.google.gson.Gson;

import java.util.HashMap;

import retrofit2.Retrofit;


public interface LoginSDK {

    static Profile getLoginCredentials(Context context, @LoginType int loginType) {
        return LoginPrefUtil.getUserProfile(context, loginType);
    }

    boolean isDebugMode();

    static LoginSDK getInstance() {
        return LoginClass.getInstance();
    }

    static LoginSDK getInstance(Context context, String baseUrl, boolean isDebug) {
        return LoginClass.Builder(context, baseUrl, isDebug);
    }

    LoginSDK setFacebookLogin(boolean facebookLogin);

    LoginSDK setGoogleLogin(boolean googleLogin);

    LoginSDK setEmailLogin(boolean emailLogin);

    static Gson getGson() {
        return new Gson();
    }

    ApiInterface getApiInterface();

    void setApiInterface(Retrofit retrofit);

    void openLoginPage(final Context context, @LoginType int loginType);

    void openLoginPage(final Context context, @LoginType int loginType, final boolean isOpenProfile, final boolean isOpenEditProfile);

    boolean isFacebookLogin();

    boolean isGoogleLogin();

    boolean isEmailLogin();

    Retrofit getRetrofit();

    LoginCallback.TermUseListener getTermsOfUseListener();

    void setTermsOfUseListener(LoginCallback.TermUseListener termsOfUseListener);

    LoginSDK addLoginListener(LoginCallback.Listener callback);

    void removeLoginListener(LoginCallback.Listener callback);

    void dispatchLoginListener(Profile profile, Exception e);

    boolean isEnableLogin();

    LoginSDK setEnableLogin(boolean enableLogin);

    boolean isEnableSignup(ApiRequest apiRequest);


    boolean isEnableForgetPass(ApiRequest apiRequest);

    boolean isEnableAuthentication(ApiRequest apiRequest);

    LoginSDK setApiRequests(HashMap<Integer, HashMap<Integer, ApiRequest>> apiRequests);

    HashMap<Integer, HashMap<Integer, ApiRequest>> getApiRequests();

    LoginSDK setSignupForm(HashMap<Integer, FormBuilderModel> signupFormDetail);

    HashMap<Integer, FormBuilderModel> getSignupFormDetail();

    void syncSignupForm();
}
