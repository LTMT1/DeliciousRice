package com.example.deliciousrice.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.deliciousrice.R;

public class RegisterActivity extends AppCompatActivity {

    private ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ivBack = findViewById(R.id.ivBack);

        ivBack.setOnClickListener(v -> backToLogin());
    }

    private void backToLogin(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}