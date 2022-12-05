package com.example.deliciousrice.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.deliciousrice.Model.Product;
import com.example.deliciousrice.R;
import com.example.deliciousrice.callback.ProductItemClick;
import com.example.deliciousrice.ui.shop.Activity.ViewAllHotActivity;
import com.example.deliciousrice.ui.shop.Activity.ViewAllNewActivity;

import java.util.ArrayList;

public class AdapterViewAllHot extends RecyclerView.Adapter<AdapterViewAllHot.ProductViewAllHolder> {

    private ArrayList<Product> data;
    private ViewAllHotActivity context;
    private ProductItemClick productItemClick;

    public AdapterViewAllHot(ArrayList<Product> data, ViewAllHotActivity context, ProductItemClick productItemClick) {
        this.data = data;
        this.context = context;
        this.productItemClick = productItemClick;
    }

    @NonNull
    @Override
    public ProductViewAllHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_viewallhot_products, parent, false);
        return new ProductViewAllHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewAllHolder holder, int position) {
        Product product = data.get(position);
        Glide.with(context).load(product.getImage()).centerCrop().into(holder.imgVallImgsp);
        holder.tvVallTensp.setText(product.getProduct_name());
        holder.tvVallTime.setText(product.getProcessing_time());
        holder.tvVallGia.setText(String.valueOf(product.getPrice()));
        holder.layoutViewAll.setOnClickListener(v -> {
            productItemClick.itemProductClick(product);
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ProductViewAllHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout layoutViewAll;
        private ImageView imgVallImgsp;
        private TextView tvVallTensp;
        private TextView tvVallTime;
        private TextView tvVallGia;
        private ImageView imgVallAdd;


        public ProductViewAllHolder(@NonNull View itemView) {
            super(itemView);
            layoutViewAll = itemView.findViewById(R.id.layout_ViewAll);
            imgVallImgsp = itemView.findViewById(R.id.img_vall_imgsp);
            tvVallTensp = itemView.findViewById(R.id.tv_vall_tensp);
            tvVallTime = itemView.findViewById(R.id.tv_vall_time);
            tvVallGia = itemView.findViewById(R.id.tv_vall_gia);
            imgVallAdd = itemView.findViewById(R.id.img_vall_add);
        }
    }
}
