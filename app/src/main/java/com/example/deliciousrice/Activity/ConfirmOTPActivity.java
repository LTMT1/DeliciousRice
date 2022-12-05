package com.example.deliciousrice.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
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
    TextView button;
    CountDownTimer countDownTimer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BarColor.setStatusBarColor(this);
        setContentView(R.layout.activity_confirm_otpactivity);
        editTextCheckOTP = findViewById(R.id.editTextCheckOTP);
        button = findViewById(R.id.btnContinue);
        resend = findViewById(R.id.tvguilaima);
        customer = (Customer) getIntent().getSerializableExtra("data");

        button.setOnClickListener(view -> {
            onCLickCheckOtp();
        });

        editTextCheckOTP.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
                        (i == KeyEvent.KEYCODE_ENTER)) {
                    onCLickCheckOtp();
                }
                return false;
            }
        });

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

    private void onCLickCheckOtp() {
        Intent intents = getIntent();
        int Chechotp = intents.getIntExtra("otp", 0);
        String str_otp = editTextCheckOTP.getText().toString().trim();
        if (str_otp.equalsIgnoreCase(String.valueOf(Chechotp))) {
            intents.removeExtra("otp");
            editTextCheckOTP.setText("");
            Intent intent = new Intent(this, ChangePassActivity.class);
            intent.putExtra("email", customer.getEmail());
            startActivity(intent);
        } else {
            Toast.makeText(this, "otp code is not correct.", Toast.LENGTH_SHORT).show();
        }
    }
}