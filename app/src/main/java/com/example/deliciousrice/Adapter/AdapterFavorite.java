package com.example.deliciousrice.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.deliciousrice.Api.ApiProduct;
import com.example.deliciousrice.Api.ApiService;
import com.example.deliciousrice.Model.Product;
import com.example.deliciousrice.R;
import com.example.deliciousrice.callback.ProductFavoriteItemClick;
import com.example.deliciousrice.ui.shop.Activity.DetailActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterFavorite extends RecyclerView.Adapter<AdapterFavorite.FavoriteViewHolder> {

    private ArrayList<Product> list;
    private Context context;
    private ProductFavoriteItemClick productFavoriteItemClick;

    public AdapterFavorite(ArrayList<Product> list, Context context, ProductFavoriteItemClick productFavoriteItemClick) {
        this.list = list;
        this.context = context;
        this.productFavoriteItemClick = productFavoriteItemClick;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite, parent, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        Product favorite = list.get(position);
        holder.tvName.setText(favorite.getProduct_name());
        Glide.with(context).load(favorite.getImage()).centerCrop().into(holder.imgProduct);
        holder.tvTime.setText(favorite.getProcessing_time());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.tvPrice.setText(decimalFormat.format(favorite.getPrice())+" đ");

        if (list.size() > 0) {
            holder.icontym.setImageResource(R.drawable.ic_baseline_favorite_24);
        }
        holder.icontym.setOnClickListener(view -> {
            if (DetailActivity.Click != 0) {
                Animation animation = AnimationUtils.loadAnimation(context, R.anim.anim_timclick);
                holder.icontym.setImageResource(R.drawable.ic_baseline_favorite_24);
                view.startAnimation(animation);
                insertFavorite(favorite.getId_customer(), favorite.getId_product());
                DetailActivity.Click++;
            } else {
                holder.icontym.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                deleteFavorite(favorite.getId_customer(), favorite.getId_product());
                DetailActivity.Click--;
            }
        });
        holder.itemView.setOnClickListener(v -> {
            productFavoriteItemClick.itemProductFavoriteClick(favorite);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class FavoriteViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgProduct;
        private ImageView icontym;
        private TextView tvName;
        private TextView tvPrice;
        private TextView tvTime;

        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.img_product);
            icontym = itemView.findViewById(R.id.icontym);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvTime = itemView.findViewById(R.id.tv_time);
        }
    }

    private void insertFavorite(int idcustomer, int idproduct) {
        ApiProduct apiProduct = ApiService.getService();
        Call<String> callback = apiProduct.insertfavorite(idcustomer, idproduct);
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
    }

    private void deleteFavorite(int idcustomer, int idproduct) {
        ApiProduct apiProduct = ApiService.getService();
        Call<Product> callback = apiProduct.deletefavorite(idcustomer, idproduct);
        callback.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
            }
        });
    }
}