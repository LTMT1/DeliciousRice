package com.example.deliciousrice.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Favorite {
    private  int id_customer;
    private int id_product;
    private String product_name;
    private String image;
    private String processing_time;
    private int price;
    private String description;

    public Favorite(int id_customer, int id_product, String product_name, String image, String processing_time, int price, String description) {
        this.id_customer = id_customer;
        this.id_product = id_product;
        this.product_name = product_name;
        this.image = image;
        this.processing_time = processing_time;
        this.price = price;
        this.description = description;
    }

    public int getId_customer() {
        return id_customer;
    }

    public void setId_customer(int id_customer) {
        this.id_customer = id_customer;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getProcessing_time() {
        return processing_time;
    }

    public void setProcessing_time(String processing_time) {
        this.processing_time = processing_time;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
