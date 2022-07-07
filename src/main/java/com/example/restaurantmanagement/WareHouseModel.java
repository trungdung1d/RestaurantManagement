package com.example.restaurantmanagement;

public class WareHouseModel {
    private String ingredient;
    private int id;
    private int number;
    private double price;

    public WareHouseModel(String ingredient, int id, int number, double price) {
        this.ingredient = ingredient;
        this.id = id;
        this.number = number;
        this.price = price;
    }
    public WareHouseModel(){

    }
    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
