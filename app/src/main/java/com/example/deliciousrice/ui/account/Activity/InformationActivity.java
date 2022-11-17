package com.example.deliciousrice.ui.account.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.deliciousrice.Activity.ChangePassActivity;
import com.example.deliciousrice.Api.ApiProduct;
import com.example.deliciousrice.Api.ApiService;
import com.example.deliciousrice.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;

public class InformationActivity extends AppCompatActivity {
    private CircleImageView profileImage;
    private EditText editTextSDT;
    private EditText editTextdate;
    private EditText editTextname;
    private TextView tvsave;


    int click = 0;
    private static final int IMAGE_REQUEST = 1;
    int id;
    private Uri imageUri;
    Bitmap bitmap;
    String encodedImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        Anhxa();
        setview();
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImage();
                StringRequest request = new StringRequest(Request.Method.POST, "https://appsellrice.000webhostapp.com/Deliciousrice/API/Uploadimage.php"
                        , new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(InformationActivity.this, response, Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(InformationActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("image", encodedImage);
                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(InformationActivity.this);
                requestQueue.add(request);
            }
        });
        tvsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (click != 0){
                    editTextSDT.setEnabled(false);
                    editTextdate.setEnabled(false);
                    editTextname.setEnabled(false);
                    tvsave.setText("Chỉnh sửa");
                    String name=editTextname.getText().toString().trim();
                    String sdt =editTextSDT.getText().toString().trim();
                    String birthday =editTextdate.getText().toString().trim();
                    updateCustomer(id,name,sdt,birthday);
                    click++;
                }else{
                    editTextSDT.setEnabled(true);
                    editTextdate.setEnabled(true);
                    editTextname.setEnabled(true);
                    tvsave.setText("Lưu");
                    click--;
                }

            }
        });
    }
    private void openImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == 1 && resultCode == RESULT_OK && data!=null){

            Uri filePath = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(filePath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                profileImage.setImageBitmap(bitmap);
                imageStore(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void imageStore(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
        byte[] imageBytes = stream.toByteArray();
        encodedImage = android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    private void Anhxa() {
        profileImage = findViewById(R.id.profile_image);
        editTextSDT = findViewById(R.id.editTextSDT);
        editTextdate = findViewById(R.id.editTextdate);
        editTextname = findViewById(R.id.editTextname);
        tvsave = findViewById(R.id.tvsave);
    }
    private void setview(){
        Intent intent=getIntent();
        id=intent.getIntExtra("id",0);
        String name=intent.getStringExtra("name");
        String name1=intent.getStringExtra("name1");
        String name2=intent.getStringExtra("name2");
        String name3=intent.getStringExtra("name3");

        editTextname.setText(name);
        editTextdate.setText(name1);
        editTextSDT.setText(name2);
        Glide.with(getApplicationContext()).load(name3).centerCrop().into(profileImage);
    }
    private void updateCustomer(int ida, String name,String sdt,String birthday ){
        final ProgressDialog progressDialog = new ProgressDialog(InformationActivity.this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiProduct apiProduct = ApiService.getService();
        Call<String> callback = apiProduct.updatecustomer(ida,name,sdt,birthday);
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                Toast.makeText(InformationActivity.this, "Sửa thành công", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(InformationActivity.this, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
}