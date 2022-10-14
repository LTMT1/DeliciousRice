package com.example.deliciousrice.Api;

import retrofit2.Retrofit;

public interface ApiService {

    ApiService retrofit = new Retrofit.Builder()
            .baseUrl("https://appsellrice.000webhostapp.com/Deliciousrice/API/")
            .build()
            .create(ApiService.class);
}
