package com.example.deliciousrice.Api;

import com.example.deliciousrice.Model.Adderss;
import com.example.deliciousrice.Model.Bill;
import com.example.deliciousrice.Model.Customer;
import com.example.deliciousrice.Model.Favorite;
import com.example.deliciousrice.Model.Product;
import com.example.deliciousrice.Model.ProductBill;
import com.example.deliciousrice.Model.ResponseApi;

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

    @GET("DetailBill.php")
    Call<ArrayList<ProductBill>> getListProductBill();


    @FormUrlEncoded
    @POST("getdataCustomer.php")
    Call<List<Customer>> getcustomer(@Field("email") String email, @Field("pass") String pass);

    @FormUrlEncoded
    @POST("ChangePassword.php")
    Call<String> changepass(@Field("email") String email, @Field("passnew") String pass);

    @FormUrlEncoded
    @POST("getFavorite.php")
    Call<List<Favorite>> getListFavorite(@Field("customer") int customer);

    @FormUrlEncoded
    @POST("insertFavorite.php")
    Call<String> insertfavorite(@Field("customer") int idcustomer, @Field("product") int idproduct);

    @FormUrlEncoded
    @POST("RegisterFacebook.php")
    Call<String> registerfacebook(@Field("id") String idfacebook, @Field("image") String image, @Field("username") String username);

    @FormUrlEncoded
    @POST("RegisterGoogle.php")
    Call<String> registergoogle(@Field("id") String idgoogle, @Field("username") String username,@Field("image") String image,@Field("email") String email);

    @FormUrlEncoded
    @POST("DeleteFavorite.php")
    Call<Favorite> deletefavorite(@Field("customer") int idcustomer, @Field("product") int idproduct);

    @FormUrlEncoded
    @POST("checkFavorite.php")
    Call<String> checkFavorite(@Field("customer") int idcustomer, @Field("product") int idproduct);

    @FormUrlEncoded
    @POST("SeachProduct.php")
    Call<List<Product>> SeachProduct(@Field("product_name") String product_name);

    @FormUrlEncoded
    @POST("updatecustomer.php")
    Call<String> updatecustomer(@Field("customer") int idcustomer, @Field("user_name") String user_name, @Field("phone_number") String phone_number, @Field("birthday") String birthday);

    /*Địa chỉ*/
    @FormUrlEncoded
    @POST("getaddress.php")
    Call<ArrayList<Adderss>> getListAddresss(@Field("id_customer") int id_customer);

    @FormUrlEncoded
    @POST("insertaddress.php")
    Call<String> addAdderss(@Field("id_customer") int id_customer, @Field("address_name")
            String address_name, @Field("address_specifically") String address_specifically);

    @FormUrlEncoded
    @POST("deleteAddress.php")
    Call<String> deleteAdderss(@Field("id_address") int id_address, @Field("id_customer") int id_customer);

    @FormUrlEncoded
    @POST("upAddress.php")
    Call<String> updateAdderss(@Field("id_address") int id_address, @Field("id_customer") int id_customer, @Field("address_name")
            String address_name, @Field("address_specifically") String address_specifically);

    @FormUrlEncoded
    @POST("GetBill.php")
    Call<List<Bill>> getListbill(@Field("id_customer") int id_customer);

    @FormUrlEncoded
    @POST("imgUpload.php")
    Call<String> imgUpload(@Field("id_customer") int id_customer, @Field("image_tag") String name, @Field("image_data") String image);

    @FormUrlEncoded
    @POST("LoginRestApi.php")
    Call<ResponseApi> login(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("RegisterRestApi.php")
    Call<ResponseApi> register(@Field("username") String username, @Field("email") String email, @Field("password") String password);

    @POST("insertBill.php")
    Call<String> addbill(@Field("Bill") String bill, @Field("customer") int id_customer, @Field("Date")
            String Date, @Field("status") String status, @Field("money") int money);

    @FormUrlEncoded
    @POST("insertDetailBill.php")
    Call<String> adddetailbill(@Field("bill") String bill, @Field("idproduct")
            int idproduct, @Field("amount") int amount, @Field("totalmoney") int totalmoney);

}