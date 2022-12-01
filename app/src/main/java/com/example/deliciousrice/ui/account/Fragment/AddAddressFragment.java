package com.example.deliciousrice.ui.account.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deliciousrice.Api.ApiProduct;
import com.example.deliciousrice.Api.ApiService;
import com.example.deliciousrice.R;
import com.example.deliciousrice.ui.account.Activity.AddAddressActivity;
import com.example.deliciousrice.ui.account.Activity.AddressActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddAddressFragment extends Fragment {
    private ImageView imgBackAddAddress;
    private TextView tvAddAddress;
    private EditText edAddCtDiachi;
    private EditText edAddNameAddress;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_address, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imgBackAddAddress = view.findViewById(R.id.img_backAdd_Address);


        edAddCtDiachi = view.findViewById(R.id.ed_add_ctDiachi);
        edAddNameAddress = view.findViewById(R.id.ed_add_nameAddress);
        tvAddAddress = view.findViewById(R.id.tv_add_Address);
        tvAddAddress.setOnClickListener(v -> {
                    SaveAdderssNew();
                }
        );
    }
    private void SaveAdderssNew() {
        if (!checkhollow()){
            return;
        } else {
            Bundle  bundle = new Bundle();
            int idc = bundle.getInt("iccome", 0);


            String pos_name = edAddNameAddress.getText().toString().trim();
            String pos_address = edAddCtDiachi.getText().toString().trim();
            ApiProduct apiProduct = ApiService.getService();
            Call<String> adAddegrss = apiProduct.addAdderss(idc, pos_name, pos_address);
            adAddegrss.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                     /// Chỉnh Lai

                    Intent intent=new Intent(getContext(), AddressActivity.class);
                    intent.putExtra("Adrress",idc);
                    startActivity( intent);
                    Toast.makeText(getContext(), "Thêm địa chỉ thành công", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(getContext(), "Thêm địa chỉ không thành công", Toast.LENGTH_SHORT).show();

                }
            });

        }


    }
    public boolean checkhollow() {
        if (edAddNameAddress.getText().toString().trim().equals("")|edAddCtDiachi.getText().toString().trim().equals("")) {
            edAddNameAddress.setError("Hãy Nhập Tên.");
            edAddCtDiachi.setError("Hãy nhập địa chỉ của bạn");

            return false;
        } else {
            edAddNameAddress.setError(null);
            edAddCtDiachi.setError(null);
            return true;
        }
    }
}