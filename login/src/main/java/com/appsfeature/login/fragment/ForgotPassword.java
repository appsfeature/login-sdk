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
import com.appsfeature.login.network.LoginListener;
import com.appsfeature.login.network.LoginNetwork;
import com.appsfeature.login.util.FieldValidation;
import com.appsfeature.login.util.LoginUtil;
import com.progressbutton.ProgressButton;


public class ForgotPassword extends BaseFragment {

    private EditText etUsername, etPinCode;
    private LinearLayout llSignup;
    private boolean isOtpSend;
    private Listener mListener;
    private ProgressButton btnAction;

    public interface Listener {
        void onAddSignupScreen();
        void addPasswordChangeScreen();

    }

    public static ForgotPassword newInstance(Listener mListener) {
        ForgotPassword fragment = new ForgotPassword();
        fragment.mListener = mListener;
        LoginUtil.setSlideAnimation(fragment, Gravity.TOP);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.login_forgot, container, false);
        initToolBarTheme(getActivity(), v, "Forgot Password");
        InitUI(v);
        return v;
    }

    private void InitUI(View v) {

        etUsername = (EditText) v.findViewById(R.id.et_employee_username);
        etPinCode = (EditText) v.findViewById(R.id.et_employee_pin);
        llSignup = (LinearLayout) v.findViewById(R.id.ll_signup);

        btnAction = ProgressButton.newInstance(getContext(), v)
                .setText(getString(R.string.login))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!isOtpSend) {
                            if (!FieldValidation.isEmpty(getContext(), etUsername)) {
                                return;
                            }
                        } else if (!FieldValidation.isEmpty(getContext(), etPinCode)) {
                            return;
                        }
                        LoginUtil.hideKeybord(getActivity());
                        executeTask();
                    }
                });


        llSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener!=null){
                    mListener.onAddSignupScreen();
                }
            }
        });

        btnAction.setOnEditorActionListener(etPinCode,"Send OTP");

    }


    private void executeTask() {
        String username = etUsername.getText().toString();
        String otp = etPinCode.getText().toString();

        LoginNetwork.getInstance(getContext())
                .forgotPassword(username, otp, isOtpSend, new LoginListener<Boolean>() {
                    @Override
                    public void onPreExecute() {
                        btnAction.startProgress();

                    }

                    @Override
                    public void onSuccess(Boolean response) {
                        if(!isOtpSend) {
                            etUsername.setVisibility(View.GONE);
                            etPinCode.setVisibility(View.VISIBLE);
                            btnAction.setOnEditorActionListener(etPinCode,"Submit");
                            isOtpSend = true;
                            btnAction.revertProgress();
                            btnAction.setText("Submit");
                        }else{
                            btnAction.revertSuccessProgress(new ProgressButton.Listener() {
                                @Override
                                public void onAnimationCompleted() {
                                    mListener.addPasswordChangeScreen();
                                }
                            });
                        }

                    }

                    @Override
                    public void onError(Exception e) {
                        btnAction.revertProgress();
                    }
                });


    }


}