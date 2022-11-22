package com.example.deliciousrice.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliciousrice.Model.Bill;
import com.example.deliciousrice.R;

import java.util.ArrayList;

public class AdapterHistoryBill extends RecyclerView.Adapter<AdapterHistoryBill.HistoryViewHolder> {

    private ArrayList<Bill> list;
    private Context context;

    public AdapterHistoryBill(ArrayList<Bill> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterHistoryBill.HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_receipt, parent, false);
        return new AdapterHistoryBill.HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHistoryBill.HistoryViewHolder holder, int position) {
        Bill bill = list.get(position);
        holder.tvbill.setText(bill.getId_bill()+"");
        holder.tvdate.setText(bill.getDate());
        holder.tvmoney.setText(bill.getMoney()+"");
        holder.itemView.setOnClickListener(view -> {

        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class HistoryViewHolder extends RecyclerView.ViewHolder {
        private TextView tvbill;
        private TextView tvdate;
        private TextView tvmoney;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvbill = itemView.findViewById(R.id.tvbill);
            tvdate = itemView.findViewById(R.id.tvdate);
            tvmoney = itemView.findViewById(R.id.tvmoney);
        }
    }
}
