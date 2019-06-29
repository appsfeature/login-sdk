package com.appsfeature.login.network;


import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.appsfeature.login.LoginSDK;
import com.appsfeature.login.model.BaseModel;

import org.json.JSONException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Amit on 4/10/2018.
 */

public class ResponseCallBack implements Callback<BaseModel> {

    public interface OnNetworkCall {
        void onComplete(boolean status, String data);

        void onError(Exception e);
    }

    private OnNetworkCall onNetworkCall;

    ResponseCallBack(OnNetworkCall onNetworkCall) {
        this.onNetworkCall = onNetworkCall;
    }

    private ResponseCallBack() {
    }

    @Override
    public void onResponse(@NonNull Call<BaseModel> call, @NonNull Response<BaseModel> response) {
        if (onNetworkCall == null) {
            return;
        }
        if (response.code() != 0) {
            int responseCode = response.code();
            if (responseCode == 200) {
                if (response.body() != null) {
                    BaseModel baseModel = response.body();
                    boolean status = !TextUtils.isEmpty(baseModel.getStatus())
                            && baseModel.getStatus().equals("success");
                    String s = LoginSDK.getGson().toJson(baseModel.getData());
                    if (!TextUtils.isEmpty(s)) {
                        onNetworkCall.onComplete(status, status ? s : "");
                    } else {
                        invalidResponse(response);
                    }
                } else {
                    invalidResponse(response);
                }
            } else if (responseCode == INTERNAL_SERVER_ERROR || responseCode == NOT_FOUND
                    || responseCode == BAD_GATEWAY || responseCode == SERVICE_UNAVAILABLE
                    || responseCode == GATEWAY_TIMEOUT) {
                invalidResponse(response);
            } else {
                invalidResponse(response);
            }
        } else {
            invalidResponse(response);
        }
    }

    private void invalidResponse(Response<BaseModel> response) {
        if (onNetworkCall != null) {
            onNetworkCall.onError(new Exception("ResponseCode : " + response.code() + " Message : " + response.message()));
        }
    }

    @Override
    public void onFailure(@NonNull Call<BaseModel> call, @NonNull Throwable t) {
        if (onNetworkCall != null) {
            onNetworkCall.onError(new Exception(t.getMessage()));
        }
    }

    private static final int NOT_FOUND = 404;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

}
