package com.example.deliciousrice.Api;

import com.example.deliciousrice.Model.Adderss;
import com.example.deliciousrice.Model.Customer;
import com.example.deliciousrice.Model.Favorite;
import com.example.deliciousrice.Model.Product;
import com.example.deliciousrice.Model.ProductHot;
import com.example.deliciousrice.Model.ProductNew;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiProduct {

    //https://appsellrice.000webhostapp.com/Deliciousrice/API/product.php

    @GET("product.php")
    Call<ArrayList<Product>> getListProduct();

    @GET("productNew.php")
    Call<ArrayList<Product>> getListProductNew();

    @GET("productHot.php")
    Call<ArrayList<Product>> getListProductHot();

    @FormUrlEncoded
    @POST("getdataCustomer.php")
    Call<List<Customer>> getcustomer(@Field("email") String email, @Field("pass") String pass);

    @FormUrlEncoded
    @POST("getFavorite.php")
    Call<List<Favorite>> getListFavorite(@Field("customer") int customer);

    @FormUrlEncoded
    @POST("insertFavorite.php")
    Call<String> insertfavorite(@Field("customer") int idcustomer, @Field("product") int idproduct);

    @FormUrlEncoded
    @POST("DeleteFavorite.php")
    Call<Favorite> deletefavorite(@Field("customer") int idcustomer, @Field("product") int idproduct);

    @FormUrlEncoded
    @POST("checkFavorite.php")
    Call<String> checkFavorite(@Field("customer") int idcustomer, @Field("product") int idproduct);

    @FormUrlEncoded
    @POST("SeachProduct.php")
    Call<List<Product>> SeachProduct(@Field("product_name") String product_name);


    /*Địa chỉ*/
    @FormUrlEncoded
    @POST("getaddress.php")
    Call<List<Adderss>> getListAddresss(@Field("id_customer") int id_customer);

    @FormUrlEncoded
    @POST("")
    Call<Adderss> addAdderss(@Field("id_customer") int id_customer,@Field("address_name") String address_name,@Field("address_specifically") String address_specifically);
}
