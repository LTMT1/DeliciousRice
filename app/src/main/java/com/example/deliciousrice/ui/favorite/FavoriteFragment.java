package com.example.deliciousrice.ui.favorite;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliciousrice.Adapter.AdapterFavorite;
import com.example.deliciousrice.Api.ApiProduct;
import com.example.deliciousrice.Api.ApiService;
import com.example.deliciousrice.MainActivity2;
import com.example.deliciousrice.Model.Favorite;
import com.example.deliciousrice.R;
import com.example.deliciousrice.databinding.FragmentFavoriteBinding;
import com.example.deliciousrice.ui.shop.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteFragment extends Fragment {

    private RecyclerView rclviewfavorite;

    AdapterFavorite adapterFavorite;
    MainActivity2 main;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        rclviewfavorite = view.findViewById(R.id.rclviewfavorite);
        main = (MainActivity2) getActivity();
        ApiProduct apiProduct = ApiService.getService();
        Call<List<Favorite>> callback = apiProduct.getListFavorite(main.getId_customer());
        callback.enqueue(new Callback<List<Favorite>>() {
            @Override
            public void onResponse(Call<List<Favorite>> call, Response<List<Favorite>> response) {
                ArrayList<Favorite> mangyeuthich = (ArrayList<Favorite>) response.body();
                adapterFavorite = new AdapterFavorite(mangyeuthich, getActivity());
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                rclviewfavorite.setLayoutManager(linearLayoutManager);
                rclviewfavorite.setAdapter(adapterFavorite);
            }

            @Override
            public void onFailure(Call<List<Favorite>> call, Throwable t) {

            }
        });
        return view;
    }

}