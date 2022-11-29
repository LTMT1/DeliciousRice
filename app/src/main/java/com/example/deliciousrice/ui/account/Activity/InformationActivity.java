package com.example.deliciousrice.ui.account.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
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
import com.example.deliciousrice.Adapter.AdapterSearchProduct;
import com.example.deliciousrice.Api.ApiProduct;
import com.example.deliciousrice.Api.ApiService;
import com.example.deliciousrice.MainActivity2;
import com.example.deliciousrice.Model.Product;
import com.example.deliciousrice.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class InformationActivity extends AppCompatActivity {
    private CircleImageView profileImage;
    private EditText editTextSDT;
    private EditText editTextdate;
    private EditText editTextname;
    private TextView tvsave;


    int click = 0;
    private static final int GALLERY = 1, CAMERA = 2;
    Bitmap FixBitmap;

    int id;
    private Uri imageUri;
    Bitmap bitmap;
    String encodedImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        Anhxa();
        requestPermissions();
        setview();
        calenderDate();
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImage();

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
            AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
            pictureDialog.setTitle("Select Action");
            String[] pictureDialogItems = {
                    "Photo Gallery",
                    "Camera" };
            pictureDialog.setItems(pictureDialogItems,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case 0:
                                    choosePhotoFromGallary();
                                    break;
                                case 1:
                                    takePhotoFromCamera();
                                    break;
                            }
                        }
                    });
            pictureDialog.show();
    }
    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    FixBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    // String path = saveImage(bitmap);
                    //Toast.makeText(MainActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    profileImage.setImageBitmap(FixBitmap);
//                    UploadImageOnServerButton.setVisibility(View.VISIBLE);
                    uploadImage(FixBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(InformationActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            FixBitmap = (Bitmap) data.getExtras().get("data");
            profileImage.setImageBitmap(FixBitmap);
            uploadImage(FixBitmap);
//            UploadImageOnServerButton.setVisibility(View.VISIBLE);
            //  saveImage(thumbnail);
            //Toast.makeText(ShadiRegistrationPart5.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }
    private void uploadImage(Bitmap bitmap){

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
        String imgname = String.valueOf(Calendar.getInstance().getTimeInMillis());

        ApiProduct apiProduct = ApiService.getService();
        Call<String> callback = apiProduct.imgUpload(id,imgname,encodedImage);
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call,retrofit2.Response<String> response) {

            }

            @Override
            public void onFailure(Call<String>  call, Throwable t) {

            }
        });
    }
    private void  requestPermissions(){
        Dexter.withActivity(this)
                .withPermissions(

                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(getApplicationContext(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                        }
                        if (report.isAnyPermissionPermanentlyDenied()) {

                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }


                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
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

    private void calenderDate(){
        editTextdate.setOnClickListener(view -> {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    calendar.set(Calendar.YEAR,year);
                    calendar.set(Calendar.MONTH,month);
                    calendar.set(Calendar.DAY_OF_MONTH,day);

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy ");
                    editTextdate.setText(simpleDateFormat.format(calendar.getTime()));
                }
            };
            new DatePickerDialog(InformationActivity.this,onDateSetListener,calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();

        });
    }
}