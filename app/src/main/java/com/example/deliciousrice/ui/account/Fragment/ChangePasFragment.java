package com.example.deliciousrice.ui.account.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.deliciousrice.Api.ApiProduct;
import com.example.deliciousrice.Api.ApiService;
import com.example.deliciousrice.R;
import com.example.deliciousrice.ui.account.AccountFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasFragment extends Fragment {
    private EditText edtPassChange;
    private EditText edtRePassChange;
    private EditText edtPass;
    String pass, str_pass, str_passnew, str_repassnew, str_email;
    private TextView btnLuupass;

    private ImageView imgBackChangei;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_change_pas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edtPassChange = view.findViewById(R.id.edtPassChange);
        edtRePassChange = view.findViewById(R.id.edtRePassChange);
        edtPass = view.findViewById(R.id.edtPass);
        btnLuupass = view.findViewById(R.id.btnLuupass);
        imgBackChangei = view.findViewById(R.id.img_back_Changei);

        imgBackChangei.setOnClickListener(v -> {

            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();

            AccountFragment fragment = new AccountFragment();
            Bundle bundle = new Bundle();
            bundle.putString("name", pass);
            bundle.putString("name1", str_email);
            fragment.setArguments(bundle);

            ft.replace(R.id.nav_host_fragment_activity_main2, fragment);
            ft.commit();

        });

        btnLuupass.setOnClickListener(v -> {
            onCLickChangepasss(v);
        });


    }

    public void onCLickChangepasss(View view) {
        Bundle bundle = getArguments();
        pass = bundle.getString("name");
        str_email = bundle.getString("name1");
        str_passnew = edtPassChange.getText().toString().trim();
        str_repassnew = edtRePassChange.getText().toString().trim();
        str_pass = edtPass.getText().toString().trim();
        if (!validatepass() || !validaterepass()) {
            return;
        } else {
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Please Wait..");
            progressDialog.setCancelable(false);
            progressDialog.show();
            ApiProduct apiProduct = ApiService.getService();
            Call<String> callback = apiProduct.changepass(str_email, str_passnew);
            callback.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
//                    SharedPreferences preferences = getContext().getSharedPreferences("user_file", MODE_PRIVATE);
//                    SharedPreferences.Editor editor = preferences.edit();
//                    editor.putString("matkhau", str_passnew);
//                    editor.commit();
                    edtPassChange.setText("");
                    edtRePassChange.setText("");
                    edtPass.setText("");
                    Toast.makeText(getContext(), "Thay ?????i m???t kh???u th??nh c??ng", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(getContext(), "Thay ?????i m???t kh???u th???t b???i", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    public boolean validatepass() {
        if (edtPassChange.getText().toString().trim().equals("")) {
            edtPassChange.setError("Nh???p m???t kh???u c???a b???n");
            return false;
        } else if (edtPassChange.length() < 6) {
            edtPassChange.setError("Nh???p m???t kh???u tr??n 6 k?? t???.");
            return false;
        } else if (!edtPass.getText().toString().trim().equalsIgnoreCase(pass)) {
            edtPass.setError("M???t kh???u c?? kh??ng ????ng");
            return false;
        } else {
            edtPassChange.setError(null);
            return true;
        }
    }

    public boolean validaterepass() {
        if (!str_repassnew.equals(str_passnew)) {
            edtRePassChange.setError("M???t kh???u nh???p l???i kh??ng ????ng");
            return false;
        } else if (pass.equals(str_passnew)) {
            edtPassChange.setError("M???t kh???u ???? s??? tr?????c ????y");
            return false;
        } else {
            edtRePassChange.setError(null);
            return true;
        }
    }
}