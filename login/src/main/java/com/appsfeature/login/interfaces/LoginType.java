package com.appsfeature.login.interfaces;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({LoginType.DEFAULT_USER, LoginType.ADMIN, LoginType.MANAGER})
@Retention(RetentionPolicy.SOURCE)
public @interface LoginType {
    int DEFAULT_USER = 0;
    int ADMIN = 1;
    int MANAGER = 2;
}
