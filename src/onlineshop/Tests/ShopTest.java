package onlineshop.Tests;

import onlineshop.shared.Account;
import onlineshop.shared.IShopFX;
import onlineshop.shop.Shop;
import onlineshop.shop.ShopProduct;
import onlineshop.supplier.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

class ShopTest {
    String shopName = "TestShop";
    String databaseUsername = "test@test.nl";
    String databasepw = "Test";

    @Test
    void logIn() {
        try {
            StubShopFX shopFX = new StubShopFX();
            Shop shop = new Shop(shopFX, shopName);

            if (shop.logIn(databaseUsername, databasepw, "sfdsdfsdfs") == null) {
                Assertions.fail("Login failed when has to be success");
            }

            if (shop.logIn(databaseUsername, "wrongPassword","sfdsdfs") != null) {
                Assertions.fail("Login success when has to be failed");
            }

            if (shop.logIn("wrongUsername", databasepw, "ssdfgngfsdfs") != null) {
                Assertions.fail("Login success when has to be failed");
            }
        } catch (RemoteException e) {
            Assertions.fail("Unable to create supplier");
        }
    }

    @Test
    void logInShop() {
        try {
            StubShopFX shopFX = new StubShopFX();
            Shop shop = new Shop(shopFX, shopName);

            if (!shop.logInShop(databaseUsername, databasepw)) {
                Assertions.fail("Login failed when has to be success");
            }

            if (shop.logInShop(databaseUsername, "wrongPassword")) {
                Assertions.fail("Login success when has to be failed");
            }

            if (shop.logInShop("wrongUsername", databasepw)) {
                Assertions.fail("Login success when has to be failed");
            }
        } catch (RemoteException e) {
            Assertions.fail("Unable to create supplier");
        }
    }

    @Test
    void register() {
        try {
            StubShopFX shopFX = new StubShopFX();
            Shop shop = new Shop(shopFX, shopName);

            shop.register("Jurian", "Jurianvanwoerkom@hotmail.com", "password");
        } catch (RemoteException e) {
            Assertions.fail("Unable to create supplier");
        }
    }

    private class StubShopFX implements IShopFX {
        public void update() {

        }
    }
}