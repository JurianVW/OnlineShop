package onlineshop.shared;

import onlineshop.supplier.DatabaseSupplier;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class DatabaseConnection {
    private static final Logger LOGGER = Logger.getLogger(DatabaseSupplier.class.getName());
    private Connection conn;

    public void setConnection() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=OnlineShop;integratedSecurity=true;");
            LOGGER.info("Connection to database SUCCEED");
        } catch (SQLException e) {
            LOGGER.info(e.getMessage());
            LOGGER.info("Error with driver library");
        }
    }

    public void closeConnection() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            LOGGER.info(ex.getMessage());
        } finally {
            conn = null;
        }
    }

    public Connection getConn() {
        return conn;
    }
}
