package com.example.deliciousrice.ui.account.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deliciousrice.Api.ApiProduct;
import com.example.deliciousrice.Api.ApiService;
import com.example.deliciousrice.Model.Adderss;
import com.example.deliciousrice.R;
import com.example.deliciousrice.ui.account.Fragment.AddressFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditAddressActivity extends AppCompatActivity {
    private ConstraintLayout clDeteleAddress;
    private TextView tvUpAddress;
    private EditText edEdctAddress;
    private EditText edEdNameAdd;
    private ImageView ImgBack;
    ApiProduct apiProduct;

    Adderss adderss;
    int anInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_address);

        ImgBack=findViewById(R.id.img_backEd_setting);
        ImgBack.setOnClickListener(v->{
            Intent intents = new Intent(EditAddressActivity.this, AddressFragment.class);
            startActivity(intents);
        });

        clDeteleAddress = findViewById(R.id.cl_detele_address);
        tvUpAddress = findViewById(R.id.tv_upAddress);
        edEdctAddress = findViewById(R.id.ed_edctAddress);
        edEdNameAdd = findViewById(R.id.ed_edNameAdd);

        adderss=new Adderss();

        Intent  intent=getIntent();
        adderss= (Adderss) intent.getSerializableExtra("getdataAddress");
        anInt=intent.getIntExtra("idcustomer",0);


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

            String ed_mane=edEdNameAdd.getText().toString().trim();
            String ed_addrress=edEdctAddress.getText().toString().trim();
            apiProduct= ApiService.getService();
            Call<String> adAdderss = apiProduct.updateAdderss(adderss.getId_address(),anInt,ed_mane,ed_addrress);


          adAdderss.enqueue(new Callback<String>() {
              @Override
              public void onResponse(Call<String> call, Response<String> response) {
                  Intent intent=new Intent(EditAddressActivity.this,AddressActivity.class);
                  intent.putExtra("Adrress",anInt);
                  startActivity( intent);
                  Toast.makeText(EditAddressActivity.this, "Thay đổi địa chỉ thành công", Toast.LENGTH_SHORT).show();
              }

              @Override
              public void onFailure(Call<String> call, Throwable t) {
                  Toast.makeText(EditAddressActivity.this, "Thay đổi địa chỉ không thành công", Toast.LENGTH_SHORT).show();
              }
          });

        }


    }

    private void Delete_addresss() {
            apiProduct= ApiService.getService();
            Call<String> adAdderss = apiProduct.deleteAdderss(adderss.getId_address(),anInt);
             adAdderss.enqueue(new Callback<String>() {
              @Override
              public void onResponse(Call<String> call, Response<String> response) {

                  Intent intent=new Intent(EditAddressActivity.this,AddressFragment.class);
                  intent.putExtra("Adrress",anInt);
                  startActivity( intent);

                  Toast.makeText(EditAddressActivity.this, "Địa chỉ đã được xóa.", Toast.LENGTH_SHORT).show();
              }

              @Override
              public void onFailure(Call<String> call, Throwable t) {
                  Toast.makeText(EditAddressActivity.this, "Lỗi khi xóa đia chỉ.", Toast.LENGTH_SHORT).show();
              }
          });
    }
    public boolean checkllow() {
        if (edEdNameAdd.getText().toString().trim().equals("")|edEdctAddress.getText().toString().trim().equals("")) {
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