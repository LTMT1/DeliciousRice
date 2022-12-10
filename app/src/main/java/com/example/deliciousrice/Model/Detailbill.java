package com.example.deliciousrice.Model;

public class Detailbill {
    private String id_bill;
    private int id_customer;
    private String date_created;
    private String address;
    private String product_name;
    private int amount;
    private int total_money;
    private int priceproduct;
    private String user_namenv;
    private int id_product;
    private String image;

    public Detailbill() {
    }

    public Detailbill(String id_bill, int id_customer, String date_created, String address, String product_name, int amount, int total_money, int priceproduct, String user_namenv, int id_product, String image) {
        this.id_bill = id_bill;
        this.id_customer = id_customer;
        this.date_created = date_created;
        this.address = address;
        this.product_name = product_name;
        this.amount = amount;
        this.total_money = total_money;
        this.priceproduct = priceproduct;
        this.user_namenv = user_namenv;
        this.id_product = id_product;
        this.image = image;
    }

    public String getId_bill() {
        return id_bill;
    }

    public void setId_bill(String id_bill) {
        this.id_bill = id_bill;
    }

    public int getId_customer() {
        return id_customer;
    }

    public void setId_customer(int id_customer) {
        this.id_customer = id_customer;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getTotal_money() {
        return total_money;
    }

    public void setTotal_money(int total_money) {
        this.total_money = total_money;
    }

    public int getPriceproduct() {
        return priceproduct;
    }

    public void setPriceproduct(int priceproduct) {
        this.priceproduct = priceproduct;
    }

    public String getUser_namenv() {
        return user_namenv;
    }

    public void setUser_namenv(String user_namenv) {
        this.user_namenv = user_namenv;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
