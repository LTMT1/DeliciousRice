package com.example.deliciousrice.ui.account.Fragment;

import static com.facebook.FacebookSdk.getApplicationContext;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.deliciousrice.Api.ApiProduct;
import com.example.deliciousrice.Api.ApiService;
import com.example.deliciousrice.R;
import com.example.deliciousrice.ui.account.AccountFragment;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;


public class InformationFragment extends Fragment {

    private CircleImageView profileImage;
    private EditText editTextSDT;
    private EditText editTextdate;
    private EditText editTextname;
    private ImageView imgBackInformation;
    private TextView btnedit;
    private TextView btnsave;

    private static final int GALLERY = 1, CAMERA = 2;
    Bitmap FixBitmap;

    int id;
    String name, name1, name2, name3;
    private int RESULT_CANCELED;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_information, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Anhxa(view);
        requestPermissions();
        setview();
        calenderDate();
        UpdateCustomer();
        imgBackInformation.setOnClickListener(v -> {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            AccountFragment informationFragment = new AccountFragment();

            Bundle bundle = new Bundle();
            bundle.putInt("id", id);
            bundle.putString("name", name);
            bundle.putString("name1", name1);
            bundle.putString("name2", name2);
            bundle.putString("name3", name3);
            informationFragment.setArguments(bundle);

            ft.replace(R.id.nav_host_fragment_activity_main2, informationFragment);
            ft.commit();
        });

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImage();

            }
        });
        btnsave.setVisibility(View.GONE);
    }

    private void openImage() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(getContext());
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Photo Gallery",
                "Camera"};
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
                    FixBitmap = MediaStore.Images.Media.getBitmap(this.getActivity().getContentResolver(), contentURI);
                    profileImage.setImageBitmap(FixBitmap);
                    uploadImage(FixBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            FixBitmap = (Bitmap) data.getExtras().get("data");
            profileImage.setImageBitmap(FixBitmap);
            uploadImage(FixBitmap);
            Toast.makeText(getApplicationContext(), "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
        String imgname = String.valueOf(Calendar.getInstance().getTimeInMillis());

        ApiProduct apiProduct = ApiService.getService();
        Call<String> callback = apiProduct.imgUpload(id, imgname, encodedImage);
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void requestPermissions() {
        Dexter.withActivity(getActivity())
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

    private void Anhxa(View view) {
        profileImage = view.findViewById(R.id.profile_image);
        editTextSDT = view.findViewById(R.id.editTextSDT);
        editTextdate = view.findViewById(R.id.editTextdate);
        editTextname = view.findViewById(R.id.editTextname);
        imgBackInformation = view.findViewById(R.id.img_back_Information);
        btnedit = view.findViewById(R.id.btnedit);
        btnsave = view.findViewById(R.id.btnsave);

    }

    private void setview() {
        Bundle bundle = getArguments();
        id = bundle.getInt("id", 0);
        String name = bundle.getString("name");
        String name1 = bundle.getString("name1");
        String name2 = bundle.getString("name2");
        String name3 = bundle.getString("name3");

        editTextname.setText(name);
        editTextdate.setText(name1);
        editTextSDT.setText(name2);
        Glide.with(getApplicationContext()).load(name3).centerCrop().into(profileImage);
    }


    private void updateName(int ida, String name, String birthday, String sdt) {
        try {
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Please Wait..");
            progressDialog.setCancelable(false);
            progressDialog.show();

            ApiProduct apiProduct = ApiService.getService();
            Call<String> callback = apiProduct.updatename(ida, name, birthday, sdt);
            callback.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                    Toast.makeText(getContext(), "S???a th??nh c??ng", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(getContext(), "S???a th???t b???i", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        }catch (Exception e){

        }
    }

    private void calenderDate() {
        editTextdate.setOnClickListener(view -> {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, day);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy ");
                    editTextdate.setText(simpleDateFormat.format(calendar.getTime()));
                }
            };
            new DatePickerDialog(getContext(), onDateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();

        });
    }

    private void UpdateCustomer() {
        btnedit.setOnClickListener(view -> {
            btnedit.setVisibility(View.GONE);
            editTextSDT.setEnabled(true);
            editTextdate.setEnabled(true);
            editTextname.setEnabled(true);
            btnsave.setVisibility(View.VISIBLE);
        });
        btnsave.setOnClickListener(view -> {
            if (!validatePhone() || !validateName()) {
                return;
            } else {
                String sdt = editTextSDT.getText().toString().trim();
                String name = editTextname.getText().toString().trim();
                String birthday = editTextdate.getText().toString().trim();
                updateName(id, name, birthday, sdt);
                editTextSDT.setEnabled(false);
                editTextdate.setEnabled(false);
                editTextname.setEnabled(false);
                btnsave.setVisibility(View.GONE);
                btnedit.setVisibility(View.VISIBLE);
            }
        });
    }

    public boolean validateName() {
        if (editTextname.getText().toString().trim().equals("")) {
            editTextname.setError("H??y nh???p t??n c???a b???n.");
            return false;
        } else {
            editTextname.setError(null);
            return true;
        }
    }

    public boolean validatePhone() {
//        String a = "(((\\+|)84)|0)(3|5|7|8|9)+([0-9]{8,15})\\b";
        String a = "^0[0-9]{9,15}$";
        if (editTextSDT.getText().toString().trim().equals("")) {
            editTextSDT.setError("H??y nh???p s??? ??i???n tho???i c???a b???n.");
            return false;
        } else if (!editTextSDT.getText().toString().trim().matches(a)) {
            editTextSDT.setError("Nh???p ????ng ?????nh d???ng s??? ??i???n tho???i.");
            return false;
        } else {
            editTextSDT.setError(null);
            return true;
        }
    }

}