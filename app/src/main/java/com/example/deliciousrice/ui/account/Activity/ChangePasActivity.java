package com.example.deliciousrice.ui.account.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.accounts.Account;
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
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.deliciousrice.Activity.LoginActivity;
import com.example.deliciousrice.Adapter.AdapterFavorite;
import com.example.deliciousrice.Api.ApiProduct;
import com.example.deliciousrice.Api.ApiService;
import com.example.deliciousrice.Model.Customer;
import com.example.deliciousrice.Model.Favorite;
import com.example.deliciousrice.R;
import com.example.deliciousrice.ui.account.AccountFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasActivity extends AppCompatActivity {

    private EditText edtPassChange;
    private EditText edtRePassChange;
    private EditText edtPass;
    String pass, str_pass, str_passnew, str_repassnew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass2);
        edtPassChange = findViewById(R.id.edtPassChange);
        edtRePassChange = findViewById(R.id.edtRePassChange);
        edtPass = findViewById(R.id.edtPass);
    }

    public void onCLickChangepass(View view) {
        Intent intent = getIntent();
        pass = intent.getStringExtra("name");
        String str_email = intent.getStringExtra("name1");
        str_passnew = edtPassChange.getText().toString().trim();
        str_repassnew = edtRePassChange.getText().toString().trim();
        str_pass = edtPass.getText().toString().trim();
        if (!validatepass() || !validaterepass()) {
            return;
        } else {
            final ProgressDialog progressDialog = new ProgressDialog(ChangePasActivity.this);
            progressDialog.setMessage("Please Wait..");
            progressDialog.setCancelable(false);
            progressDialog.show();
            ApiProduct apiProduct = ApiService.getService();
            Call<String> callback = apiProduct.changepass(str_email, str_passnew);
            callback.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    SharedPreferences preferences = getSharedPreferences("user_file", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("matkhau", str_passnew);
                    editor.commit();
                    edtPassChange.setText("");
                    edtRePassChange.setText("");
                    edtPass.setText("");
                    Toast.makeText(ChangePasActivity.this, "Thay đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(ChangePasActivity.this, "Thay đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    public boolean validatepass() {
        if (edtPassChange.getText().toString().trim().equals("")) {
            edtPassChange.setError("Nhập mật khẩu của bạn");
            return false;
        } else if (edtPassChange.length() < 6) {
            edtPassChange.setError("Nhập mật khẩu trên 6 kí tự.");
            return false;
        } else if (!edtPass.getText().toString().trim().equalsIgnoreCase(pass)) {
            edtPass.setError("Mật khẩu cũ không đúng");
            return false;
        } else {
            edtPassChange.setError(null);
            return true;
        }
    }

    public boolean validaterepass() {
        if (!str_repassnew.equals(str_passnew)) {
            edtRePassChange.setError("Mật khẩu nhập lại không đúng");
            return false;
        } else if (pass.equals(str_passnew)) {
            edtPassChange.setError("Mật khẩu đã sử trước đây");
            return false;
        } else {
            edtRePassChange.setError(null);
            return true;
        }
    }
}