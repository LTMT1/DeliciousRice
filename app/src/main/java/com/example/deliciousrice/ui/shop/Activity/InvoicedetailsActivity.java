package com.example.deliciousrice.ui.shop.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliciousrice.Activity.BarColor;
import com.example.deliciousrice.Adapter.AdapterDetailBill;
import com.example.deliciousrice.Api.ApiProduct;
import com.example.deliciousrice.Api.ApiService;
import com.example.deliciousrice.Model.Bill;
import com.example.deliciousrice.Model.Detailbill;
import com.example.deliciousrice.R;
import com.example.deliciousrice.ui.cart.DaoCart;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvoicedetailsActivity extends AppCompatActivity {

    private TextView tvMaBill,tvNameKH,tvPhoneKH,tvDiaChi,tvNameNV,tvDateDat,tvTongTien,tvSoMon,tvDatLai,tvCountDownTime;
    private RecyclerView rcyViewDetailReceipt;
    private TextView tvshipkm,tvTongtienBill;
    private TextView tvmoneyship;
    private TextView tvkhuyenmai,textVieưgone;
    private ImageView imgBackInvoicedetails;
    CountDownTimer countDownTimer;
    Boolean counterIsActive = false;



    AdapterDetailBill adapterDetailBill;
    Bill bill;
    String Name,SDT;
    ArrayList<Detailbill> detailbillArrayList;
    DaoCart daoCart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoicedetails);
        BarColor.setStatusBarColor(this);
        Intent intent = getIntent();
        SDT=intent.getStringExtra("phone");
        Name = intent.getStringExtra("name");
        bill = (Bill) intent.getSerializableExtra("getData");
        daoCart=new DaoCart(getApplicationContext());
        Anhxa();
        setData();
        getDataDetailBill();
        countDowmTime();
    }

    private void Anhxa() {
        tvCountDownTime = findViewById(R.id.tvCountDownTime);
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
        tvshipkm = findViewById(R.id.tvshipkm);
        tvmoneyship = findViewById(R.id.tvmoneyship);
        tvkhuyenmai = findViewById(R.id.tvkhuyenmai);
        tvTongtienBill=findViewById(R.id.tvTongtien);
        textVieưgone=findViewById(R.id.textView63);
        imgBackInvoicedetails = findViewById(R.id.img_back_Invoicedetails);
        imgBackInvoicedetails.setOnClickListener(view -> {

        });

    }

    private void setData(){
        tvMaBill.setText(bill.getId_bill());
        tvNameKH.setText(Name);
        tvDateDat.setText(bill.getDate());
        tvPhoneKH.setText(SDT);
    }

    private void getDataDetailBill(){
        ApiProduct apiProduct = ApiService.getService();
        Call<List<Detailbill>> listCallProductBill = apiProduct.getProductBill(bill.getId_customer(), bill.getId_bill());
        listCallProductBill.enqueue(new Callback<List<Detailbill>>() {
            public void onResponse(Call<List<Detailbill>> call, Response<List<Detailbill>> response) {
                detailbillArrayList = (ArrayList<Detailbill>) response.body();
                Detailbill detailbill = detailbillArrayList.get(0);
                int tongslproduct = 0,priceproduct=0;
                for(int i=0;i<detailbillArrayList.size();i++){
                    tongslproduct =detailbillArrayList.get(i).getAmount()+tongslproduct;
                    priceproduct =detailbillArrayList.get(i).getTotal_money()+priceproduct;
                }
                tvDiaChi.setText(detailbill.getAddress());
                tvNameNV.setText(detailbill.getUser_namenv());
//                datLaiOnClick(detailbillArrayList);
                Khuyenmai(priceproduct,tongslproduct);
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
    private void datLaiOnClick(ArrayList<Detailbill> list){
        for(int i=0;i<list.size();i++){
            daoCart.InsertData(1, list.get(i).getProduct_name(),  list.get(i).getTotal_money(),"c",list.get(i).getAmount());
        }
        tvDatLai.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), PayActivity.class);

            startActivity(intent);
        });
    }
    private void Khuyenmai(int priceproduct,int tongslproduct){
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tvTongTien.setText(decimalFormat.format(priceproduct)+"đ");
        if(tongslproduct>3){
            textVieưgone.setVisibility(View.VISIBLE);
            tvkhuyenmai.setVisibility(View.VISIBLE);
            double khuyenmai=priceproduct*0.2;
            tvkhuyenmai.setText(decimalFormat.format(khuyenmai)+"đ");
            double tongtiensp=priceproduct-khuyenmai+20000;
            tvTongtienBill.setText(decimalFormat.format(tongtiensp)+"đ");
        }else{
            textVieưgone.setVisibility(View.GONE);
            tvkhuyenmai.setVisibility(View.GONE);
            double tongtiensp=priceproduct+20000;
            tvTongtienBill.setText(decimalFormat.format(tongtiensp)+"đ");
        }
        tvSoMon.setText("Tổng số("+tongslproduct+" món)");
    }


    private void countDowmTime(){

        long duration = TimeUnit.MINUTES.toMillis(1);

        new CountDownTimer(duration, 1000) {
            @Override
            public void onTick(long l) {
                String sDuration = String.format(Locale.ENGLISH,"%02d : %02d"
                    ,TimeUnit.MILLISECONDS.toMinutes(l)
                    ,TimeUnit.MILLISECONDS.toSeconds(l) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(l)));

                tvCountDownTime.setText(sDuration);
            }

            @SuppressLint("ResourceAsColor")
            @Override
            public void onFinish() {
//                tvCountDownTime.setVisibility(View.GONE);
                tvCountDownTime.setText("Bat dau giao hang");
                tvCountDownTime.setTextColor(R.color.purple_700);
            }
        }.start();
    }
}