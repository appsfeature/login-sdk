package com.appsfeature.loginsdk;

import android.content.Context;

import com.appsfeature.login.LoginDetail;
import com.appsfeature.login.interfaces.LoginType;

public class SupportUtil {

    public static String getUsername(Context context) {
        AppProfileModel profile = null;
        if (LoginDetail.isLoginComplete(context, LoginType.DEFAULT_USER)) {
            profile = LoginDetail.getProfileModel(context, LoginType.DEFAULT_USER, AppProfileModel.class);
        } else if (LoginDetail.isLoginComplete(context, LoginType.ADMIN)) {
            profile = LoginDetail.getProfileModel(context, LoginType.ADMIN, AppProfileModel.class);
        }
        return (profile != null && profile.getUsername() != null) ? profile.getUsername() : "";
    }
}
