package com.example.deliciousrice.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.deliciousrice.R;
import com.example.deliciousrice.databinding.FragmentAccountBinding;
import com.example.deliciousrice.ui.account.Activity.AddressActivity;
import com.example.deliciousrice.ui.account.Activity.ChangeiPassActivity;
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
    private TextView tvDangXuat;





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

        tvDangXuat = root.findViewById(R.id.tvDangXuat);

        clThongtin.setOnClickListener( view -> {
            Intent intent=new Intent(getContext(), InformationActivity.class);
            startActivity(intent);

        });
        llayReceipt.setOnClickListener( view -> {
            Intent intent=new Intent(getContext(), ReceiptActivity.class);
            startActivity(intent);

        });
        llayAddress.setOnClickListener( view -> {
            Intent intent=new Intent(getContext(), AddressActivity.class);
            startActivity(intent);

        });
        clContact.setOnClickListener( view -> {
            Intent intent=new Intent(getContext(), ContactActivity.class);
            startActivity(intent);

        });

        clPolicy.setOnClickListener( view -> {
            Intent intent=new Intent(getContext(), PolicyActivity.class);
            startActivity(intent);

        });

        clDoipass.setOnClickListener( view -> {
            Intent intent=new Intent(getContext(), ChangeiPassActivity.class);
            startActivity(intent);

        });
        clSetting.setOnClickListener( view -> {
            Intent intent=new Intent(getContext(), SettingActivity.class);
            startActivity(intent);

        });

        tvDangXuat.setOnClickListener(view -> {

        });

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}