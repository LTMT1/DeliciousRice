package com.example.deliciousrice.ui.account.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
        imgBackAddress = view.findViewById(R.id.img_back_Address);
        imgBackAddress.setOnClickListener(v->{
            Navigation.findNavController(view).navigate(R.id.action_addressFragment_to_accountFragment);
        });
        cl_insertAdsress = view.findViewById(R.id.cl_insertAdsress);

        Bundle bundle=new Bundle();
        idadr =bundle.getInt("Adrress",0);

        imgBackAddress.setOnClickListener(v->{
            Fragment fragment=new AccountFragment();
            FragmentTransaction fragmentTransaction=getChildFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.Address,fragment).commit();

        });
        cl_insertAdsress.setOnClickListener(v -> {

            Navigation.findNavController(view).navigate(R.id.action_addressFragment_to_addAddressFragment);

            Intent i=new Intent(getContext(), AddAddressActivity.class);
            i.putExtra("iccome",idadr);
            startActivity(i);



        });



        rclAddress = view.findViewById(R.id.rcl_address);
        ShowAddress();

    }
    private void ShowAddress() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();


        ApiProduct apiProduct= ApiService.getService();
        Call<List<Adderss>> listAddre = apiProduct.getListAddresss(idadr);
        listAddre.enqueue(new Callback<List<Adderss>>() {
            @Override
            public void onResponse(Call<List<Adderss>> call, Response<List<Adderss>> response) {
                ArrayList<Adderss> addersses = new ArrayList<>();
                progressDialog.dismiss();
                addersses = (ArrayList<Adderss>) response.body();
                rclAddress.setHasFixedSize(true);
                rclAddress.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL, false));
                adapterAddress = new AdapterAddress(addersses, getContext(), addressNew -> {


                    Intent intent=new Intent(getContext(), EditAddressActivity.class);
                    intent.putExtra("idcustomer",idadr);
                    intent.putExtra("getdataAddress", addressNew);
                    startActivity(intent);




                });
                rclAddress.setAdapter(adapterAddress);
                adapterAddress.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Adderss>> call, Throwable t) {

            }
        });


    }
}