package onlineshop.supplier;

import onlineshop.shared.Account;
import onlineshop.shared.AccountType;
import onlineshop.shared.DatabaseConnection;
import onlineshop.shared.MD5Digest;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class DatabaseSupplier {
    private static final Logger LOGGER = Logger.getLogger(DatabaseSupplier.class.getName());
    private DatabaseConnection dbConn = new DatabaseConnection();

    String supplierName;

    private MD5Digest md5Digest = new MD5Digest();

    public DatabaseSupplier(String supplierName) {
        this.supplierName = supplierName;
    }

    public Account logIn(String username, String password) {
        Account account = null;
        String digestedPassword = md5Digest.digest(password);
        if (digestedPassword != null) {
            dbConn.setConnection();
            try (PreparedStatement myStmt = dbConn.getConn().prepareStatement("SELECT * FROM [Account] WHERE [Email] = ? AND [Password] = ? AND [AccountType] = ? ")) {
                myStmt.setString(1, username);
                myStmt.setString(2, digestedPassword);
                myStmt.setString(3, AccountType.SUPPLIEREMPLOYEE.toString());
                try (ResultSet myRs = myStmt.executeQuery()) {
                    while (myRs.next()) {
                        account = new Account(
                                myRs.getInt("ID"),
                                AccountType.valueOf(myRs.getString("AccountType")),
                                myRs.getString("Name"),
                                myRs.getString("Email"));
                    }
                }
            } catch (SQLException ex) {
                LOGGER.info(ex.getMessage());
            } finally {
                dbConn.closeConnection();
            }
        }
        return account;
    }

    public void addProduct(Product product) {
        Integer id = getNextProductNumber();
        product.setId(id);
        dbConn.setConnection();
        try (PreparedStatement myStmt = dbConn.getConn().prepareStatement("INSERT INTO Product (ProductNumber, ProductName, SupplierName, PurchaseCost, Amount, Edition) VALUES (?,?,?,?,?,?) ")) {
            myStmt.setInt(1, id);
            myStmt.setString(2, product.getName());
            myStmt.setString(3, supplierName);
            myStmt.setDouble(4, product.getPurchasePrice());
            myStmt.setInt(5, product.getAmount());
            myStmt.setInt(6, product.getEdition());
            myStmt.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.info(ex.getMessage());
        } finally {
            dbConn.closeConnection();
        }
    }

    public void removeProduct(Product product) {
        dbConn.setConnection();
        try (PreparedStatement myStmt = dbConn.getConn().prepareStatement("DELETE FROM Product WHERE [ProductNumber] = ?")) {
            myStmt.setInt(1, product.getId());
            myStmt.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.info(ex.getMessage());
        } finally {
            dbConn.closeConnection();
        }
    }

    public void productChanged(Product product) {
        dbConn.setConnection();
        try (PreparedStatement myStmt = dbConn.getConn().prepareStatement("UPDATE Product SET [ProductName] = ?, [PurchaseCost] = ?, [Amount] = ?, [Edition] = ? WHERE [ProductNumber] = ?")) {
            myStmt.setString(1, product.getName());
            myStmt.setDouble(2, product.getPurchasePrice());
            myStmt.setInt(3, product.getAmount());
            myStmt.setInt(4, product.getEdition());
            myStmt.setInt(5, product.getId());
            myStmt.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.info(ex.getMessage());
        } finally {
            dbConn.closeConnection();
        }
    }

    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        dbConn.setConnection();
        try (PreparedStatement myStmt = dbConn.getConn().prepareStatement("SELECT * FROM [Product] WHERE [SupplierName] = ? ORDER BY [ProductName]")) {
            myStmt.setString(1, supplierName);
            try (ResultSet myRs = myStmt.executeQuery()) {
                while (myRs.next()) {
                    Product product = new Product(
                            myRs.getString("ProductName"),
                            myRs.getDouble("PurchaseCost"),
                            myRs.getInt("Amount"),
                            myRs.getInt("Edition"));
                    product.setId(myRs.getInt("ProductNumber"));
                    products.add(product);
                }
            }

        } catch (SQLException ex) {
            LOGGER.info(ex.getMessage());
        } finally {
            dbConn.closeConnection();
        }
        return products;
    }

    private Integer getNextProductNumber() {
        Integer number = 0;
        dbConn.setConnection();
        try (PreparedStatement myStmt = dbConn.getConn().prepareStatement("SELECT TOP 1 * FROM [Product] ORDER BY ProductNumber DESC")) {
            try (ResultSet myRs = myStmt.executeQuery()) {
                while (myRs.next()) {
                    number = myRs.getInt("ProductNumber");
                }
            }
        } catch (SQLException ex) {
            LOGGER.info(ex.getMessage());
        } finally {
            dbConn.closeConnection();
        }
        return number + 1;
    }
}