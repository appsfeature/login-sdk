package com.appsfeature.login.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.appsfeature.login.LoginSDK;
import com.appsfeature.login.R;
import com.appsfeature.login.interfaces.ApiType;
import com.appsfeature.login.interfaces.NetworkListener;
import com.appsfeature.login.network.LoginNetwork;
import com.appsfeature.login.util.FieldValidation;
import com.appsfeature.login.util.LoginUtil;
import com.progressbutton.ProgressButton;

/**
 * @author Abhijit Rao on 5/22/2017.
 */
public class ForgotPassword extends BaseFragment {

    private EditText etEmailOrMobile, etOtp;
    private boolean isOtpSend;
    private Listener mListener;
    private ProgressButton btnAction;
    private Activity activity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ForgotPassword.Listener) {
            mListener = (ForgotPassword.Listener) context;
        }
    }

    public interface Listener {
        void onForgetPassAddSignupScreen();
        void onForgetAddPasswordChangeScreen();
    }

    public static ForgotPassword newInstance(Bundle bundle) {
        ForgotPassword fragment = new ForgotPassword();
        fragment.setArguments(bundle);
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
        etEmailOrMobile = v.findViewById(R.id.et_employee_username);
        etOtp = v.findViewById(R.id.et_employee_pin);
        LinearLayout llSignup = v.findViewById(R.id.ll_signup);
        LinearLayout llDivider = v.findViewById(R.id.ll_divider);

        if(!LoginSDK.getInstance().isEnableSignup(apiRequestMap.get(ApiType.SIGNUP))){
            llSignup.setVisibility(View.GONE);
            llDivider.setVisibility(View.GONE);
        }

        TextView tagSignup = v.findViewById(R.id.tag_signup);
        tagSignup.setText(getString(R.string.sign_up));

        btnAction = ProgressButton.newInstance(getContext(), v)
                .setText("Generate OTP")
                .setOnClickListener(new ProgressButton.ClickListener() {
                    @Override
                    public void onClicked(View view) {
                        if(!isOtpSend) {
                            if (!FieldValidation.isEmpty(getContext(), etEmailOrMobile)) {
                                return;
                            }
                        } else if (!FieldValidation.isEmpty(getContext(), etOtp)) {
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
                    mListener.onForgetPassAddSignupScreen();
                }
            }
        });

        btnAction.setOnEditorActionListener(etOtp,"Send OTP");

    }


    private void executeTask() {
        String emailOrMobile = etEmailOrMobile.getText().toString();
        String otp = etOtp.getText().toString();

        LoginNetwork.get(loginType)
                .forgotPassword(activity, emailOrMobile, otp, isOtpSend, new NetworkListener<Boolean>() {
                    @Override
                    public void onPreExecute() {
                        btnAction.startProgress();
                    }

                    @Override
                    public void onSuccess(Boolean response) {
                        if(!isOtpSend) {
                            etEmailOrMobile.setVisibility(View.GONE);
                            etOtp.setVisibility(View.VISIBLE);
                            btnAction.setOnEditorActionListener(etOtp,"Submit");
                            isOtpSend = true;
                            btnAction.revertProgress();
                            btnAction.setText("Submit");
                        }else{
                            btnAction.revertSuccessProgress(new ProgressButton.Listener() {
                                @Override
                                public void onAnimationCompleted() {
                                    mListener.onForgetAddPasswordChangeScreen();
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