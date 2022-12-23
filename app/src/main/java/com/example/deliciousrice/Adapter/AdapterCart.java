package com.example.deliciousrice.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliciousrice.Model.Cart;
import com.example.deliciousrice.R;
import com.example.deliciousrice.callback.OnClickitem;
import com.example.deliciousrice.ui.cart.CartFragment;
import com.example.deliciousrice.ui.shop.Activity.DetailFragment;
import com.example.deliciousrice.ui.shop.ShopFragment;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AdapterCart extends RecyclerView.Adapter<AdapterCart.CartViewHolder> {

    private ArrayList<Cart> list;
    private Context context;
    CartFragment cartFragment;

    public AdapterCart(ArrayList<Cart> list, Context context, CartFragment cartFragment) {
        this.list = list;
        this.context = context;
        this.cartFragment = cartFragment;
    }

    @NonNull
    @Override
    public AdapterCart.CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart_products, parent, false);
        return new AdapterCart.CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCart.CartViewHolder holder, int position) {
        Cart cart = list.get(position);
        holder.tvname.setText(cart.getName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.tvprice.setText(decimalFormat.format(cart.getPrice()) + " đ");
        Picasso.get().load(cart.getImage())
                .into(holder.imgsp);
        holder.tvsoluong.setText(Integer.toString(cart.getAmount()));
        holder.setOnClickitem((view, pos, giatri) -> {
            int slht = ShopFragment.Cartlist.get(position).getAmount();
            int giaht = ShopFragment.Cartlist.get(position).getPrice();
            if (giatri == 1) {
                int slm = Integer.parseInt(holder.tvsoluong.getText().toString()) - 1;
                int giamoia = (giaht * slm) / slht;

                if (slht == 1) {
                    DeleteItemCart(cart.id_product);
                } else {
                    ShopFragment.Cartlist.get(position).setAmount(slm);
                    list.get(position).setAmount(slm);
                    ShopFragment.Cartlist.get(position).setPrice(giamoia);
                }
            } else if (giatri == 2) {
                int slm = Integer.parseInt(holder.tvsoluong.getText().toString()) + 1;
                ShopFragment.Cartlist.get(position).setAmount(slm);
                int giamoia = (giaht * slm) / slht;
                list.get(position).setAmount(slm);
                ShopFragment.Cartlist.get(position).setPrice(giamoia);
            }
            holder.tvsoluong.setText(ShopFragment.Cartlist.get(position).getAmount() + "");
            holder.tvprice.setText(decimalFormat.format(ShopFragment.Cartlist.get(position).getPrice())+ " đ");
            DetailFragment.UpdateProduct(cart.id_product, ShopFragment.Cartlist.get(position).getPrice(), ShopFragment.Cartlist.get(position).getAmount());
            cartFragment.UpdateTongTien();
        });
        holder.frameLayout.setOnClickListener(view -> {
            DeleteItemCart(cart.id_product);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvname;
        private TextView tvsoluong;
        private ImageView imgcong;
        private ImageView imgtru;
        private TextView tvprice;
        ImageView imgsp;
        ConstraintLayout constraint;
        OnClickitem onClickitem;
        FrameLayout frameLayout;

        public void setOnClickitem(OnClickitem onClickitem) {
            this.onClickitem = onClickitem;
        }

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            constraint = itemView.findViewById(R.id.constraint);
            imgsp = itemView.findViewById(R.id.imgsp);
            tvname = itemView.findViewById(R.id.tvname);
            tvsoluong = itemView.findViewById(R.id.tvsoluong);
            imgcong = itemView.findViewById(R.id.imgcong);
            imgtru = itemView.findViewById(R.id.imgtru);
            tvprice = itemView.findViewById(R.id.tvprice);
            frameLayout = itemView.findViewById(R.id.delete);
            imgcong.setOnClickListener(this);
            imgtru.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view == imgtru) {
                onClickitem.onclickitem(view, getAdapterPosition(), 1);
            } else if (view == imgcong) {
                onClickitem.onclickitem(view, getAdapterPosition(), 2);
            }
        }
    }

    private void DeleteItemCart(int id_product) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Xác nhận xóa sản phẩm");
        builder.setMessage("Bạn có muốn xóa sản phẩm này?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cartFragment.DeleteProduct(id_product);
                cartFragment.CheckData();
                cartFragment.UpdateTongTien();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cartFragment.UpdateTongTien();
                cartFragment.CheckData();
            }
        });
        builder.show();

    }
}
