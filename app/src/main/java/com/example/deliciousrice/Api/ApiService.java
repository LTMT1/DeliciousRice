package com.example.deliciousrice.Api;

import retrofit2.Retrofit;

public interface ApiService {

    static String base_url = "http://chucdong.com/Deliciousrice/API/";
    public static ApiProduct getService(){
        return APIRetrofitClient.getClient(base_url).create(ApiProduct.class);
    }
}
