package com.appsfeature.login;

import android.content.Context;

import com.appsfeature.login.interfaces.LoginType;
import com.appsfeature.login.model.Profile;
import com.appsfeature.login.util.LoginConstant;
import com.appsfeature.login.util.LoginPrefUtil;
import com.appsfeature.login.util.LoginUtil;

public class LoginDetail {


    public static boolean isRegComplete(Context context) {
        return isRegComplete(context, LoginType.DEFAULT_USER);
    }
    public static boolean isRegComplete(Context context, @LoginType int loginType) {
        return LoginPrefUtil.isRegComplete(context, loginType);
    }

    public static boolean isLoginComplete(Context context) {
        return isLoginComplete(context, LoginType.DEFAULT_USER);
    }
    public static boolean isLoginComplete(Context context, @LoginType int loginType) {
        return LoginPrefUtil.isLoginComplete(context, loginType);
    }

    public static String getUserName(Context context) {
        return getUserName(context, LoginType.DEFAULT_USER);
    }
    public static String getUserName(Context context, @LoginType int loginType) {
        return LoginPrefUtil.getUserName(context, loginType);
    }

    public static String getUserImage(Context context) {
        return getUserImage(context, LoginType.DEFAULT_USER);
    }
    public static String getUserImage(Context context, @LoginType int loginType) {
        return LoginPrefUtil.getUserImage(context, loginType);
    }

    public static String getUserId(Context context) {
        return getUserId(context, LoginType.DEFAULT_USER);
    }
    public static String getUserId(Context context, @LoginType int loginType) {
        return LoginPrefUtil.getUserId(context, loginType);
    }

    public static String getUserMobile(Context context) {
        return getUserMobile(context, LoginType.DEFAULT_USER);
    }
    public static String getUserMobile(Context context, @LoginType int loginType) {
        return LoginPrefUtil.getUserMobile(context, loginType);
    }

    public static String getEmailId(Context context) {
        return getEmailId(context, LoginType.DEFAULT_USER);
    }
    public static String getEmailId(Context context, @LoginType int loginType) {
        return LoginPrefUtil.getEmailId(context, loginType);
    }

    public static String getProfileJson(Context context) {
        return getProfileJson(context, LoginType.DEFAULT_USER);
    }
    public static String getProfileJson(Context context, @LoginType int loginType) {
        return LoginPrefUtil.getProfileJson(context, loginType);
    }

    public static Profile getUserProfile(Context context) {
        return getUserProfile(context, LoginType.DEFAULT_USER);
    }
    public static Profile getUserProfile(Context context, @LoginType int loginType) {
        return LoginUtil.getUserProfileData(context, loginType);
    }
}
