package com.example.deliciousrice.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.deliciousrice.R;

public class ChangePassActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        BarColor.setStatusBarColor(this);
    }
}