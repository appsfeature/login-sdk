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


public class ScreenSignUp extends BaseFragment {

    private EditText etName, etEmail, etPassword;
    private LinearLayout llTearms, llLogin;

    private Listener mListener;
    private ProgressButton btnAction;

    public interface Listener {
        void addLoginCompanyOption();

        void onLoginSuccess();
    }

    public static ScreenSignUp newInstance(Listener mListener) {
        ScreenSignUp fragment = new ScreenSignUp();
        fragment.mListener = mListener;
        LoginUtil.setSlideAnimation(fragment, Gravity.TOP);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.login_signup, container, false);
        initToolBarTheme(getActivity(), v, "SignUp");
        InitUI(v);
        return v;
    }

    private void InitUI(View v) {

        etName = (EditText) v.findViewById(R.id.et_company_name);
        etEmail = (EditText) v.findViewById(R.id.et_company_email);
        etPassword = (EditText) v.findViewById(R.id.et_company_password);

        llTearms = (LinearLayout) v.findViewById(R.id.ll_tearm_user);
        llLogin = (LinearLayout) v.findViewById(R.id.ll_login);

        btnAction = ProgressButton.newInstance(getContext(), v)
                .setText("SignUp")
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!FieldValidation.isEmpty(getContext(), etName)) {
                            return;
                        } else if (!FieldValidation.isEmpty(getContext(), etEmail)) {
                            return;
                        } else if (!FieldValidation.isEmpty(getContext(), etPassword)) {
                            return;
                        }
                        LoginUtil.hideKeybord(getActivity());
                        executeTask();
                    }
                });


        llTearms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                addFragment(AppWebView.newInstance("TEARMS OF USE", ConfigLibrary.TERMS_OF_USE, AppWebView.URL), "appWebView");
            }
        });

        llLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.addLoginCompanyOption();
            }
        });

        btnAction.setOnEditorActionListener(etPassword, "Signup");
    }


    private void executeTask() {
        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        LoginNetwork.getInstance(getContext())
                .signUp(name, email, password, new LoginListener<Profile>() {
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