package com.appsfeature.loginsdk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.appsfeature.login.LoginSDK;
import com.appsfeature.login.model.Profile;

public class MainActivity extends AppCompatActivity {


    private LoginSDK loginSdk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginSdk = LoginSDK.getInstance(this, AppApplication.getInstance().getRetrofit())
                .setFacebookLogin(false)
                .setGoogleLogin(true)
                .setEmailLogin(true)
                .addListener(new LoginSDK.Listener() {
                    @Override
                    public void onSuccess(Profile response) {

                    }

                    @Override
                    public void onFailure(Exception e) {

                    }
                });
    }

    public void onLoginOpen(View view) {
        loginSdk.openLoginPage(this, false);
    }
}
