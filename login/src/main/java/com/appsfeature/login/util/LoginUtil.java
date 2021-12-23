package com.appsfeature.login.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.provider.Settings;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appsfeature.login.R;
import com.appsfeature.login.interfaces.LoginType;
import com.appsfeature.login.model.Profile;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginUtil {
    private static final long ANIM_DURATION_MEDIUM = 500;

    public static void setSlideAnimation(Fragment fragment, int gravity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Fade slideTransition = new Fade();
//            slideTransition.setDuration(ANIM_DURATION_MEDIUM);
////            ChangeBounds changeBoundsTransition = new ChangeBounds();
////            changeBoundsTransition.setDuration(ANIM_DURATION_MEDIUM);
//            fragment.setEnterTransition(slideTransition);
//            fragment.setAllowEnterTransitionOverlap(true);
//            fragment.setAllowReturnTransitionOverlap(true);
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
        TextView tvTitle = (TextView) v.findViewById(R.id.tv_title);
        tvTitle.setText(title);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeybord(act);
                act.finish();
            }
        });
    }

    public static void saveUserProfileData(Context context, @LoginType int loginType, Profile loginUser) {
        if (loginUser != null) {
            LoginPrefUtil.setProfile(context, loginType, loginUser);
        }
    }

    public static Profile getUserProfileData(Context context, @LoginType int loginType) {
        return LoginPrefUtil.getUserProfile(context, loginType);
    }

    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    public static String getSecurityCode(Context ctx) {
        String keyHash = null;
        try {
            @SuppressLint("PackageManagerGetSignatures")
            PackageInfo info = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                keyHash = Base64.encodeToString(md.digest(), Base64.NO_WRAP);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return keyHash;
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * @apiNote throw new IllegalArgumentException();
     */
    public static void logIntegration(String message){
        String tag = "login-sdk";
        Log.e(tag, ".     |  |");
        Log.e(tag, ".     |  |");
        Log.e(tag, ".     |  |");
        Log.e(tag, ".   \\ |  | /");
        Log.e(tag, ".    \\    /");
        Log.e(tag, ".     \\  /");
        Log.e(tag, ".      \\/");
        Log.e(tag, ".");
        Log.e(tag, message);
        Log.e(tag, ".");
        Log.e(tag, ".      /\\");
        Log.e(tag, ".     /  \\");
        Log.e(tag, ".    /    \\");
        Log.e(tag, ".   / |  | \\");
        Log.e(tag, ".     |  |");
        Log.e(tag, ".     |  |");
        Log.e(tag, ".");
    }
}
