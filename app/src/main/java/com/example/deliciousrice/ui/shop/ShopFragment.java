package com.example.deliciousrice.ui.shop;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.deliciousrice.Model.Product;
import com.example.deliciousrice.Model.ProductHot;
import com.example.deliciousrice.Model.ProductNew;
import com.example.deliciousrice.R;
import com.example.deliciousrice.databinding.FragmentShopBinding;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
    ApiProduct apiProduct;
    AdapterProduct adapterProduct;
    AdapterProductNew adapterProductNew;
    AdapterProductHot adapterProductHot;

    private FragmentShopBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentShopBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imgSilde = view.findViewById(R.id.imgSilde);
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
        Gson gson = new GsonBuilder().serializeNulls().create();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://appsellrice.000webhostapp.com/Deliciousrice/API/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();

        apiProduct = retrofit.create(ApiProduct.class);
        CallApi_Combo();
        CallApi_New();
        CallApi_Hot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void CallApi_Combo(){
        Call<ArrayList<Product>> listCallProduct = apiProduct.getListProduct();
        listCallProduct.enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                ArrayList<Product> productList = new ArrayList<>();
                productList = response.body();
                recyclerView.setHasFixedSize(true);
                //   GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL, false));
                adapterProduct = new AdapterProduct(productList, ShopFragment.this, product -> {
                    Toast.makeText(getContext(), "product: " + product.getProduct_name(), Toast.LENGTH_SHORT).show();
                });
                recyclerView.setAdapter(adapterProduct);
            }

            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {

            }
        });
    }

    private void CallApi_New(){
        Call<ArrayList<ProductNew>> listCallProductNew = apiProduct.getListProductNew();
        listCallProductNew.enqueue(new Callback<ArrayList<ProductNew>>() {
            @Override
            public void onResponse(Call<ArrayList<ProductNew>> call, Response<ArrayList<ProductNew>> response) {
                ArrayList<ProductNew>  productNews = new ArrayList<>();
                productNews = response.body();
                recyclerViewNew.setHasFixedSize(true);
                recyclerViewNew.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL, false));
                adapterProductNew = new AdapterProductNew(productNews, ShopFragment.this, productNew -> {
                    Toast.makeText(getContext(), "product: " + productNew.getProduct_name_new(), Toast.LENGTH_SHORT).show();
                });
                recyclerViewNew.setAdapter(adapterProductNew);
            }

            @Override
            public void onFailure(Call<ArrayList<ProductNew>> call, Throwable t) {

            }
        });
    }

    private void CallApi_Hot(){
        Call<ArrayList<ProductHot>> listCallProductHot = apiProduct.getListProductHot();
        listCallProductHot.enqueue(new Callback<ArrayList<ProductHot>>() {
            @Override
            public void onResponse(Call<ArrayList<ProductHot>> call, Response<ArrayList<ProductHot>> response) {
                ArrayList<ProductHot> productHots = new ArrayList<>();
                productHots = response.body();
                recyclerViewHot.setHasFixedSize(true);
                recyclerViewHot.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL, false));
                adapterProductHot = new AdapterProductHot(productHots, ShopFragment.this, productHot -> {
                    Toast.makeText(getContext(), "product: " + productHot.getProduct_name_hot(), Toast.LENGTH_SHORT).show();
                });
                recyclerViewHot.setAdapter(adapterProduct);
            }

            @Override
            public void onFailure(Call<ArrayList<ProductHot>> call, Throwable t) {

            }
        });
    }
}