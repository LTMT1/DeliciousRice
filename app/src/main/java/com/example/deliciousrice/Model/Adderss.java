package com.example.deliciousrice.Model;

import java.io.Serializable;

public class Adderss implements Serializable {
    private int id_address;
    private int id_customer;
    private String address_name;
    private String address_specifically;

    public Adderss() {
    }

    public int getId_customer() {
        return id_customer;
    }

    public void setId_customer(int id_customer) {
        this.id_customer = id_customer;
    }

    public int getId_address() {
        return id_address;
    }

    public void setId_address(int id_address) {
        this.id_address = id_address;
    }

    public String getAddress_name() {
        return address_name;
    }

    public void setAddress_name(String address_name) {
        this.address_name = address_name;
    }

    public String getAddress_specifically() {
        return address_specifically;
    }

    public void setAddress_specifically(String address_specifically) {
        this.address_specifically = address_specifically;
    }

    public Adderss(int id_customer, int id_address, String address_name, String address_specifically) {
        this.id_customer = id_customer;
        this.id_address = id_address;
        this.address_name = address_name;
        this.address_specifically = address_specifically;
    }
}
