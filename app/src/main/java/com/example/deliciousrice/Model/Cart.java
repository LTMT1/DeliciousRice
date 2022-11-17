package com.example.deliciousrice.Model;

public class Cart {
    private int id_product;
    private String name;
    private int price;
    private String image;
    private int amount;

    public Cart() {
    }

    public Cart(int id_product, String name, int price, String image, int amount) {
        this.id_product = id_product;
        this.name = name;
        this.price = price;
        this.image = image;
        this.amount = amount;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
