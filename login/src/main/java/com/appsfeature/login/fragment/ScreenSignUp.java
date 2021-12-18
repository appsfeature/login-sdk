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
import android.widget.Toast;

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
public class ScreenSignUp extends BaseFragment {

    private EditText etName, etEmailOrMobile, etUsername, etPassword;

    private Listener mListener;
    private ProgressButton btnAction;
    private Activity activity;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.login_signup, container, false);
        activity = getActivity();
        initToolBarTheme(activity, v, LoginSDK.getInstance().getTitleSignup());
        InitUI(v);
        return v;
    }

    private void InitUI(View v) {

        etName = v.findViewById(R.id.et_name);
        etEmailOrMobile = v.findViewById(R.id.et_email_mobile);
        etUsername = v.findViewById(R.id.et_username);
        etPassword = v.findViewById(R.id.et_password);
        TextView tagLogin = v.findViewById(R.id.tag_login);
        tagLogin.setText(LoginSDK.getInstance().getTitleLogin());

        LinearLayout llTearms = v.findViewById(R.id.ll_tearm_user);
        LinearLayout llLogin = v.findViewById(R.id.ll_login);

        if(!LoginSDK.getInstance().isEnableLogin()){
            llLogin.setVisibility(View.GONE);
        }

        btnAction = ProgressButton.newInstance(getContext(), v)
                .setText(LoginSDK.getInstance().getTitleSignup())
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!FieldValidation.isEmpty(getContext(), etName)) {
                            return;
                        } else if (!FieldValidation.isEmpty(getContext(), etEmailOrMobile)) {
                            return;
                        } else if (!FieldValidation.isEmpty(getContext(), etUsername)) {
                            return;
                        } else if (!FieldValidation.isEmpty(getContext(), etPassword)) {
                            return;
                        }
                        LoginUtil.hideKeybord(activity);
                        executeTask();
                    }
                });


        llTearms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LoginSDK.getInstance().getTermsOfUseListener() != null) {
                    LoginSDK.getInstance().getTermsOfUseListener().onOpenTermOfUse();
                }else {
                    Toast.makeText(v.getContext(), "Update later.", Toast.LENGTH_SHORT).show();
                }
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
        String emailOrMobile = etEmailOrMobile.getText().toString();
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        LoginNetwork.getInstance()
                .signUp(activity, name, emailOrMobile, username, password, new NetworkListener<Profile>() {
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