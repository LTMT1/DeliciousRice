package com.example.deliciousrice.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.deliciousrice.Model.Product;
import com.example.deliciousrice.R;
import com.example.deliciousrice.ui.shop.ShopFragment;

import java.util.ArrayList;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ProductViewHolder> {

    ArrayList<Product> data;
    ShopFragment context;

    public AdapterProduct(ArrayList<Product> data, ShopFragment context) {
        this.data = data;
        this.context = context;
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
        //holder.tvusername.setText(timKiem.getUser_name());
        holder.tvNameProduct.setText(product.getProduct_name());
        Glide.with(context).load(product.getImage()).centerCrop().into(holder.imgProduct);
        holder.tvMassPoduct.setText(product.getProcessing_time());
        holder.tvPricePoduct.setText(String.valueOf(product.getPrice()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }




    public class ProductViewHolder extends RecyclerView.ViewHolder {

        ImageView imgProduct, imgBuyPoduct;
        TextView tvNameProduct, tvMassPoduct, tvPricePoduct;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            tvNameProduct = itemView.findViewById(R.id.tvNamePoduct);
            tvMassPoduct = itemView.findViewById(R.id.tvMassPoduct);
            tvPricePoduct = itemView.findViewById(R.id.tvPricePoduct);
        }
    }
}
