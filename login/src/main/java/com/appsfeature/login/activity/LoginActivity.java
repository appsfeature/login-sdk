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
public class LoginActivity extends BaseLoginActivity{

    @Override
    public int getLayoutContentView() {
        return R.layout.login_activity;
    }

    @Override
    public void onInitViews() {

    }

    @Override
    public Fragment getLoginFragment() {
        return ScreenLogin.newInstance(bundle);
    }

    @Override
    public Fragment getSignupFragment() {
        return ScreenSignUp.newInstance(bundle);
    }

    @Override
    public Fragment getScreenAuthFragment(String emailOrMobile) {
        return ScreenAuthentication.newInstance(bundle, emailOrMobile);
    }

    @Override
    public Fragment getForgotPasswordFragment() {
        return ForgotPassword.newInstance(bundle);
    }

    @Override
    public Fragment getChangePasswordFragment(String userId) {
        return ChangePassword.newInstance(bundle, userId);
    }

    @Nullable
    @Override
    public Fragment getFormBuilderFragment(FormBuilderModel property, FormResponse.FormSubmitListener formSubmitListener) {
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


}