package com.example.deliciousrice.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.deliciousrice.R;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText edtHoTen;
    private EditText edtEmailDangNhap;
    private EditText edtPassWordDangKy;
    private EditText edtRePassWordDangKy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        BarColor.setStatusBarColor(this);
        edtHoTen = findViewById(R.id.edtHoTen);
        edtEmailDangNhap = findViewById(R.id.edtEmailDangNhap);
        edtPassWordDangKy = findViewById(R.id.edtPassWordDangKy);
        edtRePassWordDangKy = findViewById(R.id.edtRePassWordDangKy);
    }

    public void BackToLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void onClickRegister(View view) {

        if (!validatename() | !validaterepass() | !validateemail() | !validatepass()) {
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

    public boolean validatename() {
        if (edtHoTen.getText().toString().trim().equals("")) {
            edtHoTen.setError("Hãy nhập tên của bạn.");
            return false;
        } else {
            edtHoTen.setError(null);
            return true;
        }
    }

    public boolean validateemail() {
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

    public boolean validatepass() {
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

    public boolean validaterepass() {
        if (!edtRePassWordDangKy.getText().toString().trim().equals(edtPassWordDangKy.getText().toString().trim())) {
            edtRePassWordDangKy.setError("Mật khẩu nhập lại không đúng");
            return false;
        } else {
            edtRePassWordDangKy.setError(null);
            return true;
        }
    }
}