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
        tvLoginDangNhap.setOnClickListener( v -> LoginDangNhap());
        tvTextLoginDangky = findViewById(R.id.tvTextLoginDangky);
        tvTextLoginDangky.setOnClickListener(v -> ManDangKy());

    }
    private  void LoginDangNhap(){
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);

    }
    private  void  ManDangKy(){
        Intent intent=new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }
}