# LoginSdk
Have following options like: login, Signup, forgetPassword, changePassword
Also multiple login support.
  
## Setup

Add this to your project build.gradle
``` gradle
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }

    ext {
        appcompat = '1.3.0'
        cardview = '1.0.0'
        material = '1.3.0'
        retrofit_version = '2.9.0'
        retrofit_okhttp_version = '4.8.1'

        form_builder = '1.5'
    }
}
```
#### Dependency
[![](https://jitpack.io/v/appsfeature/login-sdk.svg)](https://jitpack.io/#appsfeature/login-sdk)
```gradle
dependencies {
    implementation 'com.github.appsfeature:login-sdk:1.2'

    //Add this dependency if you need to make dynamic signup form.
    implementation "com.github.appsfeature:form-builder:$rootProject.ext.form_builder"
}
```
 
### Usage

#### In your MainActivity class:
```java 
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onLoginUser(View view) {
        AppApplication.getInstance().getLoginSdk()
                .openLoginPage(this, LoginType.DEFAULT_USER);
    }

    public void onLoginAdmin(View view) {
        AppApplication.getInstance().getLoginSdk()
                .openLoginPage(this, LoginType.ADMIN);
    }

    public void onClearPreferences(View view) {
        LoginPrefUtil.clearPreferences(this);
    }

    public void onAttachFragment(@LoginType int loginType) {
        Fragment fragment = FormBuilder.getInstance().getFragment(LoginSDK.getInstance().getSignupFormDetail().get(loginType), new FormResponse.FormSubmitListener() {
            @Override
            public void onFormSubmitted(String data) {
                Profile profile = LoginSDK.getGson().fromJson(data, Profile.class);
            }
        });
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(android.R.id.content, fragment, "tag");
        transaction.addToBackStack("tag");
        transaction.commit();
    }
}
```

#### In your Application class:
```java
public class AppApplication extends Application {

    private static AppApplication instance;
    private LoginSDK loginSdk;

    public static AppApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public LoginSDK getLoginSdk() {
        if(loginSdk == null) {
            loginSdk = LoginSDK.getInstance(this, LoginDataMap.LOGIN_BASE_URL, BuildConfig.DEBUG)
                    .setApiRequests(LoginDataMap.getApiRequestKeys())
                    .setSignupForm(LoginDataMap.getSignupFormDetail())
                    .setFacebookLogin(false)
                    .setGoogleLogin(true)
                    .setEmailLogin(true)
                    .addLoginListener(new LoginCallback.Listener() {
                        @Override
                        public void onSuccess(Profile response) {

                        }

                        @Override
                        public void onFailure(Exception e) {

                        }
                    });
        }
        return loginSdk;
    }

    public void openLoginPage(final Context context, @LoginType int loginType) {
        if (!LoginPrefUtil.isLoginComplete(context, loginType)) {
            context.startActivity(new Intent(context, AppLoginActivity.class)
                    .putExtra(LoginConstant.LOGIN_TYPE, loginType));
        }else {
            LoginUtil.showToast(context, "User already logged in.");
        }
    }
}
```

#### In your LoginDataMap class:
```java
public class LoginDataMap {

    public static final String LOGIN_BASE_URL = "http://yourdomain.com/mobile-app/";

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
```

#### Custom App Login Activity class: for custom designed fragments and more
```java
public class AppLoginActivity extends BaseLoginActivity {

    @Override
    public int getLayoutContentView() {
        return R.layout.login_activity;
    }

    @Override
    public void onInitViews() {

    }

    @Override
    public Fragment getLoginFragment() {
        return ScreenLogin.newInstance(bundle);
    }

    @Override
    public Fragment getSignupFragment() {
        return ScreenSignUp.newInstance(bundle);
    }

    @Override
    public Fragment getScreenAuthFragment(String emailOrMobile) {
        return ScreenAuthentication.newInstance(bundle, emailOrMobile);
    }

    @Override
    public Fragment getForgotPasswordFragment() {
        return ForgotPassword.newInstance(bundle);
    }

    @Override
    public Fragment getChangePasswordFragment(String userId) {
        return ChangePassword.newInstance(bundle, userId);
    }

    @Nullable
    @Override
    public Fragment getFormBuilderFragment(FormBuilderModel property, FormResponse.FormSubmitListener formSubmitListener) {
        if(property == null){
            return null;
        }
        FormBuilder.getInstance().setFormSubmitListener(formSubmitListener);
        Fragment fragment = new FormBuilderFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(FBConstant.CATEGORY_PROPERTY, property);
        fragment.setArguments(bundle);
        return fragment;
    }
}
```

#### Api Params and Responses
```json
     Login Api params are   : username, password
     Signup Api params are  : name, emailOrMobile, username, password

     Similar response for both login and signup api.
     {
       "status": "success/failure",
       "message": "success/failure message",
       "data": {
         "name": "Alpha User",
         "mobile": "9876543210",
         "user_id": "121212",
         "email": "alphauser@gmail.com",
         "image": "8cb8ebcfbc1848e1cd9134.jpg"
       }
     }

     generateOtp Api params are  : emailOrMobile
     validateOtp Api params are     : emailOrMobile, otp
     changePassword Api params are  : userId, password

     Similar response for forgetPassword, validateOtp and changePassword.
     {
       "status": "success/failure",
       "message": "success/failure message",
     }
```
