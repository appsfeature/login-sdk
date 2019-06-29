package com.appsfeature.loginsdk;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SupportUtil {

    public static String getSecurityCode(Context ctx) {
        String keyHash = null;
        try {
            @SuppressLint("PackageManagerGetSignatures")
            PackageInfo info = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                keyHash = Base64.encodeToString(md.digest(), Base64.NO_WRAP);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return keyHash;
    }
}
