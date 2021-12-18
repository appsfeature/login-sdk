package com.appsfeature.loginsdk;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.appsfeature.login.LoginSDK;
import com.appsfeature.login.model.Profile;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onLoginOpen(View view) {
        AppApplication.getInstance().getLoginSdk()
                .openLoginPage(this, false);
    }
}
