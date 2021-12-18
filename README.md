# LoginSdk
Have following options like: login, Signup, forgetPassword, changePassword
  
## Setup

Add this to your project build.gradle
``` gradle
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```
#### Dependency
[![](https://jitpack.io/v/appsfeature/login-sdk.svg)](https://jitpack.io/#appsfeature/login-sdk)
```gradle
dependencies {
    implementation 'com.github.appsfeature:login-sdk:1.0'
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
                    .openLoginPage(this, false);
        }
    }
```

#### In your <b>Application</b> class:
```java
    public class AppApplication extends Application {

        private static final String HOST_URL = "http://yourwebsite.com/appname/";
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
                loginSdk = LoginSDK.getInstance(this, HOST_URL, BuildConfig.DEBUG)
                        .setApiRequests(getApiRequestKeys())
                        .setFacebookLogin(false)
                        .setGoogleLogin(true)
                        .setEmailLogin(true)
                        .setEnableForgetPass(false)
                        .setEnableSignup(false)
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
            map.put(LoginParams.UserName, "username");
            hashMap.put(LoginType.FORGET_PASSWORD, new ApiRequest("forgetPassword", RequestType.POST, map));

            map = new HashMap<>();
            map.put(LoginParams.UserName, "username");
            map.put(LoginParams.Otp, "otp");
            hashMap.put(LoginType.VALIDATE_OTP, new ApiRequest("validateOtp", RequestType.POST, map));

            map = new HashMap<>();
            map.put(LoginParams.UserName, "username");
            map.put(LoginParams.Password, "password");
            hashMap.put(LoginType.CHANGE_PASSWORD, new ApiRequest("changePassword", RequestType.POST, map));

            return hashMap;
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
