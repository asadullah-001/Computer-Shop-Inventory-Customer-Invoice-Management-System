# Computer Shop Inventory, Customer & Invoice Management System

## Course
CSE215L: Programming Language II Lab

## Description
A Java Swing application for small computer shops to manage:
- Product inventory (Hardware, Software, Accessories)
- Customer records with purchase history
- Invoice generation with dynamic discounts

## OOP Concepts Implemented
- Encapsulation (private fields with getters/setters)
- Inheritance (Item → Hardware, Software, Accessory)
- Abstraction (Abstract Item class)
- Method Overloading (applyDiscount)
- Method Overriding (getCategory, applyDiscount)
- Custom Exceptions (InvalidDiscountException, ProductNotFoundException)

## How to Run
1. Compile: `javac com/computershop/model/*.java com/computershop/utils/*.java com/computershop/ui/*.java`
2. Run: `java com.computershop.ui.MainFrame`

## Data Storage
Uses text files (`products.txt`, `customers.txt`, `invoices.txt`) with File I/O.

## Group Members
- Md. Asadullah Oli, ID: 2414010642
- Raonok Matabbar, ID: 2523862642
