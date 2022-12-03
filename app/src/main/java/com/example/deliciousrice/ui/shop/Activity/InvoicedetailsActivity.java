package com.example.deliciousrice.ui.shop.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.deliciousrice.Adapter.AdapterDetailBill;
import com.example.deliciousrice.Adapter.AdapterHistoryBill;
import com.example.deliciousrice.Adapter.AdapterProductBill;
import com.example.deliciousrice.Api.ApiProduct;
import com.example.deliciousrice.Api.ApiService;
import com.example.deliciousrice.Model.Bill;
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
    String id_billl;
    int id_customer;
    Bill bill;
    Detailbill detailbill;


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
        datLaiOnClick();
    }

    private void Anhxa() {
        tvMaBill = findViewById(R.id.tvMaDonHang);
        tvNameKH = findViewById(R.id.tvNameKH);
        tvPhoneKH = findViewById(R.id.tvPhoneKH);
        tvNameNV = findViewById(R.id.tvNameNV);
        tvDiaChi = findViewById(R.id.tvDiaChi);
        tvDateDat = findViewById(R.id.tvDateDat);
        tvTongTien = findViewById(R.id.tvTongTien);
        tvSoMon = findViewById(R.id.tvSoMon);
        tvDatLai = findViewById(R.id.tvDatlai);
        rcyViewDetailReceipt = findViewById(R.id.rcyViewChiTietDH);
    }

    private void setData(){
        tvMaBill.setText(bill.getId_bill());
        tvDateDat.setText(bill.getDate()+"");
        tvTongTien.setText(String.valueOf(bill.getMoney()));
    }

    private void getDataDetailBill(){
        ApiProduct apiProduct = ApiService.getService();
        Call<List<Detailbill>> listCallProductBill = apiProduct.getProductBill(bill.getId_customer(), id_billl);
        listCallProductBill.enqueue(new Callback<List<Detailbill>>() {
            @Override
            public void onResponse(Call<List<Detailbill>> call, Response<List<Detailbill>> response) {
                List<Detailbill> detailbillArrayList = new ArrayList<>();
                detailbillArrayList = response.body();

                detailbill = response.body().get(0);
                tvDiaChi.setText(detailbill.getAddress());
                tvNameNV.setText(detailbill.getUser_name());
                tvSoMon.setText("Tạm tính ("+detailbill.getAmount()+" mon)");

                rcyViewDetailReceipt.setHasFixedSize(true);
                rcyViewDetailReceipt.setLayoutManager(new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false));
                adapterDetailBill = new AdapterDetailBill(detailbillArrayList, getApplicationContext());
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