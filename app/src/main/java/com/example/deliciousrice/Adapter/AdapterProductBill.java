package com.example.deliciousrice.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.deliciousrice.Model.Bill;
import com.example.deliciousrice.Model.Product;
import com.example.deliciousrice.Model.ProductBill;
import com.example.deliciousrice.R;
import com.example.deliciousrice.callback.ProductItemClick;
import com.example.deliciousrice.ui.shop.ShopFragment;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AdapterProductBill extends RecyclerView.Adapter<AdapterProductBill.ProductBillViewHolder> {

    private ArrayList<ProductBill> list;
    private Context context;

    public AdapterProductBill(ArrayList<ProductBill> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterProductBill.ProductBillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_buy,parent,false);
        return new ProductBillViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull AdapterProductBill.ProductBillViewHolder holder, int position) {
        ProductBill bill = list.get(position);
        holder.tvname.setText(bill.getProduct_name());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.tvprice.setText(decimalFormat.format(bill.getTotal_money())+"đ");
        holder.tvsl.setText(bill.getAmount()+"x");
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ProductBillViewHolder extends RecyclerView.ViewHolder {
        private TextView tvsl;
        private TextView tvname;
        private TextView tvprice;
        public ProductBillViewHolder(@NonNull View itemView) {
            super(itemView);
            tvsl = itemView.findViewById(R.id.tvsl);
            tvname = itemView.findViewById(R.id.tvname);
            tvprice = itemView.findViewById(R.id.tvprice);
        }
    }

}


