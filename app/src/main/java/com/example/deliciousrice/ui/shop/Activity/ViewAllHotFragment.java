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

import com.example.deliciousrice.Activity.BarColor;
import com.example.deliciousrice.Adapter.AdapterViewAllHot;
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


public class ViewAllHotFragment extends Fragment {

    RecyclerView recyclerViewAllHot;
    AdapterViewAllHot adapterViewAllHot;
    private ImageView imgBackViewAllHot;
    private ProgressBar prgLoadingSearch;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_view_all_hot, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        prgLoadingSearch =view.findViewById(R.id.prgLoadingSearch);
        recyclerViewAllHot = view.findViewById(R.id.rcyViewAllHot);
        imgBackViewAllHot = view.findViewById(R.id.img_back_ViewAllHot);

        prgLoadingSearch.setIndeterminateDrawable(new Circle());

        imgBackViewAllHot.setOnClickListener(view1 -> {
            Navigation.findNavController(view).navigate(R.id.action_viewAllHotFragment_to_shopFragment);
        });
        getAllProductHot();
    }
    private void getAllProductHot(){
        prgLoadingSearch.setVisibility(View.VISIBLE);
        ApiProduct apiProduct = ApiService.getService();
        Call<ArrayList<Product>> listCallProductHot = apiProduct.getListProductHot();
        listCallProductHot.enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                prgLoadingSearch.setVisibility(View.GONE);
                ArrayList<Product> productHots = new ArrayList<>();
                productHots = response.body();
                recyclerViewAllHot.setHasFixedSize(true);
                recyclerViewAllHot.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
                adapterViewAllHot = new AdapterViewAllHot(productHots, ViewAllHotFragment.this, productHot -> {
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    DetailFragment fragment = new DetailFragment();
                    Bundle bundle2 = new Bundle();
                    bundle2.putSerializable("getdataproduct", productHot);
                    fragment.setArguments(bundle2);
                    ft.replace(R.id.nav_host_fragment_activity_main2, fragment);
                    ft.commit();


                });
                recyclerViewAllHot.setAdapter(adapterViewAllHot);
            }

            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {

            }
        });
    }
}