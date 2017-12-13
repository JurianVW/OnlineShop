package online_shop.shared;

import online_shop.shop.ShopProduct;

import java.rmi.Remote;
import java.util.List;

public interface IShop extends Remote {
    List<ShopProduct> getShopProducts();
    void orderProducts(List<ShopProduct> shopProducts, Integer accountId, String session);
    Account logIn(String username, String password);
    void logOut(String session);
    Account register(String name, String email, String streetname, String houseNumber, String postalCode, String place);
}
