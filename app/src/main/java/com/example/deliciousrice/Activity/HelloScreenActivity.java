package com.example.deliciousrice.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.deliciousrice.Api.ApiProduct;
import com.example.deliciousrice.Api.ApiService;
import com.example.deliciousrice.MainActivity2;
import com.example.deliciousrice.R;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class HelloScreenActivity extends AppCompatActivity {
    private ImageView imghellosceen;
    Animation animation;
    private String email ="", password="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_screen);
        BarColor.setStatusBarColor(this);
        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        } else {
            imghellosceen = findViewById(R.id.imgHelloScreen);
            getDatas();
            overridePendingTransition(R.anim.anim_intent_in, R.anim.anim_intent_out);
            animation = AnimationUtils.loadAnimation(HelloScreenActivity.this, R.anim.anim_intent_in_main);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    imghellosceen.setVisibility(View.VISIBLE);
                    imghellosceen.startAnimation(animation);
                }
            }, 2500);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (email.isEmpty() || password.isEmpty()) {
                        startActivity(new Intent(getApplicationContext(), LoginFaGoActivity.class));
                    } else {
                        login();
                    }
                }
            }, 4000);
        }
    }

    private void getDatas() {
        SharedPreferences preferences = getSharedPreferences("user_file", MODE_PRIVATE);
        email=preferences.getString("gmail", "");
        password=preferences.getString("matkhau", "");
    }

    private void login() {
        ApiProduct apiProduct = ApiService.getService();
        Call<String> callback = apiProduct.login(email, password);
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                if (response.body().equals("true")) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                    startActivity(intent);
                } else {
                    startActivity(new Intent(getApplicationContext(), LoginFaGoActivity.class));
                    SharedPreferences.Editor editor = getSharedPreferences("user_file", MODE_PRIVATE).edit();
                    editor.clear().commit();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                startActivity(new Intent(getApplicationContext(), LoginFaGoActivity.class));
                SharedPreferences.Editor editor = getSharedPreferences("user_file", MODE_PRIVATE).edit();
                editor.clear().commit();
            }
        });
    }
    }