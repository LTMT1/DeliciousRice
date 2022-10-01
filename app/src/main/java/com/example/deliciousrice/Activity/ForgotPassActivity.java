package com.example.deliciousrice.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.deliciousrice.R;

public class ForgotPassActivity extends AppCompatActivity {

    private TextView tvGui;
    private ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);
        BarColor.setStatusBarColor(this);

        tvGui = findViewById(R.id.tvGui);
        ivBack = findViewById(R.id.ivBack);

        tvGui.setOnClickListener(v -> changePassword());
        ivBack.setOnClickListener(v -> backToLogin());
    }

    private void changePassword(){
        Intent intent = new Intent(this, ChangePassActivity.class);
        startActivity(intent);
    }

    private void backToLogin(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}