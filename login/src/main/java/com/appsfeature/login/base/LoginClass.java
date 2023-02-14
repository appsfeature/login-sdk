package com.appsfeature.login.base;

import android.content.Context;
import android.content.Intent;

import com.appsfeature.login.LoginSDK;
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
import com.formbuilder.FormBuilder;
import com.formbuilder.model.FormBuilderModel;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Retrofit;


public class LoginClass implements LoginSDK {
    private static LoginClass instance;
    private final boolean isDebugModeEnabled;
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

    private LoginClass(Context context, String baseUrl, boolean isDebug) {
        this.isDebugModeEnabled = isDebug;
        retrofit = RetrofitGenerator.getClient(baseUrl, LoginUtil.getSecurityCode(context), isDebug);
        setApiInterface(retrofit);
        FormBuilder.getInstance().setDebugModeEnabled(isDebug);
    }

    public static LoginClass getInstance() {
        return instance;
    }

    public static LoginClass Builder(Context context, String baseUrl, boolean isDebug) {
        if (instance == null) {
            instance = new LoginClass(context, baseUrl, isDebug);
        }
        return instance;
    }

    @Override
    public boolean isDebugMode() {
        return isDebugModeEnabled;
    }

    @Override
    public LoginClass setFacebookLogin(boolean facebookLogin) {
        isFacebookLogin = facebookLogin;
        return this;
    }

    @Override
    public LoginClass setGoogleLogin(boolean googleLogin) {
        isGoogleLogin = googleLogin;
        return this;
    }

    @Override
    public LoginClass setEmailLogin(boolean emailLogin) {
        isEmailLogin = emailLogin;
        return this;
    }

    @Override
    public ApiInterface getApiInterface() {
        return apiInterface;
    }

    @Override
    public void setApiInterface(Retrofit retrofit) {
        if (retrofit != null) {
            this.apiInterface = retrofit.create(ApiInterface.class);
        }
    }

    @Override
    public void openLoginPage(final Context context, @LoginType int loginType) {
        if (!LoginPrefUtil.isLoginComplete(context, loginType)) {
            context.startActivity(new Intent(context, LoginActivity.class)
                    .putExtra(LoginConstant.LOGIN_TYPE, loginType));
        }else {
            LoginUtil.showToast(context, "User already logged in.");
        }
    }

    @Override
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

    @Override
    public boolean isFacebookLogin() {
        return isFacebookLogin;
    }

    @Override
    public boolean isGoogleLogin() {
        return isGoogleLogin;
    }

    @Override
    public boolean isEmailLogin() {
        return isEmailLogin;
    }

    @Override
    public Retrofit getRetrofit() {
        return retrofit;
    }

    @Override
    public LoginCallback.TermUseListener getTermsOfUseListener() {
        return termsOfUseListener;
    }

    @Override
    public void setTermsOfUseListener(LoginCallback.TermUseListener termsOfUseListener) {
        this.termsOfUseListener = termsOfUseListener;
    }

    @Override
    public LoginClass addLoginListener(LoginCallback.Listener callback) {
        synchronized (mLoginListener) {
            this.mLoginListener.put(callback.hashCode(), callback);
        }
        return this;
    }

    @Override
    public void removeLoginListener(LoginCallback.Listener callback) {
        if (callback != null && mLoginListener.get(callback.hashCode()) != null) {
            synchronized (mLoginListener) {
                this.mLoginListener.remove(callback.hashCode());
            }
        }
    }

    @Override
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

    @Override
    public boolean isEnableLogin() {
        return enableLogin;
    }

    @Override
    public LoginClass setEnableLogin(boolean enableLogin) {
        this.enableLogin = enableLogin;
        return this;
    }

    @Override
    public boolean isEnableSignup(ApiRequest apiRequest) {
        return apiRequest != null;
    }

    @Override
    public boolean isEnableForgetPass(ApiRequest apiRequest) {
        return apiRequest != null;
    }

    @Override
    public boolean isEnableAuthentication(ApiRequest apiRequest) {
        return apiRequest != null;
    }

    @Override
    public LoginClass setApiRequests(HashMap<Integer, HashMap<Integer, ApiRequest>> apiRequests) {
        this.apiRequests = apiRequests;
        return this;
    }

    @Override
    public HashMap<Integer, HashMap<Integer, ApiRequest>> getApiRequests() {
        return apiRequests;
    }

    @Override
    public LoginClass setSignupForm(HashMap<Integer, FormBuilderModel> signupFormDetail) {
        this.signupFormDetail = signupFormDetail;
        return this;
    }

    @Override
    public HashMap<Integer, FormBuilderModel> getSignupFormDetail() {
        return signupFormDetail;
    }

    @Override
    public void syncSignupForm(){
        FormBuilder.getInstance().syncSignupForm();
    }
}
