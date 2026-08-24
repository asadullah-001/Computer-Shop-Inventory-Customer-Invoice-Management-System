package com.computershop.model;

public class Accessory extends Item {
    public Accessory(int id, String name, double price) {
        super(id, name, price);
    }

    @Override
    public String getCategory() {
        return "Accessory";
    }
}