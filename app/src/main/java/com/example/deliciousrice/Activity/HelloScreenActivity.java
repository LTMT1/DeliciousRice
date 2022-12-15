package com.example.deliciousrice.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.deliciousrice.Api.ApiProduct;
import com.example.deliciousrice.Api.ApiService;
import com.example.deliciousrice.MainActivity2;
import com.example.deliciousrice.Model.ResponseApi;
import com.example.deliciousrice.R;
import com.github.ybq.android.spinkit.style.ThreeBounce;

import retrofit2.Call;
import retrofit2.Callback;

public class HelloScreenActivity extends AppCompatActivity {
    private ImageView imghellosceen;
    Animation animation;
    private String email = "", password = "", id_customer = "";
    private ProgressBar prgLoadingSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_screen);
        BarColor.setStatusBarColor(this);
        prgLoadingSplash = findViewById(R.id.prgLoadingSplash);
        prgLoadingSplash.setIndeterminateDrawable(new ThreeBounce());
        prgLoadingSplash.setVisibility(View.VISIBLE);
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
                    if (!email.isEmpty() || !password.isEmpty()) {
                        loginApi(email, password);
                    } else if (!email.isEmpty() || !id_customer.isEmpty()) {
                        loginGGApi(email, id_customer);
                    } else {
                        startActivity(new Intent(getApplicationContext(), LoginFaGoActivity.class));
                    }
                }
            }, 4000);
        }
    }

    private void getDatas() {
        SharedPreferences preferences = getSharedPreferences("user_file", MODE_PRIVATE);
        email = preferences.getString("gmail", "");
        id_customer = preferences.getString("id_customer", "");
        password = preferences.getString("matkhau", "");
    }

    private void loginApi(String email, String password) {
        ApiProduct apiProduct = ApiService.getService();
        Call<ResponseApi> callback = apiProduct.login(email, password);
        callback.enqueue(new Callback<ResponseApi>() {
            @Override
            public void onResponse(@NonNull Call<ResponseApi> call, @NonNull retrofit2.Response<ResponseApi> response) {
                if (response.body() != null) {
                    prgLoadingSplash.setVisibility(View.GONE);

                    if (response.body().isStatus()) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                        startActivity(intent);
                    } else {
                        startActivity(new Intent(getApplicationContext(), LoginFaGoActivity.class));
                        SharedPreferences.Editor editor = getSharedPreferences("user_file", MODE_PRIVATE).edit();
                        editor.clear().apply();
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<ResponseApi> call, @NonNull Throwable t) {
            }
        });
    }

    private void loginGGApi(String email, String id) {
        ApiProduct apiProduct = ApiService.getService();
        Call<String> callback = apiProduct.loginGG(email, id);
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull retrofit2.Response<String> response) {
                if (response.body().equals("Success")) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                    startActivity(intent);
                } else {
                    startActivity(new Intent(getApplicationContext(), LoginFaGoActivity.class));
                    SharedPreferences.Editor editor = getSharedPreferences("user_file", MODE_PRIVATE).edit();
                    editor.clear().apply();
                }

            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
            }
        });
    }

}