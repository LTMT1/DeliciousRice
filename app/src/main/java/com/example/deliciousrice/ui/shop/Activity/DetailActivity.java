package com.example.deliciousrice.ui.shop.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.deliciousrice.Api.ApiProduct;
import com.example.deliciousrice.Api.ApiService;
import com.example.deliciousrice.Model.Favorite;
import com.example.deliciousrice.Model.Product;
import com.example.deliciousrice.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {
    private RoundedImageView roundedImageView;
    private TextView tvnamesp;
    private TextView tvtimesp;
    private TextView tvpricesp;
    private TextView tvnumbersp;
    private ImageView imgtymsp;
    private ImageView imgTru;
    private ImageView imgCong;
    private TextView tv;
    private ImageView tvcuon;
    private TextView tvdetaisp;
    private Button btnaddcart;

    Product product;
    int getId_customer;
    public static int Click = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        getId_customer=intent.getIntExtra("idcustomer",0);
        product = (Product) intent.getSerializableExtra("getdataproduct");
        Anhxa();
        setviewdata();
    }
    private void Anhxa(){
        roundedImageView = findViewById(R.id.roundedImageView);
        tvnamesp = findViewById(R.id.tvnamesp);
        tvtimesp = findViewById(R.id.tvtimesp);
        tvpricesp= findViewById(R.id.tvpricesp);
        tvnumbersp = findViewById(R.id.tvnumbersp);
        imgtymsp = findViewById(R.id.imgtymsp);
        imgTru = findViewById(R.id.img_tru);
        imgCong = findViewById(R.id.img_cong);
        tv = findViewById(R.id.tv);
        tvcuon = findViewById(R.id.tvcuon);
        tvdetaisp = findViewById(R.id.tvdetaisp);
        btnaddcart = findViewById(R.id.btnaddcart);
    }
    private void setviewdata(){
        Glide.with(this).load(product.getImage()).centerCrop().into(roundedImageView);
        tvnamesp.setText(product.getProduct_name());
        tvtimesp.setText(product.getProcessing_time());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tvpricesp.setText(decimalFormat.format(product.getPrice())+"VND");
        tvdetaisp.setText(product.getDescription());
        checkYeuThich(getId_customer,product.getId_product());
        imgtymsp.setOnClickListener(view -> {
            onclickfavorite();
        });
    }
    public void checkYeuThich(int idcus, int idbh) {
        ApiProduct apiProduct = ApiService.getService();
        Call<String> callback = apiProduct.checkFavorite(idcus,idbh);
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String responseBody = response.body();
                    if (responseBody.equals("success")) {
                        imgtymsp.setImageResource(R.drawable.ic_baseline_favorite_24);
                    } else {
                        imgtymsp.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                    }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
    }
    private void onclickfavorite(){
        if (Click != 0) {
            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_timclick);
            imgtymsp.setImageResource(R.drawable.ic_baseline_favorite_24);
            imgtymsp.startAnimation(animation);
            insertFavorite(getId_customer,product.getId_product());
            Click++;
        } else {
            imgtymsp.setImageResource(R.drawable.ic_baseline_favorite_border_24);
            deleteFavorite(getId_customer,product.getId_product());
            Click--;
        }
    }

    public void insertFavorite(int idcustomer, int idproduct) {
        ApiProduct apiProduct = ApiService.getService();
        Call<String> callback = apiProduct.insertfavorite(idcustomer, idproduct);
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
    }
    private void deleteFavorite(int idcustomer, int idproduct) {
        ApiProduct apiProduct = ApiService.getService();
        Call<Favorite> callback = apiProduct.deletefavorite(idcustomer, idproduct);
        callback.enqueue(new Callback<Favorite>() {
            @Override
            public void onResponse(Call<Favorite> call, Response<Favorite> response) {
            }

            @Override
            public void onFailure(Call<Favorite> call, Throwable t) {
            }
        });
    }
}