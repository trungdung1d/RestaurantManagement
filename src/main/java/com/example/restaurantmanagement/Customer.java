package com.example.demo11;

public class Customer {
    private String name;
    private int id;
    private int point;
    private int identyCard;
    private String address;
    private String phoneNumber;

    public Customer(int id, String name, int point, int identyCard, String address, String phoneNumber) {
        this.name = name;
        this.id = id;
        this.point = point;
        this.identyCard = identyCard;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
    public Customer(){}


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getIdentyCard() {
        return identyCard;
    }

    public void setIdentyCard(int identyCard) {
        this.identyCard = identyCard;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
