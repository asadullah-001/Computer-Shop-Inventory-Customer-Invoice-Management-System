package com.computershop.utils;

import com.computershop.model.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileHandler {

    // Save single product
    public static void saveProduct(Item item) {
        try {
            FileWriter myWriter = new FileWriter("products.txt", true);
            myWriter.write(item.getId() + "," + item.getName() + "," + item.getPrice() + "," + item.getCategory() + "\n");
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred while saving product.");
        }
    }

    // Load all products
    public static ArrayList<Item> loadProducts() {
        ArrayList<Item> list = new ArrayList<Item>();
        try {
            File myObj = new File("products.txt");
            if (myObj.exists()) {
                Scanner myReader = new Scanner(myObj);
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    if (!data.trim().equals("")) {
                        String[] parts = data.split(",");
                        if (parts.length == 4) {
                            String id = parts[0];
                            String name = parts[1];
                            double price = Double.parseDouble(parts[2]);
                            String cat = parts[3];

                            if (cat.equalsIgnoreCase("Hardware")) {
                                list.add(new Hardware(id, name, price));
                            } else if (cat.equalsIgnoreCase("Software")) {
                                list.add(new Software(id, name, price));
                            } else {
                                list.add(new Accessory(id, name, price));
                            }
                        }
                    }
                }
                myReader.close();
            }
        } catch (Exception e) {
            System.out.println("An error occurred while reading products.");
        }
        return list;
    }

    // Save single customer
    public static void saveCustomer(Customer c) {
        try {
            FileWriter myWriter = new FileWriter("customers.txt", true);
            myWriter.write(c.getId() + "," + c.getName() + "," + c.getPhone() + "," + c.getTotalPurchases() + "\n");
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred while saving customer.");
        }
    }

    // Load all customers
    public static ArrayList<Customer> loadCustomers() {
        ArrayList<Customer> list = new ArrayList<Customer>();
        try {
            File myObj = new File("customers.txt");
            if (myObj.exists()) {
                Scanner myReader = new Scanner(myObj);
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    if (!data.trim().equals("")) {
                        String[] parts = data.split(",");
                        if (parts.length == 4) {
                            list.add(new Customer(parts[0], parts[1], parts[2], Double.parseDouble(parts[3])));
                        }
                    }
                }
                myReader.close();
            }
        } catch (Exception e) {
            System.out.println("An error occurred while reading customers.");
        }
        return list;
    }

    // Update customer file when invoice is generated
    public static void updateCustomers(ArrayList<Customer> customers) {
        try {
            FileWriter myWriter = new FileWriter("customers.txt", false);
            for (int i = 0; i < customers.size(); i++) {
                Customer c = customers.get(i);
                myWriter.write(c.getId() + "," + c.getName() + "," + c.getPhone() + "," + c.getTotalPurchases() + "\n");
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred while updating customers.");
        }
    }
}