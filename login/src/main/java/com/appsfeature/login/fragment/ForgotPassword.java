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

import com.appsfeature.login.LoginSDK;
import com.appsfeature.login.R;
import com.appsfeature.login.interfaces.NetworkListener;
import com.appsfeature.login.network.LoginNetwork;
import com.appsfeature.login.util.FieldValidation;
import com.appsfeature.login.util.LoginUtil;
import com.progressbutton.ProgressButton;

/**
 * @author Abhijit Rao on 5/22/2017.
 */
public class ForgotPassword extends BaseFragment {

    private EditText etUsername, etPinCode;
    private boolean isOtpSend;
    private Listener mListener;
    private ProgressButton btnAction;
    private Activity activity;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.login_forgot, container, false);
        activity = getActivity();
        initToolBarTheme(getActivity(), v, "Forgot Password");
        InitUI(v);
        return v;
    }

    private void InitUI(View v) {

        etUsername = v.findViewById(R.id.et_employee_username);
        etPinCode = v.findViewById(R.id.et_employee_pin);
        LinearLayout llSignup = v.findViewById(R.id.ll_signup);

        TextView tagSignup = v.findViewById(R.id.tag_signup);
        tagSignup.setText(LoginSDK.getInstance().getTitleSignup());

        btnAction = ProgressButton.newInstance(getContext(), v)
                .setText("Generate OTP")
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
                        LoginUtil.hideKeybord(activity);
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

        LoginNetwork.getInstance()
                .forgotPassword(username, otp, isOtpSend, new NetworkListener<Boolean>() {
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
                        LoginUtil.showToast(activity, e.getMessage());
                    }
                });


    }


}