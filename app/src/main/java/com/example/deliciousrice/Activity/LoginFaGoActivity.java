package com.example.deliciousrice.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.deliciousrice.R;

public class LoginFaGoActivity extends AppCompatActivity {
    private CardView cvLoginGoogle;
    private CardView cvLoginFacebook;

    private TextView tvLoginDangNhap;

    private TextView tvTextLoginDangky;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_fa_go);
        cvLoginGoogle = findViewById(R.id.cvLoginGoogle);
        cvLoginFacebook = findViewById(R.id.cvLoginFacebook);

        tvLoginDangNhap = findViewById(R.id.tvLoginDangNhap);
        tvTextLoginDangky = findViewById(R.id.tvTextLoginDangky);

        tvLoginDangNhap.setOnClickListener( v -> loginDangNhap());
        tvTextLoginDangky.setOnClickListener(v -> manDangKy());

    }
    private  void loginDangNhap(){
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);

    }
    private  void  manDangKy(){
        Intent intent=new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }
}