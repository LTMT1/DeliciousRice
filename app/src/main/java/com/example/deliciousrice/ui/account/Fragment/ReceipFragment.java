package com.example.deliciousrice.ui.account.Fragment;

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

import com.example.deliciousrice.Adapter.AdapterHistoryBill;
import com.example.deliciousrice.Api.ApiProduct;
import com.example.deliciousrice.Api.ApiService;
import com.example.deliciousrice.Model.Bill;
import com.example.deliciousrice.Model.Customer;
import com.example.deliciousrice.R;
import com.example.deliciousrice.ui.account.AccountFragment;
import com.example.deliciousrice.ui.shop.Activity.InvoicedetailsActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ReceipFragment extends Fragment {
    private ImageView imgBackReceipt;
    private RecyclerView rclview;
    Customer customer;
    AdapterHistoryBill adapterHistoryBill;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_receip, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imgBackReceipt = view.findViewById(R.id.img_back_Receipt);
        imgBackReceipt.setOnClickListener(v->{

            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();

            AccountFragment fragment = new AccountFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("id_cus", 0);
            fragment.setArguments(bundle);

            ft.replace(R.id.nav_host_fragment_activity_main2, fragment);
            ft.commit();
        });

        rclview = view.findViewById(R.id.rclview);
        getreceipt();
    }


    private void getreceipt(){
        Bundle bundle=getArguments();
        int idcustm =bundle.getInt("id_cus",0);

        ApiProduct apiProduct = ApiService.getService();
        Call<List<Bill>> callback = apiProduct.getListbill(idcustm);
        callback.enqueue(new Callback<List<Bill>>() {
            @Override
            public void onResponse(Call<List<Bill>> call, Response<List<Bill>> response) {
                ArrayList<Bill> bills = (ArrayList<Bill>) response.body();
                adapterHistoryBill = new AdapterHistoryBill(bills, getApplicationContext(), bill -> {
                    Intent intent = new Intent(getApplicationContext(), InvoicedetailsActivity.class);
                    intent.putExtra("id_bill",bill.getId_bill());
                    intent.putExtra("getData",bill);
                    startActivity(intent);
                });
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                rclview.setLayoutManager(linearLayoutManager);
                rclview.setAdapter(adapterHistoryBill);
            }

            @Override
            public void onFailure(Call<List<Bill>> call, Throwable t) {

            }
        });
    }


}