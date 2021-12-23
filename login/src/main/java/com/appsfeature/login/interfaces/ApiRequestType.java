package com.appsfeature.login.interfaces;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({
        ApiRequestType.GET,
        ApiRequestType.POST,
        ApiRequestType.POST_FORM,
})
@Retention(RetentionPolicy.SOURCE)
public @interface ApiRequestType {
    int GET = 0;
    int POST = 1;
    int POST_FORM = 3;
}

