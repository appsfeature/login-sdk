package com.appsfeature.login.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appsfeature.login.BuildConfig;
import com.appsfeature.login.LoginSDK;
import com.appsfeature.login.R;
import com.appsfeature.login.interfaces.NetworkListener;
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

    public interface Listener {
        void addSignupScreen();

        void addForgotPasswordScreen();

        void onLoginSuccess();
    }

    public static ScreenLogin newInstance(Listener mListener) {
        ScreenLogin fragment = new ScreenLogin();
        fragment.mListener = mListener;
        LoginUtil.setSlideAnimation(fragment, Gravity.TOP);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.login_admin, container, false);
        activity = getActivity();
        initToolBarTheme(activity, v, LoginSDK.getInstance().getTitleLogin());
        InitUI(v);
        return v;
    }

    private void InitUI(View v) {

        etUsername = v.findViewById(R.id.et_employee_username);
        etPassword = v.findViewById(R.id.et_employee_password);

        if(BuildConfig.DEBUG){
            etUsername.setText("Aiou1002");
            etPassword.setText("4545");
        }

        LinearLayout llSignup = v.findViewById(R.id.ll_signup);
        LinearLayout llForgot = v.findViewById(R.id.ll_forgot);
        LinearLayout llDivider = v.findViewById(R.id.ll_divider);

        if(!LoginSDK.getInstance().isEnableSignup()){
            llSignup.setVisibility(View.GONE);
        }

        if(!LoginSDK.getInstance().isEnableForgetPass()){
            llForgot.setVisibility(View.GONE);
        }

        if(!LoginSDK.getInstance().isEnableSignup() && !LoginSDK.getInstance().isEnableForgetPass()){
            llDivider.setVisibility(View.GONE);
        }

        TextView tagSignup = v.findViewById(R.id.tag_signup);
        tagSignup.setText(LoginSDK.getInstance().getTitleSignup());

        btnAction = ProgressButton.newInstance(getContext(), v)
                .setText(LoginSDK.getInstance().getTitleLogin())
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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
                mListener.addSignupScreen();
            }
        });

        llForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.addForgotPasswordScreen();
            }
        });

        btnAction.setOnEditorActionListener(etPassword, "Profile");

    }


    private void executeTask() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        LoginNetwork.getInstance()
                .loginUser(username, password, new NetworkListener<Profile>() {
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