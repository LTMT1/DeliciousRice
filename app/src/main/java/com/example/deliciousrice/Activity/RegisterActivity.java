package com.example.deliciousrice.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.deliciousrice.Api.ApiProduct;
import com.example.deliciousrice.Api.ApiService;
import com.example.deliciousrice.Model.ResponseApi;
import com.example.deliciousrice.R;
import com.example.deliciousrice.dialog.LoadingDialog;

import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import retrofit2.Call;
import retrofit2.Callback;

public class RegisterActivity extends AppCompatActivity {

    private EditText edtHoTen;
    private EditText edtEmailDangNhap;
    private EditText edtPassWordDangKy;
    private EditText edtRePassWordDangKy;
    private LoadingDialog loadingDialog;

    //
    CountDownTimer countDownTimer = null;
    private Random randomcode = new Random();
    private  int otp;
    private String mess;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        BarColor.setStatusBarColor(this);
        edtHoTen = findViewById(R.id.edtHoTen);
        edtEmailDangNhap = findViewById(R.id.edtEmailDangNhap);
        edtPassWordDangKy = findViewById(R.id.editPasswordDangKy);
        edtRePassWordDangKy = findViewById(R.id.editRePasswordDangky);
        TextView tvDangKy = findViewById(R.id.tvDangKy);
        loadingDialog = new LoadingDialog(this);
        tvDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateName() || !validateEmail() || !validatePass() || !validateRePass()) {
                    return;
                } else {
                String a = edtEmailDangNhap.getText().toString().trim();
                otp = randomcode.nextInt((999999 - 100000) + 100000);
                mess = "B???n ???? nh???n ???????c m???t m?? x??c nh???n t??? DeliciousRice. \n M?? x??c nh???n gmail c???a b???n b??n d?????i:" + otp + "\n\nDeliciousRice ch??n th??nh c???m ??n.";
                verifyEmail(a);
                Dialog dialog = new Dialog(RegisterActivity.this);
                dialog.setContentView(R.layout.veryfine_gmail);
                EditText edtotp = dialog.findViewById(R.id.edtotp);
                TextView tvguilai = dialog.findViewById(R.id.tvguilai);
                TextView btnaccess = dialog.findViewById(R.id.btnaccess);
                tvguilai.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        verifyEmail(a);
                        countDownTimer = new CountDownTimer(30000, 1000) {
                            @Override
                            public void onTick(long l) {
                                tvguilai.setEnabled(false);
                                tvguilai.setText("g???i l???i m??(" + l / 1000 + ")");
                            }

                            @Override
                            public void onFinish() {
                                tvguilai.setEnabled(true);
                                tvguilai.setText("g???i l???i m??");
                            }
                        };
                        countDownTimer.start();
                    }
                });
                btnaccess.setOnClickListener(v -> {
                    String str_otp = edtotp.getText().toString().trim();
                    if (str_otp.equalsIgnoreCase(String.valueOf(otp))) {
                        edtotp.setText("");
                        dialog.dismiss();
                        register();
                    } else {
                        Toast.makeText(RegisterActivity.this, "m?? otp kh??ng ch??nh x??c.", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.show();
            }
        }
        });
    }

    public void BackToLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void register() {
            loadingDialog = new LoadingDialog(this);
            loadingDialog.startLoadingDialog("Xin vui l??ng ch???...");
            String str_name = edtHoTen.getText().toString().trim();
            String str_email = edtEmailDangNhap.getText().toString().trim();
            String str_password = edtPassWordDangKy.getText().toString().trim();

            ApiProduct apiProduct = ApiService.getService();
            Call<ResponseApi> callback = apiProduct.register(str_name, str_email, str_password);
            callback.enqueue(new Callback<ResponseApi>() {
                @Override
                public void onResponse(@NonNull Call<ResponseApi> call, @NonNull retrofit2.Response<ResponseApi> response) {
                    loadingDialog.dismisDialog();
                    if (response.body() != null) {
                        if (response.body().isStatus()) {
                            edtHoTen.setText("");
                            edtEmailDangNhap.setText("");
                            edtPassWordDangKy.setText("");
                            edtRePassWordDangKy.setText("");
                            showToast(response.body().getMessage());
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                        } else {
                            showToast(response.body().getMessage());
                        }
                    }

                }

                @Override
                public void onFailure(@NonNull Call<ResponseApi> call, @NonNull Throwable t) {
                    Toast.makeText(getApplicationContext(), "X???y ra l???i", Toast.LENGTH_LONG).show();
                }
            });
    }

    public boolean validateName() {
        if (edtHoTen.getText().toString().trim().equals("")) {
            edtHoTen.setError("H??y nh???p t??n c???a b???n.");
            return false;
        } else {
            edtHoTen.setError(null);
            return true;
        }
    }

    public boolean validateEmail() {
        String a = "^[a-zA-Z0-9]*@{1}gmail.com$";
        if (edtEmailDangNhap.getText().toString().trim().equals("")) {
            edtEmailDangNhap.setError("H??y nh???p gmail c???a b???n.");
            return false;
        } else if (!edtEmailDangNhap.getText().toString().trim().matches(a)) {
            edtEmailDangNhap.setError("Nh???p ????ng ?????nh d???ng gmail.");
            return false;
        }else {
            edtEmailDangNhap.setError(null);
            return true;
        }
    }

    public boolean validatePass() {
        if (edtPassWordDangKy.getText().toString().trim().equals("")) {
            edtPassWordDangKy.setError("Nh???p m???t kh???u c???a b???n");
            return false;
        } else if (edtPassWordDangKy.length() < 6) {
            edtPassWordDangKy.setError("Nh???p m???t kh???u tr??n 6 k?? t???.");
            return false;
        } else {
            edtPassWordDangKy.setError(null);
            return true;
        }
    }

    public boolean validateRePass() {
        if (!edtRePassWordDangKy.getText().toString().trim().equals(edtPassWordDangKy.getText().toString().trim())) {
            edtRePassWordDangKy.setError("M???t kh???u kh??ng tr??ng kh???p v???i nhau!");
            return false;
        } else {
            edtRePassWordDangKy.setError(null);
            return true;
        }
    }

    @Override
    protected void onDestroy() {
        countDownTimer.cancel();
        super.onDestroy();
    }


    public void verifyEmail(String email) {

        try {
            String stringSenderEmail = "deliciousrices@gmail.com";
            String stringPasswordSenderEmail = "jzqnirrpudyvquku";
            String stringHost = "smtp.gmail.com";
            Properties properties = System.getProperties();
            properties.put("mail.smtp.host", stringHost);
            properties.put("mail.smtp.port", "465");
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.auth", "true");

            javax.mail.Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(stringSenderEmail, stringPasswordSenderEmail);
                }
            });

            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(email));

            mimeMessage.setSubject("M?? x??c minh email: " + otp);
            mimeMessage.setText(mess);
            mimeMessage.setFrom("DeliciousRice");
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Transport.send(mimeMessage);
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();

        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    private void showToast(String toast) {
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
    }
}