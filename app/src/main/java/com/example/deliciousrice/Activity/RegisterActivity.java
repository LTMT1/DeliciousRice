package com.example.deliciousrice.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.deliciousrice.Api.ApiNetWorking;
import com.example.deliciousrice.Model.ResponseApi;
import com.example.deliciousrice.R;
import com.example.deliciousrice.dialog.LoadingDialog;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class RegisterActivity extends AppCompatActivity {

    private EditText edtHoTen;
    private EditText edtEmailDangNhap;
    private EditText edtPassWordDangKy;
    private EditText edtRePassWordDangKy;
    private LoadingDialog loadingDialog;
    private TextView tvDangKy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        BarColor.setStatusBarColor(this);
        edtHoTen = findViewById(R.id.edtHoTen);
        edtEmailDangNhap = findViewById(R.id.edtEmailDangNhap);
        edtPassWordDangKy = findViewById(R.id.editPasswordDangKy);
        edtRePassWordDangKy = findViewById(R.id.editRePasswordDangky);
        tvDangKy = findViewById(R.id.tvDangKy);

        loadingDialog = new LoadingDialog(this);

        tvDangKy.setOnClickListener(v -> {
            register();
        });
    }

    public void BackToLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void onClickRegister(View view) {

        if (!validateName() | !validateRePass() | !validatePass() | !validateEmail()) {
            return;
        } else {
            final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this);
            progressDialog.setMessage("Please Wait..");
            progressDialog.setCancelable(false);
            progressDialog.show();

            String str_name = edtHoTen.getText().toString().trim();
            String str_email = edtEmailDangNhap.getText().toString().trim();
            String str_password = edtPassWordDangKy.getText().toString().trim();
            StringRequest request = new StringRequest(Request.Method.POST, "https://appsellrice.000webhostapp.com/Deliciousrice/API/Register.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    if (response.equalsIgnoreCase("Đăng kí Thành Công")) {
                        edtHoTen.setText("");
                        edtEmailDangNhap.setText("");
                        edtPassWordDangKy.setText("");
                        edtRePassWordDangKy.setText("");
                        Toast.makeText(getApplication(), response, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplication(), response, Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplication(), "xảy ra lỗi!", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("username", str_name);
                    params.put("email", str_email);
                    params.put("password", str_password);
                    return params;

                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplication());
            requestQueue.add(request);
        }

    }

    private void register(){
        if (validateName() && validateEmail() && validatePass() && validateRePass()) {
            loadingDialog = new LoadingDialog(this);
            loadingDialog.startLoadingDialog("Xin vui lòng chờ...");


            String str_name = edtHoTen.getText().toString().trim();
            String str_email = edtEmailDangNhap.getText().toString().trim();
            String str_password = edtPassWordDangKy.getText().toString().trim();

            ApiNetWorking.apiNetWorking.register(str_name, str_email, str_password).enqueue(new Callback<ResponseApi>() {
                @Override
                public void onResponse(@NonNull Call<ResponseApi> call, @NonNull retrofit2.Response<ResponseApi> response) {
                    loadingDialog.dismisDialog();
                    if (response.body() != null) {
                        if (response.body().isStatus()) {
                            edtHoTen.setText("");
                            edtEmailDangNhap.setText("");
                            edtPassWordDangKy.setText("");
                            edtRePassWordDangKy.setText("");
                            Toast.makeText(getApplicationContext(), "Đăng ký thành công!", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                }

                @Override
                public void onFailure(@NonNull Call<ResponseApi> call, @NonNull Throwable t) {
                    Toast.makeText(getApplicationContext(), "Xảy ra lỗi", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public boolean validateName() {
        if (edtHoTen.getText().toString().trim().equals("")) {
            edtHoTen.setError("Hãy nhập tên của bạn.");
            return false;
        } else {
            edtHoTen.setError(null);
            return true;
        }
    }

    public boolean validateEmail() {
        String a = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (edtEmailDangNhap.getText().toString().trim().equals("")) {
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

    public boolean validatePass() {
        if (edtPassWordDangKy.getText().toString().trim().equals("")) {
            edtPassWordDangKy.setError("Nhập mật khẩu của bạn");
            return false;
        } else if (edtPassWordDangKy.length() < 6) {
            edtPassWordDangKy.setError("Nhập mật khẩu trên 6 kí tự.");
            return false;
        } else {
            edtPassWordDangKy.setError(null);
            return true;
        }
    }

    public boolean validateRePass() {
        if (!edtRePassWordDangKy.getText().toString().trim().equals(edtPassWordDangKy.getText().toString().trim())) {
            edtRePassWordDangKy.setError("Mật khẩu không trùng khớp với nhau!");
            return false;
        } else {
            edtRePassWordDangKy.setError(null);
            return true;
        }
    }
}