package com.example.deliciousrice.ui.account.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.deliciousrice.Adapter.AdapterAddress;
import com.example.deliciousrice.Adapter.AdapterProductHot;
import com.example.deliciousrice.Api.ApiProduct;
import com.example.deliciousrice.Api.ApiService;
import com.example.deliciousrice.MainActivity2;
import com.example.deliciousrice.Model.Adderss;
import com.example.deliciousrice.Model.Product;
import com.example.deliciousrice.R;
import com.example.deliciousrice.ui.shop.Activity.DetailActivity;
import com.example.deliciousrice.ui.shop.ShopFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressActivity extends AppCompatActivity {

    private ConstraintLayout cl_insertAdsress;
    private RecyclerView rclAddress;
    AdapterAddress adapterAddress;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);



        cl_insertAdsress = findViewById(R.id.cl_insertAdsress);
        cl_insertAdsress.setOnClickListener(view -> {
            Intent intent=new Intent(AddressActivity.this, EditAddressActivity.class);
            startActivity(intent);
        });

        rclAddress = findViewById(R.id.rcl_address);
        ShowAddress();
    }

    private void ShowAddress() {
        Intent intent=getIntent();
        int idadr=intent.getIntExtra("Adrress",0);


        ApiProduct apiProduct= ApiService.getService();
        Call<List<Adderss>> listAddre = apiProduct.getListAddresss(idadr);
        listAddre.enqueue(new Callback<List<Adderss>>() {
            @Override
            public void onResponse(Call<List<Adderss>> call, Response<List<Adderss>> response) {
                ArrayList<Adderss> addersses = new ArrayList<>();
                addersses = (ArrayList<Adderss>) response.body();
                rclAddress.setHasFixedSize(true);
                rclAddress.setLayoutManager(new LinearLayoutManager(AddressActivity.this,RecyclerView.VERTICAL, false));
                adapterAddress = new AdapterAddress(addersses, AddressActivity.this, productHot -> {
                    Intent intent=new Intent(AddressActivity.this, DetailActivity.class);
                    startActivity(intent);
                });
                rclAddress.setAdapter(adapterAddress);
            }

            @Override
            public void onFailure(Call<List<Adderss>> call, Throwable t) {

            }
        });


    }
}