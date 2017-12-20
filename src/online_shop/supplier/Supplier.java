package online_shop.supplier;

import fontyspublisher.RemotePublisher;
import online_shop.shared.Account;
import online_shop.shared.IShopProduct;
import online_shop.shared.ISupplier;

import javax.xml.crypto.Data;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class Supplier extends UnicastRemoteObject implements ISupplier {

    private RemotePublisher remotePublisher;

    private List<Product> products;

    private Account account;

    private DatabaseSupplier database;

    public Supplier() throws RemoteException {
        database = new DatabaseSupplier();
    }

    public void addProduct(Product product) {
      database.addProduct(product);
    }

    public void removeProduct(Product product) {
        database.removeProduct(product);
    }

    public boolean logIn(String username, String password) {
        Account a = database.logIn(username,password);
        if(a != null){
            this.account = a;
            return true;
        }
        return false;
    }

    public List<Product> getProducts() {
        this.products =  database.getProducts();
        return products;
        //TODO: better code
    }
}
