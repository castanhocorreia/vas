package dev.castanhocorreia.view;

import java.awt.Color;
import java.awt.Container;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import dev.castanhocorreia.controller.ProductController;
import dev.castanhocorreia.model.Product;

public class Frame extends JFrame {
    final private JLabel nameLabel, priceLabel;
    final private JTextField nameField, priceField;
    final private JButton saveButton, editButton, clearButton, deleteButton;
    final private JTable table;
    final private DefaultTableModel model;
    final private ProductController productController;

    public Frame() {
        super("Products");
        this.productController = new ProductController();
        Container container = getContentPane();
        setLayout(null);

        nameLabel = new JLabel("Name");
        nameLabel.setBounds(10, 10, 250, 20);
        nameLabel.setForeground(Color.BLACK);

        nameField = new JTextField();
        nameField.setBounds(10, 25, 250, 20);

        priceLabel = new JLabel("Price");
        priceLabel.setBounds(10, 50, 250, 15);
        priceLabel.setForeground(Color.BLACK);

        priceField = new JTextField();
        priceField.setBounds(10, 65, 250, 20);

        saveButton = new JButton("Save");
        clearButton = new JButton("Clear");

        this.table = new JTable();

        this.model = (DefaultTableModel) this.table.getModel();

        this.model.addColumn("ID");
        this.model.addColumn("NAME");
        this.model.addColumn("PRICE");

        container.add(nameLabel);
        container.add(nameField);
        container.add(priceLabel);
        container.add(priceField);
        container.add(saveButton);
        container.add(clearButton);

        fillTable();

        table.setBounds(10, 185, 760, 300);
        container.add(table);

        deleteButton = new JButton("Delete");
        deleteButton.setBounds(10, 500, 80, 20);

        editButton = new JButton("Edit");
        editButton.setBounds(100, 500, 80, 20);

        container.add(deleteButton);
        container.add(editButton);

        setSize(800, 600);
        setVisible(true);
        setLocationRelativeTo(null);

        this.saveButton.addActionListener(action -> {
            save();
            clearTable();
            fillTable();
        });

        this.clearButton.addActionListener(action -> {
            clear();
        });

        this.deleteButton.addActionListener(action -> {
            delete();
            clearTable();
            fillTable();
        });

        this.editButton.addActionListener(action -> {
            edit();
            clearTable();
            fillTable();
        });

    }

    private void save() {
        if (!priceField.getText().matches("^[0-9]*$")) {
            JOptionPane.showMessageDialog(this, "Only numbers are allowed in the price field.");
            return;
        }
        if (!nameField.getText().equals("") && !priceField.getText().equals("")) {
            Product product = new Product(nameField.getText(), Float.parseFloat(priceField.getText()));
            this.productController.create(product);
            JOptionPane.showMessageDialog(this, "The product was successfully created.");
        } else {
            JOptionPane.showMessageDialog(this, "Name and price need to be informed.");
        }
    }

    private void clearTable() {
        this.model.getDataVector().clear();
    }

    private void fillTable() {
        List<Product> products = listProducts();
        try {
            for (Product product : products) {
                this.model.addRow(new Object[]{product.getId(), product.getName(), product.getPrice()});
            }
        } catch (NullPointerException exception) {
            throw new NullPointerException();
        }
    }

    private void clear() {
        this.nameField.setText("");
        this.priceField.setText("");
    }

    private void delete() {
        int selectedColumn = table.getSelectedColumn();
        int selectedRow = table.getSelectedRow();
        Object product = this.model.getValueAt(selectedRow, selectedColumn);
        if (product instanceof Integer) {
            int id = (Integer) product;
            this.productController.delete(id);
            this.model.removeRow(table.getSelectedRow());
            JOptionPane.showMessageDialog(this, "The product was successfully deleted.");
        } else {
            JOptionPane.showMessageDialog(this, "Please, select the ID column.");
        }
    }

    private void edit() {
        int selectedColumn = table.getSelectedColumn();
        int selectedRow = table.getSelectedRow();
        Object product = this.model.getValueAt(selectedRow, selectedColumn);
        if (product instanceof Integer) {
            int id = (Integer) product;
            String name = (String) this.model.getValueAt(selectedRow, 1);
            float price = (float) this.model.getValueAt(selectedRow, 2);
            this.productController.update(id, name, price);
            JOptionPane.showMessageDialog(this, "The product was successfully updated.");
        } else {
            JOptionPane.showMessageDialog(this, "Please, select the ID column.");
        }
    }

    private List<Product> listProducts() {
        return this.productController.read();
    }

}
