package online_shop.shop;

import online_shop.shared.Account;
import online_shop.shared.AccountType;
import online_shop.supplier.Product;

import java.util.ArrayList;
import java.util.List;

public class DatabaseShop {
    List<ShopProduct> shopProducts;

    public DatabaseShop() {
        shopProducts = new ArrayList<>();
        shopProducts.add(new ShopProduct(new Product("The Lion King E1", 54, 35.00, 463, 500), 34.65, "With lenticular"));
        shopProducts.add(new ShopProduct(new Product("The Lion King E2", 55, 59.99, 235, 500),50.0, "With art cards"));
        shopProducts.add(new ShopProduct(new Product("The Jungle Book", 56, 50.00, 800, 800),12.34, ""));
    }

    public Account logIn(String username, String password) {
        if (username.equals("Jurian") && password.equals("hoi")) {
            return new Account(2, AccountType.SHOPEMPLOYEE, "Jurian van Woerkom", "jurianvanwoerkom@hotmail.com");
        } else if (username.equals("Klant") && password.equals("klant")) {
            return new Account(5, AccountType.CUSTOMER, "Jurian van Woerkom", "jurianvanwoerkom@hotmail.com");
        }
        return null;
    }

    public void addShopProduct(ShopProduct product) {
        shopProducts.add(product);
    }

    public void updateShopProduct(ShopProduct product) {
        Integer index = Integer.MAX_VALUE;
        for (ShopProduct sp : shopProducts) {
            if (sp.getId() == product.getId()) {
                index = shopProducts.indexOf(sp);
                break;
            }
        }
        if (shopProducts.size() > index) {
            shopProducts.set(index, product);
        }
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

    public void removeSupplierProduct(Product product) {
        ShopProduct shopProduct = null;
        for (ShopProduct sp : shopProducts) {
            if (sp.getId() == product.getId()) {
                shopProduct = sp;
            }
        }
        if (shopProduct != null) shopProducts.remove(shopProduct);
    }

    public List<ShopProduct> getShopProducts() {
        return shopProducts;
    }

    public ShopProduct getShopProduct(Product product) {
        for (ShopProduct sp : shopProducts) {
            if (sp.getId() == product.getId()) {
                return sp;
            }
        }
        return null;
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
