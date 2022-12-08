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
import com.example.deliciousrice.ui.cart.DaoCart;
import com.example.deliciousrice.ui.shop.ShopFragment;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ProductViewHolder> {

    private ArrayList<Product> data;
    private ShopFragment context;
    private ProductItemClick productItemClick;
    static DaoCart daoCart;



    public AdapterProduct(ArrayList<Product> data, ShopFragment context, ProductItemClick productItemClick) {
        this.data = data;
        this.context = context;
        this.productItemClick = productItemClick;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vertical_products, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = data.get(position);
        holder.tvNameProduct.setText(product.getProduct_name());
        Glide.with(context).load(product.getImage()).centerCrop().into(holder.imgProduct);
        holder.tvMassPoduct.setText(product.getProcessing_time());
        holder.tvPricePoduct.setText(String.valueOf(product.getPrice()));

        holder.imgBuyPoduct.setOnClickListener(view -> {
            daoCart=new DaoCart(context.getContext());
            ShopFragment.Cartlist = (ArrayList<Cart>) daoCart.getall();
            if (ShopFragment.Cartlist.size() > 0){
                boolean tontaimahang = false;
                for (int i = 0; i <  ShopFragment.Cartlist.size(); i++)
                {
                    if (ShopFragment.Cartlist.get(i).getId_product() == product.getId_product())
                    {
                        ShopFragment.Cartlist.get(i).setAmount( ShopFragment.Cartlist.get(i).getAmount() + 1);
                        ShopFragment.Cartlist.get(i).setPrice(product.getPrice() * ShopFragment.Cartlist.get(i).getAmount());
                        UpdateProduct(product.getId_product(),ShopFragment.Cartlist.get(i).getPrice(),ShopFragment.Cartlist.get(i).getAmount());
                        tontaimahang = true;
                    }
                }
                if (tontaimahang == false)
                {
                    int sl1 = 1;//lay so luong trong spinner
                    int Tien2 = sl1 * (product.getPrice());
                    daoCart.InsertData(product.getId_product(), product.getProduct_name(), Tien2, product.getImage(), sl1);
                }

            }else
            {
                int sl2 = 1;
                int Tien2 = sl2 * (product.getPrice());
                daoCart.InsertData(product.getId_product(), product.getProduct_name(), Tien2, product.getImage(), sl2);
            }
            Toast.makeText(context.getContext(), "Thêm thàng công vào giỏ hàng", Toast.LENGTH_SHORT).show();
            updateList();
        });

        holder.cstrItemProduct.setOnClickListener(v -> {
            productItemClick.itemProductClick(product);
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


    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView imgProduct;
        ImageView  imgBuyPoduct;
        TextView tvNameProduct, tvMassPoduct, tvPricePoduct;
        private ConstraintLayout cstrItemProduct;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            imgBuyPoduct = itemView.findViewById(R.id.imgBuyPoduct);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            tvNameProduct = itemView.findViewById(R.id.tvNamePoduct);
            tvMassPoduct = itemView.findViewById(R.id.tvMassPoduct);
            tvPricePoduct = itemView.findViewById(R.id.tvPricePoduct);
            cstrItemProduct = itemView.findViewById(R.id.layout_item_product_new_combo);
        }
    }
}
