package com.example.deliciousrice.ui.account.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.deliciousrice.Adapter.AdapterFavorite;
import com.example.deliciousrice.Api.ApiProduct;
import com.example.deliciousrice.Api.ApiService;
import com.example.deliciousrice.Model.Favorite;
import com.example.deliciousrice.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReceiptActivity extends AppCompatActivity {
    private RecyclerView rclview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);
        rclview = findViewById(R.id.rclview);
        getreceipt();
    }
    private void getreceipt(){
//        ApiProduct apiProduct = ApiService.getService();
//        Call<List<Favorite>> callback = apiProduct.);
//        callback.enqueue(new Callback<List<Favorite>>() {
//            @Override
//            public void onResponse(Call<List<Favorite>> call, Response<List<Favorite>> response) {
//                ArrayList<Favorite> mangyeuthich = (ArrayList<Favorite>) response.body();
//                adapterFavorite = new AdapterFavorite(mangyeuthich, getApplicationContext());
//                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
//                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//                rclviewfavorite.setLayoutManager(linearLayoutManager);
//                rclviewfavorite.setAdapter(adapterFavorite);
//            }
//
//            @Override
//            public void onFailure(Call<List<Favorite>> call, Throwable t) {
//
//            }
//        });
    }
}