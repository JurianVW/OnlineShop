package online_shop.shop;

import fontyspublisher.IRemotePropertyListener;
import fontyspublisher.IRemotePublisherForListener;
import fontyspublisher.RemotePublisher;
import online_shop.shared.IProduct;
import online_shop.shared.IShopProduct;
import online_shop.shared.ISupplier;
import online_shop.supplier.Product;
import online_shop.supplier.Supplier;

import java.beans.PropertyChangeEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ShopCommunicator extends UnicastRemoteObject implements IRemotePropertyListener {
    private static final Logger LOGGER = Logger.getLogger(Supplier.class.getName());
    private Shop shop;

    private Registry registrySupplier = null;
    private Registry registryShop = null;

    private RemotePublisher publisherShop;
    private IRemotePublisherForListener publisherSupplier;
    private List<ISupplier> suppliers = new ArrayList<>();

    private static final int portSupplier = 1098;
    private static final int portShop = 1100;

    private String bindingNameShop;
    private String bindingNamePublisherShop;

    private InetAddress localhost;


    protected ShopCommunicator(Shop shop, String shopName) throws RemoteException {
        this.shop = shop;
        bindingNameShop = shopName;
        bindingNamePublisherShop = shopName + "Publisher";

        try {
            localhost = InetAddress.getLocalHost();
        } catch (UnknownHostException ex) {
            System.out.println("Server: Cannot get IP address of local host");
            System.out.println("Server: UnknownHostException: " + ex.getMessage());
        }

        //Create publisher
        try {
            publisherShop = new RemotePublisher();
            publisherShop.registerProperty("NewShopProduct");
            publisherShop.registerProperty("RemovedShopProduct");
            publisherShop.registerProperty("ChangedShopProduct");

        } catch (RemoteException e) {
            LOGGER.severe("Server: Cannot create publisher");
            LOGGER.severe("Server: RemoteException " + e.getMessage());
        }

        //Create shop registry at port number
        try {
            registryShop = LocateRegistry.createRegistry(portShop);
            LOGGER.info("Server: Registry created on port number " + portShop);
        } catch (RemoteException e) {
            LOGGER.severe("Server: Cannot create registry");
            LOGGER.severe("Server: RemoteException: " + e.getMessage());
        }

        //If registry exists, try find it
        if (registryShop == null) {
            try {
                registryShop = LocateRegistry.getRegistry(portShop);
                LOGGER.info("Server: Registry found on port number " + portShop);
            } catch (RemoteException e) {
                registryShop = null;
                LOGGER.severe("Server: Cannot create registry");
                LOGGER.severe("Server: RemoteException: " + e.getMessage());
            }
        }

        //Bind using shop registry
        try {
            registryShop.rebind(bindingNameShop, shop);
            registryShop.rebind(bindingNamePublisherShop, publisherShop);
            LOGGER.info("Server: Successfully bound to registry");
        } catch (RemoteException e) {
            LOGGER.severe("Server: Cannot bind Publisher");
            LOGGER.severe("Server: RemoteException: " + e.getMessage());
        } catch (NullPointerException e) {
            LOGGER.severe("Server: Port already in use. \nServer: Please check if the server isn't already running");
            LOGGER.severe("Server: NullPointerException: " + e.getMessage());
        }
    }

    public void subscribeToSuppliers(List<String> suppliers){
        for (String s:suppliers) {
            subscribeToSupplier(s);
        }
    }

    public void subscribeToSupplier(String supplierName){
        // Locate supplier registry at IP address and port number
        try {
            registrySupplier = LocateRegistry.getRegistry(localhost.getHostAddress(), portSupplier);
            publisherSupplier = (IRemotePublisherForListener) registrySupplier.lookup(supplierName + "Publisher");
            publisherSupplier.subscribeRemoteListener(this, "NewProduct");
            publisherSupplier.subscribeRemoteListener(this, "RemovedProduct");
            publisherSupplier.subscribeRemoteListener(this, "ChangedProduct");
            suppliers.add((ISupplier) registrySupplier.lookup(supplierName));
        } catch (RemoteException ex) {
            LOGGER.info("Client: Cannot locate registry");
            LOGGER.info("Client: RemoteException: " + ex.getMessage());
            registrySupplier = null;
        } catch (NotBoundException ex) {
            LOGGER.info("Client: Cannot locate " + supplierName + "Publisher");
            LOGGER.info("Client: NotBoundException: " + ex.getMessage());
            registrySupplier = null;
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        switch (propertyChangeEvent.getPropertyName()) {
            case "ChangedProduct":
                shop.productChanged((Product) propertyChangeEvent.getNewValue());
                break;
            case "NewProduct":
                shop.addShopProduct((Product) propertyChangeEvent.getNewValue());
                break;
            case "RemovedProduct":
                shop.productRemoved((Product) propertyChangeEvent.getNewValue());
                break;
        }
    }

    public void informNewShopProduct(ShopProduct shopProduct) {
        try {
            publisherShop.inform("NewShopProduct", null, shopProduct);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void informRemovedShopProduct(Integer productId) {
        try {
            publisherShop.inform("RemovedShopProduct", null, productId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void informChangedShopProduct(ShopProduct shopProduct) {
        try {
            publisherShop.inform("ChangedShopProduct", null, shopProduct);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public List<Product> getSupplierProducts() throws RemoteException {
        List<Product> allProducts = new ArrayList<>();
        for (ISupplier supplier : suppliers) {
            allProducts.addAll(supplier.getProducts());
        }
        return allProducts;
    }

    public void orderProducts(List<ShopProduct> shopProducts) throws RemoteException {
        for (ISupplier supplier : suppliers) {
            List<Product> supplierProducts = supplier.getProducts();
            List<Product> productsToOrder = new ArrayList<>();
            for (ShopProduct sp : shopProducts ) {
                for (Product p: supplierProducts   ) {
                    if(sp.getId() == p.getId()){
                        productsToOrder.add(p);
                    }
                }
            }
            supplier.orderProducts(productsToOrder);
        }
    }
}
