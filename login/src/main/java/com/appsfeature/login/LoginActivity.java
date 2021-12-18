package com.appsfeature.login;

import android.os.Bundle;

import com.appsfeature.login.fragment.ChangePassword;
import com.appsfeature.login.fragment.ForgotPassword;
import com.appsfeature.login.fragment.ScreenLogin;
import com.appsfeature.login.fragment.ScreenSignUp;
import com.appsfeature.login.util.LoginPrefUtil;


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
        LoginSDK.getInstance().dispatchLoginListener(LoginSDK.getLoginCredentials(), null);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        LoginSDK.getInstance().dispatchLoginListener(LoginSDK.getLoginCredentials(), new Exception("Login failed."));
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
        addFragment(ScreenSignUp.newInstance(new ScreenSignUp.Listener() {
            @Override
            public void addLoginCompanyOption() {
                addLoginScreen();
            }

            @Override
            public void onLoginSuccess() {
                onLoginCompletedSuccessful();
            }
        }),R.id.login_container, "signup");
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