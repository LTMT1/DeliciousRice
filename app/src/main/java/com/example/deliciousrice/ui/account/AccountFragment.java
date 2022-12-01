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
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.deliciousrice.Model.Customer;
import com.example.deliciousrice.Activity.LoginActivity;
import com.example.deliciousrice.MainActivity2;
import com.example.deliciousrice.R;
import com.example.deliciousrice.ui.account.Fragment.AddressFragment;
import com.example.deliciousrice.ui.account.Fragment.ChangePasFragment;
import com.example.deliciousrice.ui.account.Fragment.InformationFragment;
import com.example.deliciousrice.ui.account.Fragment.ReceipFragment;

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
    Customer customer;
    FragmentTransaction transection;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View views = inflater.inflate(R.layout.fragment_account, container, false);
        main = (MainActivity2) getActivity();
        Anhxa(views);
        setviewaccount();
        main.updateMain();
        return views;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        customer = new Customer();
        transection = getFragmentManager().beginTransaction();


        tvDangXuat.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), LoginActivity.class);
            SharedPreferences.Editor editor = getContext().getSharedPreferences("user_file", MODE_PRIVATE).edit();
            editor.clear().commit();
            startActivity(intent);
        });


        clThongtin.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_accountFragment_to_informationFragment);
            Bundle bundle  = new Bundle();
            InformationFragment informationFragment=new InformationFragment();
            bundle.putString("id", String.valueOf(main.getId_customer()));
            bundle.putString("name", main.getUser_name());
            bundle.putString("name1", main.getBirthday());
            bundle.putString("name2", main.getPhone_number());
            bundle.putString("name3", main.getImage());
            informationFragment.setArguments(bundle);
            transection.replace(R.id.nav_host_fragment_activity_main2, informationFragment);
            transection.commit();

        });

        llayReceipt.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_accountFragment_to_settingFragment);
            ReceipFragment fragment = new ReceipFragment();
            Bundle bundle = new Bundle();
            bundle.putString("id_cus", String.valueOf(main.getId_customer()));
            fragment.setArguments(bundle);
            transection.replace(R.id.nav_host_fragment_activity_main2, fragment);
            transection.commit();

        });

        /// địa chỉ
        llayAddress.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_accountFragment_to_addressFragment);
            AddressFragment addfragment=new AddressFragment();
            Bundle bundle = new Bundle();
            bundle.putString("Adrress", String.valueOf(main.getId_customer()));
            addfragment.setArguments(bundle);
            transection.replace(R.id.nav_host_fragment_activity_main2, addfragment);
            transection.commit();

        });
        clContact.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_accountFragment_to_contactFragment);
        });
        clPolicy.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_accountFragment_to_policFragment);

        });
        clSetting.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_accountFragment_to_settingFragment);
        });


        clDoipass.setOnClickListener(view1 -> {
            Navigation.findNavController(view).navigate(R.id.action_accountFragment_to_changePasFragment);
            ChangePasFragment  fragment=new ChangePasFragment();
            Bundle bundle = new Bundle();
            bundle.putString("name", main.getPasss());
            bundle.putString("name1", main.getEmaill());
            fragment.setArguments(bundle);
            transection.replace(R.id.nav_host_fragment_activity_main2, fragment);
            transection.commit();

        });
    }
    private void Anhxa(@NonNull View view) {
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

    private void setviewaccount() {
        tvname.setText(main.getUser_name());
        Glide.with(getActivity()).load(main.getImage()).centerCrop().into(cardView);
    }
}