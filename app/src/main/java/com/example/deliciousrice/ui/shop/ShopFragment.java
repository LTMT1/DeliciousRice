package com.example.deliciousrice.ui.shop;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.deliciousrice.R;
import com.example.deliciousrice.databinding.FragmentShopBinding;

import java.util.ArrayList;

public class ShopFragment extends Fragment {
    private ImageSlider imgSilde;



    private FragmentShopBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentShopBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imgSilde = view.findViewById(R.id.imgSilde);
        ArrayList<SlideModel> models=new ArrayList<>();
        models.add(new SlideModel(R.drawable.img_silde1,null));
        models.add(new SlideModel(R.drawable.img_silde2,null));
        models.add(new SlideModel(R.drawable.img_silde3,null));
        models.add(new SlideModel(R.drawable.img_silde4,null));
        models.add(new SlideModel(R.drawable.img_silde5,null));
        imgSilde.setImageList(models, ScaleTypes.CENTER_CROP);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}