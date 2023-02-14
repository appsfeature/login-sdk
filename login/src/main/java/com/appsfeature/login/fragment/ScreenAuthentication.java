package com.appsfeature.login.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.appsfeature.login.R;
import com.appsfeature.login.interfaces.NetworkListener;
import com.appsfeature.login.network.LoginNetwork;
import com.appsfeature.login.util.FieldValidation;
import com.appsfeature.login.util.LoginUtil;
import com.progressbutton.ProgressButton;

/**
 * @author Abhijit Rao on 5/22/2017.
 */
public class ScreenAuthentication extends BaseFragment {

    private EditText etOtp;
    private boolean isOtpSend;
    private Listener mListener;
    private ProgressButton btnAction;
    private Activity activity;
    private String emailOrMobile;
    private TextView tagMessage, tagResend;
    private View llResend;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ScreenAuthentication.Listener) {
            mListener = (ScreenAuthentication.Listener) context;
        }
    }

    public interface Listener {
        void onAuthenticationCompleted();
    }

    public static ScreenAuthentication newInstance(Bundle bundle, String emailOrMobile) {
        ScreenAuthentication fragment = new ScreenAuthentication();
        fragment.setArguments(bundle);
        fragment.emailOrMobile = emailOrMobile;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.login_authentication, container, false);
        activity = getActivity();
        initToolBarTheme(getActivity(), v, "Authentication Required");
        InitUI(v);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        generateOtp();
    }

    private void InitUI(View v) {
        etOtp = v.findViewById(R.id.et_otp);
        llResend = v.findViewById(R.id.ll_resend);
        llResend.setVisibility(View.GONE);
        tagMessage = v.findViewById(R.id.tag_message);
        tagResend = v.findViewById(R.id.tag_resend);

        btnAction = ProgressButton.newInstance(getContext(), v)
                .setText("Verify OTP")
                .setOnClickListener(new ProgressButton.ClickListener() {
                    @Override
                    public void onClicked(View view) {
                        if(!isOtpSend) {
                            if (TextUtils.isEmpty(emailOrMobile)) {
                                return;
                            }
                            return;
                        } else if (!FieldValidation.isEmpty(getContext(), etOtp)) {
                            return;
                        }
                        LoginUtil.hideKeybord(activity);
                        executeTask();
                    }
                });
        llResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateOtp();
            }
        });

        btnAction.setOnEditorActionListener(etOtp,"Verify OTP");
    }

    private void generateOtp() {
        isOtpSend = false;
        if (TextUtils.isEmpty(emailOrMobile)) {
            LoginUtil.showToast(activity, "Invalid Email or Mobile");
            return;
        }
        LoginUtil.hideKeybord(activity);
        executeTask();
    }


    private void executeTask() {
        String otp = etOtp.getText().toString();

        LoginNetwork.get(loginType)
                .authentication(activity, emailOrMobile, otp, isOtpSend, new NetworkListener<Boolean>() {
                    @Override
                    public void onPreExecute() {
                        if(isOtpSend) {
                            btnAction.startProgress();
                        }
                    }

                    @Override
                    public void onSuccess(Boolean response) {
                        if(!isOtpSend) {
                            isOtpSend = true;
                            llResend.setVisibility(View.VISIBLE);
                            LoginUtil.showToast(activity, "OTP sent successful.");
                        }else{
                            btnAction.revertSuccessProgress(new ProgressButton.Listener() {
                                @Override
                                public void onAnimationCompleted() {
                                    mListener.onAuthenticationCompleted();
                                }
                            });
                        }
                    }

                    @Override
                    public void onError(Exception e) {
                        btnAction.revertProgress();
                        llResend.setVisibility(View.VISIBLE);
                        LoginUtil.showToast(activity, e.getMessage());
                    }
                });


    }


}