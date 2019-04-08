package com.appsfeature.login.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.appsfeature.login.R;
import com.appsfeature.login.util.LoginUtil;

public class BaseFragment extends Fragment {

    public void initToolBarTheme(final Activity act, View v, String title) {
        ImageView ivBack = (ImageView) v.findViewById(R.id.iv_action_back);
        TextView tvTitle = (TextView) v.findViewById(R.id.tv_titile);
        tvTitle.setText(title);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUtil.hideKeybord(act);
                act.onBackPressed();
            }
        });
    }

    public void addFragment(Fragment fragment, String tag) {
        addFragment(fragment, android.R.id.content, tag);
    }

    public void addFragment(Fragment fragment, int container, String tag) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(container, fragment, tag);
        transaction.addToBackStack(tag);
        transaction.commit();
    }
    public void addFragmentWithoutBackStack(Fragment fragment, String tag) {
        addFragmentWithoutBackStack(fragment, android.R.id.content, tag);
    }
    public void addFragmentWithoutBackStack(Fragment fragment, int container, String tag) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(container, fragment, tag);
        transaction.commitAllowingStateLoss();
    }

}
