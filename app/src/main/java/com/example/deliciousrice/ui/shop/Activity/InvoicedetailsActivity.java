package com.example.deliciousrice.ui.shop.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.deliciousrice.Adapter.AdapterHistoryBill;
import com.example.deliciousrice.Adapter.AdapterProductBill;
import com.example.deliciousrice.Api.ApiProduct;
import com.example.deliciousrice.Api.ApiService;
import com.example.deliciousrice.Model.Bill;
import com.example.deliciousrice.Model.Product;
import com.example.deliciousrice.Model.ProductBill;
import com.example.deliciousrice.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvoicedetailsActivity extends AppCompatActivity {

    private TextView tvMaBill,tvNameKH,tvPhoneKH,tvDiaChi,tvNameNV,tvDateDat;
    private RecyclerView rcyViewDetailReceipt;

    AdapterProductBill adapterProductBill;
    String id_billl;
    Bill bill;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoicedetails);

        Intent intent = getIntent();
        id_billl = intent.getStringExtra("id_bill");
        bill = (Bill) intent.getSerializableExtra("getData");

        Anhxa();
        setData();
        getDataDetailBill();
    }

    private void Anhxa() {
        tvMaBill = findViewById(R.id.tvMaDonHang);
        tvNameKH = findViewById(R.id.tvNameKH);
        tvPhoneKH = findViewById(R.id.tvPhoneKH);
        tvNameNV = findViewById(R.id.tvNameNV);
        tvDiaChi = findViewById(R.id.tvDiaChi);
        tvDateDat = findViewById(R.id.tvDateDat);
        rcyViewDetailReceipt = findViewById(R.id.rcyViewChiTietDH);
    }

    private void setData(){
        tvMaBill.setText(bill.getId_bill());
        tvDateDat.setText(bill.getDate());
    }

    private void getDataDetailBill(){
        ApiProduct apiProduct = ApiService.getService();
        Call<ArrayList<ProductBill>> listCallProductBill = apiProduct.getListProductBill();
        listCallProductBill.enqueue(new Callback<ArrayList<ProductBill>>() {
            @Override
            public void onResponse(Call<ArrayList<ProductBill>> call, Response<ArrayList<ProductBill>> response) {
                ArrayList<ProductBill> productBillArrayList = new ArrayList<>();
                productBillArrayList = response.body();
                rcyViewDetailReceipt.setHasFixedSize(true);
                rcyViewDetailReceipt.setLayoutManager(new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false));
                adapterProductBill = new AdapterProductBill(productBillArrayList, getApplicationContext());
                rcyViewDetailReceipt.setAdapter(adapterProductBill);
            }

            @Override
            public void onFailure(Call<ArrayList<ProductBill>> call, Throwable t) {

            }
        });
    }

}