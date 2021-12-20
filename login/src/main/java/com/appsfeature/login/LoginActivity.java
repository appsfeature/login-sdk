package com.appsfeature.login;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.appsfeature.login.fragment.ChangePassword;
import com.appsfeature.login.fragment.ForgotPassword;
import com.appsfeature.login.fragment.ScreenAuthentication;
import com.appsfeature.login.fragment.ScreenLogin;
import com.appsfeature.login.fragment.ScreenSignUp;
import com.appsfeature.login.model.Profile;
import com.appsfeature.login.util.LoginPrefUtil;
import com.formbuilder.FormBuilder;
import com.formbuilder.fragment.FormBuilderFragment;
import com.formbuilder.interfaces.FormResponse;
import com.formbuilder.model.FormBuilderModel;
import com.formbuilder.util.FBConstant;


/**
 * @author Abhijit Rao on 5/22/2017.
 */
public class LoginActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        if(LoginSDK.getInstance().isEnableLogin()) {
            addLoginScreen();
        }else {
            addSignup();
        }
    }

    private void onLoginCompletedSuccessful() {
        finish();
        LoginSDK.getInstance().dispatchLoginListener(LoginSDK.getLoginCredentials(this), null);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        LoginSDK.getInstance().dispatchLoginListener(LoginSDK.getLoginCredentials(this), new Exception("Login failed."));
    }

    public void addLoginScreen() {
        addFragmentWithoutBackstack(ScreenLogin.newInstance(new ScreenLogin.Listener() {
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
            addFragment(ScreenSignUp.newInstance(new ScreenSignUp.Listener() {
                @Override
                public void addLoginCompanyOption() {
                    addLoginScreen();
                }

                @Override
                public void onLoginSuccess() {
                    if (LoginSDK.getInstance().isEnableAuthentication()) {
                        addAuthenticationScreen(LoginPrefUtil.getEmailOrMobile(LoginActivity.this));
                    } else {
                        onLoginCompletedSuccessful();
                    }
                }
            }), R.id.login_container, "signup");
        }else {
            addFragment(getFragment(LoginSDK.getInstance().getSignupFormDetail(), new FormResponse.FormSubmitListener() {
                @Override
                public void onFormSubmitted(String data) {
                    Profile profile = LoginSDK.getGson().fromJson(data, Profile.class);
                    LoginPrefUtil.setEmailOrMobile(LoginActivity.this, profile.getEmailOrMobile());
                    if (LoginSDK.getInstance().isEnableAuthentication()) {
                        addAuthenticationScreen(LoginPrefUtil.getEmailOrMobile(LoginActivity.this));
                    } else {
                        onLoginCompletedSuccessful();
                    }
                }
            }), R.id.login_container, "signup");
        }
    }
    public Fragment getFragment(FormBuilderModel property, FormResponse.FormSubmitListener formSubmitListener) {
        FormBuilder.getInstance().setFormSubmitListener(formSubmitListener);
        Fragment fragment = new FormBuilderFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(FBConstant.CATEGORY_PROPERTY, property);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void addAuthenticationScreen(String emailOrMobile) {
        addFragmentWithoutBackstack(ScreenAuthentication.newInstance(emailOrMobile, new ScreenAuthentication.Listener() {
            @Override
            public void onAuthenticationCompleted() {
                onLoginCompletedSuccessful();
            }
        }),R.id.login_container, "authentication");
    }

    public void addForgotPassword() {
        addFragment(ForgotPassword.newInstance(new ForgotPassword.Listener() {
            @Override
            public void onAddSignupScreen() {
                addSignup();
            }

            @Override
            public void addPasswordChangeScreen() {
                addPasswordChange(LoginPrefUtil.getUserId(LoginActivity.this));
            }
        }),R.id.login_container, "forgotPassword");
    }
    public void addPasswordChange(String userId) {
        addFragment(ChangePassword.newInstance(userId, new ChangePassword.Listener() {
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