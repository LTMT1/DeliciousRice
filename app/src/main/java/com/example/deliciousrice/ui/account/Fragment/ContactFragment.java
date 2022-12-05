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

public class ContactFragment extends Fragment {
    private ImageView imgBackContact;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contact, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imgBackContact = view.findViewById(R.id.img_back_Contact);
        imgBackContact.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_contactFragment_to_accountFragment);
        });
    }
}