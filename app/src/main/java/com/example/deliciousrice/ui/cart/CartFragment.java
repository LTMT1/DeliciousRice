package com.example.deliciousrice.ui.cart;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliciousrice.Adapter.AdapterCart;
import com.example.deliciousrice.MainActivity2;
import com.example.deliciousrice.Model.Cart;
import com.example.deliciousrice.R;
import com.example.deliciousrice.ui.shop.Activity.PayActivity;
import com.example.deliciousrice.ui.shop.ShopFragment;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartFragment extends Fragment {

    public static ConstraintLayout textviewthongbao;
    static RecyclerView RClViewgiohang;
    static TextView tvtongtien;
    private ConstraintLayout thanhtoan;
    public static MainActivity2 main;
    public static AdapterCart adapterCart;
    public static int total_money = 0;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        Anhxa(view);
        main = (MainActivity2) getActivity();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        RClViewgiohang.setLayoutManager(linearLayoutManager);
        Updatelist();
        CheckData();
        UpdateTongTien();
        /*Bundle bundle=getArguments();
        int idadr = bundle.getInt("image", 0);*/
        return view;
    }

    public static void CheckData() {
        if (ShopFragment.Cartlist.size() <= 0) {
            adapterCart.notifyDataSetChanged();
            textviewthongbao.setVisibility(View.VISIBLE);
            RClViewgiohang.setVisibility(View.INVISIBLE);
        } else {
            adapterCart.notifyDataSetChanged();
            textviewthongbao.setVisibility(View.INVISIBLE);
            RClViewgiohang.setVisibility(View.VISIBLE);
        }
    }

    private void Updatelist() {
        ShopFragment.Cartlist = (ArrayList<Cart>) MainActivity2.daoCart.getall();
        adapterCart = new AdapterCart(ShopFragment.Cartlist, getActivity(), this);
        RClViewgiohang.setAdapter(adapterCart);
    }

    public static void UpdateTongTien() {
        total_money = 0;
        for (int i = 0; i < ShopFragment.Cartlist.size(); i++) {
            total_money += ShopFragment.Cartlist.get(i).getPrice();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tvtongtien.setText(decimalFormat.format(total_money) + " VND");
    }

    private void Anhxa(View view) {
        textviewthongbao = view.findViewById(R.id.textviewthongbao);
        RClViewgiohang = view.findViewById(R.id.lvgiohang);
        tvtongtien = view.findViewById(R.id.tvtongtien);
        thanhtoan = view.findViewById(R.id.thanhtoan);
        thanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ShopFragment.Cartlist.size() > 0) {
                    Intent intent = new Intent(getContext(), PayActivity.class);
                    intent.putExtra("id_customer", main.getId_customer());
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), "Giỏ hàng không có sản phầm nào!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void DeleteProduct(final int id) {
        MainActivity2.daoCart.DeleteCart(id);
        Updatelist();
        MainActivity2.setBugdeNumber();
    }
}