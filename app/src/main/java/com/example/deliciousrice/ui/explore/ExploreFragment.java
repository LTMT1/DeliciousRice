package com.example.deliciousrice.ui.explore;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliciousrice.Activity.ForgotPassActivity;
import com.example.deliciousrice.Adapter.AdapterSearchProduct;
import com.example.deliciousrice.Api.ApiProduct;
import com.example.deliciousrice.Api.ApiService;
import com.example.deliciousrice.Model.Product;
import com.example.deliciousrice.R;
import com.example.deliciousrice.databinding.FragmentExploreBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExploreFragment extends Fragment {
    private SearchView searchViewProduct;
    private RecyclerView rclSeachsp;
    AdapterSearchProduct adapterSearch;
    ArrayList<Product> mangyeuthich;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explore, container, false);
        searchViewProduct = view.findViewById(R.id.searchView_Product);
        rclSeachsp = view.findViewById(R.id.rcl_seachsp);
        mangyeuthich=new ArrayList<>();
        searchViewProduct.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Seachsp();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return view;
    }

    private void Seachsp(){
        String seach = searchViewProduct.getQuery().toString().trim();
            mangyeuthich.clear();
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Please Wait..");
            progressDialog.setCancelable(false);
            progressDialog.show();
            ApiProduct apiProduct = ApiService.getService();
            Call<List<Product>> callback = apiProduct.SeachProduct(seach);
            callback.enqueue(new Callback<List<Product>>() {
                @Override
                public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                    progressDialog.dismiss();
                    mangyeuthich = (ArrayList<Product>) response.body();

                    adapterSearch = new AdapterSearchProduct(mangyeuthich, getContext());
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    rclSeachsp.setLayoutManager(linearLayoutManager);
                    rclSeachsp.setAdapter(adapterSearch);

                }

                @Override
                public void onFailure(Call<List<Product>> call, Throwable t) {

                }
            });
        }

}