package online_shop.client;

import online_shop.shared.Account;
import online_shop.shared.IShopProduct;
import online_shop.shop.ShopProduct;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Client {
    private ClientFX clientFX;

    private ClientCommunicator clientCommunicator;

    private List<ShopProduct> cart;

    private String session;
    private Account account;

    public Client(ClientFX clientFX) throws RemoteException {
        this.clientFX = clientFX;
        clientCommunicator = new ClientCommunicator(this);
    }

    public Account logIn(String username, String password) {
        try {
            this.session = "hashhereplz";
            this.account = clientCommunicator.logIn(username, password, session);
            return account;
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String register(String name, String email, String password, String streetname, String houseNumber, String postalCode, String place) {
        try {
            return clientCommunicator.register(name, email, password, streetname, houseNumber, postalCode, place);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void orderProducts(List<ShopProduct> shopProducts) {
        try {
            clientCommunicator.orderProducts(shopProducts, account.getId(), session);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public List<ShopProduct> getShopProducts() {
        try {
            return clientCommunicator.getShopProducts();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void changedShopProduct(ShopProduct shopProduct) {
        clientFX.changedShopProduct(shopProduct);
    }

    public void removedShopProduct(ShopProduct shopProduct) {
        clientFX.removedShopProduct(shopProduct);
    }

    public void newShopProduct(ShopProduct shopProduct) {
        clientFX.newShopProduct(shopProduct);
    }
}
