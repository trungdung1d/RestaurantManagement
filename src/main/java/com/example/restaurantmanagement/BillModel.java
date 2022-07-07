package com.example.restaurantmanagement;

public class BillModel {
    String ID, Date, Amount;
    Double VAT, Price, TotalPrice;

    public BillModel() {

    }

    public BillModel(String ID, String date, String amount, Double VAT, Double price, Double totalPrice) {
        this.ID = ID;
        Date = date;
        Amount = amount;
        this.VAT = VAT;
        Price = price;
        TotalPrice = totalPrice;
    }

    public BillModel(String date, String amount, double VAT, double price, double totalPrice) {
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public Double getVAT() {
        return VAT;
    }

    public void setVAT(Double VAT) {
        this.VAT = VAT;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
    }

    public Double getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        TotalPrice = totalPrice;
    }
}
