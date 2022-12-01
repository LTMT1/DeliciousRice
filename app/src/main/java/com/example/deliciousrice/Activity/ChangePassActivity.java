package com.example.deliciousrice.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.deliciousrice.Api.ApiProduct;
import com.example.deliciousrice.Api.ApiService;
import com.example.deliciousrice.R;

import retrofit2.Call;
import retrofit2.Callback;

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
        if (!validatepass() || !validaterepass()) {
            return;
        } else {
            final ProgressDialog progressDialog = new ProgressDialog(ChangePassActivity.this);
            progressDialog.setMessage("Please Wait..");
            progressDialog.setCancelable(false);
            progressDialog.show();
            ApiProduct apiProduct = ApiService.getService();
            Call<String> callback = apiProduct.changepass(str_email, str_passnew);
            callback.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                    progressDialog.dismiss();
                    if(response.body().equals("success")) {
                        SharedPreferences preferences = getSharedPreferences("user_file", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("matkhau", str_passnew);
                        editor.commit();
                        edtPassChange.setText("");
                        edtRePassChange.setText("");
                        Intent intent1 = new Intent(ChangePassActivity.this, LoginActivity.class);
                        startActivity(intent1);
                        Toast.makeText(ChangePassActivity.this, "Thay đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(ChangePassActivity.this, "Thay đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(ChangePassActivity.this, "Lỗi kết nối tới sever!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    public void Back(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
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