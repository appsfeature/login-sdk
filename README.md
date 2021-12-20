# LoginSdk
Have following options like: login, Signup, forgetPassword, changePassword
  
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

        form_builder = '1.3'
    }
}
```
#### Dependency
[![](https://jitpack.io/v/appsfeature/login-sdk.svg)](https://jitpack.io/#appsfeature/login-sdk)
```gradle
dependencies {
    implementation 'com.github.appsfeature:login-sdk:1.0'

    //Add this dependency if you need to make dynamic signup form.
    implementation "com.github.appsfeature:form-builder:$rootProject.ext.form_builder"
}
```
 
### Usage

#### In your <b>MainActivity</b> class:
```java 
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onLoginOpen(View view) {
        AppApplication.getInstance().getLoginSdk()
                .openLoginPage(this);
    }

    public void onAttachFragment(View view) {
        Fragment fragment = FormBuilder.getInstance().getFragment(LoginSDK.getInstance().getSignupFormDetail(), new FormResponse.FormSubmitListener() {
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

#### In your <b>Application</b> class:
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
                        .setEnableForgetPass(false)
                        .setEnableSignup(true)
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
    }
```

#### In your <b>LoginDataMap</b> class:
```java
    public class LoginDataMap {

        public static final String LOGIN_BASE_URL = "http://allinoneglobalmarket.com/mobile_app/";

        public static HashMap<Integer, ApiRequest> getApiRequestKeys() {
            HashMap<Integer, ApiRequest> hashMap = new HashMap<>();
            Map<String, String> map;
            map = new HashMap<>();
            map.put(LoginParams.UserName, "email");
            map.put(LoginParams.Password, "password");
            hashMap.put(LoginType.LOGIN, new ApiRequest("login_ap", RequestType.POST_FORM, map));

            map = new HashMap<>();
            map.put(LoginParams.Name, "name");
            map.put(LoginParams.EmailOrMobile, "emailOrMobile");
            map.put(LoginParams.UserName, "username");
            map.put(LoginParams.Password, "password");
            hashMap.put(LoginType.SIGNUP, new ApiRequest("signup", RequestType.POST, map));

            map = new HashMap<>();
            map.put(LoginParams.EmailOrMobile, "emailOrMobile");
            hashMap.put(LoginType.GENERATE_OTP, new ApiRequest("generateOtp", RequestType.POST, map));

            map = new HashMap<>();
            map.put(LoginParams.EmailOrMobile, "emailOrMobile");
            map.put(LoginParams.Otp, "otp");
            hashMap.put(LoginType.VALIDATE_OTP, new ApiRequest("validateOtp", RequestType.POST, map));

            map = new HashMap<>();
            map.put(LoginParams.UserId, "userId");
            map.put(LoginParams.Password, "password");
            hashMap.put(LoginType.CHANGE_PASSWORD, new ApiRequest("changePassword", RequestType.POST, map));

            return hashMap;
        }

        public static FormBuilderModel getSignupFormDetail() {
            FormBuilderModel item = new FormBuilderModel();
            item.setTitle("Signup");
            item.setSubTitle("Want to sign up fill out this form!");
            item.setBaseUrl(LOGIN_BASE_URL);
            item.setRequestApi("signup");
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
            item.setFieldSuggestions("[\"9891983694\"]");
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
