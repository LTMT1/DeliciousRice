package com.example.deliciousrice.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deliciousrice.Adapter.AdapterForgotPass;
import com.example.deliciousrice.Model.Customer;
import com.example.deliciousrice.R;


public class ConfirmOTPActivity extends AppCompatActivity {
    private EditText editTextCheckOTP;
    Customer customer;
    TextView resend;
    private long backPressTime;
    private Toast mToast;
    CountDownTimer countDownTimer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_otpactivity);
        editTextCheckOTP = findViewById(R.id.editTextCheckOTP);
        resend = findViewById(R.id.tvguilaima);
        customer = (Customer) getIntent().getSerializableExtra("data");
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdapterForgotPass.buttonSendEmail(customer.getEmail());
                countDownTimer = new CountDownTimer(30000, 1000) {
                    @Override
                    public void onTick(long l) {
                        resend.setEnabled(false);
                        resend.setText("gửi lại mã(" + l / 1000 + ")");
                    }

                    @Override
                    public void onFinish() {
                        resend.setEnabled(true);
                        resend.setText("gửi lại mã");
                    }
                };
                countDownTimer.start();
            }
        });
    }

    @Override
    protected void onDestroy() {
        countDownTimer.cancel();
        super.onDestroy();
    }


    public void onCLickBackForgotPass(View view) {
        Intent intent = new Intent(ConfirmOTPActivity.this, ForgotPassActivity.class);
        startActivity(intent);
    }

    public void onCLickCheckOtp(View view) {
        Intent intents = getIntent();
        int Chechotp = intents.getIntExtra("otp", 0);
        String str_otp = editTextCheckOTP.getText().toString().trim();
        if (str_otp.equalsIgnoreCase(String.valueOf(Chechotp))) {
            intents.removeExtra("otp");
//            otp = otp;
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

//    public void onCLickSendOtp(View view) {
//        String apiForgotPass = "https://appsellrice.000webhostapp.com/Deliciousrice/API/ForgotPassword.php";
//        final ProgressDialog progressDialog = new ProgressDialog(ConfirmOTPActivity.this);
//        progressDialog.setMessage("Please Wait..");
//        progressDialog.show();
//        StringRequest request = new StringRequest(Request.Method.POST, apiForgotPass, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                progressDialog.dismiss();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                progressDialog.dismiss();
//                Toast.makeText(ConfirmOTPActivity.this, "xảy ra lỗi!", Toast.LENGTH_SHORT).show();
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("otp", String.valueOf(otp));
//                params.put("email", customer.getEmail());
//                return params;
//
//            }
//        };
//
//        RequestQueue requestQueue = Volley.newRequestQueue(ConfirmOTPActivity.this);
//        requestQueue.add(request);
//    }
}