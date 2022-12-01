package com.example.deliciousrice.ui.account.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.deliciousrice.Api.ApiProduct;
import com.example.deliciousrice.Api.ApiService;
import com.example.deliciousrice.Model.Adderss;
import com.example.deliciousrice.R;
import com.example.deliciousrice.ui.account.Fragment.AddressFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAddressActivity extends AppCompatActivity {
    private ImageView imgBackAddAddress;
    private TextView tvAddAddress;
    private EditText edAddCtDiachi;
    private EditText edAddNameAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        imgBackAddAddress = findViewById(R.id.img_backAdd_Address);


        edAddCtDiachi = findViewById(R.id.ed_add_ctDiachi);
        edAddNameAddress = findViewById(R.id.ed_add_nameAddress);

        tvAddAddress = findViewById(R.id.tv_add_Address);
        tvAddAddress.setOnClickListener(v -> {
                    SaveAdderssNew();
                }
        );

    }

    private void SaveAdderssNew() {
        if (!checkhollow()){
            return;
        } else {
            Intent intent = getIntent();
            int idc = intent.getIntExtra("iccome", 0);
            String pos_name = edAddNameAddress.getText().toString().trim();
            String pos_address = edAddCtDiachi.getText().toString().trim();
            ApiProduct apiProduct = ApiService.getService();
            Call<String> adAddegrss = apiProduct.addAdderss(idc, pos_name, pos_address);
            adAddegrss.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Intent intent=new Intent(AddAddressActivity.this, AddressFragment.class);
                    intent.putExtra("Adrress",idc);
                    startActivity( intent);
                    Toast.makeText(AddAddressActivity.this, "Thêm địa chỉ thành công", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(AddAddressActivity.this, "Thêm địa chỉ không thành công", Toast.LENGTH_SHORT).show();

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