package com.example.deliciousrice.ui.shop.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliciousrice.Activity.BarColor;
import com.example.deliciousrice.Activity.LoadingDialog;
import com.example.deliciousrice.Adapter.AdapterProductBill;
import com.example.deliciousrice.Adapter.AdapterSelectAddress;
import com.example.deliciousrice.Api.ApiProduct;
import com.example.deliciousrice.Api.ApiService;
import com.example.deliciousrice.Api.CreateOrder;
import com.example.deliciousrice.MainActivity2;
import com.example.deliciousrice.Model.Adderss;
import com.example.deliciousrice.Model.Cart;
import com.example.deliciousrice.Model.ProductBill;
import com.example.deliciousrice.R;
import com.example.deliciousrice.ui.cart.CartFragment;
import com.example.deliciousrice.ui.cart.DaoCart;
import com.example.deliciousrice.ui.shop.ShopFragment;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class PayActivity extends AppCompatActivity {
    private TextView btnpay;
    private TextView tvmoney;
    private TextView tvgiaohang;
    private TextView tvtotalmoney;
    private RecyclerView rclview;
    private EditText edtstatus;
    AdapterProductBill adapterProductBill;
    String id_bill;
    public RadioButton radio6, radio7;
    TextView lblZpTransToken, txtToken;
    Button btnCreateOrder, btnPay;
    EditText txtAmount;
    String token = "", address;
    Spinner tvsetaddress;
    int id_customer;
    AdapterSelectAddress adapterSelectAddress;
    DaoCart daoCart;
    private TextView tvKhuyenmai;
    private TextView tvTienkm;
    private TextView tvTongmoney;
    private TextView textView65;
    int tongtiensp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        BarColor.setStatusBarColor(this);
        daoCart = new DaoCart(getApplicationContext());
        Intent intent = getIntent();
        id_customer = intent.getIntExtra("id_customer", 0);
        getlistadress(id_customer);

        //zalo pay
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ZaloPaySDK.init(2553, Environment.SANDBOX);
        BindView();
        ListProductBuy();
        Pay();
    }

    private void BindView() {
        tvKhuyenmai = findViewById(R.id.tv_khuyenmai);
        tvTienkm = findViewById(R.id.tv_tienkm);
        tvTongmoney = findViewById(R.id.tv_tongmoney);
        tvsetaddress = findViewById(R.id.tvsetaddress);
        tvmoney = findViewById(R.id.tvmoney);
        tvgiaohang = findViewById(R.id.tvgiaohang);
        rclview = findViewById(R.id.rclview);
        btnpay = findViewById(R.id.btnpay);
        edtstatus = findViewById(R.id.edtstatus);
        radio6 = findViewById(R.id.radioButton6);
        radio7 = findViewById(R.id.radioButton7);
        textView65 = findViewById(R.id.textView65);
    }

    private void ListProductBuy() {
        int tongslproduct = 0, priceproduct = 0;
        ArrayList<ProductBill> productBills = new ArrayList<>();
        for (int i = 0; i < ShopFragment.Cartlist.size(); i++) {
            tongslproduct = ShopFragment.Cartlist.get(i).getAmount() + tongslproduct;
            priceproduct = ShopFragment.Cartlist.get(i).getPrice() + priceproduct;
            Cart cart = ShopFragment.Cartlist.get(i);
            productBills.add(new ProductBill(cart.getName(), cart.getAmount(), cart.getPrice()));
        }
        Khuyenmai(priceproduct, tongslproduct);
        adapterProductBill = new AdapterProductBill(productBills, PayActivity.this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(PayActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rclview.setLayoutManager(linearLayoutManager);
        rclview.setAdapter(adapterProductBill);
    }

    private void Pay() {
        final LoadingDialog loadingDialog = new LoadingDialog(PayActivity.this);
        btnpay.setOnClickListener(view -> {
            loadingDialog.StartLoadingDialog();
            if (radio6.isChecked()) {
                Random random = new Random();
                int number = random.nextInt(10000000);

                id_bill = "DCR" + id_customer + "-" + number;

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                String currentDateandTime = sdf.format(new Date());
                String ednote = edtstatus.getText().toString().trim();

                if (ShopFragment.Cartlist.size() > 0) {
                    addBill(id_bill, id_customer, address, currentDateandTime, ednote, tongtiensp);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < ShopFragment.Cartlist.size(); i++) {
                                addDetailBill(i);
                            }
                        }
                    }, 5000);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            CartFragment.textviewthongbao.setVisibility(View.VISIBLE);
                            ShopFragment.Cartlist.clear();
                            daoCart.DeleteData();
                            CartFragment.UpdateTongTien();
                            MainActivity2.setBugdeNumber();
                            CartFragment.adapterCart.notifyDataSetChanged();
                            loadingDialog.dismissDialog();
                            Toast.makeText(PayActivity.this, "Hóa đơn của bạn đã được xử lý!", Toast.LENGTH_SHORT).show();
                            PushNotification();
                        }
                    }, 7000);
                } else {
                    loadingDialog.dismissDialog();
                    Toast.makeText(PayActivity.this, "Giỏ hàng không có sản phầm nào!", Toast.LENGTH_SHORT).show();
                }
            } else if (radio7.isChecked()) {
                if (ShopFragment.Cartlist.size() > 0) {

                    CreateOrder orderApi = new CreateOrder();

                    try {
                        JSONObject data = orderApi.createOrder("100");
                        String code = data.getString("return_code");
//                        Toast.makeText(getApplicationContext(), "return_code: " + code, Toast.LENGTH_LONG).show();

                        if (code.equals("1")) {
                            token = data.getString("zp_trans_token");
                            payWithZalo();
                            IsDone();
                            loadingDialog.dismissDialog();
                        }

                    } catch (Exception e) {
                        loadingDialog.dismissDialog();
                        e.printStackTrace();
                    }
                } else {
                    loadingDialog.dismissDialog();
                    Toast.makeText(PayActivity.this, "Giỏ hàng không có sản phầm nào!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void payWithZalo() {
        ZaloPaySDK.getInstance().payOrder(PayActivity.this, token, "demozpdk://app", new PayOrderListener() {
            @Override
            public void onPaymentSucceeded(final String transactionId, final String transToken, final String appTransID) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new AlertDialog.Builder(PayActivity.this)
                                .setTitle("Payment Success")
                                .setMessage(String.format("TransactionId: %s - TransToken: %s", transactionId, transToken))
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .setNegativeButton("Cancel", null).show();
                    }

                });
                IsLoading();
            }

            @Override
            public void onPaymentCanceled(String zpTransToken, String appTransID) {
                new AlertDialog.Builder(PayActivity.this)
                        .setTitle("User Cancel Payment")
                        .setMessage(String.format("zpTransToken: %s \n", zpTransToken))
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setNegativeButton("Cancel", null).show();
            }

            @Override
            public void onPaymentError(ZaloPayError zaloPayError, String zpTransToken, String appTransID) {
                new AlertDialog.Builder(PayActivity.this)
                        .setTitle("Payment Fail")
                        .setMessage(String.format("ZaloPayErrorCode: %s \nTransToken: %s", zaloPayError.toString(), zpTransToken))
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setNegativeButton("Cancel", null).show();
            }
        });
    }

    private void IsLoading() {
//        lblZpTransToken.setVisibility(View.INVISIBLE);
//        txtToken.setVisibility(View.INVISIBLE);
//        btnPay.setVisibility(View.INVISIBLE);
    }

    private void IsDone() {
//        lblZpTransToken.setVisibility(View.VISIBLE);
//        txtToken.setVisibility(View.VISIBLE);
//        btnPay.setVisibility(View.VISIBLE);
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
        Call<String> callback = apiProduct.adddetailbill(id_bill, cart.getName(), cart.getAmount(), cart.getPrice());
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
        Call<String> callback = apiProduct.pushNotification(MainActivity2.token);
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

    private void getlistadress(int idcustomer) {
        ApiProduct apiProduct = ApiService.getService();
        Call<ArrayList<Adderss>> listAddre = apiProduct.getListAddresss(idcustomer);
        listAddre.enqueue(new Callback<ArrayList<Adderss>>() {
            @Override
            public void onResponse(Call<ArrayList<Adderss>> call, Response<ArrayList<Adderss>> response) {
                ArrayList<Adderss> addersses = new ArrayList<>();
                addersses = response.body();
                adapterSelectAddress = new AdapterSelectAddress(PayActivity.this, R.layout.item_address, addersses);
                tvsetaddress.setAdapter(adapterSelectAddress);
                tvsetaddress.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        address = adapterSelectAddress.getItem(i).getAddress_specifically();
                        Toast.makeText(PayActivity.this, adapterSelectAddress.getItem(i).getAddress_specifically(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onFailure(Call<ArrayList<Adderss>> call, Throwable t) {

            }
        });
    }

    private void Khuyenmai(int priceproduct, int tongslproduct) {
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tvmoney.setText(decimalFormat.format(priceproduct) + "đ");
        if (tongslproduct > 3) {
            tvKhuyenmai.setVisibility(View.VISIBLE);
            tvTienkm.setVisibility(View.VISIBLE);
            double khuyenmai = priceproduct * 0.2;
            tvTienkm.setText(decimalFormat.format(khuyenmai) + "đ");
            tongtiensp = (int) (priceproduct - khuyenmai + 20000);
            tvTongmoney.setText(decimalFormat.format(tongtiensp) + "đ");
        } else {
            tvKhuyenmai.setVisibility(View.GONE);
            tvTienkm.setVisibility(View.GONE);
            tongtiensp = priceproduct + 20000;
            tvTongmoney.setText(decimalFormat.format(tongtiensp) + "đ");
        }
        textView65.setText("Tổng số(" + tongslproduct + " món)");
    }
}