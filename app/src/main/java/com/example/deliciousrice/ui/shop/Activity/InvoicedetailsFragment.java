package com.example.deliciousrice.ui.shop.Activity;

import static com.facebook.FacebookSdk.getApplicationContext;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliciousrice.Adapter.AdapterDetailBill;
import com.example.deliciousrice.Api.ApiProduct;
import com.example.deliciousrice.Api.ApiService;
import com.example.deliciousrice.MainActivity2;
import com.example.deliciousrice.Model.Bill;
import com.example.deliciousrice.Model.Cart;
import com.example.deliciousrice.Model.Detailbill;
import com.example.deliciousrice.R;
import com.example.deliciousrice.ui.account.Fragment.ReceipFragment;
import com.example.deliciousrice.ui.shop.ShopFragment;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InvoicedetailsFragment extends Fragment {

    private TextView tvMaBill, tvNameKH, tvPhoneKH, tvDiaChi, tvNameNV, tvDateDat, tvTongTien, tvSoMon, tvDatLai, tvCountDownTime, tvHoanTat;
    private RecyclerView rcyViewDetailReceipt;
    private TextView tvshipkm, tvTongtienBill;
    private TextView tvkhuyenmai, textVieưgone;
    private ImageView imgBackInvoicedetails;


    AdapterDetailBill adapterDetailBill;
    Bill bill;
    String Name, SDT;
    int Id_cus;
    ArrayList<Detailbill> detailbillArrayList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_invoicedetails, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


       /* Intent intent = getIntent();
        SDT=intent.getStringExtra("phone");
        Name = intent.getStringExtra("name");
        bill = (Bill) intent.getSerializableExtra("getData");*/
        Bundle bundle = getArguments();
        Id_cus = bundle.getInt("id_cus");
        SDT = bundle.getString("phone");
        Name = bundle.getString("name");
        bill = (Bill) bundle.getSerializable("getData");
        Log.e(Name,"ssssssssssssssss"+SDT);

        Anhxa(view);
        setData();
        getDataDetailBill();
        Log.e(bill.getPayment().trim() + "", bill.getPayment().trim());
        if (bill.getStatus().trim().equals("Đang chờ") && bill.getPayment().trim().equals("1")) {
            tvCountDownTime.setVisibility(View.GONE);
            tvHoanTat.setVisibility(View.GONE);
        } else if (bill.getStatus().trim().equals("Đang giao hàng")) {
            completedBill(bill.getId_bill(), "Hoàn tất");
            tvCountDownTime.setVisibility(View.GONE);
            tvHoanTat.setVisibility(View.VISIBLE);
        } else if (bill.getStatus().trim().equals("Đang chờ")) {
            tvCountDownTime.setVisibility(View.VISIBLE);
            cancleBill(bill.getId_bill(), "Đã Hủy");
            tvHoanTat.setVisibility(View.GONE);
        } else {
            tvCountDownTime.setVisibility(View.GONE);
            tvHoanTat.setVisibility(View.GONE);
        }
    }

    private void Anhxa(View view) {

        tvCountDownTime = view.findViewById(R.id.tvCountDownTime);
        tvMaBill = view.findViewById(R.id.tvMaDonHang);
        tvNameKH = view.findViewById(R.id.tvNameKH);
        tvPhoneKH = view.findViewById(R.id.tvPhoneKH);
        tvNameNV = view.findViewById(R.id.tvNameNV);
        tvDiaChi = view.findViewById(R.id.tvDiaChi);
        tvDateDat = view.findViewById(R.id.tvDateDat);
        rcyViewDetailReceipt = view.findViewById(R.id.rcyViewChiTietDH);
        tvTongTien = view.findViewById(R.id.tvTongTien);
        tvSoMon = view.findViewById(R.id.tvSoMon);
        tvDatLai = view.findViewById(R.id.tvDatlai);
        tvshipkm = view.findViewById(R.id.tvshipkm);
        tvkhuyenmai = view.findViewById(R.id.tvkhuyenmai);
        tvTongtienBill = view.findViewById(R.id.tvTongtien);
        textVieưgone = view.findViewById(R.id.textView63);
        tvHoanTat = view.findViewById(R.id.tvHoanTat);
        imgBackInvoicedetails = view.findViewById(R.id.img_back_Invoicedetails);
        imgBackInvoicedetails.setOnClickListener(view1 -> {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ReceipFragment fragment = new ReceipFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("getData", bill);
            bundle.putInt("id_cus", Id_cus);
            bundle.putString("name", Name);
            bundle.putString("phone", SDT);
            fragment.setArguments(bundle);
            ft.replace(R.id.nav_host_fragment_activity_main2, fragment);
            ft.commit();
        });

    }

    private void setData() {
        tvMaBill.setText("DCR" + bill.getId_bill());
        tvNameKH.setText(Name);
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
        tvDateDat.setText(sdf1.format(bill.getDate()));
        tvPhoneKH.setText(SDT);
    }

    private void getDataDetailBill() {
        try {
            ApiProduct apiProduct = ApiService.getService();
            Call<List<Detailbill>> listCallProductBill = apiProduct.getProductBill(bill.getId_customer(), bill.getId_bill());
            listCallProductBill.enqueue(new Callback<List<Detailbill>>() {
                public void onResponse(Call<List<Detailbill>> call, Response<List<Detailbill>> response) {
                    detailbillArrayList = (ArrayList<Detailbill>) response.body();
                    Detailbill detailbill = detailbillArrayList.get(0);
                    int tongslproduct = 0, priceproduct = 0;
                    for (int i = 0; i < detailbillArrayList.size(); i++) {
                        tongslproduct = detailbillArrayList.get(i).getAmount() + tongslproduct;
                        priceproduct = detailbillArrayList.get(i).getTotal_money() + priceproduct;
                    }
                    int id_customer = detailbill.getId_customer();
                    tvDiaChi.setText(detailbill.getAddress());
                    tvNameNV.setText(detailbill.getUser_namenv());
                    datLaiOnClick(detailbillArrayList, id_customer);
                    Khuyenmai(priceproduct, tongslproduct);
                    adapterDetailBill = new AdapterDetailBill(detailbillArrayList, getApplicationContext());
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    rcyViewDetailReceipt.setLayoutManager(linearLayoutManager);
                    rcyViewDetailReceipt.setAdapter(adapterDetailBill);
                    adapterDetailBill.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<List<Detailbill>> call, Throwable t) {

                }
            });
        } catch (Exception e) {

        }
    }

    private void datLaiOnClick(ArrayList<Detailbill> list, int id_customer) {
        tvDatLai.setOnClickListener(view -> {
            for (int j = 0; j < list.size(); j++) {
                ShopFragment.Cartlist = (ArrayList<Cart>) MainActivity2.daoCart.getall();
                if (ShopFragment.Cartlist.size() > 0)//gio hang khong rong
                {
                    boolean tontaimahang = false;
                    for (int i = 0; i < ShopFragment.Cartlist.size(); i++) {
                        if (ShopFragment.Cartlist.get(i).getId_product() == list.get(j).getId_product()) {
                            ShopFragment.Cartlist.get(i).setAmount(ShopFragment.Cartlist.get(i).getAmount() + list.get(j).getAmount());
                            ShopFragment.Cartlist.get(i).setPrice(list.get(j).getTotal_money() + ShopFragment.Cartlist.get(i).getPrice());
                            DetailFragment.UpdateProduct(ShopFragment.Cartlist.get(i).getId_product(), ShopFragment.Cartlist.get(i).getPrice(), ShopFragment.Cartlist.get(i).getAmount());
                            tontaimahang = true;
                        }
                    }
                    if (tontaimahang == false) {
                        MainActivity2.daoCart.InsertData(list.get(j).getId_product(), list.get(j).getProduct_name(), list.get(j).getTotal_money(), list.get(j).getImage(), list.get(j).getAmount());
                    }
                } else {
                    MainActivity2.daoCart.InsertData(list.get(j).getId_product(), list.get(j).getProduct_name(), list.get(j).getTotal_money(), list.get(j).getImage(), list.get(j).getAmount());
                }
                Toast.makeText(getContext(), "Thêm thành công.", Toast.LENGTH_SHORT).show();
                updateList();
            }
            Intent intent = new Intent(getApplicationContext(), PayActivity.class);
            intent.putExtra("id_customer", id_customer);
            startActivity(intent);
        });
    }

    private void updateList() {
        ShopFragment.Cartlist = (ArrayList<Cart>) MainActivity2.daoCart.getall();
        MainActivity2.setBugdeNumber();
    }

    private void Khuyenmai(int priceproduct, int tongslproduct) {
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tvTongTien.setText(decimalFormat.format(priceproduct) + "đ");
        if (tongslproduct > 3) {
            textVieưgone.setVisibility(View.VISIBLE);
            tvkhuyenmai.setVisibility(View.VISIBLE);
            double khuyenmai = priceproduct * 0.15;
            tvkhuyenmai.setText(decimalFormat.format(khuyenmai) + "đ");
            double tongtiensp = priceproduct - khuyenmai + 20000;
            tvTongtienBill.setText(decimalFormat.format(tongtiensp) + "đ");
        } else {
            textVieưgone.setVisibility(View.GONE);
            tvkhuyenmai.setVisibility(View.GONE);
            double tongtiensp = priceproduct + 20000;
            tvTongtienBill.setText(decimalFormat.format(tongtiensp) + "đ");
        }
        tvSoMon.setText("Tổng số(" + tongslproduct + " món)");
    }

    private void cancleBill(String id_bill, String cancle) {
        tvCountDownTime.setOnClickListener(view -> {
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Please Wait..");
            progressDialog.setCancelable(false);
            progressDialog.show();
            ApiProduct apiProduct = ApiService.getService();
            Call<String> callback = apiProduct.canclebill(id_bill, cancle);
            callback.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Toast.makeText(getApplicationContext(), "Hủy đơn hàng thành công", Toast.LENGTH_SHORT).show();
                    PushNotification(MainActivity2.token, "2");
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();

                    ReceipFragment fragment = new ReceipFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("getData", bill);
                    bundle.putInt("id_cus", Id_cus);
                    bundle.putString("name", Name);
                    bundle.putString("phone", SDT);
                    fragment.setArguments(bundle);

                    ft.replace(R.id.nav_host_fragment_activity_main2, fragment);
                    ft.commit();
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Hủy đơn hàng thất bại", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        });
    }

    private void completedBill(String id_bill, String completed) {
        tvHoanTat.setOnClickListener(view -> {
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Please Wait..");
            progressDialog.setCancelable(false);
            progressDialog.show();
            ApiProduct apiProduct = ApiService.getService();
            Call<String> callback = apiProduct.canclebill(id_bill, completed);
            callback.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Toast.makeText(getApplicationContext(), "Hoàn tất đơn hàng thành công", Toast.LENGTH_SHORT).show();
                    PushNotification(MainActivity2.token, "3");
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();

                    ReceipFragment fragment = new ReceipFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("getData", bill);
                    bundle.putInt("id_cus", Id_cus);
                    bundle.putString("name", Name);
                    bundle.putString("phone", SDT);
                    fragment.setArguments(bundle);

                    ft.replace(R.id.nav_host_fragment_activity_main2, fragment);
                    ft.commit();

                    progressDialog.dismiss();
                }


                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Hoàn tất đơn hàng thất bại", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        });
    }

    private void PushNotification(String token, String number) {
        ApiProduct apiProduct = ApiService.getService();
        Call<String> callback = apiProduct.pushNotification(token, number);
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