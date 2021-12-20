package com.appsfeature.login.fragment;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.appsfeature.login.R;
import com.appsfeature.login.util.LoginUtil;

public class BaseFragment extends Fragment {

    public void initToolBarTheme(final Activity act, View v, String title) {
        ImageView ivBack = v.findViewById(R.id.iv_action_back);
        TextView tvTitle = v.findViewById(R.id.tv_titile);
        tvTitle.setText(title);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUtil.hideKeybord(act);
                act.onBackPressed();
            }
        });
    }
}
