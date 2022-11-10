package com.example.deliciousrice.ui.account;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.deliciousrice.Activity.ChangePassActivity;
import com.example.deliciousrice.Activity.LoginActivity;
import com.example.deliciousrice.MainActivity2;
import com.example.deliciousrice.R;
import com.example.deliciousrice.databinding.FragmentAccountBinding;
import com.example.deliciousrice.ui.account.Activity.AddressActivity;
import com.example.deliciousrice.ui.account.Activity.ContactActivity;
import com.example.deliciousrice.ui.account.Activity.InformationActivity;
import com.example.deliciousrice.ui.account.Activity.PolicyActivity;
import com.example.deliciousrice.ui.account.Activity.ReceiptActivity;
import com.example.deliciousrice.ui.account.Activity.SettingActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountFragment extends Fragment {

    private ConstraintLayout clThongtin;
    private TextView tvname;
    private CircleImageView cardView;
    private ImageView imageView8;
    private LinearLayout llayReceipt;
    private LinearLayout llayAddress;
    private ConstraintLayout clContact;
    private ConstraintLayout clPolicy;
    private ConstraintLayout clSetting;
    private ConstraintLayout clDoipass;
    private TextView tvDangXuat;
    private MainActivity2 main;
    private FragmentAccountBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View views = inflater.inflate(R.layout.fragment_account, container, false);
        main =(MainActivity2) getActivity();
        Anhxa(views);
        setviewaccount();
        main.updateMain();
        return views;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvDangXuat.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), LoginActivity.class);
            SharedPreferences.Editor editor = getContext().getSharedPreferences("user_file", MODE_PRIVATE).edit();
            editor.clear().commit();
            startActivity(intent);
        });

        clThongtin.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), InformationActivity.class);
            intent.putExtra("name",main.getUser_name());
            intent.putExtra("name1",main.getBirthday());
            intent.putExtra("name2",main.getPhone_number());
            intent.putExtra("name3",main.getImage());
            startActivity(intent);
        });
        llayReceipt.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ReceiptActivity.class);
            startActivity(intent);

        });
        llayAddress.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), AddressActivity.class);
            startActivity(intent);

        });
        clContact.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ContactActivity.class);
            startActivity(intent);

        });

        clPolicy.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), PolicyActivity.class);
            startActivity(intent);

        });

        clSetting.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), SettingActivity.class);
            startActivity(intent);

        });
        clDoipass.setOnClickListener(view1 -> {
            Intent intent = new Intent(getContext(), ChangePassActivity.class);
            startActivity(intent);
        });
    }

    private void Anhxa(@NonNull View view){
        tvname = view.findViewById(R.id.tvname);
        cardView = view.findViewById(R.id.cardView);
        imageView8 = view.findViewById(R.id.imageView8);
        clThongtin = view.findViewById(R.id.cl_thongtin);
        llayReceipt = view.findViewById(R.id.llay_receipt);
        llayAddress = view.findViewById(R.id.llay_address);
        clContact = view.findViewById(R.id.cl_contact);
        clPolicy = view.findViewById(R.id.cl_policy);
        clSetting = view.findViewById(R.id.cl_setting);
        clDoipass = view.findViewById(R.id.cl_doipass);
        tvDangXuat = view.findViewById(R.id.tvDangXuat);
    }
    private void setviewaccount(){
        tvname.setText(main.getUser_name());
        Glide.with(getActivity()).load(main.getImage()).centerCrop().into(cardView);
    }
}