package onlineshop.Tests;

import onlineshop.supplier.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ProductTest {

    @Test
    void name() {
        String name = "OldName";
        Product p = new Product(name, 10.0, 100, 100);
        Assertions.assertEquals(name, p.getName());
        p.setName("New Name");
        Assertions.assertNotSame(name, p.getName());
    }

    @Test
    void id() {
        int id = 40;
        Product p = new Product(id, "Product", 15.0, 56, 75);
        Assertions.assertEquals(id, p.getId());
        p.setId(67);
        Assertions.assertNotSame(id, p.getId());
    }

    @Test
    void purchasePrice() {
        Double price = 30.50;
        Product p = new Product("ProductBlah", price, 53, 277);
        Assertions.assertEquals(price, p.getPurchasePrice());
        p.setPurchasePrice(67.3);
        Assertions.assertNotSame(price, p.getPurchasePrice());
    }

    @Test
    void amount() {
        Integer amount = 40;
        Product p = new Product("Productnew", 17.0, amount, 875);
        Assertions.assertEquals(amount, p.getAmount());
        p.setAmount(67);
        Assertions.assertNotSame(amount, p.getAmount());
    }

    @Test
    void edition() {
        Integer edition = 405;
        Product p = new Product("Producw", 17.0, 45, edition);
        Assertions.assertEquals(edition, p.getEdition());
        p.setEdition(644);
        Assertions.assertNotSame(edition, p.getEdition());
    }

    @Test
    void sell() {
        Product p = new Product("Product", 17.0, 45, 600);
        Integer beginAmount = p.getAmount();
        p.sell(3);
        Assertions.assertEquals((Integer) (beginAmount - 3), p.getAmount());
    }
}