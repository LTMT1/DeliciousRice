package com.example.deliciousrice.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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

public class ChangePassActivity extends AppCompatActivity {
    private EditText edtPassChange;
    private EditText edtRePassChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        BarColor.setStatusBarColor(this);
        edtPassChange = findViewById(R.id.edtPassChange);
        edtRePassChange = findViewById(R.id.edtRePassChange);

    }

    public void onCLickChangepass(View view) {
        String str_passnew = edtPassChange.getText().toString().trim();
        String str_repassnew = edtRePassChange.getText().toString().trim();
        String urlchangepass = "https://website1812.000webhostapp.com/Coffee/changepassnew.php";

        if (!validatepass() || !validaterepass()) {
            return;
        } else {
            final ProgressDialog progressDialog = new ProgressDialog(ChangePassActivity.this);
            progressDialog.setMessage("Please Wait..");
            progressDialog.show();
            RequestQueue requestQueue = Volley.newRequestQueue(ChangePassActivity.this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, urlchangepass, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    if (response.equalsIgnoreCase("Thay đổi mật khẩu thành công")) {
                        Toast.makeText(ChangePassActivity.this, response, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ChangePassActivity.this, response, Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(ChangePassActivity.this, "Xảy ra lỗi", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
//                    params.put("Gmail",timKiem.getGmail());
                    params.put("passnew", str_passnew);
                    params.put("repassnew", str_repassnew);
                    return params;
                }
            };

            requestQueue.add(stringRequest);
        }
    }

    public boolean validatepass() {
        if (edtPassChange.getText().toString().trim().equals("")) {
            edtPassChange.setError("Nhập mật khẩu của bạn");
            return false;
        } else if (edtPassChange.length() < 6) {
            edtPassChange.setError("Nhập mật khẩu trên 6 kí tự.");
            return false;
        } else {
            edtPassChange.setError(null);
            return true;
        }
    }

    public boolean validaterepass() {
        if (!edtRePassChange.getText().toString().trim().equals(edtPassChange.getText().toString().trim())) {
            edtRePassChange.setError("Mật khẩu nhập lại không đúng");
            return false;
        } else {
            edtRePassChange.setError(null);
            return true;
        }
    }
}