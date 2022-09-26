package com.example.deliciousrice.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.deliciousrice.R;

public class HelloScreenActivity extends AppCompatActivity {
    private ImageView imghellosceen;
    private LinearLayout linear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_screen);
        BarColor.setStatusBarColor(this);

        imghellosceen = findViewById(R.id.imgHelloScreen);
        linear = findViewById(R.id.linear);
        imghellosceen.setX(2000);
        imghellosceen.animate().translationXBy(-2000).setDuration(3000);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                linear.setVisibility(View.GONE);
                Intent intent = new Intent(HelloScreenActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 5000);
    }
}