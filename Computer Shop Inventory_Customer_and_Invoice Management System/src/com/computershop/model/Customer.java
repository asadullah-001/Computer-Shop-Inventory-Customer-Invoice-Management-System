package com.computershop.model;

public class Customer {
    private int id;
    private String name, phone, email, address;
    private double totalSpent;

    public Customer(int id, String name, String phone, String email, String address) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.totalSpent = 0;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public double getTotalSpent() { return totalSpent; }
    public void setTotalSpent(double totalSpent) { this.totalSpent = totalSpent; }
    public void addPurchase(double amount) { this.totalSpent += amount; }

    public String toFileString() {
        return id + "," + name + "," + phone + "," + email + "," + address + "," + totalSpent;
    }

    public static Customer fromFileString(String line) {
        String[] p = line.split(",");
        Customer c = new Customer(Integer.parseInt(p[0]), p[1], p[2], p[3], p[4]);
        c.setTotalSpent(Double.parseDouble(p[5]));
        return c;
    }
}