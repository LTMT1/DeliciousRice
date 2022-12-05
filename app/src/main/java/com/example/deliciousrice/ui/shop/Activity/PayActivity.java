package com.example.deliciousrice.ui.shop.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deliciousrice.Activity.BarColor;
import com.example.deliciousrice.Adapter.AdapterProductBill;
import com.example.deliciousrice.Api.ApiProduct;
import com.example.deliciousrice.Api.ApiService;
import com.example.deliciousrice.Model.Cart;
import com.example.deliciousrice.Model.ProductBill;
import com.example.deliciousrice.R;
import com.example.deliciousrice.ui.cart.CartFragment;
import com.example.deliciousrice.ui.shop.ShopFragment;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PayActivity extends AppCompatActivity {
    private TextView tvselectaddress;
    private TextView btnpay;
    private TextView tvmoney;
    private TextView tvgiaohang;
    private TextView tvtotalmoney;
    private RecyclerView rclview;
    private EditText edtstatus;
    AdapterProductBill adapterProductBill;
    String id_bill;
    int totalmoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        BarColor.setStatusBarColor(this);
        Anhxa();
        ListProductBuy();
        Pay();
    }

    private void Anhxa() {
        tvselectaddress = findViewById(R.id.tvselectaddress);
        tvmoney = findViewById(R.id.tvmoney);
        tvgiaohang = findViewById(R.id.tvgiaohang);
        tvtotalmoney = findViewById(R.id.textView166666);
        rclview = findViewById(R.id.rclview);
        btnpay = findViewById(R.id.btnpay);
        edtstatus = findViewById(R.id.edtstatus);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tvmoney.setText(decimalFormat.format(CartFragment.total_money) + "đ");
        totalmoney = CartFragment.total_money;
        tvtotalmoney.setText(decimalFormat.format(totalmoney) + "đ");
    }

    private void ListProductBuy() {
        ArrayList<ProductBill> productBills = new ArrayList<>();
        for (int i = 0; i < ShopFragment.Cartlist.size(); i++) {
            Cart cart =  ShopFragment.Cartlist.get(i);
            productBills.add(new ProductBill(cart.getName(), cart.getAmount(), cart.getPrice()));
        }
        adapterProductBill = new AdapterProductBill(productBills, PayActivity.this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(PayActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rclview.setLayoutManager(linearLayoutManager);
        rclview.setAdapter(adapterProductBill);
    }

    private void Pay() {
        Intent intent = getIntent();
        int id_customer = intent.getIntExtra("id_customer", 0);
        btnpay.setOnClickListener(view -> {
            Random random = new Random();
            int number = random.nextInt(10000000);

            id_bill = "DCR" + id_customer + "-" + number;

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String currentDateandTime = sdf.format(new Date());
            String ednote = edtstatus.getText().toString().trim();

            if ( ShopFragment.Cartlist.size() > 0) {
                addBill(id_bill, id_customer, "Hà Nội", currentDateandTime, ednote, totalmoney);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i <  ShopFragment.Cartlist.size(); i++) {
                            addDetailBill(i);
                        }
                    }
                }, 5000);

                Toast.makeText(PayActivity.this, "Vui lòng chờ trong giây lát ....", Toast.LENGTH_LONG).show();

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        CartFragment.textviewthongbao.setVisibility(View.VISIBLE);
                        ShopFragment.Cartlist.clear();
                        CartFragment.UpdateTongTien();
                        CartFragment.adapterCart.notifyDataSetChanged();
                        Toast.makeText(PayActivity.this, "Hóa đơn của bạn đã được xử lý!", Toast.LENGTH_SHORT).show();
                        PushNotification();
                    }
                }, 7000);

            } else {
                Toast.makeText(PayActivity.this, "Giỏ hàng không có sản phầm nào!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addBill(String bill, int idcus, String adreess, String date, String note, int money) {
        ApiProduct apiProduct = ApiService.getService();
        Call<String> callback = apiProduct.addbill(bill, idcus, adreess, date, note, money);
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void addDetailBill(int i) {
        Cart cart = ShopFragment.Cartlist.get(i);
        ApiProduct apiProduct = ApiService.getService();
        Call<String> callback = apiProduct.adddetailbill(id_bill, cart.getId_product(), cart.getAmount(), cart.getPrice());
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void PushNotification() {
        ApiProduct apiProduct = ApiService.getService();
        Call<String> callback = apiProduct.pushNotification();
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("that bai cc", "");
            }
        });
    }
}