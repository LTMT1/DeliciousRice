package com.example.deliciousrice.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.deliciousrice.Api.ApiProduct;
import com.example.deliciousrice.Api.ApiService;
import com.example.deliciousrice.MainActivity2;
import com.example.deliciousrice.R;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class LoginActivity extends AppCompatActivity {

    private EditText edtEmailDangNhap;
    private EditText edtPassWordDangNhap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        BarColor.setStatusBarColor(this);
        edtEmailDangNhap = findViewById(R.id.edtEmailDangNhap);
        edtPassWordDangNhap = findViewById(R.id.edtPassWordDangNhap);
        getPreferences();
    }

    //Login
    public void onClickLogin(View view) {
        if (!validateemail() | !validatepass()) {
            return;
        } else {
            final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setMessage("Please Wait..");
            progressDialog.setCancelable(false);
            progressDialog.show();
            String strname = edtEmailDangNhap.getText().toString().trim();
            String strpass = edtPassWordDangNhap.getText().toString().trim();
            ApiProduct apiProduct = ApiService.getService();
            Call<String> callback = apiProduct.login(strname, strpass);
            callback.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                    if (response.body().equals("true")) {
                        progressDialog.dismiss();
                        remember(strname, strpass);
                        Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Mật khẩu hoặc tài khoản không đúng!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Lỗi kết nối tới sever!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public boolean validateemail() {
        String a = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (edtEmailDangNhap.getText().toString().equals("")) {
            edtEmailDangNhap.setError("Hãy nhập gmail của bạn.");
            return false;
        } else if (!edtEmailDangNhap.getText().toString().trim().matches(a)) {
            edtEmailDangNhap.setError("Nhập đúng định dạng gmail.");
            return false;
        } else {
            edtEmailDangNhap.setError(null);
            return true;
        }
    }

    public boolean validatepass() {
        if (edtPassWordDangNhap.getText().toString().equals("")) {
            edtPassWordDangNhap.setError("Nhập mật khẩu của bạn");
            return false;
        } else {
            edtPassWordDangNhap.setError(null);
            return true;
        }
    }

    public void getPreferences() {
        SharedPreferences preferences = getSharedPreferences("user_file", MODE_PRIVATE);
        edtEmailDangNhap.setText(preferences.getString("gmail", ""));
        edtPassWordDangNhap.setText(preferences.getString("matkhau", ""));
    }

    private void remember(String strname, String strpass) {
        SharedPreferences preferences = getSharedPreferences("user_file", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("gmail", strname);
        editor.putString("matkhau", strpass);
        editor.commit();
    }

    //register
    public void onClickRegisteraccount(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    //forgotpassword
    public void onClickForgotPassword(View view) {
        Intent intent = new Intent(LoginActivity.this, ForgotPassActivity.class);
        startActivity(intent);
    }

    //backsceen
    public void onClickBackSceen(View view) {
        Intent intent = new Intent(LoginActivity.this, LoginFaGoActivity.class);
        startActivity(intent);
    }

}