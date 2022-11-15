package com.example.deliciousrice.ui.account.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.deliciousrice.Api.ApiProduct;
import com.example.deliciousrice.Api.ApiService;
import com.example.deliciousrice.Model.Adderss;
import com.example.deliciousrice.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditAddressActivity extends AppCompatActivity {
    private ConstraintLayout clDeteleAddress;
    private TextView tvUpAddress;
    private EditText edEdctAddress;
    private EditText edEdNameAdd;
    ApiProduct apiProduct;

    Adderss adderss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_address);

        clDeteleAddress = findViewById(R.id.cl_detele_address);
        tvUpAddress = findViewById(R.id.tv_upAddress);
        edEdctAddress = findViewById(R.id.ed_edctAddress);
        edEdNameAdd = findViewById(R.id.ed_edNameAdd);

        adderss=new Adderss();

        Intent  intent=getIntent();
        adderss= (Adderss) intent.getSerializableExtra("getdataAddress");
        edEdctAddress.setText(adderss.getAddress_specifically());
        edEdNameAdd.setText(adderss.getAddress_name());




        clDeteleAddress.setOnClickListener(v->{
            Delete_addresss();

        });
        tvUpAddress.setOnClickListener(v->{
            Up_Address();
        });

    }

    private void Up_Address() {
        if (!checkllow()){
            return;
        }else {
            apiProduct= ApiService.getService();
            Call<Adderss> adAdderss = apiProduct.deleteAdderss(adderss.getId_address(),adderss.getId_customer());
            adAdderss.enqueue(new Callback<Adderss>() {
                @Override
                public void onResponse(Call<Adderss> call, Response<Adderss> response) {
                    Adderss adderss1=response.body();


                }

                @Override
                public void onFailure(Call<Adderss> call, Throwable t) {

                }
            });

        }


    }

    private void Delete_addresss() {
        adderss.setAddress_name(edEdNameAdd.getText().toString().trim());
        adderss.setAddress_specifically(edEdctAddress.getText().toString().trim());
        apiProduct= ApiService.getService();
        Call<Adderss> adAdderss = apiProduct.updateAdderss(adderss.getId_customer(),adderss.getAddress_name(),adderss.getAddress_specifically());
        adAdderss.enqueue(new Callback<Adderss>() {
            @Override
            public void onResponse(Call<Adderss> call, Response<Adderss> response) {
                Adderss adderss=response.body();

            }

            @Override
            public void onFailure(Call<Adderss> call, Throwable t) {

            }
        });

    }
    public boolean checkllow() {
        if (edEdNameAdd.getText().toString().trim().equals("")|edEdctAddress.getText().toString().trim().equals("")) {
            edEdNameAdd.setError("Hãy Nhập Tên.");
            edEdctAddress.setError("Hãy nhập địa chỉ của bạn");

            return false;
        } else {
            edEdNameAdd.setError(null);
            edEdctAddress.setError(null);
            return true;
        }
    }
}