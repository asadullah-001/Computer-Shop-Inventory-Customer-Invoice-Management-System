package com.computershop.model;

public class Software extends Item {
    public Software(int id, String name, double price) {
        super(id, name, price);
    }

    @Override
    public String getCategory() {
        return "Software";
    }
}