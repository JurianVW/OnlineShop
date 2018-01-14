package onlineshop.Tests;

import onlineshop.shop.ShopProduct;
import onlineshop.supplier.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ShopProductTest {

    Product p = new Product(1, "Product", 34.56, 100, 300);

    @Test
    void price() {
        Double price = 60.0;
        ShopProduct sp = new ShopProduct(p, price, "");
        Assertions.assertEquals(price, sp.getPrice());
        sp.setPrice(50.0);
        Assertions.assertNotSame(price, sp.getPrice());
    }

    @Test
    void description() {
        String description = "old";
        ShopProduct sp = new ShopProduct(p, 40.5, description);
        Assertions.assertTrue(description.equals(sp.getDescription()));
        sp.setDescription("new value");
        Assertions.assertFalse(description.equals(sp.getDescription()));
    }
}