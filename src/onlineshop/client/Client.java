package onlineshop.client;

import onlineshop.shared.Account;
import onlineshop.shared.MD5Digest;
import onlineshop.shop.ShopProduct;
import onlineshop.supplier.Supplier;

import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Logger;

public class Client {
    private static final Logger LOGGER = Logger.getLogger(Supplier.class.getName());
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
            LOGGER.severe(e.getMessage());
        }
        return null;
    }

    public void logOut() {
        try {
            clientCommunicator.logOut(session);
        } catch (RemoteException e) {
            LOGGER.severe(e.getMessage());
        }
    }

    public Boolean register(String name, String email, String password) {
        try {
            return clientCommunicator.register(name, email, password);
        } catch (RemoteException e) {
            LOGGER.severe(e.getMessage());
        }
        return false;
    }

    public void orderProducts(List<ShopProduct> shopProducts) {
        try {
            clientCommunicator.orderProducts(shopProducts, account.getId(), session);
        } catch (RemoteException e) {
            LOGGER.severe(e.getMessage());
        }
    }

    public List<ShopProduct> getShopProducts() {
        try {
            return clientCommunicator.getShopProducts();
        } catch (RemoteException e) {
            LOGGER.severe(e.getMessage());
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

    public Boolean shopAvailable() {
        return clientCommunicator.shopAvailable();
    }
}
