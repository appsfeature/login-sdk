package com.appsfeature.loginsdk;

import com.appsfeature.login.interfaces.ApiRequestType;
import com.appsfeature.login.interfaces.ApiType;
import com.appsfeature.login.interfaces.LoginType;
import com.appsfeature.login.model.ApiRequest;
import com.appsfeature.login.network.LoginParams;
import com.formbuilder.interfaces.FieldInputType;
import com.formbuilder.interfaces.FieldType;
import com.formbuilder.interfaces.RequestType;
import com.formbuilder.interfaces.SubmissionType;
import com.formbuilder.model.DynamicInputModel;
import com.formbuilder.model.FormBuilderModel;
import com.formbuilder.model.entity.PopupEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginDataMap {

    public static final String LOGIN_BASE_URL = "http://allinoneglobalmarket.com/mobile_app/";

    public static HashMap<Integer, HashMap<Integer, ApiRequest>> getApiRequestKeys() {
        HashMap<Integer, HashMap<Integer, ApiRequest>> loginMap = new HashMap<>();
        loginMap.put(LoginType.DEFAULT_USER, getApiRequestKeysForUserLogin());
        loginMap.put(LoginType.ADMIN, getApiRequestKeysForAdminLogin());
        return loginMap;
    }

    public static HashMap<Integer, ApiRequest> getApiRequestKeysForUserLogin() {
        HashMap<Integer, ApiRequest> hashMap = new HashMap<>();
        Map<String, String> map;
        map = new HashMap<>();
        map.put(LoginParams.UserName, "email");
        map.put(LoginParams.Password, "password");
        hashMap.put(ApiType.LOGIN, new ApiRequest( "Login", "login_ap", ApiRequestType.POST_FORM, map));

        map = new HashMap<>();
        map.put(LoginParams.Name, "name");
        map.put(LoginParams.EmailOrMobile, "emailOrMobile");
        map.put(LoginParams.UserName, "username");
        map.put(LoginParams.Password, "password");
        hashMap.put(ApiType.SIGNUP, new ApiRequest("Signup","signup", ApiRequestType.POST, map));

        map = new HashMap<>();
        map.put(LoginParams.EmailOrMobile, "emailOrMobile");
        hashMap.put(ApiType.GENERATE_OTP, new ApiRequest("generateOtp", ApiRequestType.POST, map));

        map = new HashMap<>();
        map.put(LoginParams.EmailOrMobile, "emailOrMobile");
        map.put(LoginParams.Otp, "otp");
        hashMap.put(ApiType.VALIDATE_OTP, new ApiRequest("validateOtp", ApiRequestType.GET, map));

        map = new HashMap<>();
        map.put(LoginParams.UserId, "userId");
        map.put(LoginParams.Password, "password");
        hashMap.put(ApiType.CHANGE_PASSWORD, new ApiRequest("changePassword", ApiRequestType.POST, map));

        return hashMap;
    }

    public static HashMap<Integer, ApiRequest> getApiRequestKeysForAdminLogin() {
        HashMap<Integer, ApiRequest> hashMap = new HashMap<>();
        Map<String, String> map;
        map = new HashMap<>();
        map.put(LoginParams.UserName, "email");
        map.put(LoginParams.Password, "password");
        hashMap.put(ApiType.LOGIN, new ApiRequest( "Admin Login", "login-admin", ApiRequestType.POST_FORM, map));

        map = new HashMap<>();
        map.put(LoginParams.Name, "name");
        map.put(LoginParams.EmailOrMobile, "emailOrMobile");
        map.put(LoginParams.UserName, "username");
        map.put(LoginParams.Password, "password");
        hashMap.put(ApiType.SIGNUP, new ApiRequest("Admin Registration","signup-admin", ApiRequestType.POST, map));
        return hashMap;
    }

    public static HashMap<Integer, FormBuilderModel> getSignupFormDetail() {
        HashMap<Integer, FormBuilderModel> loginMap = new HashMap<>();
        loginMap.put(LoginType.DEFAULT_USER, getSignupFormDetail("Signup"));
        loginMap.put(LoginType.ADMIN, getSignupFormDetail("Admin Registration"));
        return loginMap;
    }


    public static FormBuilderModel getSignupFormDetail(String title) {
        FormBuilderModel item = new FormBuilderModel();
        item.setTitle(title);
        item.setSubTitle("Want to sign up fill out this form!");
        item.setBaseUrl(LOGIN_BASE_URL);
        item.setRequestApi("signup");
        item.setShowActionbar(true);
        item.setRequestType(RequestType.POST_FORM);
        item.setSubmissionType(SubmissionType.KEY_VALUE_PAIR);
        item.setPopup(getPopup());
        item.setInputList(getInputFieldList());
        item.setExtraParams(getExtraParams());
        return item;
    }

    private static List<DynamicInputModel> getInputFieldList() {
        List<DynamicInputModel> fieldList = new ArrayList<>();
        DynamicInputModel item;

        item = new DynamicInputModel();
        item.setFieldName("Name");
        item.setParamKey("name");
        item.setInputType(FieldInputType.textPersonName);
        item.setFieldType(FieldType.EDIT_TEXT);
        fieldList.add(item);

        item = new DynamicInputModel();
        item.setFieldName("Select Steam");
        item.setParamKey("steam");
        item.setFieldType(FieldType.SPINNER);
        item.setFieldData("[{\"id\":1,\"title\":\"PCM\"},{\"id\":2,\"title\":\"PCMB\"},{\"id\":3,\"title\":\"Arts\"},{\"id\":4,\"title\":\"Commerce\"}]");
        fieldList.add(item);

        item = new DynamicInputModel();
        item.setFieldName("Select Gender");
        item.setParamKey("gender");
        item.setFieldType(FieldType.RADIO_BUTTON);
        item.setFieldData("[\"Male\",\"Female\"]");
        fieldList.add(item);

        item = new DynamicInputModel();
        item.setFieldName("Date of Birth");
        item.setParamKey("dob");
        item.setFieldType(FieldType.DATE_PICKER);
        item.setFieldData("Select Date");
        fieldList.add(item);

        item = new DynamicInputModel();
        item.setFieldName("Personal Detail");
        item.setParamKey("personal_detail");
        item.setFieldType(FieldType.TEXT_VIEW);
        fieldList.add(item);

        item = new DynamicInputModel();
        item.setFieldName("Mobile No");
        item.setParamKey("mobile");
        item.setInputType(FieldInputType.phone);
        item.setFieldType(FieldType.EDIT_TEXT);
        item.setMaxLength(10);
        item.setFieldSuggestions("[\"9876543210\"]");
        fieldList.add(item);

        item = new DynamicInputModel();
        item.setFieldName("Email Id");
        item.setParamKey("email_id");
        item.setInputType(FieldInputType.textEmailAddress);
        item.setFieldType(FieldType.EDIT_TEXT);
        item.setFieldSuggestions("[\"@gmail.com\", \"@yahoo.com\", \"@hotmail.com\", \"@outlook.com\"]");
        fieldList.add(item);

        item = new DynamicInputModel();
        item.setFieldName("Address");
        item.setParamKey("address");
        item.setInputType(FieldInputType.textMultiLine);
        item.setFieldType(FieldType.EDIT_TEXT);
        fieldList.add(item);

        item = new DynamicInputModel();
        item.setFieldName("I accept the term of use.");
        item.setParamKey("agree_check_box");
        item.setFieldType(FieldType.CHECK_BOX);
        fieldList.add(item);

        return fieldList;
    }

    private static PopupEntity getPopup() {
        PopupEntity popupEntity = new PopupEntity();
        popupEntity.setTitle("Thank You!");
        popupEntity.setDescription("You have successfully signup.");
        popupEntity.setButtonText("Continue");
        return popupEntity;
    }

    private static Map<String, String> getExtraParams() {
        Map<String, String> params = new HashMap<>();
        params.put("extra_params", "extraParams");
        return params;
    }
}
