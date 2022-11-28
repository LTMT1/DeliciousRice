package com.example.deliciousrice.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.deliciousrice.Api.ApiNetWorking;
import com.example.deliciousrice.MainActivity2;
import com.example.deliciousrice.Model.ResponseApi;
import com.example.deliciousrice.R;
import com.example.deliciousrice.dialog.LoadingDialog;
import com.github.ybq.android.spinkit.style.ThreeBounce;

import retrofit2.Call;
import retrofit2.Callback;

public class LoginActivity extends AppCompatActivity {

    private EditText edtEmailDangNhap;
    private EditText edtPassWordDangNhap;
    private ProgressBar prgLoadingLogin;
    private TextView tvResultLogin;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        BarColor.setStatusBarColor(this);
        edtEmailDangNhap = findViewById(R.id.edtEmailDangNhap);
        edtPassWordDangNhap = findViewById(R.id.edtPassWordDangNhap);
        prgLoadingLogin = findViewById(R.id.prgLoadingLogin);
        TextView tvLogin = findViewById(R.id.tvDangNhap);
        tvResultLogin = findViewById(R.id.tvResultLogin);


        prgLoadingLogin.setIndeterminateDrawable(new ThreeBounce());
        loadingDialog = new LoadingDialog(this);

        tvLogin.setOnClickListener(v -> {
            login();
        });
        getPreferences();
    }
    
    @SuppressLint("SetTextI18n")
    private void login() {

        if (validateEmail() && validateEmail()) {
            loadingDialog = new LoadingDialog(this);
            loadingDialog.startLoadingDialog("Xin vui lòng chờ...");
            tvResultLogin.setText("Loading ...");


            String strname = edtEmailDangNhap.getText().toString().trim();
            String strpass = edtPassWordDangNhap.getText().toString().trim();

            ApiNetWorking.apiNetWorking.login(strname, strpass).enqueue(new Callback<ResponseApi>() {
                @Override
                public void onResponse(@NonNull Call<ResponseApi> call, @NonNull retrofit2.Response<ResponseApi> response) {
                    loadingDialog.dismisDialog();
                    if (response.body() != null) {
                        if (response.body().isStatus()) {
                            remember(strname, strpass);
                            Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Tài khoản hoặc mật khẩu bị sai!", Toast.LENGTH_SHORT).show();
                        }
                    }

                }

                @Override
                public void onFailure(@NonNull Call<ResponseApi> call, @NonNull Throwable t) {

                }
            });
        }
    }

    public boolean validateEmail() {
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

    public boolean validateEmail() {
        if (edtPassWordDangNhap.getText().toString().equals("")) {
            edtPassWordDangNhap.setError("Nhập mật khẩu của bạn");
            return false;
        } else if (edtPassWordDangNhap.getText().toString().length() < 6) {
            edtPassWordDangNhap.setError("Mật khẩu của bạn phải lớn hơn 6");
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
        editor.apply();
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