package com.example.deliciousrice.ui.shop.Activity;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliciousrice.Activity.BarColor;
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
import com.example.deliciousrice.dialog.LoadingDialog1;
import com.example.deliciousrice.ui.cart.CartFragment;
import com.example.deliciousrice.ui.cart.DaoCart;
import com.example.deliciousrice.ui.shop.ShopFragment;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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
    private RecyclerView rclview;
    private EditText edtstatus;

    AdapterProductBill adapterProductBill;
    //    String id_bill;
    public RadioButton radio6, radio7;
    String token = "", address;
    Spinner tvsetaddress;
    AdapterSelectAddress adapterSelectAddress;
    DaoCart daoCart;
    private TextView tvKhuyenmai;
    private TextView tvTienkm;
    private TextView tvTongmoney;
    private TextView textView65;
    int tongtiensp,id_customer;
    private LoadingDialog1 loadingDialog;
    String productList;
    ArrayList<Adderss> addersses;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        BarColor.setStatusBarColor(this);
        //anhxa
        BindView();
        addersses = new ArrayList<>();
        daoCart = new DaoCart(getApplicationContext());
        Intent intent = getIntent();
        id_customer = intent.getIntExtra("id_customer", 0);
        getlistadress(id_customer);
        getIdBill();
        //zalo pay
        loadingDialog = new LoadingDialog1(this);
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ZaloPaySDK.init(2553, Environment.SANDBOX);
        //
        //
        ListProductBuy();
        Pay();
    }

    private void BindView() {
        tvKhuyenmai = findViewById(R.id.tv_khuyenmai);
        tvTienkm = findViewById(R.id.tv_tienkm);
        tvTongmoney = findViewById(R.id.tv_tongmoney);
        tvsetaddress = findViewById(R.id.tvsetaddress);
        tvmoney = findViewById(R.id.tvmoney);
        rclview = findViewById(R.id.rclview);
        btnpay = findViewById(R.id.btnpay);
        edtstatus = findViewById(R.id.edtstatus);
        radio6 = findViewById(R.id.radioButton6);
        radio7 = findViewById(R.id.radioButton7);
        textView65 = findViewById(R.id.textView65);
//        imgBackThanhtoan = findViewById(R.id.img_back_thanhtoan);
//        imgBackThanhtoan.setOnClickListener(view -> {
//        });
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
        btnpay.setOnClickListener(view -> {
            if (addersses.size() == 0 || MainActivity2.phone_number.equals("")) {
                Toast.makeText(getApplicationContext(), "Bạn cần phải có địa chỉ nhận hàng và số điện thoại để mua hàng", Toast.LENGTH_SHORT).show();
            } else {
                loadingDialog.StartLoadingDialog();
                if (radio6.isChecked()) {
                    insertPay("0");
                } else if (radio7.isChecked()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PayActivity.this);
                    builder.setTitle("Xác nhận thanh toán bằng Zalo Pay");
                    builder.setCancelable(false);
                    builder.setMessage("Bạn sẽ không được phép hủy đơn hàng sau khi thanh toán.");
                    builder.setPositiveButton("Tiếp tục", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            CreateOrder orderApi = new CreateOrder();
                            try {
                                JSONObject data = orderApi.createOrder(String.valueOf(tongtiensp));
                                String code = data.getString("return_code");

                                if (code.equals("1")) {
                                    token = data.getString("zp_trans_token");
                                    payWithZalo();
                                }

                            } catch (Exception e) {
                                loadingDialog.dismissDialog();
                                e.printStackTrace();
                            }
                        }
                    });
                    builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                            loadingDialog.dismissDialog();
                        }
                    });
                    builder.show();

                } else {
                    loadingDialog.dismissDialog();
                    Toast.makeText(this, "Bạn chưa chọn phương thức mua hàng", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void payWithZalo() {
        loadingDialog.dismissDialog();
        ZaloPaySDK.getInstance().payOrder(PayActivity.this, token, "demozpdk://app", new PayOrderListener() {
            @Override
            public void onPaymentSucceeded(final String transactionId, final String transToken, final String appTransID) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loadingDialog.StartLoadingDialog();
                        insertPay("1");
                    }

                });
            }

            @Override
            public void onPaymentCanceled(String zpTransToken, String appTransID) {
                Log.e("TAG", "run: " + "Cancel");
                new AlertDialog.Builder(PayActivity.this)
                        .setTitle("Bạn đã hủy thanh toán!")
                        .setCancelable(false)
//                        .setMessage(String.format("zpTransToken: %s \n", zpTransToken))
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();

            }

            @Override
            public void onPaymentError(ZaloPayError zaloPayError, String zpTransToken, String appTransID) {
                new AlertDialog.Builder(PayActivity.this)
                        .setTitle("Thanh toán thất bại!")
                        .setCancelable(false)
                        .setMessage("Bạn cần phải có app ZaloPay để thanh toán")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
            }
        });
    }

    private void addBill(String bill, int idcus, String adreess, String date, String note, int money,String pay) {
            ApiProduct apiProduct = ApiService.getService();
            Call<String> callback = apiProduct.addbill(bill, idcus, adreess, date, note, money,pay);
            callback.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });
    }
    private void getlistadress(int idcustomer) {
        ApiProduct apiProduct = ApiService.getService();
        Call<ArrayList<Adderss>> listAddre = apiProduct.getListAddresss(idcustomer);
        listAddre.enqueue(new Callback<ArrayList<Adderss>>() {
            @Override
            public void onResponse(Call<ArrayList<Adderss>> call, Response<ArrayList<Adderss>> response) {
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
    private void addDetailBill(String idbill, int i) {
            Cart cart = ShopFragment.Cartlist.get(i);
            ApiProduct apiProduct = ApiService.getService();
            Call<String> callback = apiProduct.adddetailbill(idbill, cart.getName(), cart.getAmount(), cart.getPrice());
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
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("YOUR_CHANNEL_ID",
                    "YOUR_CHANNEL_NAME",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("YOUR_NOTIFICATION_CHANNEL_DESCRIPTION");
            mNotificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "YOUR_CHANNEL_ID")
                .setSmallIcon(R.drawable.imghelloscreen) // notification icon
                .setContentTitle("Đặt hàng thành công") // title for notification
                .setContentText("Vào app ngay để biết thêm thông tin chi tiết về các đơn hàng của bạn.")// message for notification;
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Vào app ngay để biết thêm thông tin chi tiết về các đơn hàng của bạn."))
                .setAutoCancel(true);// clear notification after click
        Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pi);
        mNotificationManager.notify(0, mBuilder.build());
    }

    private void Khuyenmai(int priceproduct, int tongslproduct) {
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tvmoney.setText(decimalFormat.format(priceproduct) + " đ");
        if (tongslproduct > 3) {
            tvKhuyenmai.setVisibility(View.VISIBLE);
            tvTienkm.setVisibility(View.VISIBLE);
            double khuyenmai = priceproduct * 0.15;
            tvTienkm.setText(decimalFormat.format(khuyenmai) + " đ");
            tongtiensp = (int) (priceproduct - khuyenmai + 20000);
            tvTongmoney.setText(decimalFormat.format(tongtiensp) );
        } else {
            tvKhuyenmai.setVisibility(View.GONE);
            tvTienkm.setVisibility(View.GONE);
            tongtiensp = priceproduct + 20000;
            tvTongmoney.setText(decimalFormat.format(tongtiensp) );
        }
        textView65.setText("Tổng số(" + tongslproduct + " món)");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ZaloPaySDK.getInstance().onResult(intent);
    }

    private void getIdBill() {
        ApiProduct apiProduct = ApiService.getService();
        Call<String> listCallProduct = apiProduct.getidBill();
        listCallProduct.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                productList = response.body();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void insertPay(String Pay) {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY/MM/dd", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        String ednote = edtstatus.getText().toString().trim();

        addBill(productList, id_customer, address, currentDateandTime, ednote, tongtiensp,Pay);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < ShopFragment.Cartlist.size(); i++) {
                    addDetailBill(productList, i);
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
                finish();
            }
        }, 7000);
    }
}