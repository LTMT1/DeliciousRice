package com.example.deliciousrice.ui.shop.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.deliciousrice.Activity.BarColor;
import com.example.deliciousrice.Adapter.AdapterDetailBill;
import com.example.deliciousrice.Adapter.AdapterHistoryBill;
import com.example.deliciousrice.Adapter.AdapterProductBill;
import com.example.deliciousrice.Api.ApiProduct;
import com.example.deliciousrice.Api.ApiService;
import com.example.deliciousrice.Model.Bill;
import com.example.deliciousrice.Model.Customer;
import com.example.deliciousrice.Model.Detailbill;
import com.example.deliciousrice.Model.Product;
import com.example.deliciousrice.Model.ProductBill;
import com.example.deliciousrice.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvoicedetailsActivity extends AppCompatActivity {

    private TextView tvMaBill,tvNameKH,tvPhoneKH,tvDiaChi,tvNameNV,tvDateDat,tvTongTien,tvSoMon,tvDatLai;
    private RecyclerView rcyViewDetailReceipt;

    AdapterDetailBill adapterDetailBill;
    Bill bill;
    String Name,SDT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoicedetails);
        BarColor.setStatusBarColor(this);
        Intent intent = getIntent();
        SDT=intent.getStringExtra("phone");
        Name = intent.getStringExtra("name");
        bill = (Bill) intent.getSerializableExtra("getData");

        Anhxa();
        setData();
        getDataDetailBill();
        datLaiOnClick();
    }

    private void Anhxa() {
        tvMaBill = findViewById(R.id.tvMaDonHang);
        tvNameKH = findViewById(R.id.tvNameKH);
        tvPhoneKH = findViewById(R.id.tvPhoneKH);
        tvNameNV = findViewById(R.id.tvNameNV);
        tvDiaChi = findViewById(R.id.tvDiaChi);
        tvDateDat = findViewById(R.id.tvDateDat);
        rcyViewDetailReceipt = findViewById(R.id.rcyViewChiTietDH);
        tvTongTien = findViewById(R.id.tvTongTien);
        tvSoMon = findViewById(R.id.tvSoMon);
        tvDatLai = findViewById(R.id.tvDatlai);
    }

    private void setData(){
        tvMaBill.setText(bill.getId_bill());
        tvNameKH.setText(Name);
        tvDateDat.setText(bill.getDate());
        tvTongTien.setText(String.valueOf(bill.getMoney()));
        tvPhoneKH.setText(SDT);
    }

    private void getDataDetailBill(){
        ApiProduct apiProduct = ApiService.getService();
        Call<List<Detailbill>> listCallProductBill = apiProduct.getProductBill(bill.getId_customer(), bill.getId_bill());
        listCallProductBill.enqueue(new Callback<List<Detailbill>>() {
            public void onResponse(Call<List<Detailbill>> call, Response<List<Detailbill>> response) {
                ArrayList<Detailbill> detailbillArrayList = (ArrayList<Detailbill>) response.body();
                Detailbill detailbill = detailbillArrayList.get(0);
                tvDiaChi.setText(detailbill.getAddress());
                tvNameNV.setText(detailbill.getUser_name());
                tvSoMon.setText("Tổng số "+detailbillArrayList.size()+" món");
                adapterDetailBill = new AdapterDetailBill(detailbillArrayList, getApplicationContext());
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                rcyViewDetailReceipt.setLayoutManager(linearLayoutManager);
                rcyViewDetailReceipt.setAdapter(adapterDetailBill);
            }

            @Override
            public void onFailure(Call<List<Detailbill>> call, Throwable t) {

            }
        });
    }
    private void datLaiOnClick(){
        tvDatLai.setOnClickListener(view -> {

        });
    }

}