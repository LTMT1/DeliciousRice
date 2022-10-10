package com.example.deliciousrice.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import java.util.Random;

public class ConfirmOTPActivity extends AppCompatActivity {
    private EditText editTextCheckOTP;
    private Random randomcode = new Random();
    private int otp;
    Customer customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_otpactivity);
        editTextCheckOTP = findViewById(R.id.editTextCheckOTP);
        customer = (Customer) getIntent().getSerializableExtra("data");
        otp = randomcode.nextInt((999999 - 100000) + 100000);
    }

    public void onCLickBackForgotPass(View view) {
        Intent intent = new Intent(ConfirmOTPActivity.this, ForgotPassActivity.class);
        startActivity(intent);
    }

    public void onCLickCheckOtp(View view) {
        Intent intents = getIntent();
        int Chechotp = intents.getIntExtra("otp", 0);
        String str_otp = editTextCheckOTP.getText().toString().trim();
        if (str_otp.equalsIgnoreCase(String.valueOf(Chechotp)) || str_otp.equalsIgnoreCase(String.valueOf(otp))) {
            intents.removeExtra("otp");
            otp = otp;
            //
            editTextCheckOTP.setText("");
            Intent intent = new Intent(this, ChangePassActivity.class);
            intent.putExtra("email", customer.getEmail());
            startActivity(intent);
            //
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "otp code is not correct.", Toast.LENGTH_SHORT).show();
        }
    }

    public void onCLickSendOtp(View view) {
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
                params.put("otp", String.valueOf(otp));
                params.put("email", customer.getEmail());
                return params;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(ConfirmOTPActivity.this);
        requestQueue.add(request);
    }
}