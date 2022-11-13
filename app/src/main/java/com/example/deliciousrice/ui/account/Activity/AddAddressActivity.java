package com.example.deliciousrice.ui.account.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.deliciousrice.Api.ApiProduct;
import com.example.deliciousrice.Api.ApiService;
import com.example.deliciousrice.Model.Adderss;
import com.example.deliciousrice.R;

import java.util.List;

import retrofit2.Call;

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
        }else {
            Adderss adderss=new Adderss();

            adderss.setAddress_name(edAddNameAddress.getText().toString().trim());
            adderss.setAddress_specifically(edAddCtDiachi.getText().toString().trim());


            ApiProduct apiProduct= ApiService.getService();
            Call<Adderss> adAdderss = apiProduct.addAdderss(adderss.getId_customer(),adderss.getAddress_name(),adderss.getAddress_specifically());

        }


    }
    public boolean checkhollow() {
        if (edAddNameAddress.getText().toString().trim().equals("")|edAddCtDiachi.getText().toString().trim().equals("")) {
            edAddNameAddress.setError("Hãy nhập tên của bạn.");
            edAddCtDiachi.setError("Hãy nhập tên của bạn.");

            return false;
        } else {
            edAddNameAddress.setError(null);
            edAddCtDiachi.setError(null);
            return true;
        }
    }

}