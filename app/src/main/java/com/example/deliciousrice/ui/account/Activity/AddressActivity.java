package com.example.deliciousrice.ui.account.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;

import com.example.deliciousrice.R;

public class AddressActivity extends AppCompatActivity {

    private ConstraintLayout cl_insertAdsress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        cl_insertAdsress = findViewById(R.id.cl_insertAdsress);

        cl_insertAdsress.setOnClickListener(view -> {
            Intent intent=new Intent(AddressActivity.this, EditAddressActivity.class);
            startActivity(intent);
        });
    }
}