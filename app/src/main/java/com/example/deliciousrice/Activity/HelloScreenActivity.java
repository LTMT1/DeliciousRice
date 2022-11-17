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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.deliciousrice.MainActivity2;
import com.example.deliciousrice.R;

import java.util.HashMap;
import java.util.Map;

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
        String apilogin = "https://appsellrice.000webhostapp.com/Deliciousrice/API/Login.php";
        final ProgressDialog progressDialog = new ProgressDialog(HelloScreenActivity.this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, apilogin, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                if (response.equalsIgnoreCase("Đăng Nhập Thành Công")) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Email or password wrong", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), LoginFaGoActivity.class));
                    SharedPreferences.Editor editor = getSharedPreferences("user_file", MODE_PRIVATE).edit();
                    editor.clear().commit();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "xảy ra lỗi!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), LoginFaGoActivity.class));
                SharedPreferences.Editor editor = getSharedPreferences("user_file", MODE_PRIVATE).edit();
                editor.clear().commit();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);
                return params;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);
    }
    }