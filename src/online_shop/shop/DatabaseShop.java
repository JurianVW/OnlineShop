package online_shop.shop;

import online_shop.shared.Account;
import online_shop.shared.AccountType;
import online_shop.supplier.Product;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseShop {
    List<ShopProduct> shopProducts;

    public DatabaseShop() {
        shopProducts = new ArrayList<>();
        shopProducts.add(new ShopProduct(new Product("The Lion King E1", 54, 35.00, 463, 500)));
        shopProducts.add(new ShopProduct(new Product("The Lion King E2", 55, 59.99, 235, 500)));
        shopProducts.add(new ShopProduct(new Product("The Jungle Book", 56, 50.00, 800, 800)));
    }

    public Account logIn(String username, String password) {
        if (username.equals("Jurian") && password.equals("hoi")) {
            return new Account(2, AccountType.SHOPEMPLOYEE, "Jurian van Woerkom", "jurianvanwoerkom@hotmail.com");
        }
        return null;
    }

    public void addShopProduct(ShopProduct product) {
        shopProducts.add(product);
    }

    public void updateShopProduct(ShopProduct product) {
        for (ShopProduct sp : shopProducts) {
            if (sp.getId() == product.getId()) {
                sp = product;
            }
        }
    }

    public void removeShopProduct(ShopProduct product) {
        shopProducts.remove(product);
    }

    public void updateSupplierProducts(List<Product> supplierProducts) {
        Boolean alreadyExistst = false;
        for (Product p : supplierProducts) {
            alreadyExistst = false;
            for (ShopProduct sp : shopProducts) {
                if (sp.getId() == p.getId()) {
                    alreadyExistst = true;
                }
            }
            if (!alreadyExistst) shopProducts.add(new ShopProduct(p));
        }
    }

    public List<ShopProduct> getShopProducts() {
        return shopProducts;
    }

    public void updateProduct(Product product) {
        for (ShopProduct sp : shopProducts) {
            if (sp.getId() == product.getId()) {
                sp.setName(product.getName());
                sp.setPurchasePrice(product.getPurchasePrice());
                sp.setAmount(product.getAmount());
                sp.setEdition(product.getEdition());
            }
        }
    }
}
