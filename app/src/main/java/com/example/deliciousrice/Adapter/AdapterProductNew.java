package com.example.deliciousrice.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.deliciousrice.MainActivity2;
import com.example.deliciousrice.Model.Cart;
import com.example.deliciousrice.Model.Product;
import com.example.deliciousrice.R;
import com.example.deliciousrice.callback.ProductItemClick;
import com.example.deliciousrice.callback.ProductNewItemClick;
import com.example.deliciousrice.ui.cart.DaoCart;
import com.example.deliciousrice.ui.shop.ShopFragment;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class AdapterProductNew extends RecyclerView.Adapter<AdapterProductNew.ProductNewViewHolder> {

    private ArrayList<Product> data;
    private ShopFragment context;
    private ProductNewItemClick productNewItemClick;
    static DaoCart daoCart;

    public AdapterProductNew(ArrayList<Product> data, ShopFragment context, ProductNewItemClick productNewItemClick) {
        this.data = data;
        this.context = context;
        this.productNewItemClick = productNewItemClick;
    }

    @NonNull
    @Override
    public AdapterProductNew.ProductNewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vertical_products_new, parent, false);
        return new ProductNewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterProductNew.ProductNewViewHolder holder, int position) {
        Product productNew = data.get(position);
        holder.tvNameProductNew.setText(productNew.getProduct_name());
        Glide.with(context).load(productNew.getImage()).centerCrop().into(holder.imgProductNew);
        holder.tvMassPoductNew.setText(productNew.getProcessing_time());
        holder.tvPricePoductNew.setText(String.valueOf(productNew.getPrice())+ " đ");

        holder.imgBuyPoductNew.setOnClickListener(view -> {
            daoCart=new DaoCart(context.getContext());
            ShopFragment.Cartlist = (ArrayList<Cart>) daoCart.getall();
            if (ShopFragment.Cartlist.size() > 0){
                boolean tontaimahang = false;
                for (int i = 0; i <  ShopFragment.Cartlist.size(); i++)
                {
                    if (ShopFragment.Cartlist.get(i).getId_product() == productNew.getId_product())
                    {
                        ShopFragment.Cartlist.get(i).setAmount( ShopFragment.Cartlist.get(i).getAmount() + 1);
                        ShopFragment.Cartlist.get(i).setPrice(productNew.getPrice() * ShopFragment.Cartlist.get(i).getAmount());
                        UpdateProduct(productNew.getId_product(),ShopFragment.Cartlist.get(i).getPrice(),ShopFragment.Cartlist.get(i).getAmount());
                        tontaimahang = true;
                    }
                }
                if (tontaimahang == false)
                {
                    int sl1 = 1;//lay so luong trong spinner
                    int Tien2 = sl1 * (productNew.getPrice());
                    daoCart.InsertData(productNew.getId_product(), productNew.getProduct_name(), Tien2, productNew.getImage(), sl1);
                }

            }else
            {
                int sl2 = 1;
                int Tien2 = sl2 * (productNew.getPrice());
                daoCart.InsertData(productNew.getId_product(), productNew.getProduct_name(), Tien2, productNew.getImage(), sl2);
            }
            Toast.makeText(context.getContext(), "Thêm thàng công vào giỏ hàng", Toast.LENGTH_SHORT).show();
            updateList();
        });

        holder.cstrItemProductNew.setOnClickListener(v -> {
            productNewItemClick.itemProductNewClick(productNew);
        });
    }


    public static void UpdateProduct(int id, int price, int amount) {
        daoCart.UpdateCart(id, price, amount);
    }
    private void updateList(){
        ShopFragment.Cartlist= (ArrayList<Cart>) daoCart.getall();
        MainActivity2.setBugdeNumber();
    }
    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ProductNewViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView imgProductNew;
        ImageView imgBuyPoductNew;
        TextView tvNameProductNew, tvMassPoductNew, tvPricePoductNew;
        private ConstraintLayout cstrItemProductNew;

        public ProductNewViewHolder(@NonNull View itemView) {
            super(itemView);

            imgBuyPoductNew = itemView.findViewById(R.id.imgBuyPoductNew);
            imgProductNew = itemView.findViewById(R.id.imgProductNew);
            tvNameProductNew = itemView.findViewById(R.id.tvNamePoductNew);
            tvMassPoductNew = itemView.findViewById(R.id.tvMassPoductNew);
            tvPricePoductNew = itemView.findViewById(R.id.tvPricePoductNew);
            cstrItemProductNew = itemView.findViewById(R.id.layout_item_product_new);
        }
    }
}
