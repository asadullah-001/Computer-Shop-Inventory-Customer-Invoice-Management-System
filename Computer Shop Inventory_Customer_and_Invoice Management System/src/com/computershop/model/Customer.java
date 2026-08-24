package com.computershop.model;

public class Customer {
    private String id;
    private String name;
    private String phone;
    private double totalPurchases;

    public Customer(String id, String name, String phone, double totalPurchases) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.totalPurchases = totalPurchases;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public double getTotalPurchases() {
        return totalPurchases;
    }

    public void addPurchase(double amount) {
        this.totalPurchases = this.totalPurchases + amount;
    }
}