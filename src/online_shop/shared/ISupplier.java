package online_shop.shared;

import online_shop.supplier.Product;

import java.rmi.Remote;
import java.util.List;

public interface ISupplier extends Remote {
    /**
     *
     * @return the list of all products of that supplier
     */
    List<Product> getProducts();
}
