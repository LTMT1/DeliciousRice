package com.example.deliciousrice.ui.shop.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.deliciousrice.Activity.BarColor;
import com.example.deliciousrice.Adapter.AdapterProductNew;
import com.example.deliciousrice.Adapter.AdapterViewAllCombo;
import com.example.deliciousrice.Adapter.AdapterViewAllNew;
import com.example.deliciousrice.Api.ApiProduct;
import com.example.deliciousrice.Api.ApiService;
import com.example.deliciousrice.Model.Product;
import com.example.deliciousrice.R;
import com.example.deliciousrice.ui.shop.ShopFragment;
import com.github.ybq.android.spinkit.style.Circle;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewAllNewActivity extends AppCompatActivity {

    RecyclerView recyclerViewAllNew;
    AdapterViewAllNew adapterViewAllNew;
    private ImageView imgBackViewAllCbo;
    private ProgressBar prgLoadingSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_new);

        BarColor.setStatusBarColor(this);
        prgLoadingSearch = findViewById(R.id.prgLoadingSearch);
        recyclerViewAllNew = findViewById(R.id.rcyViewAllNew);
        imgBackViewAllCbo = findViewById(R.id.img_back_ViewAllnew);
        prgLoadingSearch.setIndeterminateDrawable(new Circle());
        imgBackViewAllCbo.setOnClickListener(view -> {
            overridePendingTransition(R.anim.anim_intent_in, R.anim.anim_intent_out);
            Intent intent=new Intent(this, ShopFragment.class);
            startActivity(intent);
        });
        getAllProductNew();
    }
    private void getAllProductNew(){
        prgLoadingSearch.setVisibility(View.VISIBLE);
        ApiProduct apiProduct = ApiService.getService();
        Call<ArrayList<Product>> listCallProductNew = apiProduct.getListProductNew();
        listCallProductNew.enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                prgLoadingSearch.setVisibility(View.GONE);
                ArrayList<Product> productNews = new ArrayList<>();
                productNews = response.body();
                recyclerViewAllNew.setHasFixedSize(true);
                recyclerViewAllNew.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
                adapterViewAllNew = new AdapterViewAllNew(productNews, ViewAllNewActivity.this, productNew -> {
                    Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                    intent.putExtra("getdataproduct", productNew);
                    startActivity(intent);
                });
                recyclerViewAllNew.setAdapter(adapterViewAllNew);
            }

            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {

            }
        });
    }
}