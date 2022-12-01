package com.example.deliciousrice.ui.account.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.deliciousrice.Adapter.AdapterAddress;
import com.example.deliciousrice.Adapter.AdapterProductHot;
import com.example.deliciousrice.Api.ApiProduct;
import com.example.deliciousrice.Api.ApiService;
import com.example.deliciousrice.MainActivity2;
import com.example.deliciousrice.Model.Adderss;
import com.example.deliciousrice.Model.Product;
import com.example.deliciousrice.R;
import com.example.deliciousrice.ui.account.AccountFragment;
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
    int idadr;
    private ImageView imgBackAddress;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        imgBackAddress = findViewById(R.id.img_back_Address);
        imgBackAddress.setOnClickListener(v->{
            Fragment fragment=new AccountFragment();
            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.Address,fragment).commit();
        });



        Intent intent=getIntent();
        idadr =intent.getIntExtra("Adrress",0);



        cl_insertAdsress = findViewById(R.id.cl_insertAdsress);
        cl_insertAdsress.setOnClickListener(view -> {
            Intent i=new Intent(AddressActivity.this, AddAddressActivity.class);
            i.putExtra("iccome",idadr);
            startActivity(i);
        });

        rclAddress = findViewById(R.id.rcl_address);
   /*     ShowAddress();*/

    }

   /* private void ShowAddress() {
        final ProgressDialog progressDialog = new ProgressDialog(AddressActivity.this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();


        ApiProduct apiProduct= ApiService.getService();
        Call<List<Adderss>> listAddre = apiProduct.getListAddresss(idadr);
        listAddre.enqueue(new Callback<List<Adderss>>() {
            @Override
            public void onResponse(Call<List<Adderss>> call, Response<List<Adderss>> response) {
                ArrayList<Adderss> addersses = new ArrayList<>();
                progressDialog.dismiss();
                addersses = (ArrayList<Adderss>) response.body();
                rclAddress.setHasFixedSize(true);
                rclAddress.setLayoutManager(new LinearLayoutManager(AddressActivity.this,RecyclerView.VERTICAL, false));

                adapterAddress = new AdapterAddress(addersses, AddressActivity.this, addressNew -> {

                    Intent intent=new Intent(AddressActivity.this, EditAddressActivity.class);
                    intent.putExtra("idcustomer",idadr);
                    intent.putExtra("getdataAddress", addressNew);
                    startActivity(intent);

                });
                rclAddress.setAdapter(adapterAddress);
                adapterAddress.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Adderss>> call, Throwable t) {

            }
        });


    }*/
}