package com.example.deliciousrice.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.ViewUtils;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliciousrice.Model.Adderss;
import com.example.deliciousrice.R;
import com.example.deliciousrice.callback.AddressItemClick;
import com.example.deliciousrice.ui.account.Fragment.AddressFragment;

import java.util.ArrayList;

public class AdapterAddress extends RecyclerView.Adapter<AdapterAddress.AddressViewHoldel> {
    private ArrayList<Adderss> list;
    private AddressFragment fragment;
    private AddressItemClick addressItemClick;


    public AdapterAddress(ArrayList<Adderss> list, AddressFragment fragment, AddressItemClick addressItemClick) {
        this.list = list;
        this.fragment = fragment;
        this.addressItemClick = addressItemClick;
    }

    @NonNull
    @Override
    public AddressViewHoldel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address, parent, false);
        return new AdapterAddress.AddressViewHoldel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHoldel holder, int position) {
        Adderss adderss = list.get(position);
        holder.tvNameAddress.setText(adderss.getAddress_name());
        holder.tvAddress.setText(adderss.getAddress_specifically());
        holder.itemAddress.setOnClickListener(v -> {
            addressItemClick.itemAddressItemClick(adderss);

        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class AddressViewHoldel extends RecyclerView.ViewHolder {
        private ConstraintLayout itemAddress;
        private TextView tvNameAddress;
        private TextView tvAddress;

        public AddressViewHoldel(@NonNull View itemView) {
            super(itemView);
            itemAddress = itemView.findViewById(R.id.item_address);
            tvNameAddress = itemView.findViewById(R.id.tv_name_address);
            tvAddress = itemView.findViewById(R.id.tv_address);

        }
    }
}
