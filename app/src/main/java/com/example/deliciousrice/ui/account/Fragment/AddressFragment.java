package com.example.deliciousrice.ui.account.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.deliciousrice.Adapter.AdapterAddress;
import com.example.deliciousrice.Api.ApiProduct;
import com.example.deliciousrice.Api.ApiService;
import com.example.deliciousrice.Model.Adderss;
import com.example.deliciousrice.R;
import com.example.deliciousrice.ui.account.AccountFragment;
import com.example.deliciousrice.ui.account.Activity.AddAddressActivity;
import com.example.deliciousrice.ui.account.Activity.AddressActivity;
import com.example.deliciousrice.ui.account.Activity.EditAddressActivity;

import java.util.ArrayList;
import java.util.List;

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
        imgBackAddress.setOnClickListener(v->{
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            AccountFragment fragment=new AccountFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("Adrress",idadr);
            fragment.setArguments(bundle);

            ft.replace(R.id.nav_host_fragment_activity_main2, fragment);
            ft.commit();
        });


        Bundle bundle=getArguments();
        idadr =bundle.getInt("Adrress", 0);
        Log.e("alo id", String.valueOf(idadr));

        cl_insertAdsress.setOnClickListener(v -> {
            Intent i=new Intent(getContext(), AddAddressActivity.class);
            i.putExtra("iccome",idadr);
            startActivity(i);

        });
        ShowAddress();


    }
    private void ShowAddress() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();


        ApiProduct apiProduct= ApiService.getService();
        Call<ArrayList<Adderss>> listAddre = apiProduct.getListAddresss(idadr);
        listAddre.enqueue(new Callback<ArrayList<Adderss>>() {
            @Override
            public void onResponse(Call<ArrayList<Adderss>> call, Response<ArrayList<Adderss>> response) {
                ArrayList<Adderss> addersses = new ArrayList<>();
                progressDialog.dismiss();
                addersses =  response.body();
                rclAddress.setHasFixedSize(true);
                rclAddress.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL, false));


                adapterAddress = new AdapterAddress(addersses, AddressFragment.this, addressNew -> {

                    Intent intent=new Intent(getContext(), EditAddressActivity.class);
                    intent.putExtra("idcustomer",idadr);
                    intent.putExtra("getdataAddress", addressNew);
                    startActivity(intent);

                });
                rclAddress.setAdapter(adapterAddress);

            }

            @Override
            public void onFailure(Call<ArrayList<Adderss>> call, Throwable t) {

            }
        });


    }
}