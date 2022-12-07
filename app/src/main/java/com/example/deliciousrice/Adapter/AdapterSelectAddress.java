package com.example.deliciousrice.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.deliciousrice.Model.Adderss;
import com.example.deliciousrice.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterSelectAddress extends ArrayAdapter<Adderss> {
    private ArrayList<Adderss> list;
    Context context;


    public AdapterSelectAddress(@NonNull Context context, int resource, @NonNull List<Adderss> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select,parent,false);
        TextView tvadressselec=convertView.findViewById(R.id.tv_addresss);
        Adderss adderss=this.getItem(position);
        if(adderss!=null){
            tvadressselec.setText(adderss.getAddress_specifically());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select_address,parent,false);
        TextView tvadress=convertView.findViewById(R.id.tv_selectaddress);
        Adderss adderss=this.getItem(position);
        if(adderss!=null){
            tvadress.setText(adderss.getAddress_specifically());
        }
        return convertView;
    }
}
