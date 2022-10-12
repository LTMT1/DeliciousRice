package com.example.deliciousrice.ui.shop;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.example.deliciousrice.Api.ApiProduct;
import com.example.deliciousrice.Model.Product;
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

    RecyclerView recyclerView;
    ApiProduct apiProduct;
    AdapterProduct adapterProduct;

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


        recyclerView = view.findViewById(R.id.rcyShop);

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

        Call<ArrayList<Product>> listCallProduct = apiProduct.getListProduct();
        listCallProduct.enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                ArrayList<Product> productList = new ArrayList<>();
                productList = response.body();

                recyclerView.setHasFixedSize(true);
             //   GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL, false));
                adapterProduct = new AdapterProduct(productList, ShopFragment.this);
                recyclerView.setAdapter(adapterProduct);
            }

            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}