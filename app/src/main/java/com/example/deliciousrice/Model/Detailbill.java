package com.example.deliciousrice.Model;

public class Detailbill {
    private String id_bill;
    private int id_customer;
    private int id_product;
    private String date_created;
    private String address;
    private String product_name;
    private int amount;
    private int total_money;
    private String user_name;

    public Detailbill() {
    }

    public Detailbill(String id_bill, int id_customer, int id_product, String date_created, String address, String product_name, int amount, int total_money, String user_name) {
        this.id_bill = id_bill;
        this.id_customer = id_customer;
        this.id_product = id_product;
        this.date_created = date_created;
        this.address = address;
        this.product_name = product_name;
        this.amount = amount;
        this.total_money = total_money;
        this.user_name = user_name;
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

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
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

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
