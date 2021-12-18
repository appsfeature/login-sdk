package com.appsfeature.login.network;

import com.appsfeature.login.LoginSDK;
import com.appsfeature.login.interfaces.NetworkListener;
import com.appsfeature.login.model.ApiRequest;
import com.appsfeature.login.model.BaseModel;
import com.appsfeature.login.model.Profile;
import com.google.gson.JsonSyntaxException;

import java.util.HashMap;
import java.util.Map;

public class LoginNetwork {
    private static LoginNetwork instance;

    public LoginNetwork() {
        if(LoginSDK.getInstance().getApiRequests() == null){
            throw new RuntimeException("Invalid Integration: getApiRequests == null");
        }
    }

    public static LoginNetwork getInstance() {
        if(instance == null){
            instance = new LoginNetwork();
        }
        return instance;
    }

    private void getData(int reqType, String endPoint, Map<String, String> params, ResponseCallBack.OnNetworkCall callback) {
        if(reqType == RequestType.POST){
            LoginSDK.getInstance().getApiInterface().requestPost(endPoint, params)
                    .enqueue(new ResponseCallBack(callback));
        }else if(reqType == RequestType.POST_FORM){
            LoginSDK.getInstance().getApiInterface().requestPostDataForm(endPoint, params)
                    .enqueue(new ResponseCallBack(callback));
        }else {
            LoginSDK.getInstance().getApiInterface().requestGet(endPoint, params)
                    .enqueue(new ResponseCallBack(callback));
        }
    }

    public void changePassword(String username, String newPassword, final NetworkListener<Boolean> callback) {
        callback.onPreExecute();
        ApiRequest apiRequest = LoginSDK.getInstance().getApiRequests().get(LoginType.CHANGE_PASSWORD);
        if(apiRequest != null) {
            Map<String, String> params = new HashMap<>();
            params.put(apiRequest.getParams().get(LoginParams.UserName), username);
            params.put(apiRequest.getParams().get(LoginParams.Password), newPassword);

            getData(apiRequest.getReqType(), apiRequest.getEndPoint(), params, new ResponseCallBack.OnNetworkCall() {
                @Override
                public void onComplete(boolean status, BaseModel data) {
                    callback.onSuccess(status);
                }

                @Override
                public void onError(Exception e) {
                    callback.onError(e);
                }
            });
        }
    }



    public void forgotPassword(String username, String otp, boolean isOtpSend, final NetworkListener<Boolean> callback) {
        callback.onPreExecute();
        if(!isOtpSend) {
            ApiRequest apiRequest = LoginSDK.getInstance().getApiRequests().get(LoginType.FORGET_PASSWORD);
            if(apiRequest != null) {
                Map<String, String> params = new HashMap<>();
                params.put(apiRequest.getParams().get(LoginParams.UserName), username);

                getData(apiRequest.getReqType(), apiRequest.getEndPoint(), params, new ResponseCallBack.OnNetworkCall() {
                    @Override
                    public void onComplete(boolean status, BaseModel data) {
                        callback.onSuccess(status);
                    }

                    @Override
                    public void onError(Exception e) {
                        callback.onError(e);
                    }
                });
            }
        }else{
            ApiRequest apiRequest = LoginSDK.getInstance().getApiRequests().get(LoginType.VALIDATE_OTP);
            if(apiRequest != null) {
                Map<String, String> params = new HashMap<>();
                params.put(apiRequest.getParams().get(LoginParams.UserName), username);
                params.put(apiRequest.getParams().get(LoginParams.Otp), otp);

                getData(apiRequest.getReqType(), apiRequest.getEndPoint(), params, new ResponseCallBack.OnNetworkCall() {
                    @Override
                    public void onComplete(boolean status, BaseModel data) {
                        callback.onSuccess(status);
                    }

                    @Override
                    public void onError(Exception e) {
                        callback.onError(e);
                    }
                });
            }
        }
    }

    public void loginUser(String username, String password, final NetworkListener<Profile> callback) {
        callback.onPreExecute();
        ApiRequest apiRequest = LoginSDK.getInstance().getApiRequests().get(LoginType.LOGIN);
        if(apiRequest != null) {
            Map<String, String> params = new HashMap<>();
            params.put(apiRequest.getParams().get(LoginParams.UserName), username);
            params.put(apiRequest.getParams().get(LoginParams.Password), password);

            getData(apiRequest.getReqType(), apiRequest.getEndPoint(), params, new ResponseCallBack.OnNetworkCall() {
                @Override
                public void onComplete(boolean status, BaseModel data) {
                    try {
                        if(status) {
                            Profile profile = LoginSDK.getGson().fromJson(data.getData(), Profile.class);
                            profile.setJsonData(data.getData());
                            callback.onSuccess(profile);
                        }else {
                            callback.onError(new Exception(data.getMessage()));
                        }
                    } catch (JsonSyntaxException e) {
                        callback.onError(e);
                    }
                }

                @Override
                public void onError(Exception e) {
                    callback.onError(e);
                }
            });
        }

    }

    public void signUp(String name, String emailOrMobile, String username, String password, final NetworkListener<Profile> callback) {
        callback.onPreExecute();
        ApiRequest apiRequest = LoginSDK.getInstance().getApiRequests().get(LoginType.SIGNUP);
        if(apiRequest != null) {
            Map<String, String> params = new HashMap<>();
            params.put(apiRequest.getParams().get(LoginParams.Name), name);
            params.put(apiRequest.getParams().get(LoginParams.EmailOrMobile), emailOrMobile);
            params.put(apiRequest.getParams().get(LoginParams.UserName), username);
            params.put(apiRequest.getParams().get(LoginParams.Password), password);

            getData(apiRequest.getReqType(), apiRequest.getEndPoint(), params, new ResponseCallBack.OnNetworkCall() {
                @Override
                public void onComplete(boolean status, BaseModel data) {
                    try {
                        if(status) {
                            Profile profile = LoginSDK.getGson().fromJson(data.getData(), Profile.class);
                            profile.setJsonData(data.getData());
                            callback.onSuccess(profile);
                        }else {
                            callback.onError(new Exception(data.getMessage()));
                        }
                    } catch (JsonSyntaxException e) {
                        callback.onError(e);
                    }
                }

                @Override
                public void onError(Exception e) {
                    callback.onError(e);
                }
            });
        }
    }
}
