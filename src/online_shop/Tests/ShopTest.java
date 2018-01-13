package online_shop.Tests;

import javafx.application.Application;
import javafx.stage.Stage;
import online_shop.shared.Account;
import online_shop.shared.IShopFX;
import online_shop.shop.Shop;
import online_shop.shop.ShopFX;
import online_shop.shop.ShopProduct;
import online_shop.supplier.Product;
import online_shop.supplier.Supplier;
import online_shop.supplier.SupplierFX;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShopTest {
    String shopName = "TestShop";

    @Test
    void addShopProduct() {
        try {
            StubShopFX shopFX = new StubShopFX();
            Shop shop = new Shop(shopFX, shopName);

            Integer beginSize = shop.getShopProducts().size();

            shop.addShopProduct(new ShopProduct(new Product("Steelbook", 45.4, 400, 600)));

            Assertions.assertEquals((beginSize + 1), shop.getShopProducts().size());
        } catch (RemoteException e) {
            Assertions.fail("Unable to create supplier");
        }
    }

    @Test
    void shopProductChanged() {
        try {
            ShopFX shopFX = new ShopFX();
            Shop shop = new Shop(shopFX, shopName);

            Integer beginSize = shop.getShopProducts().size();

            ShopProduct product = shop.getShopProducts().get(0);
            product.setDescription("Steelbook blah");
            shop.shopProductChanged(product);

            Assertions.assertEquals(true, shop.getShopProducts().get(0).getDescription().equals(product.getDescription()));
        } catch (RemoteException e) {
            Assertions.fail("Unable to create supplier");
        }
    }

    @Test
    void orderProducts() {
        try {
            ShopFX shopFX = new ShopFX();
            Shop shop = new Shop(shopFX, shopName);
            String session = "sessionblahblah";
            Account a = shop.logIn("Klant", "klant", session);

            if (a == null) {
                Assertions.fail("Failed to login");
            }
            List<ShopProduct> productsToOrder = new ArrayList<>();
            productsToOrder.add(new ShopProduct(new Product("Steelbook", 45.4, 400, 600)));
            shop.orderProducts(productsToOrder, a.getId(), session);
        } catch (RemoteException e) {
            Assertions.fail("Unable to create supplier");
        }
    }

    @Test
    void logIn() {
        try {
            ShopFX shopFX = new ShopFX();
            Shop shop = new Shop(shopFX, shopName);

            if (shop.logIn("Klant", "klant", "sessionhere") == null) {
                Assertions.fail("Login failed when has to be succes");
            }

            if (shop.logIn("Jur", "blahblahblah", "sessionblahblah") != null) {
                Assertions.fail("Login succedded when has to be failed");
            }
        } catch (RemoteException e) {
            Assertions.fail("Unable to create supplier");
        }
    }

    @Test
    void logInShop() {
        try {
            ShopFX shopFX = new ShopFX();
            Shop shop = new Shop(shopFX, shopName);

            if (!shop.logInShop("Jurian", "hoi")) {
                Assertions.fail("Login failed when has to be succes");
            }

            if (shop.logInShop("Jur", "blahblahblah")) {
                Assertions.fail("Login succedded when has to be failed");
            }
        } catch (RemoteException e) {
            Assertions.fail("Unable to create supplier");
        }
    }

    @Test
    void register() {
        try {
            ShopFX shopFX = new ShopFX();
            Shop shop = new Shop(shopFX, shopName);

            //  shop.register("Jurian", "Jurianvanwoerkom@hotmail.com", "password");
        } catch (RemoteException e) {
            Assertions.fail("Unable to create supplier");
        }
    }

    private class StubShopFX implements IShopFX {

        public void update() {

        }
    }
}