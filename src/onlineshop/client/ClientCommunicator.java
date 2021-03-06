package onlineshop.client;

import fontyspublisher.IRemotePropertyListener;
import fontyspublisher.IRemotePublisherForListener;
import onlineshop.shared.Account;
import onlineshop.shared.IShop;
import onlineshop.shop.ShopProduct;
import onlineshop.supplier.Supplier;

import java.beans.PropertyChangeEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.logging.Logger;

public class ClientCommunicator extends UnicastRemoteObject implements IRemotePropertyListener {
    private static final Logger LOGGER = Logger.getLogger(Supplier.class.getName());
    private Client client;

    private Registry registry;
    private IRemotePublisherForListener publisher;
    private IShop shop;

    private static final int portShop = 1100;
    private String remoteShopName = "Bol.com";

    private InetAddress localhost;

    protected ClientCommunicator(Client client, String shopName) throws RemoteException {
        this.client = client;
        this.remoteShopName = shopName;

        try {
            localhost = InetAddress.getLocalHost();
        } catch (UnknownHostException ex) {
            System.out.println("Server: Cannot get IP address of local host");
            System.out.println("Server: UnknownHostException: " + ex.getMessage());
        }

        // Locate shop registry at IP address and port number
        try {
            registry = LocateRegistry.getRegistry(localhost.getHostAddress(), portShop);
            publisher = (IRemotePublisherForListener) registry.lookup(remoteShopName + "Publisher");
            publisher.subscribeRemoteListener(this, "NewShopProduct");
            publisher.subscribeRemoteListener(this, "RemovedShopProduct");
            publisher.subscribeRemoteListener(this, "ChangedShopProduct");
            shop = (IShop) registry.lookup(remoteShopName);
        } catch (RemoteException ex) {
            LOGGER.info("Client: Cannot locate registry");
            LOGGER.info("Client: RemoteException: " + ex.getMessage());
            registry = null;
        } catch (NotBoundException ex) {
            LOGGER.info("Client: Cannot locate " + remoteShopName + "Publisher");
            LOGGER.info("Client: NotBoundException: " + ex.getMessage());
            registry = null;
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        switch (propertyChangeEvent.getPropertyName()) {
            case "ChangedShopProduct":
                client.changedShopProduct((ShopProduct) propertyChangeEvent.getNewValue());
                break;
            case "NewShopProduct":
                client.newShopProduct((ShopProduct) propertyChangeEvent.getNewValue());
                break;
            case "RemovedShopProduct":
                client.removedShopProduct((Integer) propertyChangeEvent.getNewValue());
                break;
        }
    }

    public List<ShopProduct> getShopProducts() throws RemoteException {
        return shop.getShopProducts();
    }

    public Account logIn(String username, String password, String session) throws RemoteException {
        return shop.logIn(username, password, session);
    }

    public void logOut(String session) throws RemoteException {
        shop.logOut(session);
    }

    public Boolean register(String name, String email, String password) throws RemoteException {
        return shop.register(name, email, password);
    }

    public void orderProducts(List<ShopProduct> shopProducts, Integer accountId, String session) throws RemoteException {
        shop.orderProducts(shopProducts, accountId, session);
    }

    public Boolean shopAvailable() {
        return registry != null;
    }
}
