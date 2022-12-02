package com.example.deliciousrice.ui.shop.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.deliciousrice.Api.ApiProduct;
import com.example.deliciousrice.Api.ApiService;
import com.example.deliciousrice.MainActivity2;
import com.example.deliciousrice.Model.Cart;
import com.example.deliciousrice.Model.Favorite;
import com.example.deliciousrice.Model.Product;
import com.example.deliciousrice.R;
import com.example.deliciousrice.ui.cart.CartFragment;
import com.example.deliciousrice.ui.favorite.FavoriteFragment;
import com.example.deliciousrice.ui.shop.ShopFragment;
import com.makeramen.roundedimageview.RoundedImageView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

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
    private ImageView tvcuon;
    private TextView tvdetaisp;
    private Button btnaddcart;

    Product product;
    private SQLiteDatabase db;
    int getId_customer,id_product;
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
        tvcuon = findViewById(R.id.tvcuon);
        tvdetaisp = findViewById(R.id.tvdetaisp);
        btnaddcart = findViewById(R.id.btnaddcart);
        id_product=product.getId_product();
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
        imgTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoi = Integer.parseInt(tvnumbersp.getText().toString().trim()) - 1;
                tvnumbersp.setText(Integer.toString(slmoi));
            }
        });
        imgCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoi = Integer.parseInt(tvnumbersp.getText().toString().trim()) + 1;
                tvnumbersp.setText(Integer.toString(slmoi));
            }
        });
        btnaddcart.setOnClickListener(view -> {
            if (ShopFragment.Cartlist.size() > 0)//gio hang khong rong
            {
                int sl = Integer.parseInt(tvnumbersp.getText().toString().trim());
                boolean tontaimahang = false;
                for (int i = 0; i < ShopFragment.Cartlist.size(); i++)
                {
                    if (ShopFragment.Cartlist.get(i).getId_product() == id_product)
                    {
                        ShopFragment.Cartlist.get(i).setAmount(ShopFragment.Cartlist.get(i).getAmount() + sl);
                        ShopFragment.Cartlist.get(i).setPrice(product.getPrice() * ShopFragment.Cartlist.get(i).getAmount());
                        tontaimahang = true;
                    }
                }
                if (tontaimahang == false)
                {
                    int sl1 = Integer.parseInt(tvnumbersp.getText().toString().trim());//lay so luong trong spinner
                    //tinh tien
                    int Tien2 = sl1 * (product.getPrice());
                    //them vao mang gio hang
                    ShopFragment.Cartlist.add(new Cart(id_product, product.getProduct_name(), Tien2, product.getImage(), sl1));
                    Toast.makeText(this, "Thêm thành công.", Toast.LENGTH_SHORT).show();
                    MainActivity2.setBugdeNumber();
                }
            } else //gio hang rong
            {
                int sl2 = Integer.parseInt(tvnumbersp.getText().toString().trim());//lay so luong trong spinner
                //tinh tien
                int Tien2 = sl2 * (product.getPrice());
                //them vao mang gio hang
                ShopFragment.Cartlist.add(new Cart(id_product, product.getProduct_name(), Tien2, product.getImage(), sl2));
                Toast.makeText(this, "Thêm thành công.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void checkYeuThich(int idcus, int idbh) {
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

    private void insertFavorite(int idcustomer, int idproduct) {
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