package com.appsfeature.login.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.appsfeature.login.LoginSDK;
import com.appsfeature.login.model.Profile;

public class LoginPrefUtil {

    private static final String TAG = "LoginPrefUtil";

    public static String getString(Context context, String key) {
        if (getDefaultSharedPref(context) != null) {
            return decrypt(getDefaultSharedPref(context).getString(encrypt(key), ""));
        } else {
            return decrypt("");
        }
    }

    public static int getInt(Context context, String key) {
        if (getDefaultSharedPref(context) != null) {
            return getDefaultSharedPref(context).getInt(encrypt(key), 0);
        } else {
            return (0);
        }
    }

    public static int getIntDef(Context context, String key, int def) {
        return getDefaultSharedPref(context).getInt(encrypt(key), def);
    }

    public static float getFloat(Context context, String key) {
        return getDefaultSharedPref(context).getFloat(encrypt(key), 0);

    }

    public static long getLong(Context context, String key) {
        return getDefaultSharedPref(context).getLong(encrypt(key), 0);
    }

    public static boolean getBoolean(Context context, String key) {
        if (getDefaultSharedPref(context) != null) {
            return getDefaultSharedPref(context).getBoolean(key, false);
        } else {
            return false;
        }
    }


    private static String encrypt(String input) {
        // This is base64 encoding, which is not an encryption
        return input;
//        if (SupportUtil.isEmptyOrNull( input )) {
//            return input ;
//        } else {
//            return Base64.encodeToString(input.getBytes(), Base64.DEFAULT);
//        }
    }

    private static String decrypt(String input) {
        return input;
//        if (SupportUtil.isEmptyOrNull( input )) {
//            return input ;
//        } else {
//            return new String(Base64.decode(input, Base64.DEFAULT));
//        }
    }

    /**
     * Set String value for a particular key.
     *
     */
    public static void setProfile(Context context, Profile profile) {
        if (getDefaultSharedPref(context) != null) {
            final SharedPreferences.Editor editor = getDefaultSharedPref(context).edit();
            if (editor != null) {
                editor.putString(encrypt(LoginConstant.SharedPref.USER_ID_AUTO), getEmptyData(profile.getUserId()));
                editor.putString(encrypt(LoginConstant.SharedPref.USER_NAME), getEmptyData(profile.getName()));
                editor.putString(encrypt(LoginConstant.SharedPref.USER_PHOTO_URL), getEmptyData(profile.getImage()));
                editor.putString(encrypt(LoginConstant.SharedPref.USER_PHONE), getEmptyData(profile.getMobile()));
                editor.putString(encrypt(LoginConstant.SharedPref.USER_EMAIL), getEmptyData(profile.getEmail()));
                editor.apply();
            }
        }
    }

    public static String getUserName(Context context) {
        return LoginPrefUtil.getString(context, LoginConstant.SharedPref.USER_NAME);
    }

    public static String getUserImage(Context context) {
        return LoginPrefUtil.getString(context, LoginConstant.SharedPref.USER_PHOTO_URL);
    }

    public static String getUserId(Context context) {
        return LoginPrefUtil.getString(context, LoginConstant.SharedPref.USER_ID_AUTO);
    }

    public static String getUserMobile(Context context) {
        return LoginPrefUtil.getString(context, LoginConstant.SharedPref.USER_PHONE);
    }

    public static String getEmailId(Context context) {
        return LoginPrefUtil.getString(context, LoginConstant.SharedPref.USER_EMAIL);
    }
    public static Profile getUserProfile(Context context) {
        return LoginUtil.getUserProfileData(context);
    }

    public static boolean isRegComplete(Context context) {
        return LoginPrefUtil.getBoolean(context, LoginConstant.SharedPref.IS_REGISTRATION_COMPLETE);
    }

    public static boolean isLoginComplete(Context context) {
        return LoginPrefUtil.getBoolean(context, LoginConstant.SharedPref.IS_LOGIN_COMPLETE);
    }

    public static void setRegComplete(Context context, boolean flag) {
        LoginPrefUtil.setBoolean(context, LoginConstant.SharedPref.IS_REGISTRATION_COMPLETE, flag);
    }

    public static void setLoginComplete(Context context, boolean flag) {
        LoginPrefUtil.setBoolean(context, LoginConstant.SharedPref.IS_LOGIN_COMPLETE, flag);
    }

    private static String getEmptyData(String data) {
        return TextUtils.isEmpty(data) ? "" : data;
    }

    public static void setString(Context context, String key, String... values) {
        if (getDefaultSharedPref(context) != null && !TextUtils.isEmpty(key)) {
            final SharedPreferences.Editor editor = getDefaultSharedPref(context).edit();
            if (editor != null) {
                for (String keys : values) {
                    editor.putString(encrypt(key), encrypt(keys));
                    editor.apply();
                }
            }
        }
    }

    /**
     * Set int value for key.
     *
     * @param key   The string resource Id of the key
     * @param value The value to set for the key
     */
    public static void setInt(Context context, String key, int value) {
        if (getDefaultSharedPref(context) != null && !TextUtils.isEmpty(key)) {
            final SharedPreferences.Editor editor = getDefaultSharedPref(context).edit();
            if (editor != null) {
                editor.putInt(encrypt(key), value);
                editor.apply();
            }
        }
    }

    /**
     * Set float value for a key.
     *
     * @param key   The string resource Id of the key
     * @param value The value to set for the key
     */
    public static void setFloat(Context context, String key, float value) {
        final SharedPreferences.Editor editor = getDefaultSharedPref(context).edit();
        editor.putFloat(encrypt(key), value);
        editor.apply();
    }

    /**
     * Set long value for key.
     *
     * @param key   The string resource Id of the key
     * @param value The value to set for the key
     */
    public static void setLong(Context context, String key, long value) {
        final SharedPreferences.Editor editor = getDefaultSharedPref(context).edit();
        editor.putLong(encrypt(key), value);
        editor.apply();
    }

    /**
     * Set boolean value for key.
     *
     * @param key   The string resource Id of the key
     * @param value The value to set for the key
     */
    public static void setBoolean(Context context, String key, boolean value) {
        if (getDefaultSharedPref(context) != null && !TextUtils.isEmpty(key)) {
            final SharedPreferences.Editor editor = getDefaultSharedPref(context).edit();
            if (editor != null) {
                editor.putBoolean(encrypt(key), value);
                editor.apply();
            }
        }
    }

    /**
     * Clear all preferences.
     */
    public static void clearPreferences(Context context) {
        final SharedPreferences.Editor editor = getDefaultSharedPref(context).edit();
        editor.clear();
        editor.apply();
    }

    private static SharedPreferences sharedPreferences;

    private static SharedPreferences getDefaultSharedPref(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(context.getPackageName() + ".login.sdk", Context.MODE_PRIVATE);
        }
        return sharedPreferences;
    }

}