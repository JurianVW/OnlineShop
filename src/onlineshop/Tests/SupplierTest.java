package onlineshop.Tests;

import onlineshop.supplier.Product;
import onlineshop.supplier.Supplier;
import onlineshop.supplier.SupplierFX;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;

class SupplierTest {

    private int portNr = 1080;
    private String supplierName = "TestSupplier";



    private Product testProduct = new Product(20, "TestProduct", 45.4, 400, 600);

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
            if (result != null) {
                Assertions.assertEquals(true, result.getName().equals(testProduct.getName()));
            } else {
                Assertions.fail("Result was null");
            }

        } catch (RemoteException e) {
            Assertions.fail("Unable to create supplier");
        }
    }

    @Test
    void logIn() {
        try {
            SupplierFX supplierFX = new SupplierFX();
            Supplier supplier = new Supplier(supplierFX, portNr, supplierName);
            String databaseUsername = "test@test.nl";
            String databasepw = "Test";

            if (!supplier.logIn(databaseUsername, databasepw)) {
                Assertions.fail("Login failed when has to be success");
            }

            if (supplier.logIn(databaseUsername, "wrongPassword")) {
                Assertions.fail("Login success when has to be failed");
            }

            if (supplier.logIn("wrongUsername", databasepw)) {
                Assertions.fail("Login success when has to be failed");
            }
        } catch (RemoteException e) {
            Assertions.fail("Unable to create supplier");
        }
    }
}