package com.example.deliciousrice.Api;

public interface ApiService {

    String base_url = "https://appsellrice.000webhostapp.com/Deliciousrice/API/";
    static ApiProduct getService(){
        return APIRetrofitClient.getClient(base_url).create(ApiProduct.class);
    }
}
