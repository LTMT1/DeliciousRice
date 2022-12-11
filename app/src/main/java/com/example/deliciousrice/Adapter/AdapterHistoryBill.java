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
        holder.tvbill.setText("Mã HD: DCR"+bill.getId_bill());
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
        holder.tvdate.setText("Ngày: "+ sdf1.format(bill.getDate()));
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.tvmoney.setText("Tổng thanh toán: "+decimalFormat.format(bill.getMoney())+" đ");
        holder.tvstatus.setText(bill.getStatus());
        if(bill.getStatus().trim().equals("Hoàn tất")) {
            holder.imgstatus.setVisibility(View.GONE);
            holder.imgstatus2.setVisibility(View.GONE);
            holder.imgstatus3.setVisibility(View.GONE);
            holder.imgstatus1.setVisibility(View.VISIBLE);
        }else if(bill.getStatus().trim().equals("Đã Hủy")){
            holder.imgstatus.setVisibility(View.VISIBLE);
            holder.imgstatus1.setVisibility(View.GONE);
            holder.imgstatus2.setVisibility(View.GONE);
            holder.imgstatus3.setVisibility(View.GONE);
        }else if(bill.getStatus().trim().equals("Đang Giao Hàng")){
            holder.imgstatus2.setVisibility(View.VISIBLE);
            holder.imgstatus.setVisibility(View.GONE);
            holder.imgstatus1.setVisibility(View.GONE);
            holder.imgstatus3.setVisibility(View.GONE);
        }else if(bill.getStatus().trim().equals("Đang chờ")){
            holder.imgstatus3.setVisibility(View.VISIBLE);
            holder.imgstatus.setVisibility(View.GONE);
            holder.imgstatus1.setVisibility(View.GONE);
            holder.imgstatus2.setVisibility(View.GONE);
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
        private ImageView imgstatus1,imgstatus2,imgstatus3;

        private ConstraintLayout ctrLayoutReceipt;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvbill = itemView.findViewById(R.id.tvbill);
            tvdate = itemView.findViewById(R.id.tvdate);
            tvmoney = itemView.findViewById(R.id.tvmoney);
            tvstatus=itemView.findViewById(R.id.tvstatus);
            imgstatus = itemView.findViewById(R.id.imgstatus);
            imgstatus1 = itemView.findViewById(R.id.imgstatus1);
            imgstatus2 = itemView.findViewById(R.id.imgstatus2);
            imgstatus3 = itemView.findViewById(R.id.imgstatus3);
            ctrLayoutReceipt = itemView.findViewById(R.id.ctrLayoutReceipt);

        }
    }
}
