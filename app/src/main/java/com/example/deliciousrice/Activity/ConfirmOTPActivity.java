package com.example.deliciousrice.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.example.deliciousrice.Model.Customer;
import com.example.deliciousrice.R;

import java.util.HashMap;
import java.util.Map;

public class ConfirmOTPActivity extends AppCompatActivity {
    private EditText editTextCheckOTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_otpactivity);
        editTextCheckOTP = findViewById(R.id.editTextCheckOTP);
    }

    public void onCLickBackForgotPass(View view) {
        Intent intent = new Intent(ConfirmOTPActivity.this, ForgotPassActivity.class);
        startActivity(intent);
    }

    public void onCLickCheckOtp(View view) {
        String apiotp = "https://appsellrice.000webhostapp.com/Deliciousrice/API/Pickotp.php";
        final ProgressDialog progressDialog = new ProgressDialog(ConfirmOTPActivity.this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.show();
        String str_otp = editTextCheckOTP.getText().toString().trim();
        StringRequest request = new StringRequest(Request.Method.POST, apiotp, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.e(response, "opt");
                if (response.equalsIgnoreCase("success")) {
                    Intent intent = new Intent(getApplicationContext(), ChangePassActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "OTP code is not correct", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "xảy ra lỗi!", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("check", str_otp);
                return params;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);
    }

    public void onCLickSendOtp(View view) {
        Customer customer = (Customer) getIntent().getSerializableExtra("data");
        String apiForgotPass = "https://appsellrice.000webhostapp.com/Deliciousrice/API/ForgotPassword.php";
        final ProgressDialog progressDialog = new ProgressDialog(ConfirmOTPActivity.this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, apiForgotPass, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(ConfirmOTPActivity.this, "xảy ra lỗi!", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", customer.getEmail());
                return params;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(ConfirmOTPActivity.this);
        requestQueue.add(request);
    }
}