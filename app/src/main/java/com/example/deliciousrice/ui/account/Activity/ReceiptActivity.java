package com.example.deliciousrice.ui.account.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.deliciousrice.Adapter.AdapterFavorite;
import com.example.deliciousrice.Adapter.AdapterHistoryBill;
import com.example.deliciousrice.Api.ApiProduct;
import com.example.deliciousrice.Api.ApiService;
import com.example.deliciousrice.Model.Bill;
import com.example.deliciousrice.Model.Favorite;
import com.example.deliciousrice.R;
import com.example.deliciousrice.ui.shop.Activity.InvoicedetailsActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReceiptActivity extends AppCompatActivity {
    private RecyclerView rclview;
    AdapterHistoryBill adapterHistoryBill;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);
        rclview = findViewById(R.id.rclview);
        getreceipt();
    }
    private void getreceipt(){
        Intent intent=getIntent();
        int idcustm =intent.getIntExtra("id_cus",0);
        ApiProduct apiProduct = ApiService.getService();
        Call<List<Bill>> callback = apiProduct.getListbill(idcustm);
        callback.enqueue(new Callback<List<Bill>>() {
            @Override
            public void onResponse(Call<List<Bill>> call, Response<List<Bill>> response) {
                ArrayList<Bill> bills = (ArrayList<Bill>) response.body();
                adapterHistoryBill = new AdapterHistoryBill(bills, getApplicationContext(), bill -> {
                    Intent intent = new Intent(getApplicationContext(), InvoicedetailsActivity.class);
                    intent.putExtra("id_bill",bill.getId_bill());
                    intent.putExtra("getData",bill);
                    startActivity(intent);
                });
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ReceiptActivity.this);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                rclview.setLayoutManager(linearLayoutManager);
                rclview.setAdapter(adapterHistoryBill);
            }

            @Override
            public void onFailure(Call<List<Bill>> call, Throwable t) {

            }
        });
    }
}