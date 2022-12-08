package com.example.deliciousrice.ui.account.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deliciousrice.Api.ApiProduct;
import com.example.deliciousrice.Api.ApiService;
import com.example.deliciousrice.Model.Adderss;
import com.example.deliciousrice.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditAddressFragment extends Fragment {
    private ConstraintLayout clDeteleAddress;
    private TextView tvUpAddress;
    private EditText edEdctAddress;
    private EditText edEdNameAdd;
    private ImageView ImgBack;
    ApiProduct apiProduct;

    Adderss adderss;
    int anInt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_edit_address, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImgBack = view.findViewById(R.id.img_backEd_setting);
        ImgBack.setOnClickListener(v -> {

            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            AddressFragment fragment = new AddressFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("Adrress", anInt);
            fragment.setArguments(bundle);

            ft.replace(R.id.nav_host_fragment_activity_main2, fragment);
            ft.commit();

        });

        clDeteleAddress = view.findViewById(R.id.cl_detele_address);
        tvUpAddress = view.findViewById(R.id.tv_upAddress);
        edEdctAddress = view.findViewById(R.id.ed_edctAddress);
        edEdNameAdd =view.findViewById(R.id.ed_edNameAdd);

        adderss = new Adderss();

        Bundle bundle2 = getArguments();
        anInt = bundle2.getInt("iccome", 0);
        adderss = (Adderss) bundle2.getSerializable("getdataAddress");



        edEdctAddress.setText(adderss.getAddress_specifically());
        edEdNameAdd.setText(adderss.getAddress_name());

        clDeteleAddress.setOnClickListener(v -> {
            Delete_addresss();

        });
        tvUpAddress.setOnClickListener(v -> {
            Up_Address();
        });
    }
    private void Up_Address() {
        if (!checkllow()) {
            return;
        } else {
            String ed_mane = edEdNameAdd.getText().toString().trim();
            String ed_addrress = edEdctAddress.getText().toString().trim();
            apiProduct = ApiService.getService();
            Call<String> adAdderss = apiProduct.updateAdderss(adderss.getId_address(), anInt, ed_mane, ed_addrress);
            adAdderss.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    AddressFragment fragment = new AddressFragment();
                    Bundle bundle2 = new Bundle();
                    bundle2.putInt("Adrress", anInt);

                    fragment.setArguments(bundle2);
                    ft.replace(R.id.nav_host_fragment_activity_main2, fragment);
                    ft.commit();

                    Toast.makeText(getContext(), "Thay đổi địa chỉ thành công", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(getContext(), "Thay đổi địa chỉ không thành công", Toast.LENGTH_SHORT).show();
                }
            });

        }


    }

    private void Delete_addresss() {
        apiProduct = ApiService.getService();
        Call<String> adAdderss = apiProduct.deleteAdderss(adderss.getId_address(), anInt);
        adAdderss.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                AddressFragment fragment = new AddressFragment();
                Bundle bundle1 = new Bundle();
                bundle1.putInt("Adrress", anInt);
                fragment.setArguments(bundle1);

                ft.replace(R.id.nav_host_fragment_activity_main2, fragment);
                ft.commit();


                Toast.makeText(getContext(), "Địa chỉ đã được xóa.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi khi xóa đia chỉ.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean checkllow() {
        if (edEdNameAdd.getText().toString().trim().equals("") | edEdctAddress.getText().toString().trim().equals("")) {
            edEdNameAdd.setError("Hãy nhập Tên.");
            edEdctAddress.setError("Hãy nhập địa chỉ của bạn");

            return false;
        } else {
            edEdNameAdd.setError(null);
            edEdctAddress.setError(null);
            return true;
        }
    }
}