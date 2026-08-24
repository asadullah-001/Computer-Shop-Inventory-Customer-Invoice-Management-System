package com.computershop.model;

import java.util.*;

public class Invoice {
    private int invoiceNumber;
    private Customer customer;
    private List<InvoiceItem> items;
    private Date date;
    private double discountPercent;
    private double finalTotal;

    public static class InvoiceItem {
        private Item item;
        private int quantity;
        public InvoiceItem(Item item, int quantity) {
            this.item = item;
            this.quantity = quantity;
        }
        public Item getItem() { return item; }
        public int getQuantity() { return quantity; }
        public double getTotal() { return item.getPrice() * quantity; }
        public String toFileString() { return item.getId() + "," + quantity; }
        public static InvoiceItem fromFileString(String line, List<Item> items) {
            String[] p = line.split(",");
            int id = Integer.parseInt(p[0]);
            int qty = Integer.parseInt(p[1]);
            for (Item i : items) if (i.getId() == id) return new InvoiceItem(i, qty);
            return null;
        }
    }

    public Invoice(int invoiceNumber, Customer customer, Date date) {
        this.invoiceNumber = invoiceNumber;
        this.customer = customer;
        this.date = date;
        this.items = new ArrayList<>();
        this.discountPercent = 0;
        this.finalTotal = 0;
    }

    public void addItem(Item item, int qty) {
        items.add(new InvoiceItem(item, qty));
        recalculate();
    }

    public void setDiscountPercent(double discountPercent) {
        this.discountPercent = discountPercent;
        recalculate();
    }

    public double calculateFinalTotal(double subtotal, double discountPercent) {
        return subtotal - (subtotal * discountPercent / 100);
    }

    public double calculateFinalTotal(double subtotal, double discountPercent, double customerTotalSpent) {
        double extra = (customerTotalSpent > 10000) ? 2 : 0;
        return calculateFinalTotal(subtotal, discountPercent + extra);
    }

    private void recalculate() {
        double subtotal = items.stream().mapToDouble(InvoiceItem::getTotal).sum();
        this.finalTotal = calculateFinalTotal(subtotal, discountPercent, customer.getTotalSpent());
    }

    public int getInvoiceNumber() { return invoiceNumber; }
    public Customer getCustomer() { return customer; }
    public List<InvoiceItem> getItems() { return items; }
    public Date getDate() { return date; }
    public double getFinalTotal() { return finalTotal; }
    public double getDiscountPercent() { return discountPercent; }
}