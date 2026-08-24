package com.computershop.ui;

import com.computershop.model.*;
import com.computershop.utils.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class MainFrame extends JFrame {
    private List<Item> items = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();
    private List<Invoice> invoices = new ArrayList<>();

    private DefaultTableModel productModel, customerModel, invoiceModel;
    private JTable productTable, customerTable, invoiceTable;

    public MainFrame() {
        setTitle("Computer Shop Management System");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        loadData();

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Products", createProductPanel());
        tabs.addTab("Customers", createCustomerPanel());
        tabs.addTab("Invoices", createInvoicePanel());
        add(tabs);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                saveData();
            }
        });
        setVisible(true);
    }

    private JPanel createProductPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        productModel = new DefaultTableModel(new String[]{"ID", "Name", "Price", "Category"}, 0);
        productTable = new JTable(productModel);

        JPanel controls = new JPanel(new FlowLayout());
        JTextField idField = new JTextField(5);
        JTextField nameField = new JTextField(10);
        JTextField priceField = new JTextField(8);
        JComboBox<String> catCombo = new JComboBox<>(new String[]{"Hardware", "Software", "Accessory"});
        JButton addBtn = new JButton("Add");
        JButton updateBtn = new JButton("Update");
        JButton deleteBtn = new JButton("Delete");

        controls.add(new JLabel("ID:")); controls.add(idField);
        controls.add(new JLabel("Name:")); controls.add(nameField);
        controls.add(new JLabel("Price:")); controls.add(priceField);
        controls.add(new JLabel("Category:")); controls.add(catCombo);
        controls.add(addBtn); controls.add(updateBtn); controls.add(deleteBtn);

        addBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                double price = Double.parseDouble(priceField.getText());
                String cat = (String) catCombo.getSelectedItem();
                Item item;
                if (cat.equals("Hardware")) item = new Hardware(id, name, price);
                else if (cat.equals("Software")) item = new Software(id, name, price);
                else item = new Accessory(id, name, price);
                items.add(item);
                refreshProductTable();
                idField.setText(""); nameField.setText(""); priceField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input");
            }
        });

        updateBtn.addActionListener(e -> {
            int row = productTable.getSelectedRow();
            if (row == -1) { JOptionPane.showMessageDialog(this, "Select a row first"); return; }
            try {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                double price = Double.parseDouble(priceField.getText());
                String cat = (String) catCombo.getSelectedItem();
                for (Item i : items) {
                    if (i.getId() == id) {
                        i.setName(name);
                        i.setPrice(price);
                        // Note: Category change not supported for simplicity
                        break;
                    }
                }
                refreshProductTable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input");
            }
        });

        deleteBtn.addActionListener(e -> {
            int row = productTable.getSelectedRow();
            if (row == -1) { JOptionPane.showMessageDialog(this, "Select a row first"); return; }
            int id = (int) productModel.getValueAt(row, 0);
            items.removeIf(i -> i.getId() == id);
            refreshProductTable();
        });

        panel.add(new JScrollPane(productTable), BorderLayout.CENTER);
        panel.add(controls, BorderLayout.SOUTH);
        refreshProductTable();
        return panel;
    }

    private JPanel createCustomerPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        customerModel = new DefaultTableModel(new String[]{"ID", "Name", "Phone", "Email", "Address", "Total Spent"}, 0);
        customerTable = new JTable(customerModel);

        JPanel controls = new JPanel(new FlowLayout());
        JTextField idField = new JTextField(5);
        JTextField nameField = new JTextField(10);
        JTextField phoneField = new JTextField(10);
        JTextField emailField = new JTextField(10);
        JTextField addressField = new JTextField(10);
        JButton addBtn = new JButton("Add");
        JButton deleteBtn = new JButton("Delete");

        controls.add(new JLabel("ID:")); controls.add(idField);
        controls.add(new JLabel("Name:")); controls.add(nameField);
        controls.add(new JLabel("Phone:")); controls.add(phoneField);
        controls.add(new JLabel("Email:")); controls.add(emailField);
        controls.add(new JLabel("Address:")); controls.add(addressField);
        controls.add(addBtn); controls.add(deleteBtn);

        addBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                String phone = phoneField.getText();
                String email = emailField.getText();
                String address = addressField.getText();
                customers.add(new Customer(id, name, phone, email, address));
                refreshCustomerTable();
                idField.setText(""); nameField.setText(""); phoneField.setText("");
                emailField.setText(""); addressField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input");
            }
        });

        deleteBtn.addActionListener(e -> {
            int row = customerTable.getSelectedRow();
            if (row == -1) { JOptionPane.showMessageDialog(this, "Select a row first"); return; }
            int id = (int) customerModel.getValueAt(row, 0);
            customers.removeIf(c -> c.getId() == id);
            refreshCustomerTable();
        });

        panel.add(new JScrollPane(customerTable), BorderLayout.CENTER);
        panel.add(controls, BorderLayout.SOUTH);
        refreshCustomerTable();
        return panel;
    }

    private JPanel createInvoicePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        invoiceModel = new DefaultTableModel(new String[]{"Invoice #", "Customer", "Items", "Total"}, 0);
        invoiceTable = new JTable(invoiceModel);

        JPanel controls = new JPanel(new FlowLayout());
        JTextField invNoField = new JTextField(5);
        JComboBox<Customer> customerCombo = new JComboBox<>();
        JButton generateBtn = new JButton("Generate Invoice");

        controls.add(new JLabel("Invoice #:")); controls.add(invNoField);
        controls.add(new JLabel("Customer:")); controls.add(customerCombo);
        controls.add(generateBtn);

        // Refresh customer combo box
        Runnable refreshCombo = () -> {
            customerCombo.removeAllItems();
            for (Customer c : customers) customerCombo.addItem(c);
        };
        refreshCombo.run();

        generateBtn.addActionListener(e -> {
            try {
                int invNo = Integer.parseInt(invNoField.getText());
                Customer selected = (Customer) customerCombo.getSelectedItem();
                if (selected == null) { JOptionPane.showMessageDialog(this, "No customer selected"); return; }

                // Simple invoice: add first product with quantity 2 to demonstrate discount
                if (items.isEmpty()) { JOptionPane.showMessageDialog(this, "No products available"); return; }
                Item firstItem = items.get(0);

                Invoice inv = new Invoice(invNo, selected, new Date());
                inv.addItem(firstItem, 2); // quantity 2
                inv.setDiscountPercent(10); // 10% discount
                invoices.add(inv);

                // Add to customer's total spent
                selected.addPurchase(inv.getFinalTotal());
                refreshCustomerTable();

                JOptionPane.showMessageDialog(this, "Invoice #" + invNo + " generated!\nTotal: " + inv.getFinalTotal() +
                        "\nDiscount applied: " + inv.getDiscountPercent() + "%");
                refreshInvoiceTable();
                invNoField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        panel.add(new JScrollPane(invoiceTable), BorderLayout.CENTER);
        panel.add(controls, BorderLayout.SOUTH);
        refreshInvoiceTable();
        return panel;
    }

    private void refreshProductTable() {
        productModel.setRowCount(0);
        for (Item i : items) {
            productModel.addRow(new Object[]{i.getId(), i.getName(), i.getPrice(), i.getCategory()});
        }
    }

    private void refreshCustomerTable() {
        customerModel.setRowCount(0);
        for (Customer c : customers) {
            customerModel.addRow(new Object[]{c.getId(), c.getName(), c.getPhone(), c.getEmail(), c.getAddress(), c.getTotalSpent()});
        }
    }

    private void refreshInvoiceTable() {
        invoiceModel.setRowCount(0);
        for (Invoice i : invoices) {
            invoiceModel.addRow(new Object[]{i.getInvoiceNumber(), i.getCustomer().getName(), i.getItems().size(), i.getFinalTotal()});
        }
    }

    private void loadData() {
        items = FileHandler.readFromFile("products.txt", Item::fromFileString);
        customers = FileHandler.readFromFile("customers.txt", Customer::fromFileString);
    }

    private void saveData() {
        FileHandler.writeToFile("products.txt", items, Item::toFileString);
        FileHandler.writeToFile("customers.txt", customers, Customer::toFileString);
        // Invoices saving omitted for simplicity, but can be added similarly
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }
}