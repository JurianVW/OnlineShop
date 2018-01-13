package online_shop.Tests;

import online_shop.supplier.Product;
import online_shop.supplier.Supplier;
import online_shop.supplier.SupplierFX;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SupplierTest {

    int portNr = 1080;
    String supplierName = "TestSupplier";

    @Test
    void addProduct() {
        try {
            SupplierFX supplierFX = new SupplierFX();
            Supplier supplier = new Supplier(supplierFX, portNr, supplierName);

            Integer beginSize = supplier.getProducts().size();

            supplier.addProduct(new Product("Steelbook", 45.4, 400, 600));

            Assertions.assertEquals((beginSize + 1), supplier.getProducts().size());
        } catch (RemoteException e) {
            Assertions.fail("Unable to create supplier");
        }
    }

    @Test
    void removeProduct() {
        try {
            SupplierFX supplierFX = new SupplierFX();
            Supplier supplier = new Supplier(supplierFX, portNr, supplierName);

            Integer beginSize = supplier.getProducts().size();

            supplier.removeProduct(supplier.getProducts().get(0));

            Assertions.assertEquals((beginSize - 1), supplier.getProducts().size());
        } catch (RemoteException e) {
            Assertions.fail("Unable to create supplier");
        }
    }

    @Test
    void productChanged() {
        try {
            SupplierFX supplierFX = new SupplierFX();
            Supplier supplier = new Supplier(supplierFX, portNr, supplierName);

            Integer beginSize = supplier.getProducts().size();

            Product product = supplier.getProducts().get(0);
            product.setName("Steelbook blah");
            supplier.productChanged(product);

            Assertions.assertEquals(true, supplier.getProducts().get(0).getName().equals(product.getName()));
        } catch (RemoteException e) {
            Assertions.fail("Unable to create supplier");
        }
    }

    @Test
    void logIn() {
        try {
            SupplierFX supplierFX = new SupplierFX();
            Supplier supplier = new Supplier(supplierFX, portNr, supplierName);

            if (!supplier.logIn("Jurian", "hoi")) {
                Assertions.fail("Login failed when has to be succes");
            }

            if (supplier.logIn("Jur", "blahblahblah")) {
                Assertions.fail("Login succedded when has to be failed");
            }
        } catch (RemoteException e) {
            Assertions.fail("Unable to create supplier");
        }
    }

    @Test
    void orderProducts() {
        try {
            SupplierFX supplierFX = new SupplierFX();
            Supplier supplier = new Supplier(supplierFX, portNr, supplierName);

            List<Product> productsToOrder = new ArrayList<>();
            List<Product> orderableProducts = supplier.getProducts();
            productsToOrder.add(orderableProducts.get(0));
            productsToOrder.add(orderableProducts.get(0));
            productsToOrder.add(orderableProducts.get(1));
            productsToOrder.add(orderableProducts.get(2));

            //    supplier.orderProducts(productsToOrder);
            //unable to test this

        } catch (RemoteException e) {
            Assertions.fail("Unable to create supplier");
        }
    }
}