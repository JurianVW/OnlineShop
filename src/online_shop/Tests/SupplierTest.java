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

    String databaseUsername = "test@test.nl";
    String databasePassword = "Test";

    Product testProduct = new Product(20, "TestProduct", 45.4, 400, 600);

    @Test
    void removeProduct() {
        try {
            SupplierFX supplierFX = new SupplierFX();
            Supplier supplier = new Supplier(supplierFX, portNr, supplierName);

            Integer beginSize = supplier.getProducts().size();

            supplier.removeProduct(testProduct);

            Assertions.assertEquals((beginSize - 1), supplier.getProducts().size());
        } catch (RemoteException e) {
            Assertions.fail("Unable to create supplier");
        }
    }

    @Test
    void addProduct() {
        try {
            SupplierFX supplierFX = new SupplierFX();
            Supplier supplier = new Supplier(supplierFX, portNr, supplierName);

            Integer beginSize = supplier.getProducts().size();

            supplier.addProduct(testProduct);
            Assertions.assertEquals((beginSize + 1), supplier.getProducts().size());
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

            testProduct.setName("Steelbook blah");
            supplier.productChanged(testProduct);

            Product result = null;
            for (Product p : supplier.getProducts()) {
                if (p.getId() == testProduct.getId()) {
                    result = p;
                }
            }

            Assertions.assertEquals(true, result.getName().equals(testProduct.getName()));
        } catch (RemoteException e) {
            Assertions.fail("Unable to create supplier");
        }
    }

    @Test
    void logIn() {
        try {
            SupplierFX supplierFX = new SupplierFX();
            Supplier supplier = new Supplier(supplierFX, portNr, supplierName);

            if (!supplier.logIn(databaseUsername, databasePassword)) {
                Assertions.fail("Login failed when has to be success");
            }

            if (supplier.logIn(databaseUsername, "wrongPassword")) {
                Assertions.fail("Login success when has to be failed");
            }

            if (supplier.logIn("wrongUsername", databasePassword)) {
                Assertions.fail("Login success when has to be failed");
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