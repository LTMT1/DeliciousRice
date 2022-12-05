package com.example.deliciousrice.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliciousrice.Model.Bill;
import com.example.deliciousrice.R;
import com.example.deliciousrice.callback.ReceiptItemClick;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class AdapterHistoryBill extends RecyclerView.Adapter<AdapterHistoryBill.HistoryViewHolder> {

    private ArrayList<Bill> list;
    private Context context;
    ReceiptItemClick receiptItemClick;

    public AdapterHistoryBill(ArrayList<Bill> list, Context context, ReceiptItemClick receiptItemClick) {
        this.list = list;
        this.context = context;
        this.receiptItemClick = receiptItemClick;
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
        holder.tvbill.setText("Mã HD: "+bill.getId_bill());
        holder.tvdate.setText("Ngày: "+bill.getDate());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.tvmoney.setText("Tổng thanh toán: "+decimalFormat.format(bill.getMoney())+"đ");
        holder.tvstatus.setText(bill.getStatus());
        if(bill.getStatus().equals("Hoàn tất")) {
            holder.imgstatus.setVisibility(View.GONE);
            holder.imgstatus1.setVisibility(View.VISIBLE);
        }else if(bill.getStatus().equals("Đã Hủy")){
            holder.imgstatus.setVisibility(View.VISIBLE);
            holder.imgstatus1.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(view -> {
            receiptItemClick.itemReceiptClick(bill);
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
        private TextView tvstatus;
        private ImageView imgstatus;
        private ImageView imgstatus1;

        private ConstraintLayout ctrLayoutReceipt;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvbill = itemView.findViewById(R.id.tvbill);
            tvdate = itemView.findViewById(R.id.tvdate);
            tvmoney = itemView.findViewById(R.id.tvmoney);
            tvstatus=itemView.findViewById(R.id.tvstatus);
            imgstatus = itemView.findViewById(R.id.imgstatus);
            imgstatus1 = itemView.findViewById(R.id.imgstatus1);
            ctrLayoutReceipt = itemView.findViewById(R.id.ctrLayoutReceipt);
        }
    }
}
