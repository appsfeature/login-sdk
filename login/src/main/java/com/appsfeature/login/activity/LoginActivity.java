package com.appsfeature.login.activity;

import android.content.Intent;
import android.os.Bundle;

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
import com.formbuilder.FormBuilder;
import com.formbuilder.fragment.FormBuilderFragment;
import com.formbuilder.interfaces.FormResponse;
import com.formbuilder.model.FormBuilderModel;
import com.formbuilder.util.FBConstant;

import java.util.HashMap;


/**
 * @author Abhijit Rao on 5/22/2017.
 */
public class LoginActivity extends BaseActivity {


    @LoginType
    private int loginType;
    private Bundle bundle;
    private HashMap<Integer, ApiRequest> apiRequestMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        getArguments(getIntent());
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

    private void onLoginCompletedSuccessful() {
        finish();
        LoginSDK.getInstance().dispatchLoginListener(LoginSDK.getLoginCredentials(this, loginType), null);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        LoginSDK.getInstance().dispatchLoginListener(LoginSDK.getLoginCredentials(this, loginType), new Exception("Login failed."));
    }

    public void addLoginScreen() {
        addFragmentWithoutBackstack(ScreenLogin.newInstance(bundle, new ScreenLogin.Listener() {
            @Override
            public void addSignupScreen() {
                addSignup();
            }

            @Override
            public void addForgotPasswordScreen() {
                addForgotPassword();
            }

            @Override
            public void onLoginSuccess() {
                onLoginCompletedSuccessful();
            }
        }),R.id.login_container, "login");
    }

    public void addSignup() {
        if(LoginSDK.getInstance().getSignupFormDetail() == null) {
            addFragment(ScreenSignUp.newInstance(bundle, new ScreenSignUp.Listener() {
                @Override
                public void addLoginCompanyOption() {
                    addLoginScreen();
                }

                @Override
                public void onLoginSuccess() {
                    if (LoginSDK.getInstance().isEnableAuthentication(apiRequestMap.get(ApiType.GENERATE_OTP))) {
                        addAuthenticationScreen(LoginPrefUtil.getEmailOrMobile(LoginActivity.this, loginType));
                    } else {
                        onLoginCompletedSuccessful();
                    }
                }
            }), R.id.login_container, "signup");
        }else {
            addFragment(getFragment(LoginSDK.getInstance().getSignupFormDetail().get(loginType), new FormResponse.FormSubmitListener() {
                @Override
                public void onFormSubmitted(String data) {
                    Profile profile = LoginSDK.getGson().fromJson(data, Profile.class);
                    LoginPrefUtil.setEmailOrMobile(LoginActivity.this, loginType, profile.getEmailOrMobile());
                    if (LoginSDK.getInstance().isEnableAuthentication(apiRequestMap.get(ApiType.GENERATE_OTP))) {
                        addAuthenticationScreen(LoginPrefUtil.getEmailOrMobile(LoginActivity.this, loginType));
                    } else {
                        onLoginCompletedSuccessful();
                    }
                }
            }), R.id.login_container, "signup");
        }
    }

    @Nullable
    public Fragment getFragment(FormBuilderModel property, FormResponse.FormSubmitListener formSubmitListener) {
        if(property == null){
            return null;
        }
        FormBuilder.getInstance().setFormSubmitListener(formSubmitListener);
        Fragment fragment = new FormBuilderFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(FBConstant.CATEGORY_PROPERTY, property);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void addAuthenticationScreen(String emailOrMobile) {
        addFragmentWithoutBackstack(ScreenAuthentication.newInstance(bundle, emailOrMobile, new ScreenAuthentication.Listener() {
            @Override
            public void onAuthenticationCompleted() {
                onLoginCompletedSuccessful();
            }
        }),R.id.login_container, "authentication");
    }

    public void addForgotPassword() {
        addFragment(ForgotPassword.newInstance(bundle, new ForgotPassword.Listener() {
            @Override
            public void onAddSignupScreen() {
                addSignup();
            }

            @Override
            public void addPasswordChangeScreen() {
                addPasswordChange(LoginPrefUtil.getUserId(LoginActivity.this, loginType));
            }
        }),R.id.login_container, "forgotPassword");
    }
    public void addPasswordChange(String userId) {
        addFragment(ChangePassword.newInstance(bundle, userId, new ChangePassword.Listener() {
            @Override
            public void onAddSignupScreen() {
                addSignup();
            }

            @Override
            public void onPasswordChangedSuccess() {

            }
        }),R.id.login_container, "changePassword");
    }

}