package com.example.deliciousrice.Api;

import com.example.deliciousrice.Model.ResponseApi;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiNetWorking {

    ApiNetWorking apiNetWorking = new Retrofit.Builder()
            .baseUrl("https://appsellrice.000webhostapp.com/Deliciousrice/API/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiNetWorking.class);

    @FormUrlEncoded
    @POST("LoginRestApi.php")
    Call<ResponseApi> login(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("RegisterRestApi.php")
    Call<ResponseApi> register(@Field("username") String username, @Field("email") String email, @Field("password") String password);

}
