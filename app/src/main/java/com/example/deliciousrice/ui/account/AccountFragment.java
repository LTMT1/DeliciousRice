package com.example.deliciousrice.ui.account;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.deliciousrice.Activity.LoginActivity;
import com.example.deliciousrice.Activity.LoginFaGoActivity;
import com.example.deliciousrice.Activity.RegisterActivity;
import com.example.deliciousrice.MainActivity;
import com.example.deliciousrice.MainActivity2;
import com.example.deliciousrice.R;
import com.example.deliciousrice.databinding.FragmentAccountBinding;
import com.example.deliciousrice.ui.account.Activity.AddressActivity;
import com.example.deliciousrice.ui.account.Activity.ContactActivity;
import com.example.deliciousrice.ui.account.Activity.InformationActivity;
import com.example.deliciousrice.ui.account.Activity.PolicyActivity;
import com.example.deliciousrice.ui.account.Activity.ReceiptActivity;
import com.example.deliciousrice.ui.account.Activity.SettingActivity;
import com.example.deliciousrice.ui.explore.DashboardViewModel;

public class AccountFragment extends Fragment {

    private ConstraintLayout clThongtin;
    private LinearLayout llayReceipt;
    private LinearLayout llayAddress;
    private ConstraintLayout clContact;
    private ConstraintLayout clPolicy;
    private ConstraintLayout clSetting;
    private ConstraintLayout clDoipass;
    private Button tvDangXuat;
    private MainActivity2 main;

    private FragmentAccountBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        binding = FragmentAccountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        clThongtin = root.findViewById(R.id.cl_thongtin);
        llayReceipt = root.findViewById(R.id.llay_receipt);
        llayAddress = root.findViewById(R.id.llay_address);
        clContact = root.findViewById(R.id.cl_contact);
        clPolicy = root.findViewById(R.id.cl_policy);
        clSetting = root.findViewById(R.id.cl_setting);
        clDoipass = root.findViewById(R.id.cl_doipass);
        main =(MainActivity2) getActivity();
        tvDangXuat = root.findViewById(R.id.tvDangXuat);
        tvDangXuat.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), LoginActivity.class);
            Log.e("ahaah","");
            SharedPreferences preferences = getContext().getSharedPreferences("user_file", MODE_PRIVATE);
            SharedPreferences.Editor editor=preferences.edit();
            editor.clear();
            Log.e("ahaah","sss");
            startActivity(intent);
            main.finish();
        });

        clThongtin.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), InformationActivity.class);
            startActivity(intent);

        });
        llayReceipt.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), ReceiptActivity.class);
            startActivity(intent);

        });
        llayAddress.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), AddressActivity.class);
            startActivity(intent);

        });
        clContact.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), ContactActivity.class);
            startActivity(intent);

        });

        clPolicy.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), PolicyActivity.class);
            startActivity(intent);

        });

        clSetting.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), SettingActivity.class);
            startActivity(intent);

        });

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}