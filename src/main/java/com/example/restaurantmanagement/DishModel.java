package com.example.restaurantmanagement;

public class DishModel {
    String ID, Name, Amount;
    Double VAT, Price, TotalPrice;

    public DishModel() {

    }

    public DishModel(String ID, String name, String amount, Double VAT, Double price, Double totalPrice) {
        this.ID = ID;
        Name = name;
        Amount = amount;
        this.VAT = VAT;
        Price = price;
        TotalPrice = totalPrice;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
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
