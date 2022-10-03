package com.example.deliciousrice.ui.account.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.deliciousrice.R;
import com.example.deliciousrice.ui.account.AccountFragment;

public class PolicyActivity extends AppCompatActivity {
    private ImageView imgBackPolicy;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policy);
        imgBackPolicy = findViewById(R.id.img_back_policy);
        imgBackPolicy.setOnClickListener(view -> {
            Intent intent=new Intent(this, AccountFragment.class);
            startActivity(intent);
        });
    }
}