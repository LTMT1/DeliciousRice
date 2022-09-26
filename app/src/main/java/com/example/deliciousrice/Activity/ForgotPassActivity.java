package com.example.deliciousrice.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.deliciousrice.R;

public class ForgotPassActivity extends AppCompatActivity {

    private TextView tvGui;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);
        BarColor.setStatusBarColor(this);

        tvGui = findViewById(R.id.tvGui);

        tvGui.setOnClickListener(v -> changePassword());
    }

    private void changePassword(){
        Intent intent = new Intent(this, ChangePassActivity.class);
        startActivity(intent);
    }
}