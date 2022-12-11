package com.example.deliciousrice.ui.shop.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliciousrice.Activity.BarColor;
import com.example.deliciousrice.Adapter.AdapterDetailBill;
import com.example.deliciousrice.Api.ApiProduct;
import com.example.deliciousrice.Api.ApiService;
import com.example.deliciousrice.MainActivity2;
import com.example.deliciousrice.Model.Bill;
import com.example.deliciousrice.Model.Cart;
import com.example.deliciousrice.Model.Detailbill;
import com.example.deliciousrice.R;
import com.example.deliciousrice.ui.shop.ShopFragment;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvoicedetailsActivity extends AppCompatActivity {

    private TextView tvMaBill,tvNameKH,tvPhoneKH,tvDiaChi,tvNameNV,tvDateDat,tvTongTien,tvSoMon,tvDatLai,tvCountDownTime;
    private RecyclerView rcyViewDetailReceipt;
    private TextView tvshipkm,tvTongtienBill;
    private TextView tvkhuyenmai,textVieưgone;
    private ImageView imgBackInvoicedetails;
    private CardView textView7;


    AdapterDetailBill adapterDetailBill;
    Bill bill;
    String Name,SDT;
    ArrayList<Detailbill> detailbillArrayList;


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
        if(bill.getStatus().trim().equals("Đang chờ")){
            tvCountDownTime.setVisibility(View.VISIBLE);
            cancleBill(bill.getId_bill(),"Đã Hủy");
        }else {
            tvCountDownTime.setVisibility(View.GONE);
        }
    }

    private void Anhxa() {
        //textView7 = findViewById(R.id.textView7);
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
        tvkhuyenmai = findViewById(R.id.tvkhuyenmai);
        tvTongtienBill=findViewById(R.id.tvTongtien);
        textVieưgone=findViewById(R.id.textView63);
        imgBackInvoicedetails = findViewById(R.id.img_back_Invoicedetails);
        imgBackInvoicedetails.setOnClickListener(view -> {

        });

    }

    private void setData(){
        tvMaBill.setText("DCR"+bill.getId_bill());
        tvNameKH.setText(Name);
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
        tvDateDat.setText(sdf1.format(bill.getDate()));
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
                int id_customer=detailbill.getId_customer();
                tvDiaChi.setText(detailbill.getAddress());
                tvNameNV.setText(detailbill.getUser_namenv());
                datLaiOnClick(detailbillArrayList,id_customer);
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
    private void datLaiOnClick(ArrayList<Detailbill> list,int id_customer){
        tvDatLai.setOnClickListener(view -> {
            for(int j=0;j<list.size();j++){
            ShopFragment.Cartlist= (ArrayList<Cart>)  MainActivity2.daoCart.getall();
            if (ShopFragment.Cartlist.size() > 0)//gio hang khong rong
            {
                boolean tontaimahang = false;
                for (int i = 0; i <  ShopFragment.Cartlist.size(); i++)
                {
                    if (ShopFragment.Cartlist.get(i).getId_product() == list.get(j).getId_product())
                    {
                        ShopFragment.Cartlist.get(i).setAmount( ShopFragment.Cartlist.get(i).getAmount() + list.get(j).getAmount());
                        ShopFragment.Cartlist.get(i).setPrice(list.get(j).getTotal_money() + ShopFragment.Cartlist.get(i).getPrice());
                        DetailActivity.UpdateProduct( ShopFragment.Cartlist.get(i).getId_product(),ShopFragment.Cartlist.get(i).getPrice(),ShopFragment.Cartlist.get(i).getAmount());
                        tontaimahang = true;
                    }
                }
                if (tontaimahang == false)
                {
                    MainActivity2.daoCart.InsertData( list.get(j).getId_product(), list.get(j).getProduct_name(),  list.get(j).getTotal_money(), list.get(j).getImage(),list.get(j).getAmount());
                }
            } else
            {
                MainActivity2.daoCart.InsertData( list.get(j).getId_product(), list.get(j).getProduct_name(),  list.get(j).getTotal_money(), list.get(j).getImage(),list.get(j).getAmount());
            }
            Toast.makeText(this, "Thêm thành công.", Toast.LENGTH_SHORT).show();
          updateList();
            }
            Intent intent = new Intent(getApplicationContext(), PayActivity.class);
            intent.putExtra("id_customer",id_customer);
            startActivity(intent);
        });
    }
    private void updateList(){
        ShopFragment.Cartlist= (ArrayList<Cart>)  MainActivity2.daoCart.getall();
        MainActivity2.setBugdeNumber();
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
    private void cancleBill(String id_bill, String cancle){
        tvCountDownTime.setOnClickListener(view -> {
            final ProgressDialog progressDialog = new ProgressDialog(InvoicedetailsActivity.this);
            progressDialog.setMessage("Please Wait..");
            progressDialog.setCancelable(false);
            progressDialog.show();
            ApiProduct apiProduct = ApiService.getService();
            Call<String> callback = apiProduct.canclebill(id_bill, cancle);
            callback.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                    Toast.makeText(InvoicedetailsActivity.this, "Hủy đơn hàng thành công", Toast.LENGTH_SHORT).show();
                    PushNotification();
                    finish();
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(InvoicedetailsActivity.this, "Hủy đơn hàng thất bại", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        });
    }
    private void PushNotification() {
        ApiProduct apiProduct = ApiService.getService();
        Call<String> callback = apiProduct.pushNotification(MainActivity2.token,"2");
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