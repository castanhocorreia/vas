package dev.castanhocorreia.factory;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import javax.sql.DataSource;
import java.sql.SQLException;

public final class ConnectionFactory {
    public DataSource dataSource;

    public ConnectionFactory() {
        final String url = "jdbc:mysql://172.17.0.2:3306/store";
        final String user = "castanho";
        final String password = "mysql";
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setJdbcUrl(url);
        comboPooledDataSource.setUser(user);
        comboPooledDataSource.setPassword(password);
        comboPooledDataSource.setMaxPoolSize(15);
        this.dataSource = comboPooledDataSource;
    }

    public Connection createConnection() {
        try {
            Connection connection = this.dataSource.getConnection();
            connection.setAutoCommit(false);
            return connection;
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }
}
