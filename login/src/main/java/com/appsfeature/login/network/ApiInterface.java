package com.appsfeature.login.network;

import com.appsfeature.login.model.BaseModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * @author Created by Abhijit on 2/28/2018.
 */

public interface ApiInterface {


    /*
     *Login User so that it can generate User on server
     * get User Auto ID
     */
    @POST("Login/registration")
    Call<BaseModel> signUp(
            @Query("name") String name,
            @Query("mobile") String mobile,
            @Query("username") String username,
            @Query("password") String password
    );

    @POST("Login/login")
    Call<BaseModel> loginUser(
            @Query("username") String username,
            @Query("password") String password
    );

    @POST("Login/forgotPass")
    Call<BaseModel> forgotPass(
            @Query("username") String username
    );

    @POST("Login/validateOtp")
    Call<BaseModel> validateOtp(
            @Query("username") String username,
            @Query("otp") String otp
    );

    @POST("Login/changePassword")
    Call<BaseModel> changePassword(
            @Query("userId") String userId,
            @Query("password") String password
    );


    /*
     * Update user profile
     */
//    @Multipart
//    @POST("update-user-profile")
//    Call<BaseModel> updateUserProfile(
//            @Query("email") String email,
//            @Query("id") String auto_id,
//            @Query("email_verified") int email_verified,
//            @Part("name") RequestBody name,
//            @Part MultipartBody.Part image,
//            @Query("phone") String mobile,
//            @Query("address") String address,
//            @Query("state") int state,
//            @Query("postal") String postal,
//            @Query("dob") String dob,
//            @Query("gender") int gender,
//            @Query("device_id") String device_token,
//            @Query("player_id") String player_id,
//            @Query("about_me") String about_me,
//            @Query("application_id") String app_id
//    );

}
