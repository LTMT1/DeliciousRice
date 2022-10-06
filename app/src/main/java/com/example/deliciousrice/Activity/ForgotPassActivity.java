package com.example.deliciousrice.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.example.deliciousrice.Adapter.AdapterForgotPass;
import com.example.deliciousrice.MainActivity2;
import com.example.deliciousrice.Model.Customer;
import com.example.deliciousrice.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ForgotPassActivity extends AppCompatActivity {
    ImageButton imgbtseach;
    private EditText edtEmailQuenMK;
    RecyclerView rclTimKiem;
    ArrayList<Customer> CustomerArrayList;
    AdapterForgotPass adapterForgotPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);
        BarColor.setStatusBarColor(this);
        edtEmailQuenMK = findViewById(R.id.edtEmailQuenMK);

    }

    public void onCLickForgotPassword(View view){
        String seach = edtEmailQuenMK.getText().toString().trim();
        if (!seach.equals("")) {
            edtEmailQuenMK.setError("Hãy nhập gmail của bạn.");
            return;
        } else {
            String apiforgotpass = "https://appsellrice.000webhostapp.com/Deliciousrice/API/FindAccount.php";
            final ProgressDialog progressDialog = new ProgressDialog(ForgotPassActivity.this);
            progressDialog.setMessage("Please Wait..");
            progressDialog.show();
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, apiforgotpass, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        progressDialog.dismiss();
                        JSONObject jsonObject = new JSONObject(response);
                        String result = jsonObject.getString("status");
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        if (result.equals("thanh cong")) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                CustomerArrayList.add(new Customer(
                                        object.getInt("id_customer"),
                                        object.getString("user_name"),
                                        object.getString("image"),
                                        object.getString("birthday"),
                                        object.getString("phone_number"),
                                        object.getString("address"),
                                        object.getString("email"),
                                        object.getString("password")
                                ));
                                adapterForgotPass.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Không tìm thấy tài khoản của bạn.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
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
                    Map<String, String> params = new HashMap<>();
                    params.put("email", seach);
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        }
    }
}