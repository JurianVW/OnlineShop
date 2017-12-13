package online_shop.supplier;

import fontyspublisher.RemotePublisher;
import online_shop.shared.Account;
import online_shop.shared.IShopProduct;
import online_shop.shared.ISupplier;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class Supplier extends UnicastRemoteObject implements ISupplier{

    private RemotePublisher remotePublisher;

    private List<Product> products;

    public Supplier() throws RemoteException{

    }

    public void addProduct(Product product){
        throw new UnsupportedOperationException();
    }

    public void removeProduct(Product product){
        throw new UnsupportedOperationException();
    }

    public Account logIn(String username, String password){
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Product> getProducts() {
        throw new UnsupportedOperationException();
    }
}
