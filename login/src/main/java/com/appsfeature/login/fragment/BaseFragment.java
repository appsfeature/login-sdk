package com.appsfeature.login.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.appsfeature.login.LoginSDK;
import com.appsfeature.login.R;
import com.appsfeature.login.interfaces.LoginType;
import com.appsfeature.login.model.ApiRequest;
import com.appsfeature.login.util.LoginConstant;
import com.appsfeature.login.util.LoginUtil;

import java.util.HashMap;

public class BaseFragment extends Fragment {

    @LoginType
    protected int loginType = LoginType.DEFAULT_USER;
    protected HashMap<Integer, ApiRequest> apiRequestMap;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIntentData();
    }

    protected String getTitle(ApiRequest apiRequest, String defaultTitle) {
        return apiRequest != null ? apiRequest.getTitle() : defaultTitle;
    }

    private void getIntentData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            loginType = bundle.getInt(LoginConstant.LOGIN_TYPE);
            apiRequestMap = LoginSDK.getInstance().getApiRequests().get(loginType);
            if(apiRequestMap == null){
                LoginUtil.logIntegration("Invalid Integration: apiRequestMap == null");
            }
        }
    }

    public void initToolBarTheme(final Activity act, View v, String title) {
        ImageView ivBack = v.findViewById(R.id.iv_action_back);
        TextView tvTitle = v.findViewById(R.id.tv_title);
        tvTitle.setText(title);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUtil.hideKeybord(act);
                act.onBackPressed();
            }
        });
    }
}

