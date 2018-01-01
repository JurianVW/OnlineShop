package online_shop.shop;

import fontyspublisher.IRemotePropertyListener;
import fontyspublisher.IRemotePublisherForListener;
import online_shop.shared.IProduct;
import online_shop.shared.ISupplier;
import online_shop.supplier.Product;

import java.beans.PropertyChangeEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ShopCommunicator extends UnicastRemoteObject implements IRemotePropertyListener {
    private Shop shop;

    private Registry registry = null;

    private IRemotePublisherForListener publisher;
    private ISupplier supplier;

    private static final int portSupplier = 1098;
    private static final int portShop = 1099;
    private String remoteSupplierName = "Novamedia";

    private InetAddress localhost;


    protected ShopCommunicator(Shop shop) throws RemoteException {
        this.shop = shop;
        try {
            localhost = InetAddress.getLocalHost();
        } catch (UnknownHostException ex) {
            System.out.println("Server: Cannot get IP address of local host");
            System.out.println("Server: UnknownHostException: " + ex.getMessage());
        }

        // Locate registry at IP address and port number
        try {
            registry = LocateRegistry.getRegistry(localhost.getHostAddress(), portSupplier);
            publisher = (IRemotePublisherForListener) registry.lookup(remoteSupplierName + "Publisher");
            publisher.subscribeRemoteListener(this, "NewProduct");
            publisher.subscribeRemoteListener(this, "ChangedProduct");
            supplier = (ISupplier) registry.lookup(remoteSupplierName);
        } catch (RemoteException ex) {
            System.out.println("Client: Cannot locate registry");
            System.out.println("Client: RemoteException: " + ex.getMessage());
            registry = null;
        } catch (NotBoundException ex) {
            System.out.println("Client: Cannot locate mockEffectenbeursPublisher");
            System.out.println("Client: NotBoundException: " + ex.getMessage());
            registry = null;
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) throws RemoteException {
        switch (propertyChangeEvent.getPropertyName()) {
            case "ChangedProduct":
                shop.productChanged((Product)propertyChangeEvent.getNewValue());
                break;
            case "NewProduct":
                shop.addShopProduct((Product)propertyChangeEvent.getNewValue());
                break;
        }
    }

    public List<Product> getSupplierProducts() throws RemoteException {
        return supplier.getProducts();
    }
}
