package online_shop.client;

import com.sun.org.apache.xpath.internal.operations.Bool;
import online_shop.shared.Account;
import online_shop.shared.IShopProduct;
import online_shop.shared.MD5Digest;
import online_shop.shop.ShopProduct;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Client {
    private ClientFX clientFX;

    private ClientCommunicator clientCommunicator;

    private String session;
    private Account account;
    private MD5Digest md5Digest = new MD5Digest();

    public Client(ClientFX clientFX, String shopName) throws RemoteException {
        this.clientFX = clientFX;
        clientCommunicator = new ClientCommunicator(this, shopName);
    }

    public Account logIn(String username, String password) {
        try {
            this.session = md5Digest.digest(username);
            this.account = clientCommunicator.logIn(username, password, session);
            return account;
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void logOut() {
        try {
            clientCommunicator.logOut(session);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public Boolean register(String name, String email, String password) {
        try {
            return clientCommunicator.register(name, email, password);
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

    public void removedShopProduct(Integer productId) {
        clientFX.removedShopProduct(productId);
    }

    public void newShopProduct(ShopProduct shopProduct) {
        clientFX.newShopProduct(shopProduct);
    }
}
