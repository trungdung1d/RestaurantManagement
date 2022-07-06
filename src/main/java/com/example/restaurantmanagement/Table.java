package com.example.demo1;

public class Table {
    private int id;
    private String nameCustomer;
    private int amount;
    private double totalPrice;
    private String date;

    public Table(int id, String nameCustomer, int amount, double totalPrice, String date) {
        this.id = id;
        this.nameCustomer = nameCustomer;
        this.amount = amount;
        this.totalPrice = totalPrice;
        this.date = date;
    }
    public Table(){

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameCustomer() {
        return nameCustomer;
    }

    public void setNameCustomer(String nameCustomer) {
        this.nameCustomer = nameCustomer;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
