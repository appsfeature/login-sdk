package com.appsfeature.loginsdk;

import android.app.Application;

import com.appsfeature.login.network.RetrofitGenerator;

import retrofit2.Retrofit;

public class AppApplication extends Application {


    private static final String HOST_URL = "http://www.appsfeature.com/MrBizz/index.php/";
    private Retrofit retrofit;
    private static AppApplication instance;

    public static AppApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        retrofit = RetrofitGenerator.getClient(HOST_URL, SupportUtil.getSecurityCode(this), true);
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
