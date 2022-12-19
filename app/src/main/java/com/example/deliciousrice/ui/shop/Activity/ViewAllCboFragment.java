package com.example.deliciousrice.ui.shop.Activity;

import static com.facebook.FacebookSdk.getApplicationContext;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.deliciousrice.Adapter.AdapterViewAllCombo;
import com.example.deliciousrice.Api.ApiProduct;
import com.example.deliciousrice.Api.ApiService;
import com.example.deliciousrice.Model.Product;
import com.example.deliciousrice.R;
import com.example.deliciousrice.ui.shop.ShopFragment;
import com.github.ybq.android.spinkit.style.Circle;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ViewAllCboFragment extends Fragment {

    RecyclerView recyclerViewAll;
    AdapterViewAllCombo adapterViewAllCombo;
    private ImageView imgBackViewAllCbo;
    private ProgressBar prgLoadingSearch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_all_cbo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prgLoadingSearch = view.findViewById(R.id.prgLoadingSearch);
        imgBackViewAllCbo = view.findViewById(R.id.img_back_ViewAllCbo);
        prgLoadingSearch.setIndeterminateDrawable(new Circle());
        imgBackViewAllCbo.setOnClickListener(view1 -> {
            Navigation.findNavController(view).navigate(R.id.action_viewAllCboFragment_to_shopFragment);
        });
        recyclerViewAll = view.findViewById(R.id.rcyViewAll);
        getAllProductCbo();
    }
    private void getAllProductCbo(){
        prgLoadingSearch.setVisibility(View.VISIBLE);
        ApiProduct apiProduct = ApiService.getService();
        Call<ArrayList<Product>> listCallProduct = apiProduct.getListProduct();
        listCallProduct.enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                prgLoadingSearch.setVisibility(View.GONE);
                ArrayList<Product> productList = new ArrayList<>();
                productList = response.body();
                recyclerViewAll.setHasFixedSize(true);
                recyclerViewAll.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
                adapterViewAllCombo = new AdapterViewAllCombo(productList, ViewAllCboFragment.this, product -> {
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    DetailFragment fragment = new DetailFragment();
                    Bundle bundle2 = new Bundle();
                    bundle2.putSerializable("getdataproduct", product);
                    fragment.setArguments(bundle2);
                    ft.replace(R.id.nav_host_fragment_activity_main2, fragment);
                    ft.commit();
                });
                recyclerViewAll.setAdapter(adapterViewAllCombo);
            }

            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {

            }
        });
    }
}