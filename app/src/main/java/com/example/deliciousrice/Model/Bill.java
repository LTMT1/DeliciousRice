package com.example.deliciousrice.Model;

public class Bill {
    private int id_bill;
    private int id_customer;
    private int id_staff;
    private String date;
    private String status;
    private int money;

    public Bill(int id_bill, int id_customer, int id_staff, String date, String status, int money) {
        this.id_bill = id_bill;
        this.id_customer = id_customer;
        this.id_staff = id_staff;
        this.date = date;
        this.status = status;
        this.money = money;
    }

    public int getId_bill() {
        return id_bill;
    }

    public void setId_bill(int id_bill) {
        this.id_bill = id_bill;
    }

    public int getId_customer() {
        return id_customer;
    }

    public void setId_customer(int id_customer) {
        this.id_customer = id_customer;
    }

    public int getId_staff() {
        return id_staff;
    }

    public void setId_staff(int id_staff) {
        this.id_staff = id_staff;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
