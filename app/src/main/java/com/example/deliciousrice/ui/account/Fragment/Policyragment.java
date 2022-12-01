package com.example.deliciousrice.ui.account.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.deliciousrice.R;

public class Policyragment extends Fragment {

    private ImageView imgBackPolicy;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_polic, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imgBackPolicy = view.findViewById(R.id.img_back_policy);
        imgBackPolicy.setOnClickListener(v->{
            Navigation.findNavController(view).navigate(R.id.action_policFragment_to_accountFragment);
        });
    }
}