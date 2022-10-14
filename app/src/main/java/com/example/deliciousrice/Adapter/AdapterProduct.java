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
import com.example.deliciousrice.ui.shop.ShopFragment;

import java.util.ArrayList;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ProductViewHolder> {

    private ArrayList<Product> data;
    private ShopFragment context;
    private ProductItemClick productItemClick;

    public AdapterProduct(ArrayList<Product> data, ShopFragment context, ProductItemClick productItemClick) {
        this.data = data;
        this.context = context;
        this.productItemClick = productItemClick;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vertical_products,parent,false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = data.get(position);
        holder.tvNameProduct.setText(product.getProduct_name());
        Glide.with(context).load(product.getImage()).centerCrop().into(holder.imgProduct);
        holder.tvMassPoduct.setText(product.getProcessing_time());
        holder.tvPricePoduct.setText(String.valueOf(product.getPrice()));
        holder.cstrItemProduct.setOnClickListener(v -> {
            productItemClick.itemProductClick(product);
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }




    public static class ProductViewHolder extends RecyclerView.ViewHolder {

        ImageView imgProduct, imgBuyPoduct;
        TextView tvNameProduct, tvMassPoduct, tvPricePoduct;
        private ConstraintLayout cstrItemProduct;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            tvNameProduct = itemView.findViewById(R.id.tvNamePoduct);
            tvMassPoduct = itemView.findViewById(R.id.tvMassPoduct);
            tvPricePoduct = itemView.findViewById(R.id.tvPricePoduct);
            cstrItemProduct = itemView.findViewById(R.id.layout_item_product_new_combo);
        }
    }
}
