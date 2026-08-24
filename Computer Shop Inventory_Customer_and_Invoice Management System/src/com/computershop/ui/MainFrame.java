package com.computershop.ui;

import com.computershop.model.*;
import com.computershop.utils.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class MainFrame extends JFrame implements ActionListener {

    // UI Components for Product Section
    JTextField tProdId, tProdName, tProdPrice;
    JComboBox<String> cbCategory;
    JButton bAddProduct, bShowProducts;

    // UI Components for Customer Section
    JTextField tCustId, tCustName, tCustPhone;
    JButton bAddCustomer, bShowCustomers;

    // UI Components for Invoice Section
    JTextField tInvCustId, tInvProdId, tInvQty, tInvDiscount;
    JButton bGenerateInvoice;

    // Output area
    JTextArea displayArea;

    // In-memory collections
    ArrayList<Item> productList;
    ArrayList<Customer> customerList;

    public MainFrame() {
        super("Computer Shop Inventory & Billing System");
        setBounds(100, 50, 750, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        productList = FileHandler.loadProducts();
        customerList = FileHandler.loadCustomers();

        Container con = this.getContentPane();
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // 1. Product Panel
        JPanel pProd = new JPanel();
        pProd.setBorder(BorderFactory.createTitledBorder("Product Management"));
        pProd.add(new JLabel("ID:"));
        tProdId = new JTextField(5);
        pProd.add(tProdId);

        pProd.add(new JLabel("Name:"));
        tProdName = new JTextField(8);
        pProd.add(tProdName);

        pProd.add(new JLabel("Price:"));
        tProdPrice = new JTextField(5);
        pProd.add(tProdPrice);

        pProd.add(new JLabel("Type:"));
        String[] types = {"Hardware", "Software", "Accessory"};
        cbCategory = new JComboBox<String>(types);
        pProd.add(cbCategory);

        bAddProduct = new JButton("Add Product");
        bShowProducts = new JButton("View Products");
        bAddProduct.addActionListener(this);
        bShowProducts.addActionListener(this);
        pProd.add(bAddProduct);
        pProd.add(bShowProducts);

        // 2. Customer Panel
        JPanel pCust = new JPanel();
        pCust.setBorder(BorderFactory.createTitledBorder("Customer Management"));
        pCust.add(new JLabel("ID:"));
        tCustId = new JTextField(5);
        pCust.add(tCustId);

        pCust.add(new JLabel("Name:"));
        tCustName = new JTextField(8);
        pCust.add(tCustName);

        pCust.add(new JLabel("Phone:"));
        tCustPhone = new JTextField(8);
        pCust.add(tCustPhone);

        bAddCustomer = new JButton("Add Customer");
        bShowCustomers = new JButton("View Customers");
        bAddCustomer.addActionListener(this);
        bShowCustomers.addActionListener(this);
        pCust.add(bAddCustomer);
        pCust.add(bShowCustomers);

        // 3. Invoice Panel
        JPanel pInv = new JPanel();
        pInv.setBorder(BorderFactory.createTitledBorder("Invoice & Billing"));
        pInv.add(new JLabel("Cust ID:"));
        tInvCustId = new JTextField(5);
        pInv.add(tInvCustId);

        pInv.add(new JLabel("Prod ID:"));
        tInvProdId = new JTextField(5);
        pInv.add(tInvProdId);

        pInv.add(new JLabel("Qty:"));
        tInvQty = new JTextField(3);
        pInv.add(tInvQty);

        pInv.add(new JLabel("Discount (%):"));
        tInvDiscount = new JTextField(3);
        pInv.add(tInvDiscount);

        bGenerateInvoice = new JButton("Generate Invoice");
        bGenerateInvoice.addActionListener(this);
        pInv.add(bGenerateInvoice);

        // 4. Output Display Area
        displayArea = new JTextArea(16, 60);
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        mainPanel.add(pProd);
        mainPanel.add(pCust);
        mainPanel.add(pInv);
        mainPanel.add(scrollPane);

        con.add(mainPanel);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bAddProduct) {
            String id = tProdId.getText().trim();
            String name = tProdName.getText().trim();
            String priceStr = tProdPrice.getText().trim();
            String cat = (String) cbCategory.getSelectedItem();

            if (id.equals("") || name.equals("") || priceStr.equals("")) {
                displayArea.setText("Please fill all product fields!");
                return;
            }

            try {
                double price = Double.parseDouble(priceStr);
                Item item;
                if (cat.equals("Hardware")) {
                    item = new Hardware(id, name, price);
                } else if (cat.equals("Software")) {
                    item = new Software(id, name, price);
                } else {
                    item = new Accessory(id, name, price);
                }

                productList.add(item);
                FileHandler.saveProduct(item);

                tProdId.setText("");
                tProdName.setText("");
                tProdPrice.setText("");
                displayArea.setText("Product added successfully!\nID: " + id + " | Name: " + name + " | Price: " + price);
            } catch (Exception ex) {
                displayArea.setText("Invalid price entered!");
            }
        } else if (e.getSource() == bShowProducts) {
            String out = "=== PRODUCT INVENTORY ===\n";
            for (int i = 0; i < productList.size(); i++) {
                Item it = productList.get(i);
                out += "ID: " + it.getId() + " | Name: " + it.getName() + " | Price: " + it.getPrice() + " | Category: " + it.getCategory() + "\n";
            }
            displayArea.setText(out);
        } else if (e.getSource() == bAddCustomer) {
            String id = tCustId.getText().trim();
            String name = tCustName.getText().trim();
            String phone = tCustPhone.getText().trim();

            if (id.equals("") || name.equals("") || phone.equals("")) {
                displayArea.setText("Please fill all customer fields!");
                return;
            }

            Customer c = new Customer(id, name, phone, 0.0);
            customerList.add(c);
            FileHandler.saveCustomer(c);

            tCustId.setText("");
            tCustName.setText("");
            tCustPhone.setText("");
            displayArea.setText("Customer added successfully!\nID: " + id + " | Name: " + name);
        } else if (e.getSource() == bShowCustomers) {
            String out = "=== CUSTOMER RECORDS ===\n";
            for (int i = 0; i < customerList.size(); i++) {
                Customer c = customerList.get(i);
                out += "ID: " + c.getId() + " | Name: " + c.getName() + " | Phone: " + c.getPhone() + " | Total Purchase: " + c.getTotalPurchases() + " BDT\n";
            }
            displayArea.setText(out);
        } else if (e.getSource() == bGenerateInvoice) {
            String cId = tInvCustId.getText().trim();
            String pId = tInvProdId.getText().trim();
            String qStr = tInvQty.getText().trim();
            String dStr = tInvDiscount.getText().trim();

            if (cId.equals("") || pId.equals("") || qStr.equals("")) {
                displayArea.setText("Please enter Customer ID, Product ID, and Quantity!");
                return;
            }

            Customer foundCust = null;
            for (int i = 0; i < customerList.size(); i++) {
                if (customerList.get(i).getId().equalsIgnoreCase(cId)) {
                    foundCust = customerList.get(i);
                    break;
                }
            }

            Item foundProd = null;
            for (int i = 0; i < productList.size(); i++) {
                if (productList.get(i).getId().equalsIgnoreCase(pId)) {
                    foundProd = productList.get(i);
                    break;
                }
            }

            try {
                if (foundCust == null) {
                    displayArea.setText("Error: Customer ID not found!");
                    return;
                }
                if (foundProd == null) {
                    throw new ProductNotFoundException("Product with ID " + pId + " does not exist.");
                }

                int qty = Integer.parseInt(qStr);
                double discount = 0.0;
                if (!dStr.equals("")) {
                    discount = Double.parseDouble(dStr);
                }

                if (discount < 0 || discount > 100) {
                    throw new InvalidDiscountException("Discount must be between 0% and 100%");
                }

                double subtotal = foundProd.getPrice() * qty;
                double discountAmount = (subtotal * discount) / 100.0;
                double finalTotal = subtotal - discountAmount;

                foundCust.addPurchase(finalTotal);
                FileHandler.updateCustomers(customerList);

                String invoice = "========================================\n";
                invoice += "            COMPUTER SHOP INVOICE       \n";
                invoice += "========================================\n";
                invoice += "Customer Name : " + foundCust.getName() + " (ID: " + foundCust.getId() + ")\n";
                invoice += "Contact No    : " + foundCust.getPhone() + "\n";
                invoice += "----------------------------------------\n";
                invoice += "Item Name     : " + foundProd.getName() + " (" + foundProd.getCategory() + ")\n";
                invoice += "Unit Price    : " + foundProd.getPrice() + " BDT\n";
                invoice += "Quantity      : " + qty + "\n";
                invoice += "Subtotal      : " + subtotal + " BDT\n";
                invoice += "Discount (" + discount + "%) : -" + discountAmount + " BDT\n";
                invoice += "----------------------------------------\n";
                invoice += "Grand Total   : " + finalTotal + " BDT\n";
                invoice += "========================================\n";
                invoice += "Customer Total Spend : " + foundCust.getTotalPurchases() + " BDT\n";

                displayArea.setText(invoice);

            } catch (ProductNotFoundException pEx) {
                displayArea.setText("Error: " + pEx.getMessage());
            } catch (InvalidDiscountException dEx) {
                displayArea.setText("Error: " + dEx.getMessage());
            } catch (NumberFormatException nEx) {
                displayArea.setText("Error: Please enter valid numbers for Quantity and Discount!");
            }
        }
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}