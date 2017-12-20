package online_shop.client;

import online_shop.shared.Account;
import online_shop.shared.IShopProduct;

import java.util.List;

public class Client {

    private List<IShopProduct> cart;

    public Account logIn(String username, String password) {
        return null;
    }

    public void addToCart(IShopProduct product, int amount) {
        throw new UnsupportedOperationException();
    }

    public void removeFromCart(IShopProduct product, int amount) {
        throw new UnsupportedOperationException();
    }

    public void orderProducts() {
        throw new UnsupportedOperationException();
    }
}
