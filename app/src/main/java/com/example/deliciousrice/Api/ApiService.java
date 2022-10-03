package com.example.deliciousrice.Api;

import com.example.deliciousrice.Model.Customer;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    ApiService retrofit = new Retrofit.Builder()
            .baseUrl("https://appsellrice.000webhostapp.com/Deliciousrice/API/")
            .build()
            .create(ApiService.class);
}
