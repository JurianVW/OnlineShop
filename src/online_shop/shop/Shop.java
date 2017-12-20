package online_shop.shop;

import fontyspublisher.RemotePublisher;
import online_shop.shared.Account;
import online_shop.shared.IShop;
import online_shop.supplier.Product;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class Shop extends UnicastRemoteObject implements IShop {
    private RemotePublisher remotePublisher;

    private ShopCommunicator shopCommunicator;

    private List<ShopProduct> shopProducts;

    private Account account;

    private DatabaseShop database;

    public Shop() throws RemoteException {
        database = new DatabaseShop();
        shopCommunicator = new ShopCommunicator();
    }

    public List<ShopProduct> getShopProducts() {
        List<ShopProduct> shopProducts = new ArrayList<>();
        shopProducts.addAll(database.getShopProducts());
        try {
            for (Product p : shopCommunicator.getSupplierProducts()) {
                shopProducts.add(new ShopProduct(p));
            }
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
        return shopProducts;
    }

    public void orderProducts(List<ShopProduct> shopProducts, Integer accountId, String session) {
        throw new UnsupportedOperationException();
    }

    public boolean logIn(String username, String password) {
        Account a = database.logIn(username, password);
        if (a != null) {
            this.account = a;
            return true;
        }
        return false;
    }

    public void logOut(String session) {
        throw new UnsupportedOperationException();
    }

    public Account register(String name, String email, String streetname, String houseNumber, String postalCode, String place) {
        throw new UnsupportedOperationException();
    }
}
