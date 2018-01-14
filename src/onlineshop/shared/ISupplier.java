package onlineshop.shared;

import onlineshop.supplier.Product;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ISupplier extends Remote {
    /**
     * @return the list of all products of that supplier
     */
    List<Product> getProducts() throws RemoteException;

    Boolean orderProducts(List<Product> products) throws RemoteException;
}
