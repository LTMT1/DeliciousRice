package com.example.deliciousrice.Model;

import java.io.Serializable;

public class Customer implements Serializable {
    private int id_customer;
    private String id_application;
    private String user_name;
    private String image;
    private String birthday;
    private String phone_number;
    private String address;
    private String email;
    private String password;

    public Customer(int id_customer, String id_application, String user_name, String image, String birthday, String phone_number, String address, String email, String password) {
        this.id_customer = id_customer;
        this.id_application = id_application;
        this.user_name = user_name;
        this.image = image;
        this.birthday = birthday;
        this.phone_number = phone_number;
        this.address = address;
        this.email = email;
        this.password = password;
    }

    public Customer() {
    }

    public String getId_application() {
        return id_application;
    }

    public void setId_application(String id_application) {
        this.id_application = id_application;
    }

    public int getId_customer() {
        return id_customer;
    }

    public void setId_customer(int id_customer) {
        this.id_customer = id_customer;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
