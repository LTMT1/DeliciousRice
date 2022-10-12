package com.example.deliciousrice.Api;

import com.example.deliciousrice.Model.Product;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiProduct {

    //https://appsellrice.000webhostapp.com/Deliciousrice/API/product.php

    @GET("product.php")
    Call<ArrayList<Product>> getListProduct();
}
