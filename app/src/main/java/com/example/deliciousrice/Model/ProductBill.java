package com.example.deliciousrice.Model;

public class ProductBill {
    private String product_name;
    private int amount;
    private int money;

    public ProductBill() {
    }

    public ProductBill( String product_name, int amount, int money) {
        this.product_name = product_name;
        this.amount = amount;
        this.money = money;
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

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
