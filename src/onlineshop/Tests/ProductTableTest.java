package onlineshop.Tests;

import onlineshop.supplier.Product;
import onlineshop.supplier.ProductTable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ProductTableTest {
    @Test
    void name() {
        String name = "OldName";
        ProductTable p = new ProductTable(new Product(1, name, 10.0, 100, 100));
        Assertions.assertEquals(name, p.getName());
        p.setName("New Name");
        Assertions.assertNotSame(name, p.getName());
    }

    @Test
    void id() {
        int id = 40;
        ProductTable p = new ProductTable(new Product(id, "Product", 15.0, 56, 75));
        Assertions.assertEquals(id, p.getId());
    }

    @Test
    void purchasePrice() {
        Double price = 30.50;
        ProductTable p = new ProductTable(new Product(1, "ProductBlah", price, 53, 277));
        Assertions.assertEquals(price, p.getPurchasePrice());
        p.setPurchasePrice(67.3);
        Assertions.assertNotSame(price, p.getPurchasePrice());
    }

    @Test
    void amount() {
        Integer amount = 40;
        ProductTable p = new ProductTable(new Product(1, "Productnew", 17.0, amount, 875));
        Assertions.assertEquals(amount, p.getAmount());
        p.setAmount(67);
        Assertions.assertNotSame(amount, p.getAmount());
    }

    @Test
    void edition() {
        Integer edition = 405;
        ProductTable p = new ProductTable(new Product(1, "Producw", 17.0, 45, edition));
        Assertions.assertEquals(edition, p.getEdition());
        p.setEdition(644);
        Assertions.assertNotSame(edition, p.getEdition());
    }

    @Test
    void getProduct() {
        ProductTable p = new ProductTable(new Product(1, "Product", 17.0, 45, 600));
        Assertions.assertNotNull(p.getProduct());
    }
}