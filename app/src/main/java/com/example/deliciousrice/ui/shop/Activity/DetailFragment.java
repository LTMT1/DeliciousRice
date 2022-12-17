package com.example.deliciousrice.ui.shop.Activity;

import static com.facebook.FacebookSdk.getApplicationContext;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.deliciousrice.Adapter.AdapterProductDetail;
import com.example.deliciousrice.Api.ApiProduct;
import com.example.deliciousrice.Api.ApiService;
import com.example.deliciousrice.MainActivity2;
import com.example.deliciousrice.Model.Cart;
import com.example.deliciousrice.Model.Product;
import com.example.deliciousrice.R;
import com.example.deliciousrice.ui.shop.ShopFragment;
import com.makeramen.roundedimageview.RoundedImageView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailFragment extends Fragment {
    private RoundedImageView roundedImageView;
    private TextView tvnamesp;
    private TextView tvtimesp;
    private TextView tvpricesp;
    private TextView tvnumbersp;
    private ImageView imgtymsp;
    private ImageView imgTru;
    private ImageView imgCong;
    private ImageView tvcuon;
    private TextView tvdetaisp;
    private TextView btnaddcart;
    private RecyclerView rcyDetailProduct;
    AdapterProductDetail adapterProductDetail;
    private ConstraintLayout clBankShop;



    Product product;
    int getId_customer,id_product;
    public static int Click = 0;
    ArrayList<Product> productHots;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Bundle bundle=getArguments();
        getId_customer=bundle.getInt("idcustomer",0);
        product = (Product) bundle.getSerializable("getdataproduct");



        productHots = new ArrayList<>();
        Anhxa(view);
        setviewdata();
        ListProductDetail();
    }
    private void Anhxa(View view){
        roundedImageView = view.findViewById(R.id.roundedImageView);
        rcyDetailProduct = view.findViewById(R.id.rcyDetailProduct);
        tvnamesp = view.findViewById(R.id.tvnamesp);
        tvtimesp = view. findViewById(R.id.tvtimesp);
        tvpricesp= view.findViewById(R.id.tvpricesp);
        tvnumbersp = view.findViewById(R.id.tvnumbersp);
        imgtymsp = view.findViewById(R.id.imgtymsp);
        imgTru = view.findViewById(R.id.img_tru);
        imgCong = view.findViewById(R.id.img_cong);
        tvcuon = view.findViewById(R.id.tvcuon);
        tvdetaisp = view.findViewById(R.id.tvdetaisp);
        btnaddcart = view.findViewById(R.id.btnaddcart);
        id_product=product.getId_product();
        clBankShop = view.findViewById(R.id.cl_bank_shop);
        clBankShop.setOnClickListener(view1 -> {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ShopFragment fragment = new ShopFragment();
            ft.replace(R.id.nav_host_fragment_activity_main2, fragment);
            ft.commit();
        });
    }
    private void setviewdata(){
        MainActivity2.setBugdeNumber();
        Glide.with(this).load(product.getImage()).centerCrop().into(roundedImageView);
        tvnamesp.setText(product.getProduct_name());
        tvtimesp.setText(product.getProcessing_time());

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tvpricesp.setText(decimalFormat.format(product.getPrice())+" đ");


        tvdetaisp.setText(product.getDescription());
        checkYeuThich(getId_customer,product.getId_product());
        imgtymsp.setOnClickListener(view -> {
            onclickfavorite();
        });
        imgTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoi = Integer.parseInt(tvnumbersp.getText().toString().trim()) - 1;
                if(slmoi>0){
                    tvnumbersp.setText(Integer.toString(slmoi));
                }
            }
        });
        imgCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoi = Integer.parseInt(tvnumbersp.getText().toString().trim()) + 1;
                tvnumbersp.setText(Integer.toString(slmoi));
            }
        });
        btnaddcart.setOnClickListener(view -> {
            ShopFragment.Cartlist= (ArrayList<Cart>)MainActivity2.daoCart.getall();
            if (ShopFragment.Cartlist.size() > 0)//gio hang khong rong
            {
                int sl = Integer.parseInt(tvnumbersp.getText().toString().trim());
                boolean tontaimahang = false;
                for (int i = 0; i <  ShopFragment.Cartlist.size(); i++)
                {
                    if (ShopFragment.Cartlist.get(i).getId_product() == id_product)
                    {
                        ShopFragment.Cartlist.get(i).setAmount( ShopFragment.Cartlist.get(i).getAmount() + sl);
                        ShopFragment.Cartlist.get(i).setPrice(product.getPrice() * ShopFragment.Cartlist.get(i).getAmount());
                        UpdateProduct( ShopFragment.Cartlist.get(i).getId_product(),ShopFragment.Cartlist.get(i).getPrice(),ShopFragment.Cartlist.get(i).getAmount());
                        tontaimahang = true;
                    }
                }
                if (tontaimahang == false)
                {
                    int sl1 = Integer.parseInt(tvnumbersp.getText().toString().trim());//lay so luong trong spinner
                    int Tien2 = sl1 * (product.getPrice());
                    MainActivity2.daoCart.InsertData(id_product, product.getProduct_name(), Tien2, product.getImage(), sl1);
                }
            } else
            {
                int sl2 = Integer.parseInt(tvnumbersp.getText().toString().trim());
                int Tien2 = sl2 * (product.getPrice());
                MainActivity2.daoCart.InsertData(id_product, product.getProduct_name(), Tien2, product.getImage(), sl2);
            }
            Toast.makeText(getContext(), "Thêm thành công.", Toast.LENGTH_SHORT).show();
            updateList();
        });
    }
    private void checkYeuThich(int idcus, int idbh) {
        ApiProduct apiProduct = ApiService.getService();
        Call<String> callback = apiProduct.checkFavorite(idcus,idbh);
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String responseBody = response.body();
                if (responseBody.equals("success")) {
                    imgtymsp.setImageResource(R.drawable.ic_baseline_favorite_24);
                } else {
                    imgtymsp.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
    }
    private void onclickfavorite(){
        if (Click != 0) {
            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_timclick);
            imgtymsp.setImageResource(R.drawable.ic_baseline_favorite_24);
            imgtymsp.startAnimation(animation);
            insertFavorite(getId_customer,product.getId_product());
            Click++;
        } else {
            imgtymsp.setImageResource(R.drawable.ic_baseline_favorite_border_24);
            deleteFavorite(getId_customer,product.getId_product());
            Click--;
        }
    }

    private void insertFavorite(int idcustomer, int idproduct) {
        ApiProduct apiProduct = ApiService.getService();
        Call<String> callback = apiProduct.insertfavorite(idcustomer, idproduct);
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
    }
    private void deleteFavorite(int idcustomer, int idproduct) {
        ApiProduct apiProduct = ApiService.getService();
        Call<Product> callback = apiProduct.deletefavorite(idcustomer, idproduct);
        callback.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
            }
        });
    }
    public static void UpdateProduct(int id, int price, int amount) {
        MainActivity2.daoCart.UpdateCart(id, price, amount);
    }
    private void updateList(){
        ShopFragment.Cartlist= (ArrayList<Cart>)  MainActivity2.daoCart.getall();
        MainActivity2.setBugdeNumber();
    }

    private  void ListProductDetail(){
        ApiProduct apiProduct = ApiService.getService();
        Call<ArrayList<Product>> listCallProduct = apiProduct.getListProductHot();
        listCallProduct.enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                //   prgLoadingSearch.setVisibility(View.GONE);
                productHots = response.body();
                rcyDetailProduct.setHasFixedSize(true);
                rcyDetailProduct.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
                adapterProductDetail = new AdapterProductDetail(productHots, DetailFragment.this, productHot -> {
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    DetailFragment fragment = new DetailFragment();
                    Bundle bundle2 = new Bundle();
                    bundle2.putSerializable("getdataproduct", productHot);
                    fragment.setArguments(bundle2);
                    ft.replace(R.id.nav_host_fragment_activity_main2, fragment);
                    ft.commit();

                });
                rcyDetailProduct.setAdapter(adapterProductDetail);
            }

            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {

            }
        });
    }
}