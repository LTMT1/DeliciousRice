package com.example.deliciousrice.Api;

import com.example.deliciousrice.Model.Customer;
import com.example.deliciousrice.Model.Product;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiProduct {

    //https://appsellrice.000webhostapp.com/Deliciousrice/API/product.php

    @GET("product.php")
    Call<ArrayList<Product>> getListProduct();
    @FormUrlEncoded
    @POST("Login.php")
    Call<Customer> login(@Field("email") String email, @Field("password") String password);

}
