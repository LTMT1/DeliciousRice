package com.example.deliciousrice.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.deliciousrice.R;

public class ChangePassActivity extends AppCompatActivity {
    private ImageView ivBackpas;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        //ivBackpas = findViewById(R.id.ivBackpas);
        BarColor.setStatusBarColor(this);


    }
}