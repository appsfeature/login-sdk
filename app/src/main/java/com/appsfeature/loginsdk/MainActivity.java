package com.appsfeature.loginsdk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.appsfeature.login.LoginSDK;
import com.appsfeature.login.interfaces.LoginType;
import com.appsfeature.login.model.Profile;
import com.appsfeature.login.util.LoginPrefUtil;
import com.formbuilder.FormBuilder;
import com.formbuilder.interfaces.FormResponse;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onLoginUser(View view) {
        AppApplication.getInstance().getLoginSdk()
                .openLoginPage(this, LoginType.DEFAULT_USER);
    }

    public void onLoginAdmin(View view) {
        AppApplication.getInstance().getLoginSdk()
                .openLoginPage(this, LoginType.ADMIN);
    }

    public void onClearPreferences(View view) {
        LoginPrefUtil.clearPreferences(this);
    }

    public void onAttachFragment(@LoginType int loginType) {
        Fragment fragment = FormBuilder.getInstance().getFragment(LoginSDK.getInstance().getSignupFormDetail().get(loginType), new FormResponse.FormSubmitListener() {
            @Override
            public void onFormSubmitted(String data) {
                Profile profile = LoginSDK.getGson().fromJson(data, Profile.class);
            }
        });
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(android.R.id.content, fragment, "tag");
        transaction.addToBackStack("tag");
        transaction.commit();
    }
}
