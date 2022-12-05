package com.example.deliciousrice.Adapter;

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
import com.example.deliciousrice.callback.ProductItemClick;
import com.example.deliciousrice.callback.ProductNewItemClick;
import com.example.deliciousrice.ui.shop.ShopFragment;

import java.util.ArrayList;

public class AdapterProductNew extends RecyclerView.Adapter<AdapterProductNew.ProductNewViewHolder> {

    private ArrayList<Product> data;
    private ShopFragment context;
    private ProductNewItemClick productNewItemClick;

    public AdapterProductNew(ArrayList<Product> data, ShopFragment context, ProductNewItemClick productNewItemClick) {
        this.data = data;
        this.context = context;
        this.productNewItemClick = productNewItemClick;
    }

    @NonNull
    @Override
    public AdapterProductNew.ProductNewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vertical_products_new, parent, false);
        return new ProductNewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterProductNew.ProductNewViewHolder holder, int position) {
        Product productNew = data.get(position);
        holder.tvNameProductNew.setText(productNew.getProduct_name());
        Glide.with(context).load(productNew.getImage()).centerCrop().into(holder.imgProductNew);
        holder.tvMassPoductNew.setText(productNew.getProcessing_time());
        holder.tvPricePoductNew.setText(String.valueOf(productNew.getPrice()));
        holder.cstrItemProductNew.setOnClickListener(v -> {
            productNewItemClick.itemProductNewClick(productNew);
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ProductNewViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProductNew, imgBuyPoductNew;
        TextView tvNameProductNew, tvMassPoductNew, tvPricePoductNew;
        private ConstraintLayout cstrItemProductNew;

        public ProductNewViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProductNew = itemView.findViewById(R.id.imgProductNew);
            tvNameProductNew = itemView.findViewById(R.id.tvNamePoductNew);
            tvMassPoductNew = itemView.findViewById(R.id.tvMassPoductNew);
            tvPricePoductNew = itemView.findViewById(R.id.tvPricePoductNew);
            cstrItemProductNew = itemView.findViewById(R.id.layout_item_product_new);
        }
    }
}
