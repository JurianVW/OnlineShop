package onlineshop.Tests;

import onlineshop.client.Client;
import onlineshop.client.ClientFX;
import onlineshop.shared.IShopFX;
import onlineshop.shop.Shop;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;

class ClientTest {
    String shopName = "TestShop";


    @Test
    void logIn() {
        try {
            StubShopFX shopFX = new StubShopFX();
            Shop shop = new Shop(shopFX, shopName);
            ClientFX clientFX = new ClientFX();
            Client client = new Client(clientFX, shopName);
            String databaseUsername = "test@test.nl";
            String databasepw = "Test";

            if (client.logIn(databaseUsername, databasepw) == null) {
                Assertions.fail("Login failed when has to be success");
            }

            if (client.logIn(databaseUsername, "wrongPassword") != null) {
                Assertions.fail("Login success when has to be failed");
            }

            if (client.logIn("wrongUsername", databasepw ) != null) {
                Assertions.fail("Login success when has to be failed");
            }
        }
        catch (RemoteException e) {
            Assertions.fail("Unable to create supplier");
        }
    }

    @Test
    void getShopProducts() {
        try {
            StubShopFX shopFX = new StubShopFX();
            Shop shop = new Shop(shopFX, shopName);
            ClientFX clientFX = new ClientFX();
            Client client = new Client(clientFX, shopName);

            if(client.getShopProducts() == null){
                Assertions.fail("null returned");
            }
        }catch (RemoteException e){
            Assertions.fail("Unable to create supplier");
        }
    }

    private class StubShopFX implements IShopFX {
        public void update() {

        }
    }
}