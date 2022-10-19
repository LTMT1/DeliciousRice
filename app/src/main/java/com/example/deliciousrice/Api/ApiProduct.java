package com.example.deliciousrice.Api;

import com.example.deliciousrice.Model.Product;
import com.example.deliciousrice.Model.ProductHot;
import com.example.deliciousrice.Model.ProductNew;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiProduct {

    //https://appsellrice.000webhostapp.com/Deliciousrice/API/product.php

    @GET("product.php")
    Call<ArrayList<Product>> getListProduct();
    @GET("productNew.php")
    Call<ArrayList<ProductNew>> getListProductNew();
    @GET("productHot.php")
    Call<ArrayList<ProductHot>> getListProductHot();
}
