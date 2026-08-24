package com.computershop.model;

import com.computershop.utils.InvalidDiscountException;

public class Invoice {
    private Customer customer;
    private Item item;
    private int quantity;
    private double discountPercentage;

    public Invoice(Customer customer, Item item, int quantity, double discountPercentage) throws InvalidDiscountException {
        if (discountPercentage < 0 || discountPercentage > 100) {
            throw new InvalidDiscountException("Discount must be between 0% and 100%");
        }
        this.customer = customer;
        this.item = item;
        this.quantity = quantity;
        this.discountPercentage = discountPercentage;
    }

    public double calculateSubtotal() {
        return item.getPrice() * quantity;
    }

    public double calculateDiscountAmount() {
        return (calculateSubtotal() * discountPercentage) / 100.0;
    }

    public double calculateFinalTotal() {
        return calculateSubtotal() - calculateDiscountAmount();
    }

    public String generateBill() {
        double subtotal = calculateSubtotal();
        double discountAmount = calculateDiscountAmount();
        double finalTotal = calculateFinalTotal();

        String bill = "========================================\n";
        bill += "            COMPUTER SHOP INVOICE       \n";
        bill += "========================================\n";
        bill += "Customer Name : " + customer.getName() + " (ID: " + customer.getId() + ")\n";
        bill += "Contact No    : " + customer.getPhone() + "\n";
        bill += "----------------------------------------\n";
        bill += "Item Name     : " + item.getName() + " (" + item.getCategory() + ")\n";
        bill += "Unit Price    : " + item.getPrice() + " BDT\n";
        bill += "Quantity      : " + quantity + "\n";
        bill += "Subtotal      : " + subtotal + " BDT\n";
        bill += "Discount (" + discountPercentage + "%) : -" + discountAmount + " BDT\n";
        bill += "----------------------------------------\n";
        bill += "Grand Total   : " + finalTotal + " BDT\n";
        bill += "========================================\n";
        return bill;
    }
}