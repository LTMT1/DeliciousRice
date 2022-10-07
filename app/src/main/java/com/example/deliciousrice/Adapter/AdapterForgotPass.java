package com.example.deliciousrice.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.deliciousrice.Activity.ConfirmOTPActivity;
import com.example.deliciousrice.Activity.LoginActivity;
import com.example.deliciousrice.MainActivity2;
import com.example.deliciousrice.Model.Customer;
import com.example.deliciousrice.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdapterForgotPass extends RecyclerView.Adapter<AdapterForgotPass.ForgotPassHolder> {

    Context context;
    ArrayList<Customer> CustomerArrayList;

    public AdapterForgotPass(Context context, ArrayList<Customer> CustomerArrayList) {
        this.context = context;
        this.CustomerArrayList = CustomerArrayList;
    }

    @NonNull
    @Override
    public ForgotPassHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tai_khoan, parent, false);
        return new ForgotPassHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterForgotPass.ForgotPassHolder holder, @SuppressLint("RecyclerView") int position) {
        Customer timKiem = CustomerArrayList.get(position);
        Glide.with(context)
                .load(timKiem.getImage())
                .into(holder.imgCustomer);
        holder.tvusername.setText(timKiem.getUser_name());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Xác nhận tài khoản.");
                builder.setMessage("Chúng tôi sẽ gửi cho bạn mã để xác nhận tài khoản này là của bạn.");
                builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String apiForgotPass = "https://appsellrice.000webhostapp.com/Deliciousrice/API/ForgotPassword.php";
                        final ProgressDialog progressDialog = new ProgressDialog(v.getContext());
                        progressDialog.setMessage("Please Wait..");
                        progressDialog.show();
                        StringRequest request = new StringRequest(Request.Method.POST, apiForgotPass, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressDialog.dismiss();
                                Log.e(response, "opts");
                                if (response.equalsIgnoreCase("success")) {
                                    Intent intent = new Intent(context, ConfirmOTPActivity.class);
                                    intent.putExtra("data", timKiem);
                                    context.startActivity(intent);
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.dismiss();
                                Toast.makeText(context, "xảy ra lỗi!", Toast.LENGTH_SHORT).show();
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<String, String>();
                                Log.e(timKiem.getEmail(), " timKiem.getEmail()");
                                params.put("email", timKiem.getEmail());
                                return params;

                            }
                        };

                        RequestQueue requestQueue = Volley.newRequestQueue(context);
                        requestQueue.add(request);
                    }
                });
                builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });

    }


    @Override
    public int getItemCount() {
        return CustomerArrayList.size();
    }

    public class ForgotPassHolder extends RecyclerView.ViewHolder {
        ImageView imgCustomer;
        TextView tvusername;
        CardView cardView;

        public ForgotPassHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.CarviewCustomer);
            imgCustomer = (ImageView) itemView.findViewById(R.id.ImgCustomer);
            tvusername = (TextView) itemView.findViewById(R.id.tvUsernameCustomer);
        }
    }
}