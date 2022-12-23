package com.example.deliciousrice.ui.account.Fragment;

import static com.facebook.FacebookSdk.getApplicationContext;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliciousrice.Adapter.AdapterHistoryBill;
import com.example.deliciousrice.Api.ApiProduct;
import com.example.deliciousrice.Api.ApiService;
import com.example.deliciousrice.Model.Bill;
import com.example.deliciousrice.R;
import com.example.deliciousrice.ui.account.AccountFragment;
import com.example.deliciousrice.ui.shop.Activity.InvoicedetailsFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ReceipFragment extends Fragment {
    private ImageView imgBackReceipt;
    private RecyclerView rclview;
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
        imgBackReceipt.setOnClickListener(v -> {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            AccountFragment fragment = new AccountFragment();
            ft.replace(R.id.nav_host_fragment_activity_main2, fragment);
            ft.commit();
        });

        rclview = view.findViewById(R.id.rclview);
        getreceipt();

    }


    private void getreceipt() {
        Bundle bundle = getArguments();
        String phone = bundle.getString("phone_number", "");
        int idcustm = bundle.getInt("id_cus", 0);
        String name_cus = bundle.getString("name_cus", "");

        ApiProduct apiProduct = ApiService.getService();
        Call<List<Bill>> callback = apiProduct.getListbill(idcustm);
        callback.enqueue(new Callback<List<Bill>>() {
            @Override
            public void onResponse(Call<List<Bill>> call, Response<List<Bill>> response) {
                rclview.setHasFixedSize(true);
                ArrayList<Bill> bills = (ArrayList<Bill>) response.body();
                adapterHistoryBill = new AdapterHistoryBill(bills, getApplicationContext(), bill -> {

                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    InvoicedetailsFragment fragment = new InvoicedetailsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("getData", bill);
                    bundle.putString("name", name_cus);
                    bundle.putString("phone", phone);
                    bundle.putInt("id_cus",idcustm );
                    fragment.setArguments(bundle);

                    ft.replace(R.id.nav_host_fragment_activity_main2, fragment);
                    ft.commit();
                });
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                rclview.setLayoutManager(linearLayoutManager);
                rclview.setAdapter(adapterHistoryBill);
                adapterHistoryBill.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Bill>> call, Throwable t) {

            }
        });
    }


}