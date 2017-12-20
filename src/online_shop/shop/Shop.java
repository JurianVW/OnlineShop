package online_shop.shop;

import fontyspublisher.RemotePublisher;
import online_shop.shared.Account;
import online_shop.shared.IShop;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class Shop extends UnicastRemoteObject implements IShop {
    private RemotePublisher remotePublisher;

    private ShopCommunicator shopCommunicator;

    private List<ShopProduct> shopProducts;

    private Account account;

    protected Shop(int port) throws RemoteException {
        super(port);
    }

    public List<ShopProduct> getShopProducts() {
        throw new UnsupportedOperationException();
    }

    public void orderProducts(List<ShopProduct> shopProducts, Integer accountId, String session) {
        throw new UnsupportedOperationException();
    }

    public boolean logIn(String username, String password) {
        return true;
      //  throw new UnsupportedOperationException();
    }

    public void logOut(String session) {
        throw new UnsupportedOperationException();
    }

    public Account register(String name, String email, String streetname, String houseNumber, String postalCode, String place) {
        throw new UnsupportedOperationException();
    }
}
