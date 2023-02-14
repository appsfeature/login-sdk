package com.appsfeature.login.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.appsfeature.login.BuildConfig;
import com.appsfeature.login.LoginSDK;
import com.appsfeature.login.R;
import com.appsfeature.login.interfaces.ApiType;
import com.appsfeature.login.interfaces.NetworkListener;
import com.appsfeature.login.model.ApiRequest;
import com.appsfeature.login.model.Profile;
import com.appsfeature.login.network.LoginNetwork;
import com.appsfeature.login.util.FieldValidation;
import com.appsfeature.login.util.LoginUtil;
import com.progressbutton.ProgressButton;

/**
 * @author Abhijit Rao on 5/22/2017.
 */
public class ScreenLogin extends BaseFragment {

    private EditText etUsername, etPassword;
    private Listener mListener;
    private ProgressButton btnAction;
    private Activity activity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Listener) {
            mListener = (Listener) context;
        }
    }

    public interface Listener {
        void onLoginAddSignupScreen();

        void onLoginAddForgotPasswordScreen();

        void onLoginSuccess();
    }

    public static ScreenLogin newInstance(Bundle bundle) {
        ScreenLogin fragment = new ScreenLogin();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.login_admin, container, false);
        activity = getActivity();
        initToolBarTheme(activity, v, getTitle(apiRequestMap.get(ApiType.LOGIN), "Login"));
        InitUI(v);
        return v;
    }

    private void InitUI(View v) {

        etUsername = v.findViewById(R.id.et_employee_username);
        etPassword = v.findViewById(R.id.et_employee_password);

        ApiRequest loginMap = apiRequestMap.get(ApiType.LOGIN);
        if(LoginSDK.getInstance().isDebugMode() && loginMap != null){
            if (!TextUtils.isEmpty(loginMap.getUsername())) {
                etUsername.setText(loginMap.getUsername());
            }
            if (!TextUtils.isEmpty(loginMap.getPassword())) {
                etPassword.setText(loginMap.getPassword());
            }
        }

        LinearLayout llSignup = v.findViewById(R.id.ll_signup);
        LinearLayout llForgot = v.findViewById(R.id.ll_forgot);
        LinearLayout llDivider = v.findViewById(R.id.ll_divider);

        if(!LoginSDK.getInstance().isEnableSignup(apiRequestMap.get(ApiType.SIGNUP))){
            llSignup.setVisibility(View.GONE);
        }

        if(!LoginSDK.getInstance().isEnableForgetPass(apiRequestMap.get(ApiType.GENERATE_OTP))){
            llForgot.setVisibility(View.GONE);
        }

        if(!LoginSDK.getInstance().isEnableSignup(apiRequestMap.get(ApiType.SIGNUP)) && !LoginSDK.getInstance().isEnableForgetPass(apiRequestMap.get(ApiType.GENERATE_OTP))){
            llDivider.setVisibility(View.GONE);
        }

        TextView tagSignup = v.findViewById(R.id.tag_signup);
        tagSignup.setText(getString(R.string.sign_up));

        btnAction = ProgressButton.newInstance(getContext(), v)
                .setText(getString(R.string.login))
                .setOnClickListener(new ProgressButton.ClickListener() {
                    @Override
                    public void onClicked(View view) {
                        if (!FieldValidation.isEmpty(getContext(), etUsername)) {
                            return;
                        } else if (!FieldValidation.isEmpty(getContext(), etPassword)) {
                            return;
                        }
                        LoginUtil.hideKeybord(activity);
                        executeTask();
                    }
                });

        llSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onLoginAddSignupScreen();
            }
        });

        llForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onLoginAddForgotPasswordScreen();
            }
        });

        btnAction.setOnEditorActionListener(etPassword, "Profile");

    }


    private void executeTask() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        LoginNetwork.get(loginType)
                .loginUser(activity, username, password, new NetworkListener<Profile>() {
                    @Override
                    public void onPreExecute() {
                        btnAction.startProgress();

                    }

                    @Override
                    public void onSuccess(Profile response) {
                        btnAction.revertSuccessProgress(new ProgressButton.Listener() {
                            @Override
                            public void onAnimationCompleted() {
                                mListener.onLoginSuccess();
                            }
                        });
                    }

                    @Override
                    public void onError(Exception e) {
                        btnAction.revertProgress();
                        LoginUtil.showToast(activity, e.getMessage());
                    }
                });

    }

}