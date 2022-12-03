package com.example.deliciousrice.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliciousrice.Model.Detailbill;
import com.example.deliciousrice.Model.ProductBill;
import com.example.deliciousrice.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class AdapterDetailBill extends RecyclerView.Adapter<AdapterDetailBill.DetailBillViewHolder> {

    private List<Detailbill> list;
    private Context context;

    public AdapterDetailBill(List<Detailbill> list, Context context) {
        this.list = list;
        this.context = context;
    }
    @NonNull
    @Override
    public DetailBillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_buy,parent,false);
        return new DetailBillViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailBillViewHolder holder, int position) {
        Detailbill detailbill = list.get(position);
        holder.tvname.setText(detailbill.getProduct_name());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.tvprice.setText(decimalFormat.format(detailbill.getTotal_money())+"đ");
        holder.tvsl.setText(detailbill.getAmount()+"x");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class DetailBillViewHolder extends RecyclerView.ViewHolder {
        private TextView tvsl;
        private TextView tvname;
        private TextView tvprice;
        public DetailBillViewHolder(@NonNull View itemView) {
            super(itemView);
            tvsl = itemView.findViewById(R.id.tvsl);
            tvname = itemView.findViewById(R.id.tvname);
            tvprice = itemView.findViewById(R.id.tvprice);
        }
    }
}
