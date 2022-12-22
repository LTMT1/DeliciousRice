package com.example.deliciousrice.Model;

import java.io.Serializable;
import java.util.Date;

public class Bill implements Serializable {
    private String id_bill;
    private int id_customer;
    private int id_staff;
    private Date date_created;
    private String status;
    private int money;
    private String payment;

    public Bill(String id_bill, int id_customer, int id_staff, Date date_created, String status, int money, String payment) {
        this.id_bill = id_bill;
        this.id_customer = id_customer;
        this.id_staff = id_staff;
        this.date_created = date_created;
        this.status = status;
        this.money = money;
        this.payment = payment;
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

    public int getId_staff() {
        return id_staff;
    }

    public void setId_staff(int id_staff) {
        this.id_staff = id_staff;
    }

    public Date getDate() {
        return date_created;
    }

    public void setDate_created(Date date_created) {
        this.date_created = date_created;
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

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }
}
