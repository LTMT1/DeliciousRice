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
import com.example.deliciousrice.Model.ProductHot;
import com.example.deliciousrice.Model.ProductNew;
import com.example.deliciousrice.R;
import com.example.deliciousrice.callback.ProductHotItemClick;
import com.example.deliciousrice.callback.ProductItemClick;
import com.example.deliciousrice.callback.ProductNewItemClick;
import com.example.deliciousrice.ui.shop.ShopFragment;

import java.util.ArrayList;

public class AdapterProductHot extends RecyclerView.Adapter<AdapterProductHot.ProductHotViewHolder> {

    private ArrayList<ProductHot> data;
    private ShopFragment context;
    private ProductHotItemClick productHotItemClick;

    public AdapterProductHot(ArrayList<ProductHot> data, ShopFragment context, ProductHotItemClick productHotItemClick) {
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
        ProductHot productHot = data.get(position);
        Glide.with(context).load(productHot.getImage_hot()).centerCrop().into(holder.imgProductHot);
        holder.tvProductNameHot.setText(productHot.getProduct_name_hot());
        holder.tvPriceProductHot.setText(productHot.getPrice_hot());
        holder.cstrItemProductHot.setOnClickListener(v -> {
            productHotItemClick.itemProductHotClick(productHot);
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ProductHotViewHolder extends RecyclerView.ViewHolder {

        ImageView imgProductHot, imgProductFavouriteHot;
        TextView tvProductNameHot, tvPriceProductHot;
        private ConstraintLayout cstrItemProductHot;
        public ProductHotViewHolder(@NonNull View itemView) {
            super(itemView);

            imgProductHot = itemView.findViewById(R.id.imgProductHot);
            imgProductFavouriteHot = itemView.findViewById(R.id.imgProductFavouriteHot);
            tvProductNameHot = itemView.findViewById(R.id.tvProductNameHot);
            tvPriceProductHot = itemView.findViewById(R.id.tvPriceProductHot);
            cstrItemProductHot = itemView.findViewById(R.id.layout_item_product_hot);
        }
    }
}
