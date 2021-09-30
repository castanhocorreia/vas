package dev.castanhocorreia.dao;

import dev.castanhocorreia.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    Connection connection;

    public ProductDAO(Connection connection) {
        this.connection = connection;
    }

    public void create(Product product) {
        String query = "INSERT INTO product(name, price) VALUES(?, ?)";
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(query)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setFloat(2, product.getPrice());
            preparedStatement.execute();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    System.out.println(id);
                    product.setId(id);
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public List<Product> read() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT id, name, price FROM product";
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    String name = resultSet.getString(2);
                    float price = resultSet.getFloat(3);
                    Product product = new Product(name, price);
                    product.setId(id);
                    products.add(product);
                }
            }
            return products;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }
    }

    public void update(int id, String name, float price) {
        String query = "UPDATE product P SET P.NAME = ?, P.PRICE = ? WHERE ID = ?";
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setFloat(3, price);
            preparedStatement.execute();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public void delete(int id) {
        String query = "DELETE FROM product WHERE id=?";
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
