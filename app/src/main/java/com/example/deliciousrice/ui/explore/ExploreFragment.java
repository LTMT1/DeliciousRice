package com.example.deliciousrice.ui.explore;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliciousrice.Adapter.AdapterSearchProduct;
import com.example.deliciousrice.Api.ApiProduct;
import com.example.deliciousrice.Api.ApiService;
import com.example.deliciousrice.MainActivity2;
import com.example.deliciousrice.Model.Product;
import com.example.deliciousrice.R;
import com.example.deliciousrice.ui.shop.Activity.DetailActivity;
import com.github.ybq.android.spinkit.style.Circle;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExploreFragment extends Fragment {
    private SearchView searchViewProduct;
    private RecyclerView rclSeachsp;
    AdapterSearchProduct adapterSearch;
    ArrayList<Product> searchPro;
    private ProgressBar prgLoadingSearch;
    MainActivity2 main;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explore, container, false);
        searchViewProduct = view.findViewById(R.id.searchView_Product);
        rclSeachsp = view.findViewById(R.id.rcl_seachsp);
        prgLoadingSearch = view.findViewById(R.id.prgLoadingSearch);
        main = (MainActivity2) getActivity();
        prgLoadingSearch.setIndeterminateDrawable(new Circle());
        searchPro = new ArrayList<>();

        searchViewProduct.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchSP(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchSP(newText);
                return false;
            }
        });

        return view;
    }

    private void searchSP(String search) {
        String seach = search;
        searchPro.clear();
        prgLoadingSearch.setVisibility(View.VISIBLE);
        ApiProduct apiProduct = ApiService.getService();
        Call<List<Product>> callback = apiProduct.SeachProduct(seach);
        callback.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(@NonNull Call<List<Product>> call, @NonNull Response<List<Product>> response) {
                prgLoadingSearch.setVisibility(View.GONE);
                searchPro = (ArrayList<Product>) response.body();
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                rclSeachsp.setLayoutManager(linearLayoutManager);
                adapterSearch = new AdapterSearchProduct(searchPro, getContext(),productSearch ->{
                    Intent intent=new Intent(getContext(), DetailActivity.class);
                    intent.putExtra("idcustomer",main.getId_customer());
                    intent.putExtra("getdataproduct",productSearch);
                    startActivity(intent);
                });
                rclSeachsp.setAdapter(adapterSearch);

            }

            @Override
            public void onFailure(@NonNull Call<List<Product>> call, @NonNull Throwable t) {

            }
        });
    }

}