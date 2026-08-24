package com.computershop.model;

public abstract class Item {
    private int id;
    private String name;
    private double price;

    public Item(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public abstract String getCategory();

    public double applyDiscount(double percent) {
        if (percent < 0 || percent > 100) throw new IllegalArgumentException("Invalid discount");
        return price * (1 - percent / 100);
    }

    public double applyDiscount(double percent, int quantity) {
        double extra = (quantity > 10) ? 5 : 0;
        return applyDiscount(percent + extra);
    }

    public String toFileString() {
        return id + "," + name + "," + price + "," + getCategory();
    }

    public static Item fromFileString(String line) {
        String[] parts = line.split(",");
        int id = Integer.parseInt(parts[0]);
        String name = parts[1];
        double price = Double.parseDouble(parts[2]);
        String cat = parts[3];
        if (cat.equals("Hardware")) return new Hardware(id, name, price);
        else if (cat.equals("Software")) return new Software(id, name, price);
        else return new Accessory(id, name, price);
    }
}