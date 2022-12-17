package com.example.deliciousrice.ui.favorite;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliciousrice.Adapter.AdapterFavorite;
import com.example.deliciousrice.Api.ApiProduct;
import com.example.deliciousrice.Api.ApiService;
import com.example.deliciousrice.MainActivity2;
import com.example.deliciousrice.Model.Product;
import com.example.deliciousrice.R;
import com.example.deliciousrice.ui.shop.Activity.DetailFragment;
import com.github.ybq.android.spinkit.style.Circle;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteFragment extends Fragment {

    private RecyclerView rclviewfavorite;
    AdapterFavorite adapterFavorite;
    MainActivity2 main;
    ConstraintLayout constr;
    private ProgressBar prgLoadingSearch;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        rclviewfavorite = view.findViewById(R.id.rclviewfavorite);
        constr = view.findViewById(R.id.constr);
        prgLoadingSearch = view.findViewById(R.id.prgLoadingSearch);
        prgLoadingSearch.setIndeterminateDrawable(new Circle());
        main = (MainActivity2) getActivity();
        getFavourite();
        return view;
    }

    private void getFavourite() {
        prgLoadingSearch.setVisibility(View.VISIBLE);
        ApiProduct apiProduct = ApiService.getService();
        Call<List<Product>> callback = apiProduct.getListFavorite(main.getId_customer());
        callback.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                prgLoadingSearch.setVisibility(View.GONE);
                ArrayList<Product> mangyeuthich = (ArrayList<Product>) response.body();
                if (mangyeuthich.size() == 0) {
                    constr.setVisibility(View.VISIBLE);
                    rclviewfavorite.setVisibility(View.GONE);
                } else {
                    rclviewfavorite.setVisibility(View.VISIBLE);
                    constr.setVisibility(View.GONE);
                    adapterFavorite = new AdapterFavorite(mangyeuthich, getActivity(), favorite -> {
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        DetailFragment fragment = new DetailFragment();
                        Bundle bundle2 = new Bundle();
                        bundle2.putInt("idcustomer", main.getId_customer());
                        bundle2.putSerializable("getdataproduct", favorite);
                        fragment.setArguments(bundle2);
                        ft.replace(R.id.nav_host_fragment_activity_main2, fragment);
                        ft.commit();
                    });
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    rclviewfavorite.setLayoutManager(linearLayoutManager);
                    rclviewfavorite.setAdapter(adapterFavorite);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
    }
}