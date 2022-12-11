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
import com.example.deliciousrice.callback.ProductHotItemClick;
import com.example.deliciousrice.ui.shop.ShopFragment;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AdapterProductHot extends RecyclerView.Adapter<AdapterProductHot.ProductHotViewHolder> {

    private ArrayList<Product> data;
    private ShopFragment context;
    private ProductHotItemClick productHotItemClick;

    public AdapterProductHot(ArrayList<Product> data, ShopFragment context, ProductHotItemClick productHotItemClick) {
        this.data = data;
        this.context = context;
        this.productHotItemClick = productHotItemClick;
    }

    @NonNull
    @Override
    public AdapterProductHot.ProductHotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hot_products,parent,false);
        return new ProductHotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterProductHot.ProductHotViewHolder holder, int position) {
        Product productHot = data.get(position);
        Glide.with(context).load(productHot.getImage()).centerCrop().into(holder.imgProductHot);
        holder.tvProductNameHot.setText(productHot.getProduct_name());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.tvPriceProductHot.setText(decimalFormat.format(productHot.getPrice())+" đ");

        holder.tvTimehot.setText(productHot.getProcessing_time());
        holder.cstrItemProductHot.setOnClickListener(v -> {
            productHotItemClick.itemProductHotClick(productHot);
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ProductHotViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProductHot;
        TextView tvProductNameHot, tvPriceProductHot,tvTimehot;
        private ConstraintLayout cstrItemProductHot;
        public ProductHotViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTimehot = itemView.findViewById(R.id.tv_timehot);
            imgProductHot = itemView.findViewById(R.id.imgProductHot);
            tvProductNameHot = itemView.findViewById(R.id.tvProductNameHot);
            tvPriceProductHot = itemView.findViewById(R.id.tvPriceProductHot);
            cstrItemProductHot = itemView.findViewById(R.id.layout_item_product_hot);
        }
    }
}
