package com.example.deliciousrice.ui.shop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.deliciousrice.Adapter.AdapterProduct;
import com.example.deliciousrice.Adapter.AdapterProductHot;
import com.example.deliciousrice.Adapter.AdapterProductNew;
import com.example.deliciousrice.Api.ApiProduct;
import com.example.deliciousrice.Api.ApiService;
import com.example.deliciousrice.MainActivity2;
import com.example.deliciousrice.Model.Cart;
import com.example.deliciousrice.Model.Product;
import com.example.deliciousrice.Model.ProductHot;
import com.example.deliciousrice.Model.ProductNew;
import com.example.deliciousrice.R;
import com.example.deliciousrice.databinding.FragmentShopBinding;
import com.example.deliciousrice.ui.shop.Activity.DetailActivity;
import com.example.deliciousrice.ui.shop.Activity.ViewAllCboActivity;
import com.example.deliciousrice.ui.shop.Activity.ViewAllHotActivity;
import com.example.deliciousrice.ui.shop.Activity.ViewAllNewActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShopFragment extends Fragment {
    private ImageSlider imgSilde;

    RecyclerView recyclerView,recyclerViewNew, recyclerViewHot;
    TextView tvViewAllCbo,tvViewAllNew,tvViewAllHot;
    AdapterProduct adapterProduct;
    AdapterProductNew adapterProductNew;
    AdapterProductHot adapterProductHot;
    MainActivity2 main;

    public static ArrayList<Cart> Cartlist;
    private FragmentShopBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imgSilde = view.findViewById(R.id.imgSilde);
        main = (MainActivity2) getActivity();
        ArrayList<SlideModel> models=new ArrayList<>();
        models.add(new SlideModel(R.drawable.img_silde1,null));
        models.add(new SlideModel(R.drawable.img_silde2,null));
        models.add(new SlideModel(R.drawable.img_silde3,null));
        models.add(new SlideModel(R.drawable.img_silde4,null));
        models.add(new SlideModel(R.drawable.img_silde5,null));
        imgSilde.setImageList(models, ScaleTypes.CENTER_CROP);


        recyclerView = view.findViewById(R.id.rcyProductCombo);
        recyclerViewNew = view.findViewById(R.id.rcyProductNew);
        recyclerViewHot = view.findViewById(R.id.rcyProductHot);
        tvViewAllCbo = view.findViewById(R.id.tvViewAllCbo);
        tvViewAllNew = view.findViewById(R.id.tvViewAllNew);
        tvViewAllHot = view.findViewById(R.id.tvViewAllHot);

        tvViewAllCbo.setOnClickListener(view1 -> {
            Intent intent = new Intent(getContext(), ViewAllCboActivity.class);
            startActivity(intent);
        });

        tvViewAllNew.setOnClickListener(view1 -> {
            Intent intent = new Intent(getContext(), ViewAllNewActivity.class);
            startActivity(intent);
        });

        tvViewAllHot.setOnClickListener(view1 -> {
            Intent intent = new Intent(getContext(), ViewAllHotActivity.class);
            startActivity(intent);
        });
        CallApi_Combo();
        CallApi_New();
        CallApi_Hot();
        if (Cartlist != null) {

        } else {
            Cartlist = new ArrayList<>();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void CallApi_Combo(){
        ApiProduct apiProduct = ApiService.getService();
        Call<ArrayList<Product>> listCallProduct = apiProduct.getListProduct();
        listCallProduct.enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                ArrayList<Product> productList = new ArrayList<>();
                productList = response.body();
                recyclerView.setHasFixedSize(true);
                //   GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL, false));
                adapterProduct = new AdapterProduct(productList, ShopFragment.this,product -> {
                    Intent intent=new Intent(getContext(), DetailActivity.class);
                    intent.putExtra("idcustomer",main.getId_customer());
                    intent.putExtra("getdataproduct",product);
                    startActivity(intent);
                });
                recyclerView.setAdapter(adapterProduct);
            }

            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {

            }
        });
    }

    private void CallApi_New(){
        ApiProduct apiProduct = ApiService.getService();
        Call<ArrayList<Product>> listCallProductNew = apiProduct.getListProductNew();
        listCallProductNew.enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                ArrayList<Product>  productNews = new ArrayList<>();
                productNews = response.body();
                recyclerViewNew.setHasFixedSize(true);
                recyclerViewNew.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL, false));

                adapterProductNew = new AdapterProductNew(productNews, ShopFragment.this, productNew -> {
                    Intent intent=new Intent(getContext(), DetailActivity.class);
                    intent.putExtra("idcustomer",main.getId_customer());
                    intent.putExtra("getdataproduct",productNew);
                    startActivity(intent);
                });
                recyclerViewNew.setAdapter(adapterProductNew);

            }

            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {

            }
        });
    }

    private void CallApi_Hot(){
        ApiProduct apiProduct = ApiService.getService();
        Call<ArrayList<Product>> listCallProductHot = apiProduct.getListProductHot();
        listCallProductHot.enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                ArrayList<Product> productHots = new ArrayList<>();
                productHots = response.body();
                recyclerViewHot.setHasFixedSize(true);
                recyclerViewHot.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL, false));

                adapterProductHot = new AdapterProductHot(productHots, ShopFragment.this, productHot -> {
                    Intent intent=new Intent(getContext(), DetailActivity.class);
                    intent.putExtra("idcustomer",main.getId_customer());
                    intent.putExtra("getdataproduct",productHot);
                    startActivity(intent);
                });
                recyclerViewHot.setAdapter(adapterProductHot);
            }

            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {

            }
        });
    }
}