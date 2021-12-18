package com.appsfeature.login.interfaces;

import com.appsfeature.login.model.Profile;

public class LoginCallback {

    public interface Listener {
        void onSuccess(Profile response);
        void onFailure(Exception e);
    }

    public interface TermUseListener {
        void onOpenTermOfUse();
    }
}
