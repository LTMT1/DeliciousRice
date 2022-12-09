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
import com.example.deliciousrice.Adapter.AdapterProductHot;
import com.example.deliciousrice.Adapter.AdapterViewAllCombo;
import com.example.deliciousrice.Adapter.AdapterViewAllHot;
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

public class ViewAllHotActivity extends AppCompatActivity {

    RecyclerView recyclerViewAllHot;
    AdapterViewAllHot adapterViewAllHot;
    private ImageView imgBackViewAllCbo;
    private ProgressBar prgLoadingSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_hot);

        BarColor.setStatusBarColor(this);
        prgLoadingSearch =findViewById(R.id.prgLoadingSearch);
        recyclerViewAllHot = findViewById(R.id.rcyViewAllHot);
        imgBackViewAllCbo = findViewById(R.id.img_back_ViewAllCbo);
        prgLoadingSearch.setIndeterminateDrawable(new Circle());

        imgBackViewAllCbo.setOnClickListener(view -> {
            overridePendingTransition(R.anim.anim_intent_in, R.anim.anim_intent_out);
            Intent intent=new Intent(this, ShopFragment.class);
            startActivity(intent);
        });
        getAllProductHot();
    }
    private void getAllProductHot(){
        prgLoadingSearch.setVisibility(View.VISIBLE);
        ApiProduct apiProduct = ApiService.getService();
        Call<ArrayList<Product>> listCallProductHot = apiProduct.getListProductHot();
        listCallProductHot.enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                prgLoadingSearch.setVisibility(View.GONE);
                ArrayList<Product> productHots = new ArrayList<>();
                productHots = response.body();
                recyclerViewAllHot.setHasFixedSize(true);
                recyclerViewAllHot.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
                adapterViewAllHot = new AdapterViewAllHot(productHots, ViewAllHotActivity.this, productHot -> {
                    Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                    intent.putExtra("getdataproduct", productHot);
                    startActivity(intent);
                });
                recyclerViewAllHot.setAdapter(adapterViewAllHot);
            }

            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {

            }
        });
    }
}