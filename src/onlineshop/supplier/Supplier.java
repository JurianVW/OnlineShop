package onlineshop.supplier;

import fontyspublisher.RemotePublisher;
import onlineshop.shared.Account;
import onlineshop.shared.ISupplier;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.logging.Logger;

public class Supplier extends UnicastRemoteObject implements ISupplier {
    private static final Logger LOGGER = Logger.getLogger(Supplier.class.getName());

    private SupplierFX suppliefFX;

    private RemotePublisher publisher;
    private Registry registry;
    private String bindingName;
    private String bindingNamePublisher;

    private List<Product> products;

    private Account account;

    private DatabaseSupplier database;

    public Supplier(SupplierFX suppliefFX, int port, String supplierName) throws RemoteException {
        this.suppliefFX = suppliefFX;
        database = new DatabaseSupplier(supplierName);
        publisher = new RemotePublisher();
        bindingName = supplierName;
        bindingNamePublisher = supplierName + "Publisher";

        //Create publisher
        try {
            publisher = new RemotePublisher();
            publisher.registerProperty("NewProduct");
            publisher.registerProperty("RemovedProduct");
            publisher.registerProperty("ChangedProduct");
        } catch (RemoteException e) {
            LOGGER.severe("Server: Cannot create publisher");
            LOGGER.severe("Server: RemoteException " + e.getMessage());
        }

        //Create registry at port number
        try {
            registry = LocateRegistry.createRegistry(port);
            LOGGER.info("Server: Registry created on port number " + port);
        } catch (RemoteException e) {
            registry = null;
            LOGGER.severe("Server: Cannot create registry");
            LOGGER.severe("Server: RemoteException: " + e.getMessage());
        }

        //If registry exists, try find it
        if (registry == null) {
            try {
                registry = LocateRegistry.getRegistry(port);
                LOGGER.info("Server: Registry found on port number " + port);
            } catch (RemoteException e) {
                registry = null;
                LOGGER.severe("Server: Cannot create registry");
                LOGGER.severe("Server: RemoteException: " + e.getMessage());
            }
        }


        //Bind using registry
        if (registry != null) {
            try {
                registry.rebind(bindingName, this);
                registry.rebind(bindingNamePublisher, publisher);
                LOGGER.info("Server: Successfully bound to registry");
            } catch (RemoteException e) {
                LOGGER.severe("Server: Cannot bind Publisher");
                LOGGER.severe("Server: RemoteException: " + e.getMessage());
            } catch (NullPointerException e) {
                LOGGER.severe("Server: Port already in use. \nServer: Please check if the server isn't already running");
                LOGGER.severe("Server: NullPointerException: " + e.getMessage());
            }
        }
    }

    public void addProduct(Product product) {
        database.addProduct(product);
        informNewProduct(product);
        this.products = database.getProducts();
    }

    public void removeProduct(Product product) {
        database.removeProduct(product);
        informRemovedProduct(product);
        this.products = database.getProducts();
    }

    public void productChanged(Product product) {
        database.productChanged(product);
        informChangedProduct(product);
        this.products = database.getProducts();
    }

    public boolean logIn(String username, String password) {
        Account a = database.logIn(username, password);
        if (a != null) {
            this.account = a;
            return true;
        }
        return false;
    }

    public List<Product> getProducts() {
        this.products = database.getProducts();
        return products;
        //TODO: better code
    }

    public Boolean orderProducts(List<Product> products) {
        suppliefFX.orderProducts(products);
        return true;
    }

    private void informNewProduct(Product product) {
        try {
            publisher.inform("NewProduct", null, product);
        } catch (RemoteException e) {
            LOGGER.severe(e.getMessage());
        }
    }

    private void informRemovedProduct(Product product) {
        try {
            publisher.inform("RemovedProduct", null, product);
        } catch (RemoteException e) {
            LOGGER.severe(e.getMessage());
        }
    }

    private void informChangedProduct(Product product) {
        try {
            publisher.inform("ChangedProduct", null, product);
        } catch (RemoteException e) {
            LOGGER.severe(e.getMessage());
        }
    }
}
