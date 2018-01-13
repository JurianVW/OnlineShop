package online_shop.shop;

import online_shop.shared.Account;
import online_shop.shared.AccountType;
import online_shop.shared.DatabaseConnection;
import online_shop.supplier.DatabaseSupplier;
import online_shop.supplier.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class DatabaseShop {
    private static final Logger LOGGER = Logger.getLogger(DatabaseSupplier.class.getName());
    private DatabaseConnection dbConn = new DatabaseConnection();

    private String shopName;

    public DatabaseShop(String shopName) {
        this.shopName = shopName;
    }

    public Account logIn(String username, String password) {
        Account account = null;
        dbConn.setConnection();
        try (PreparedStatement myStmt = dbConn.conn.prepareStatement("SELECT * FROM [Account] WHERE [Email] = ? AND [Password] = ?")) {
            myStmt.setString(1, username);
            myStmt.setString(2, password);
            try (ResultSet myRs = myStmt.executeQuery()) {
                while (myRs.next()) {
                    account = new Account(
                            myRs.getInt("ID"),
                            AccountType.valueOf(myRs.getString("AccountType")),
                            myRs.getString("Name"),
                            myRs.getString("Email"));
                }
            }
            Boolean justSoItWontSeeAsDuplicate; //This is here just so it wont see as duplicate which is annoying
        } catch (SQLException ex) {
            LOGGER.info(ex.getMessage());
        } finally {
            dbConn.closeConnection();
        }
        return account;
    }

    public Boolean register(String name, String email, String password) {
        Integer id = getNextAccountID();
        dbConn.setConnection();
        try (PreparedStatement myStmt = dbConn.conn.prepareStatement("INSERT INTO Account ([ID], [AccountType], [Name], [Email], [Password]) VALUES (?,?,?,?,?)")) {
            myStmt.setInt(1, id);
            myStmt.setString(2, AccountType.CUSTOMER.toString());
            myStmt.setString(3, name);
            myStmt.setString(4, email);
            myStmt.setString(5, password);
            myStmt.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.info(ex.getMessage());
        } finally {
            dbConn.closeConnection();
        }
        return logIn(email, password) != null;
    }

    public void addShopProduct(ShopProduct product) {
        dbConn.setConnection();
        try (PreparedStatement myStmt = dbConn.conn.prepareStatement("INSERT INTO ShopProduct (ProductNumber, ShopName, Price, Description) VALUES (?,?,?,?)")) {
            myStmt.setInt(1, product.getId());
            myStmt.setString(2, shopName);
            myStmt.setDouble(3, product.getPrice());
            myStmt.setString(4, product.getDescription());
            myStmt.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.info(ex.getMessage());
        } finally {
            dbConn.closeConnection();
        }
    }

    public void updateShopProduct(ShopProduct product) {
        dbConn.setConnection();
        try (PreparedStatement myStmt = dbConn.conn.prepareStatement("UPDATE ShopProduct SET [Price] = ?, [Description] = ? WHERE [ProductNumber] = ? AND [ShopName] = ?")) {
            myStmt.setDouble(1, product.getPrice());
            myStmt.setString(2, product.getDescription());
            myStmt.setInt(3, product.getId());
            myStmt.setString(4, shopName);
            myStmt.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.info(ex.getMessage());
        } finally {
            dbConn.closeConnection();
        }
    }

    public void updateSupplierProducts(List<Product> supplierProducts) {
        List<Integer> productNumbers = getProductNumbers();
        for (Product p : supplierProducts) {
            if (!productNumbers.contains(p.getId())) {
                addShopProduct(new ShopProduct(p));
            }
        }
    }

    public void removeSupplierProduct(Product product) {
        dbConn.setConnection();
        try (PreparedStatement myStmt = dbConn.conn.prepareStatement("DELETE FROM ShopProduct WHERE [ProductNumber] = ? AND [ShopName] = ?")) {
            myStmt.setInt(1, product.getId());
            myStmt.setString(2, shopName);
            myStmt.executeQuery();
        } catch (SQLException ex) {
            LOGGER.info(ex.getMessage());
        } finally {
            dbConn.closeConnection();
        }
    }

    public List<ShopProduct> getShopProducts() {
        List<ShopProduct> products = new ArrayList<>();
        dbConn.setConnection();
        try (PreparedStatement myStmt = dbConn.conn.prepareStatement("SELECT * FROM [Product],[ShopProduct] WHERE [ShopProduct].[ShopName] = ? AND [Product].[ProductNumber] = [ShopProduct].[ProductNumber] ORDER BY [Product].[ProductName]")) {
            myStmt.setString(1, shopName);
            try (ResultSet myRs = myStmt.executeQuery()) {
                while (myRs.next()) {
                    ShopProduct product = new ShopProduct(new Product(myRs.getInt("ProductNumber"),
                            myRs.getString("ProductName"),
                            myRs.getDouble("PurchaseCost"),
                            myRs.getInt("Amount"),
                            myRs.getInt("Edition")), myRs.getDouble("Price"), myRs.getString("Description"));
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

    public ShopProduct getShopProduct(Product product) {
        ShopProduct shopProduct = null;
        dbConn.setConnection();
        try (PreparedStatement myStmt = dbConn.conn.prepareStatement("SELECT * FROM [Product],[ShopProduct] WHERE [Product].[ProductNumber] = ? AND [Product].[ProductNumber] = [ShopProduct].[ProductNumber] AND [ShopName] = ? ORDER BY [Product].[ProductName]")) {
            myStmt.setInt(1, product.getId());
            myStmt.setString(2, shopName);
            try (ResultSet myRs = myStmt.executeQuery()) {
                while (myRs.next()) {
                    shopProduct = new ShopProduct(new Product(myRs.getInt("ProductNumber"),
                            myRs.getString("ProductName"),
                            myRs.getDouble("PurchaseCost"),
                            myRs.getInt("Amount"),
                            myRs.getInt("Edition")), myRs.getDouble("Price"), myRs.getString("Description"));
                }
            }
        } catch (SQLException ex) {
            LOGGER.info(ex.getMessage());
        } finally {
            dbConn.closeConnection();
        }
        return shopProduct;
    }

    public List<String> getShopSuppliers() {
        List<String> shopSuppliers = new ArrayList<>();
        dbConn.setConnection();
        try (PreparedStatement myStmt = dbConn.conn.prepareStatement("SELECT * FROM [ShopSuppliers] WHERE [ShopName] = ?")) {
            myStmt.setString(1, shopName);
            try (ResultSet myRs = myStmt.executeQuery()) {
                while (myRs.next()) {
                    shopSuppliers.add(myRs.getString("SupplierName"));
                }
            }
        } catch (SQLException ex) {
            LOGGER.info(ex.getMessage());
        } finally {
            dbConn.closeConnection();
        }
        return shopSuppliers;
    }

    private List<Integer> getProductNumbers() {
        List<Integer> productNumbers = new ArrayList<>();
        dbConn.setConnection();
        try (PreparedStatement myStmt = dbConn.conn.prepareStatement("SELECT * FROM [ShopProduct] WHERE [ShopName] = ?")) {
            myStmt.setString(1, shopName);
            try (ResultSet myRs = myStmt.executeQuery()) {
                while (myRs.next()) {
                    productNumbers.add(myRs.getInt("ProductNumber"));
                }
            }
        } catch (SQLException ex) {
            LOGGER.info(ex.getMessage());
        } finally {
            dbConn.closeConnection();
        }
        return productNumbers;
    }

    private Integer getNextAccountID() {
        Integer number = 0;
        dbConn.setConnection();
        try (PreparedStatement myStmt = dbConn.conn.prepareStatement("SELECT TOP 1 * FROM [Account] ORDER BY [ID] DESC")) {
            try (ResultSet myRs = myStmt.executeQuery()) {
                while (myRs.next()) {
                    number = myRs.getInt("ID");
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
