package com.example.deliciousrice.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliciousrice.Model.Bill;
import com.example.deliciousrice.R;

import java.util.ArrayList;

public class AdapterHistory extends RecyclerView.Adapter<AdapterHistory.HistoryViewHolder> {

    private ArrayList<Bill> list;
    private Context context;

    public AdapterHistory(ArrayList<Bill> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterHistory.HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vertical_products, parent, false);
        return new AdapterHistory.HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHistory.HistoryViewHolder holder, int position) {
        Bill bill = list.get(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class HistoryViewHolder extends RecyclerView.ViewHolder {

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
}
