package com.example.deliciousrice.ui.shop.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.deliciousrice.Activity.BarColor;
import com.example.deliciousrice.Adapter.AdapterViewAllCombo;
import com.example.deliciousrice.Api.ApiProduct;
import com.example.deliciousrice.Api.ApiService;
import com.example.deliciousrice.Model.Product;
import com.example.deliciousrice.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewAllCboActivity extends AppCompatActivity {

    RecyclerView recyclerViewAll;
    AdapterViewAllCombo adapterViewAllCombo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_cbo);
        BarColor.setStatusBarColor(this);

        recyclerViewAll = findViewById(R.id.rcyViewAll);

        ApiProduct apiProduct = ApiService.getService();
        Call<ArrayList<Product>> listCallProduct = apiProduct.getListProduct();
        listCallProduct.enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                ArrayList<Product> productList = new ArrayList<>();
                productList = response.body();
                recyclerViewAll.setHasFixedSize(true);
                recyclerViewAll.setLayoutManager(new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL, false));
                adapterViewAllCombo = new AdapterViewAllCombo(productList, ViewAllCboActivity.this, product -> {
                    Intent intent=new Intent(getApplicationContext(), DetailActivity.class);
                    intent.putExtra("getdataproduct",product);
                    startActivity(intent);
                });
                recyclerViewAll.setAdapter(adapterViewAllCombo);
            }

            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {

            }
        });
    }
}