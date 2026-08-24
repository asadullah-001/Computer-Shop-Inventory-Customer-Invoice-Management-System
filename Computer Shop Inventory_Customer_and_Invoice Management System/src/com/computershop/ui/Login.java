package com.computershop.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Login extends JFrame implements ActionListener {
    JPanel pane = new JPanel();
    JLabel userLabel = new JLabel("Username: ");
    JTextField userText = new JTextField(10);
    JLabel passLabel = new JLabel("Password: ");
    JPasswordField passText = new JPasswordField(10);
    JButton pressme = new JButton("Login");

    public Login() {
        super("Login - Computer Shop");
        setBounds(100, 100, 320, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container con = this.getContentPane();

        pane.setLayout(new FlowLayout());
        pane.add(userLabel);
        pane.add(userText);
        pane.add(passLabel);
        pane.add(passText);

        pressme.setMnemonic('P');
        pressme.addActionListener(this);
        pane.add(pressme);

        con.add(pane);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String username = userText.getText();
        String password = new String(passText.getPassword());

        if (username.equals("admin") && password.equals("admin")) {
            new MainFrame();
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials! Use admin / admin");
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}