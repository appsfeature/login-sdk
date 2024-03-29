package com.appsfeature.login.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
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
import com.appsfeature.login.util.LoginUtil;
import com.progressbutton.ProgressButton;

/**
 * @author Abhijit Rao on 5/22/2017.
 */
public class ChangePassword extends BaseFragment {

    private EditText etNewPassword;
    private String userId;
    private Listener mListener;
    private ProgressButton btnAction;
    private Activity activity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ChangePassword.Listener) {
            mListener = (ChangePassword.Listener) context;
        }
    }

    public interface Listener {
        void onChangePassAddSignupScreen();

        void onChangePasswordSuccessful();
    }

    public static ChangePassword newInstance(Bundle bundle, String userId) {
        ChangePassword fragment = new ChangePassword();
        fragment.setArguments(bundle);
        fragment.userId = userId;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.login_change_pass, container, false);
        activity = getActivity();
        initToolBarTheme(activity, v, "Change Password");
        InitUI(v);
        return v;
    }

    private void InitUI(View v) {

        etNewPassword = v.findViewById(R.id.et_employee_new_pass);
        EditText etConfirmPassword = v.findViewById(R.id.et_employee_confirm_pass);
        LinearLayout llSignup = v.findViewById(R.id.ll_signup);

        if(!LoginSDK.getInstance().isEnableSignup(apiRequestMap.get(ApiType.SIGNUP))){
            llSignup.setVisibility(View.GONE);
        }
        TextView tagSignup = v.findViewById(R.id.tag_signup);
        tagSignup.setText(getString(R.string.sign_up));

        btnAction = ProgressButton.newInstance(getContext(), v)
                .setText("Send Request")
                .setOnClickListener(new ProgressButton.ClickListener() {
                    @Override
                    public void onClicked(View view) {
                        LoginUtil.hideKeybord(activity);
                        executeTask();
                    }
                });
        btnAction.setText(getString(R.string.login));


        llSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onChangePassAddSignupScreen();
                }
            }
        });

        btnAction.setOnEditorActionListener(etConfirmPassword, "Submit");

    }


    private void executeTask() {
        String newPassword = etNewPassword.getText().toString();
        LoginNetwork.get(loginType)
                .changePassword(userId, newPassword, new NetworkListener<Boolean>() {
                    @Override
                    public void onPreExecute() {
                        btnAction.startProgress();

                    }

                    @Override
                    public void onSuccess(Boolean response) {
                        btnAction.revertSuccessProgress(new ProgressButton.Listener() {
                            @Override
                            public void onAnimationCompleted() {
                                mListener.onChangePasswordSuccessful();
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