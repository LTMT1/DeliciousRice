package com.example.deliciousrice.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.deliciousrice.Model.Product;
import com.example.deliciousrice.R;

import java.util.ArrayList;

public class AdapterSearchProduct extends  RecyclerView.Adapter<AdapterSearchProduct.SearchproductViewHolder> {

    private ArrayList<Product> list;
    private Context context;



    public AdapterSearchProduct(ArrayList<Product> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public SearchproductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_products,parent,false);
        return new AdapterSearchProduct.SearchproductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchproductViewHolder holder, int position) {
        Product product = list.get(position);
        holder.tvSeTensp.setText(product.getProduct_name());
        Glide.with(context).load(product.getImage()).centerCrop().into(holder.imgSeImgsp);
        holder.tvSeTiem.setText(product.getProcessing_time());
        holder.tvSeGia.setText(String.valueOf(product.getPrice())+" đ");



    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class SearchproductViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout layoutSeach;
        private ImageView imgSeImgsp;
        private TextView tvSeTensp;
        private TextView tvSeTiem;
        private TextView tvSeGia;
        private ImageView imgSeAdd;
        public SearchproductViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutSeach = itemView.findViewById(R.id.layout_seach);
            imgSeImgsp = itemView.findViewById(R.id.img_se_imgsp);
            tvSeTensp = itemView.findViewById(R.id.tv_se_tensp);
            tvSeTiem = itemView.findViewById(R.id.tv_se_tiem);
            tvSeGia = itemView.findViewById(R.id.tv_se_gia);
            imgSeAdd = itemView.findViewById(R.id.img_se_add);

        }
    }
}

