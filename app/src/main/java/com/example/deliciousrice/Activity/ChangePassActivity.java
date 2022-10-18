package com.example.deliciousrice.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
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
    private long backPressTime;
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        BarColor.setStatusBarColor(this);
        edtPassChange = findViewById(R.id.edtPassChange);
        edtRePassChange = findViewById(R.id.edtRePassChange);

    }
    @Override
    public void onBackPressed() {
        if (backPressTime + 2000 > System.currentTimeMillis()){
            mToast.cancel();

            Intent intent = new Intent(getApplicationContext(), HelloScreenActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("EXIT", true);
            startActivity(intent);
            finish();
            System.exit(0);

        }else {
            mToast = Toast.makeText(ChangePassActivity.this, "Ấn lần nữa để thoát", Toast.LENGTH_SHORT);
            mToast.show();
        }
        backPressTime = System.currentTimeMillis();
    }
    public void onCLickChangepass(View view) {
        Intent intent = getIntent();
        String str_email = intent.getStringExtra("email");
        String str_passnew = edtPassChange.getText().toString().trim();
        String str_repassnew = edtRePassChange.getText().toString().trim();
        String urlchangepass = "https://appsellrice.000webhostapp.com/Deliciousrice/API/ChangePassword.php";

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
                        edtPassChange.setText("");
                        edtRePassChange.setText("");
                        Intent intent1 = new Intent(ChangePassActivity.this, LoginActivity.class);
                        startActivity(intent1);
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
                    params.put("email", str_email);
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