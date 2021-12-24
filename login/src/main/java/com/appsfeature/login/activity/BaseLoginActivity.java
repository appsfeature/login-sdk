package com.appsfeature.login.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.appsfeature.login.LoginSDK;
import com.appsfeature.login.R;
import com.appsfeature.login.fragment.ChangePassword;
import com.appsfeature.login.fragment.ForgotPassword;
import com.appsfeature.login.fragment.ScreenAuthentication;
import com.appsfeature.login.fragment.ScreenLogin;
import com.appsfeature.login.fragment.ScreenSignUp;
import com.appsfeature.login.interfaces.ApiType;
import com.appsfeature.login.interfaces.LoginType;
import com.appsfeature.login.model.ApiRequest;
import com.appsfeature.login.model.Profile;
import com.appsfeature.login.util.LoginConstant;
import com.appsfeature.login.util.LoginPrefUtil;
import com.appsfeature.login.util.LoginUtil;
import com.formbuilder.interfaces.FormResponse;
import com.formbuilder.model.FormBuilderModel;

import java.util.HashMap;


/**
 * @author Abhijit Rao on 5/22/2017.
 */
public abstract class BaseLoginActivity extends BaseActivity implements ScreenLogin.Listener, ScreenSignUp.Listener, FormResponse.FormSubmitListener
        , ScreenAuthentication.Listener, ForgotPassword.Listener, ChangePassword.Listener{

    @LayoutRes
    public abstract int getLayoutContentView();
    public abstract void onInitViews();
    public abstract Fragment getLoginFragment();
    public abstract Fragment getSignupFragment();
    public abstract Fragment getScreenAuthFragment(String emailOrMobile);
    public abstract Fragment getForgotPasswordFragment();
    public abstract Fragment getChangePasswordFragment(String userId);
    @Nullable
    public abstract Fragment getFormBuilderFragment(FormBuilderModel property, FormResponse.FormSubmitListener formSubmitListener);

    @LoginType
    protected int loginType;
    protected Bundle bundle;
    protected HashMap<Integer, ApiRequest> apiRequestMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutContentView());
        getArguments(getIntent());
        onInitViews();
        if(LoginSDK.getInstance().isEnableLogin()) {
            addLoginScreen();
        }else {
            addSignup();
        }
    }

    private void getArguments(Intent intent) {
        if(LoginSDK.getInstance().getApiRequests() == null){
            LoginUtil.logIntegration("Invalid Integration: getApiRequests == null");
        }
        if (intent != null && intent.getExtras() != null) {
            bundle = intent.getExtras();
            loginType = bundle.getInt(LoginConstant.LOGIN_TYPE, LoginType.DEFAULT_USER);
        } else {
            bundle = new Bundle();
            loginType = LoginType.DEFAULT_USER;
            bundle.putInt(LoginConstant.LOGIN_TYPE, loginType);
        }
        apiRequestMap = LoginSDK.getInstance().getApiRequests().get(loginType);
    }


    public void addLoginScreen() {
        addFragmentWithoutBackstack(getLoginFragment(),R.id.login_container, "login");
    }

    @Override
    public void onLoginAddSignupScreen() {
        addSignup();
    }

    @Override
    public void onLoginAddForgotPasswordScreen() {
        addForgotPassword();
    }

    @Override
    public void onLoginSuccess() {
        onLoginCompletedSuccessful();
    }


    public void addSignup() {
        if(LoginSDK.getInstance().getSignupFormDetail() == null) {
            addFragment(getSignupFragment(), R.id.login_container, "signup");
        }else {
            addFragment(getFormBuilderFragment(LoginSDK.getInstance().getSignupFormDetail().get(loginType), this), R.id.login_container, "signup");
        }
    }

    @Override
    public void onFormSubmitted(String data) {
        Profile profile = LoginSDK.getGson().fromJson(data, Profile.class);
        LoginPrefUtil.setEmailOrMobile(BaseLoginActivity.this, loginType, profile.getEmailOrMobile());
        if (LoginSDK.getInstance().isEnableAuthentication(apiRequestMap.get(ApiType.GENERATE_OTP))) {
            addAuthenticationScreen(LoginPrefUtil.getEmailOrMobile(BaseLoginActivity.this, loginType));
        } else {
            onLoginCompletedSuccessful();
        }
    }

    @Override
    public void onSignupAddLoginScreen() {
        addLoginScreen();
    }

    @Override
    public void onSignupSuccess() {
        if (LoginSDK.getInstance().isEnableAuthentication(apiRequestMap.get(ApiType.GENERATE_OTP))) {
            addAuthenticationScreen(LoginPrefUtil.getEmailOrMobile(BaseLoginActivity.this, loginType));
        } else {
            onLoginCompletedSuccessful();
        }
    }

    public void addAuthenticationScreen(String emailOrMobile) {
        addFragmentWithoutBackstack(getScreenAuthFragment(emailOrMobile),R.id.login_container, "authentication");
    }

    @Override
    public void onAuthenticationCompleted() {
        onLoginCompletedSuccessful();
    }

    public void addForgotPassword() {
        addFragment(getForgotPasswordFragment(),R.id.login_container, "forgotPassword");
    }

    @Override
    public void onForgetPassAddSignupScreen() {
        addSignup();
    }

    @Override
    public void onForgetAddPasswordChangeScreen() {
        addPasswordChange(LoginPrefUtil.getUserId(BaseLoginActivity.this, loginType));
    }

    public void addPasswordChange(String userId) {
        addFragment(getChangePasswordFragment(userId),R.id.login_container, "changePassword");
    }
    @Override
    public void onChangePassAddSignupScreen() {
        addSignup();
    }

    @Override
    public void onChangePasswordSuccessful() {

    }

    private void onLoginCompletedSuccessful() {
        finish();
        LoginSDK.getInstance().dispatchLoginListener(LoginSDK.getLoginCredentials(this, loginType), null);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        LoginSDK.getInstance().dispatchLoginListener(LoginSDK.getLoginCredentials(this, loginType), new Exception("Login failed."));
    }

}