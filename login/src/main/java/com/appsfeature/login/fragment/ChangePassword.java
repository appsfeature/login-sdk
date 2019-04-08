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
import com.appsfeature.login.util.LoginUtil;
import com.progressbutton.ProgressButton;


public class ChangePassword extends BaseFragment {

    private EditText etNewPassword, etConfirmPassword;
    private LinearLayout llSignup;
    private String userId;
    private Listener mListener;
    private ProgressButton btnAction;

    public interface Listener {
        void onAddSignupScreen();

        void onPasswordChangedSuccess();

    }

    public static ChangePassword newInstance(String userId, Listener mListener) {
        ChangePassword fragment = new ChangePassword();
        fragment.userId = userId;
        fragment.mListener = mListener;
        LoginUtil.setSlideAnimation(fragment, Gravity.TOP);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.login_change_pass, container, false);
        initToolBarTheme(getActivity(), v, "Change Password");
        InitUI(v);
        return v;
    }

    private void InitUI(View v) {

        etNewPassword = (EditText) v.findViewById(R.id.et_employee_new_pass);
        etConfirmPassword = (EditText) v.findViewById(R.id.et_employee_confirm_pass);
        llSignup = (LinearLayout) v.findViewById(R.id.ll_signup);

        btnAction = ProgressButton.newInstance(getContext(), v)
                .setText("Send Request")
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LoginUtil.hideKeybord(getActivity());
                        executeTask();
                    }
                });
        btnAction.setText(getString(R.string.login));


        llSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onAddSignupScreen();
                }
            }
        });

        btnAction.setOnEditorActionListener(etConfirmPassword, "Submit");

    }


    private void executeTask() {
        String newPassword = etNewPassword.getText().toString();
        LoginNetwork.getInstance(getContext())
                .changePassword(newPassword, new LoginListener<Void>() {
                    @Override
                    public void onPreExecute() {
                        btnAction.startProgress();

                    }

                    @Override
                    public void onSuccess(Void response) {
                        btnAction.revertSuccessProgress(new ProgressButton.Listener() {
                            @Override
                            public void onAnimationCompleted() {
                                mListener.onPasswordChangedSuccess();
                            }
                        });
                    }

                    @Override
                    public void onError(String response) {
                        btnAction.revertProgress();

                    }
                });


    }


}