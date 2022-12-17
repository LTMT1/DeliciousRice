package com.example.deliciousrice.ui.shop;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
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
import com.example.deliciousrice.R;
import com.example.deliciousrice.databinding.FragmentShopBinding;
import com.example.deliciousrice.ui.shop.Activity.DetailFragment;
import com.github.ybq.android.spinkit.style.Wave;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShopFragment extends Fragment {
    private ImageSlider imgSilde, imgSilde2, imgSilde3;
    RecyclerView recyclerView, recyclerViewNew, recyclerViewHot;
    TextView tvViewAllCbo, tvViewAllNew, tvViewAllHot;
    AdapterProduct adapterProduct;
    AdapterProductNew adapterProductNew;
    AdapterProductHot adapterProductHot;
    MainActivity2 main;


    private ProgressBar prb1, prb2, prb3;
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
        anhxaID(view);
        Slidershow();

        main = (MainActivity2) getActivity();


        tvViewAllCbo.setOnClickListener(view1 -> {
            Navigation.findNavController(view).navigate(R.id.action_shopFragment_to_viewAllCboFragment);
        });

        tvViewAllNew.setOnClickListener(view1 -> {
            Navigation.findNavController(view).navigate(R.id.action_shopFragment_to_viewAllNewFragment);
        });

        tvViewAllHot.setOnClickListener(view1 -> {
            Navigation.findNavController(view).navigate(R.id.action_shopFragment_to_viewAllHotFragment);
        });

        prb1.setIndeterminateDrawable(new Wave());
        prb2.setIndeterminateDrawable(new Wave());
        prb3.setIndeterminateDrawable(new Wave());
        CallApi_Combo();
        CallApi_New();
        CallApi_Hot();
        if (Cartlist != null) {
        } else {
            Cartlist = new ArrayList<>();
        }
    }

    private void Slidershow() {
        ArrayList<SlideModel> models = new ArrayList<>();
        models.add(new SlideModel(R.drawable.img_banner2, null));
        models.add(new SlideModel(R.drawable.img_banner_3, null));
        models.add(new SlideModel(R.drawable.img_banner2_2, null));
        models.add(new SlideModel(R.drawable.img_final_banner2, null));
        imgSilde.setImageList(models, ScaleTypes.CENTER_CROP);


        ArrayList<SlideModel> models2 = new ArrayList<>();
        models2.add(new SlideModel(R.drawable.img_silde1, null));
        models2.add(new SlideModel(R.drawable.img_banner_4, null));
        models2.add(new SlideModel(R.drawable.im_banner_6, null));
        imgSilde2.setImageList(models2, ScaleTypes.CENTER_CROP);

        ArrayList<SlideModel> models3 = new ArrayList<>();
        models3.add(new SlideModel(R.drawable.img_sale_1, null));
        models3.add(new SlideModel(R.drawable.img_sale_2, null));

        imgSilde3.setImageList(models3, ScaleTypes.CENTER_CROP);

    }

    private void anhxaID(View view) {
        imgSilde = view.findViewById(R.id.imgSilde);
        imgSilde2 = view.findViewById(R.id.imgSilde2);
        imgSilde3 = view.findViewById(R.id.imgSilde3);
        recyclerView = view.findViewById(R.id.rcyProductCombo);
        recyclerViewNew = view.findViewById(R.id.rcyProductNew);
        recyclerViewHot = view.findViewById(R.id.rcyProductHot);
        tvViewAllCbo = view.findViewById(R.id.tvViewAllCbo);
        tvViewAllNew = view.findViewById(R.id.tvViewAllNew);
        tvViewAllHot = view.findViewById(R.id.tvViewAllHot);
        prb1 = view.findViewById(R.id.prgLoadingPrCombo);
        prb2 = view.findViewById(R.id.prgLoadingPrNew);
        prb3 = view.findViewById(R.id.prgLoadingPrHot);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void CallApi_Combo() {
        ApiProduct apiProduct = ApiService.getService();
        Call<ArrayList<Product>> listCallProduct = apiProduct.getListProduct();
        listCallProduct.enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                prb1.setVisibility(View.GONE);
                ArrayList<Product> productList = new ArrayList<>();
                productList = response.body();
                recyclerView.setHasFixedSize(true);
                //   GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
                adapterProduct = new AdapterProduct(productList, ShopFragment.this, product -> {

                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    DetailFragment fragment = new DetailFragment();
                    Bundle bundle2 = new Bundle();
                    bundle2.putInt("idcustomer",  main.getId_customer());
                    bundle2.putSerializable("getdataproduct", product);
                    fragment.setArguments(bundle2);
                    ft.replace(R.id.nav_host_fragment_activity_main2, fragment);
                    ft.commit();

                });
                recyclerView.setAdapter(adapterProduct);
            }

            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {

            }
        });
    }

    private void CallApi_New() {
        ApiProduct apiProduct = ApiService.getService();
        Call<ArrayList<Product>> listCallProductNew = apiProduct.getListProductNew();
        listCallProductNew.enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                prb2.setVisibility(View.GONE);
                ArrayList<Product> productNews = new ArrayList<>();
                productNews = response.body();
                recyclerViewNew.setHasFixedSize(true);
                recyclerViewNew.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));

                adapterProductNew = new AdapterProductNew(productNews, ShopFragment.this, productNew -> {

                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    DetailFragment fragment = new DetailFragment();
                    Bundle bundle2 = new Bundle();
                    bundle2.putInt("idcustomer",  main.getId_customer());
                    bundle2.putSerializable("getdataproduct", productNew);
                    fragment.setArguments(bundle2);
                    ft.replace(R.id.nav_host_fragment_activity_main2, fragment);
                    ft.commit();




                });
                recyclerViewNew.setAdapter(adapterProductNew);

            }

            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {

            }
        });
    }

    private void CallApi_Hot() {
        ApiProduct apiProduct = ApiService.getService();
        Call<ArrayList<Product>> listCallProductHot = apiProduct.getListProductHot();
        listCallProductHot.enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                prb3.setVisibility(View.GONE);
                ArrayList<Product> productHots = new ArrayList<>();
                productHots = response.body();
                recyclerViewHot.setHasFixedSize(true);
                recyclerViewHot.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

                adapterProductHot = new AdapterProductHot(productHots, ShopFragment.this, productHot -> {
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    DetailFragment fragment = new DetailFragment();
                    Bundle bundle2 = new Bundle();
                    bundle2.putInt("idcustomer",  main.getId_customer());
                    bundle2.putSerializable("getdataproduct", productHot);
                    fragment.setArguments(bundle2);
                    ft.replace(R.id.nav_host_fragment_activity_main2, fragment);
                    ft.commit();


                });
                recyclerViewHot.setAdapter(adapterProductHot);
            }

            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {

            }
        });
    }
}