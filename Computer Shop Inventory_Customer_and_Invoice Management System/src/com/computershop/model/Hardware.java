package com.computershop.model;

public class Hardware extends Item {
    public Hardware(int id, String name, double price) {
        super(id, name, price);
    }

    @Override
    public String getCategory() {
        return "Hardware";
    }

    @Override
    public double applyDiscount(double percent) {
        if (percent >= 10) percent += 2;
        return super.applyDiscount(percent);
    }
}