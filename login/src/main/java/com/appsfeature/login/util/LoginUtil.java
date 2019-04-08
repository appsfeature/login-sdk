package com.appsfeature.login.util;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.transition.Slide;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.appsfeature.login.R;

public class LoginUtil {
    private static final long ANIM_DURATION_MEDIUM = 500;

    public static void setSlideAnimation(Fragment fragment, int gravity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide slideTransition = new Slide(gravity);
            slideTransition.setDuration(ANIM_DURATION_MEDIUM);
//            ChangeBounds changeBoundsTransition = new ChangeBounds();
//            changeBoundsTransition.setDuration(ANIM_DURATION_MEDIUM);
            fragment.setEnterTransition(slideTransition);
            fragment.setAllowEnterTransitionOverlap(true);
            fragment.setAllowReturnTransitionOverlap(true);
//            fragment.setSharedElementEnterTransition(changeBoundsTransition);
        }
    }

    public static void hideKeybord(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        View f = activity.getCurrentFocus();
        if (null != f && null != f.getWindowToken() && EditText.class.isAssignableFrom(f.getClass()))
            imm.hideSoftInputFromWindow(f.getWindowToken(), 0);
        else
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public static void initToolBarForThemeActivity(final Activity act, View v, String title) {
        ImageView ivBack = (ImageView) v.findViewById(R.id.iv_action_back);
        TextView tvTitle = (TextView) v.findViewById(R.id.tv_titile);
        tvTitle.setText(title);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeybord(act);
                act.finish();
            }
        });
    }

}
