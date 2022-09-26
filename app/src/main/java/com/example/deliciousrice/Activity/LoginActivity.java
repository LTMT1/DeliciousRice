package com.example.deliciousrice.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.deliciousrice.MainActivity2;
import com.example.deliciousrice.R;

public class LoginActivity extends AppCompatActivity {

    private TextView tvTextDangKy, tvDangNhap, tvQuenMK;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tvTextDangKy = findViewById(R.id.tvTextDangky);
        tvDangNhap = findViewById(R.id.tvDangNhap);
        tvQuenMK = findViewById(R.id.tvQuenMK);

        tvDangNhap.setOnClickListener(v -> dangNhap());
        tvTextDangKy.setOnClickListener(v -> dangKy());
        tvQuenMK.setOnClickListener(v -> quenMK());
    }

    private void dangNhap(){
        Intent intent = new Intent(LoginActivity.this, MainActivity2.class);
        startActivity(intent);
    }

    private void dangKy(){
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    private void quenMK(){
        Intent intent = new Intent(LoginActivity.this, ForgotPassActivity.class);
        startActivity(intent);
    }
}