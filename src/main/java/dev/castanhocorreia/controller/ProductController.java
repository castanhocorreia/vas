package dev.castanhocorreia.controller;

import dev.castanhocorreia.dao.ProductDAO;
import dev.castanhocorreia.factory.ConnectionFactory;
import dev.castanhocorreia.model.Product;

import java.sql.Connection;
import java.util.List;

public class ProductController {
    final private ProductDAO productDAO;

    public ProductController() {
        Connection connection = new ConnectionFactory().createConnection();
        this.productDAO = new ProductDAO(connection);
    }

    public void create(Product product) {
        this.productDAO.create(product);
    }

    public List<Product> read() {
        return this.productDAO.read();
    }

    public void update(int id, String name, float price) {
        this.productDAO.update(id, name, price);
    }

    public void delete(int id) {
        this.productDAO.delete(id);
    }
}
