package onlineshop.Tests;

import onlineshop.shop.ShopProduct;
import onlineshop.shop.ShopProductTable;
import onlineshop.supplier.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class ShopProductTableTest {
    Product p = new Product(1, "Product", 34.56, 100, 300);

    @Test
    void name() {
        String name = "OldName";
        p.setName(name);
        ShopProductTable sp = new ShopProductTable(new ShopProduct(p, 49.0, ""));
        Assertions.assertEquals(name, sp.getName());
        sp.setName("New Name");
        Assertions.assertNotSame(name, sp.getName());
    }

    @Test
    void id() {
        int id = 40;
        p.setId(id);
        ShopProductTable sp = new ShopProductTable(new ShopProduct(p, 49.0, ""));
        Assertions.assertEquals(id, sp.getId());
    }

    @Test
    void purchasePrice() {
        Double price = 30.50;
        p.setPurchasePrice(price);
        ShopProductTable sp = new ShopProductTable(new ShopProduct(p, 49.0, ""));
        Assertions.assertEquals(price, sp.getPurchasePrice());
        sp.setPurchasePrice(67.3);
        Assertions.assertNotSame(price, sp.getPurchasePrice());
    }

    @Test
    void amount() {
        Integer amount = 40;
        p.setAmount(amount);
        ShopProductTable sp = new ShopProductTable(new ShopProduct(p, 49.0, ""));
        Assertions.assertEquals(amount, p.getAmount());
        sp.setAmount(67);
        Assertions.assertNotSame(amount, sp.getAmount());
    }

    @Test
    void edition() {
        Integer edition = 405;
        p.setEdition(edition);
        ShopProductTable sp = new ShopProductTable(new ShopProduct(p, 4.0, ""));
        Assertions.assertEquals(edition, sp.getEdition());
        sp.setEdition(644);
        Assertions.assertNotSame(edition, sp.getEdition());
    }

    @Test
    void price() {
        Double price = 60.0;
        ShopProductTable sp = new ShopProductTable(new ShopProduct(p, price, ""));
        Assertions.assertEquals(price, sp.getPrice());
        sp.setPrice(50.0);
        Assertions.assertNotSame(price, sp.getPrice());
    }

    @Test
    void description() {
        String description = "old";
        ShopProductTable sp = new ShopProductTable(new ShopProduct(p, 40.5, description));
        Assertions.assertTrue(description.equals(sp.getDescription()));
        sp.setDescription("new value");
        Assertions.assertFalse(description.equals(sp.getDescription()));
    }

    @Test
    void getShopProduct() {
        ShopProductTable sp = new ShopProductTable(new ShopProduct(p, 40.5, ""));
        Assertions.assertNotNull(sp.getShopProduct());
    }
}