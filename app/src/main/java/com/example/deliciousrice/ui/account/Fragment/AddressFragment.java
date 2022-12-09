package com.example.deliciousrice.ui.account.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliciousrice.Adapter.AdapterAddress;
import com.example.deliciousrice.Api.ApiProduct;
import com.example.deliciousrice.Api.ApiService;
import com.example.deliciousrice.Model.Adderss;
import com.example.deliciousrice.R;
import com.example.deliciousrice.ui.account.AccountFragment;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddressFragment extends Fragment {

    private ConstraintLayout cl_insertAdsress;
    private RecyclerView rclAddress;
    AdapterAddress adapterAddress;
    int idadr;
    private ImageView imgBackAddress;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_address, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cl_insertAdsress = view.findViewById(R.id.cl_insertAdsress);
        rclAddress = view.findViewById(R.id.rcl_address);
        imgBackAddress = view.findViewById(R.id.img_back_Address);
        imgBackAddress.setOnClickListener(v -> {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            AccountFragment fragment = new AccountFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("Adrress", idadr);
            fragment.setArguments(bundle);

            ft.replace(R.id.nav_host_fragment_activity_main2, fragment);
            ft.commit();
        });


        Bundle bundle = getArguments();
        idadr = bundle.getInt("Adrress", 0);

        cl_insertAdsress.setOnClickListener(v -> {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            AddAddressFragment fragment = new AddAddressFragment();
            Bundle bundle1 = new Bundle();
            bundle1.putInt("iccome", idadr);
            fragment.setArguments(bundle1);
            ft.replace(R.id.nav_host_fragment_activity_main2, fragment);
            ft.commit();

        });
        ShowAddress();


    }

    private void ShowAddress() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();


        ApiProduct apiProduct = ApiService.getService();
        Call<ArrayList<Adderss>> listAddre = apiProduct.getListAddresss(idadr);
        listAddre.enqueue(new Callback<ArrayList<Adderss>>() {
            @Override
            public void onResponse(Call<ArrayList<Adderss>> call, Response<ArrayList<Adderss>> response) {
                ArrayList<Adderss> addersses = new ArrayList<>();
                progressDialog.dismiss();
                addersses = response.body();
                rclAddress.setHasFixedSize(true);
                rclAddress.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));


                adapterAddress = new AdapterAddress(addersses, AddressFragment.this, addressNew -> {
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    EditAddressFragment fragment = new EditAddressFragment();
                    Bundle bundle2 = new Bundle();
                    bundle2.putInt("iccome", idadr);
                    bundle2.putSerializable("getdataAddress", addressNew);

                    fragment.setArguments(bundle2);
                    ft.replace(R.id.nav_host_fragment_activity_main2, fragment);
                    ft.commit();


                });
                rclAddress.setAdapter(adapterAddress);

            }

            @Override
            public void onFailure(Call<ArrayList<Adderss>> call, Throwable t) {

            }
        });


    }
}