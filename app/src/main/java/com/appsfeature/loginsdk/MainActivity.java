package com.appsfeature.loginsdk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.appsfeature.login.LoginActivity;
import com.appsfeature.login.LoginSDK;
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

    public void onLoginOpen(View view) {
        AppApplication.getInstance().getLoginSdk()
                .openLoginPage(this);
    }

    public void onAttachFragment(View view) {
        Fragment fragment = FormBuilder.getInstance().getFragment(LoginSDK.getInstance().getSignupFormDetail(), new FormResponse.FormSubmitListener() {
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
