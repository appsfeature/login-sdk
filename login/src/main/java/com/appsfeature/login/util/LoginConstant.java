package com.appsfeature.login.util;

public class LoginConstant {

    public static final String OPEN_EDIT_PROFILE = "isOpenEditProfile";

    public interface SharedPref {
        String IS_LOGIN_COMPLETE = "isLogComplete";
        String IS_REGISTRATION_COMPLETE = "isRegComplete";
        String LOGIN_JSON = "login_json" ;
        String EMAIL_OR_MOBILE = "email_or_mobile" ;
        String USER_NAME = "userName";
        String USER_PHOTO_URL = "photoUrl";
        String USER_ID_AUTO = "auto_id";
        String USER_UID = "userUid";
        String USER_EMAIL = "userEmail";
        String USER_EMAIL_VERIFIED = "userEmail_verified";
        String USER_PHONE = "userPhone";
        String USER_ADDRESS = "userAddress";
        String USER_STATE = "userState";
        String USER_POSTAL_CODE = "userPostalCode";
        String USER_GENDER = "user_gender";
        String USER_ABOUT_ME = "user_about_me";
        String USER_POINTS = "userPoints";
        String USER_DATE_OF_BIRTH = "user_date_of_birth";
        String APP_USER = "appUser";
        String FCM_TOKEN = "appUser";
        String LOGIN_PROVIDER = "login_provider";
        String PLAYER_ID = "PLAYER_ID";
        String IS_USER_NOT_VERIFIED = "isUserNotVerified";
        String IS_VERIFICATION_EMAIL_SENT = "isVerificationEmailSent";
        String USER_LOGIN_STATUS = "login_status" ;
        int USER_LOGIN_NOT = 0 ;
        int USER_LOGIN_HALF = 1 ;
        int USER_LOGIN_COMPLETE = 2 ;
    }
}
