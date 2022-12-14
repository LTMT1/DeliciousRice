package com.example.deliciousrice.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.deliciousrice.Adapter.AdapterForgotPass;
import com.example.deliciousrice.Model.Customer;
import com.example.deliciousrice.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ForgotPassActivity extends AppCompatActivity {
    RecyclerView rclTimKiem;
    ArrayList<Customer> CustomerArrayList;
    AdapterForgotPass adapterForgotPass;
    private SearchView searchViewcustomer;
    ConstraintLayout constraintLayout;
    private ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);
        BarColor.setStatusBarColor(this);
        searchViewcustomer = findViewById(R.id.searchViewcustomer);
        constraintLayout = findViewById(R.id.Seachtk);
        rclTimKiem = findViewById(R.id.rclviewseach);
        ivBack = findViewById(R.id.ivBack);
        BackToLogin();


        searchViewcustomer.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                onCLickForgotPassword(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        CustomerArrayList = new ArrayList<>();
        adapterForgotPass = new AdapterForgotPass(getApplicationContext(), CustomerArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        rclTimKiem.setLayoutManager(linearLayoutManager);
        rclTimKiem.setAdapter(adapterForgotPass);
    }

    public void BackToLogin() {
        ivBack.setOnClickListener(view -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });

    }

    public void onCLickForgotPassword( String seach) {
        CustomerArrayList.clear();
        String apiforgotpass = "http://chucdong.com/Deliciousrice/API/FindAccount.php";
        final ProgressDialog progressDialog = new ProgressDialog(ForgotPassActivity.this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
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
                                    object.getString("id_application"),
                                    object.getString("user_name"),
                                    object.getString("image"),
                                    object.getString("birthday"),
                                    object.getString("phone_number"),
                                    object.getString("address"),
                                    object.getString("email"),
                                    object.getString("password")
                            ));
                            adapterForgotPass.notifyDataSetChanged();
                            constraintLayout.setVisibility(View.INVISIBLE);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Kh??ng t??m th???y t??i kho???n c???a b???n.", Toast.LENGTH_SHORT).show();
                        constraintLayout.setVisibility(View.VISIBLE);
                        adapterForgotPass.notifyDataSetChanged();
                        CustomerArrayList.clear();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "x???y ra l???i!", Toast.LENGTH_SHORT).show();
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