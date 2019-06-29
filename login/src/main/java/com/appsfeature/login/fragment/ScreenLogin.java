package com.appsfeature.login.fragment;

/**
 * Created by Admin on 5/22/2017.
 */

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.appsfeature.login.R;
import com.appsfeature.login.model.Profile;
import com.appsfeature.login.network.LoginListener;
import com.appsfeature.login.network.LoginNetwork;
import com.appsfeature.login.util.FieldValidation;
import com.appsfeature.login.util.LoginUtil;
import com.progressbutton.ProgressButton;


public class ScreenLogin extends BaseFragment {

    private EditText etUsername, etPassword;
    private LinearLayout llSignup, llForgot;
    private Listener mListener;
    private ProgressButton btnAction;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.login_admin, container, false);
        initToolBarTheme(getActivity(), v, getString(R.string.login_admin));
        InitUI(v);
        return v;
    }

    private void InitUI(View v) {

        etUsername = (EditText) v.findViewById(R.id.et_employee_username);
        etPassword = (EditText) v.findViewById(R.id.et_employee_password);
        llSignup = (LinearLayout) v.findViewById(R.id.ll_signup);
        llForgot = (LinearLayout) v.findViewById(R.id.ll_forgot);

        btnAction = ProgressButton.newInstance(getContext(), v)
                .setText(getString(R.string.login))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!FieldValidation.isEmpty(getContext(), etUsername)) {
                            return;
                        } else if (!FieldValidation.isEmpty(getContext(), etPassword)) {
                            return;
                        }
                        LoginUtil.hideKeybord(getActivity());
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

        LoginNetwork.getInstance(getContext())
                .loginUser(username, password, new LoginListener<Profile>() {
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

                    }
                });

    }

}